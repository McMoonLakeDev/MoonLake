/*
 * Copyright (C) 2017 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.api.region;

import com.google.common.collect.Iterators;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <h1>CuboidRegion</h1>
 * 长方体区域（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Region
 * @see FlatRegion
 */
public class CuboidRegion extends AbstractRegion implements FlatRegion {

    static {

        ConfigurationSerialization.registerClass(CuboidRegion.class);
    }

    private RegionVector pos1;
    private RegionVector pos2;

    /**
     * 长方体区域构造函数
     *
     * @param world 世界
     * @param pos1 点 1
     * @param pos2 点 2
     */
    public CuboidRegion(World world, RegionVector pos1, RegionVector pos2) {

        super(world);

        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    /**
     * 长方体区域构造函数
     *
     * @param world 世界
     * @param pos1 点 1
     * @param pos2 点 2
     * @throws IllegalArgumentException 如果点1的世界对象不符合参数世界则抛出异常
     * @throws IllegalArgumentException 如果点2的世界对象不符合参数世界则抛出异常
     */
    public CuboidRegion(World world, Location pos1, Location pos2) {

        super(world);

        Validate.isTrue(pos1.getWorld().equals(world), "The pos1 location world not equals argument world object.");
        Validate.isTrue(pos2.getWorld().equals(world), "The pos2 location world not equals argument world object.");

        this.pos1 = new RegionVector(pos1.getX(), pos1.getY(), pos1.getZ());
        this.pos2 = new RegionVector(pos2.getX(), pos2.getY(), pos2.getZ());
    }

    /**
     * 获取此长方体区域的点 1 矢量
     *
     * @return 点 1 矢量
     */
    public RegionVector getPos1() {

        return pos1;
    }

    /**
     * 设置此长方体区域的点 1 矢量
     *
     * @param pos1 点 1 矢量
     * @throws IllegalArgumentException 如果点1矢量对象为 {@code null} 则抛出异常
     */
    public void setPos1(RegionVector pos1) {

        Validate.notNull(pos1, "The pos1 vector object is null.");

        this.pos1 = pos1;
    }

    /**
     * 获取此长方体区域的点 2 矢量
     *
     * @return 点 2 矢量
     */
    public RegionVector getPos2() {

        return pos2;
    }

    /**
     * 设置此长方体区域的点 2 矢量
     *
     * @param pos2 点 2 矢量
     * @throws IllegalArgumentException 如果点2矢量对象为 {@code null} 则抛出异常
     */
    public void setPos2(RegionVector pos2) {

        Validate.notNull(pos2, "The pos2 vector object is null.");

        this.pos2 = pos2;
    }

    @Override
    public RegionVector getMinimumPoint() {

        return new RegionVector(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
    }

    @Override
    public RegionVector getMaximumPoint() {

        return new RegionVector(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
    }

    @Override
    public boolean contains(RegionVector vector) {

        if(vector == null) return false;

        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        RegionVector min = getMinimumPoint();
        RegionVector max = getMaximumPoint();

        return x >= min.getBlockX() && x <= max.getBlockX() && y >= min.getBlockY() && y <= max.getBlockY() && z >= min.getBlockZ() && z <= max.getBlockZ();
    }

    @Override
    public int getMinimumY() {

        return Math.min(pos1.getBlockY(), pos2.getBlockY());
    }

    @Override
    public int getMaximumY() {

        return Math.max(pos1.getBlockY(), pos2.getBlockY());
    }

    @Override
    public Iterator<RegionBlockVector> iterator() {

        return new Iterator<RegionBlockVector>() {

            private RegionVector min = getMinimumPoint();
            private RegionVector max = getMaximumPoint();
            private int nextX = min.getBlockX();
            private int nextY = min.getBlockY();
            private int nextZ = min.getBlockZ();

            @Override
            public boolean hasNext() {

                return nextX != 0x7fffffff;
            }

            @Override
            public RegionBlockVector next() {

                if(!hasNext())
                    throw new NoSuchElementException();

                RegionBlockVector answer = new RegionBlockVector(nextX, nextY, nextZ);

                if(++nextX > max.getBlockX()) {

                    nextX = min.getBlockX();

                    if(++nextY > max.getBlockY()) {

                        nextY = min.getBlockY();

                        if(++nextZ > max.getBlockZ()) {

                            nextX = 0x7fffffff;
                        }
                    }
                }
                return answer;
            }

            @Override
            public void remove() {

                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public CuboidRegion clone() {

        return (CuboidRegion) super.clone();
    }

    @Override
    public Iterable<RegionVector2D> asFlatRegion() {

        return new Iterable<RegionVector2D>() {

            @Override
            public Iterator<RegionVector2D> iterator() {

                return new Iterator<RegionVector2D>() {

                    private RegionVector min = getMinimumPoint();
                    private RegionVector max = getMinimumPoint();
                    private int nextX = min.getBlockX();
                    private int nextZ = min.getBlockZ();

                    @Override
                    public boolean hasNext() {

                        return nextX != 0x7fffffff;
                    }

                    @Override
                    public RegionVector2D next() {

                        if(!hasNext())
                            throw new NoSuchElementException();

                        RegionVector2D answer = new RegionVector2D(nextX, nextZ);

                        if(++nextX > max.getBlockX()) {

                            nextX = min.getBlockX();

                            if(++nextZ > max.getBlockZ()) {

                                nextX = 0x7fffffff;
                            }
                        }
                        return answer;
                    }

                    @Override
                    public void remove() {

                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    /**
     * 获取此长方体区域的所有面的迭代区域方块矢量
     *
     * @return 迭代区域方块矢量
     */
    public Iterable<RegionBlockVector> getFaces() {

        return new Iterable<RegionBlockVector>() {

            private RegionVector min = getMinimumPoint();
            private RegionVector max = getMaximumPoint();
            private Region[] faceRegions = {

                    new CuboidRegion(world, pos1.setX(min.getX()), pos2.setX(min.getX())),
                    new CuboidRegion(world, pos1.setX(max.getX()), pos2.setX(max.getX())),
                    new CuboidRegion(world, pos1.setZ(min.getZ()), pos2.setZ(min.getZ())),
                    new CuboidRegion(world, pos1.setZ(max.getZ()), pos2.setZ(max.getZ())),
                    new CuboidRegion(world, pos1.setY(min.getY()), pos2.setY(min.getY())),
                    new CuboidRegion(world, pos1.setY(max.getY()), pos2.setY(max.getY()))
            };

            @Override
            public Iterator<RegionBlockVector> iterator() {

                Iterator<RegionBlockVector>[] iterators = new Iterator[faceRegions.length];

                for(int i = 0; i < faceRegions.length; i++)
                    iterators[i] = faceRegions[i].iterator();

                return Iterators.concat(iterators);
            }
        };
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("world", world.getName());
        result.put("pos1", pos1.serialize());
        result.put("pos2", pos2.serialize());
        return result;
    }

    @SuppressWarnings("unchecked")
    public static CuboidRegion deserialize(Map<String, Object> args) {

        World world = Bukkit.getServer().getWorld((String) args.get("world"));

        if(world == null)
            throw new IllegalArgumentException("unknown world");

        RegionVector pos1 = RegionVector.deserialize((Map<String, Object>) args.get("pos1"));
        RegionVector pos2 = RegionVector.deserialize((Map<String, Object>) args.get("pos2"));
        return new CuboidRegion(world, pos1, pos2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CuboidRegion that = (CuboidRegion) o;

        if (pos1 != null ? !pos1.equals(that.pos1) : that.pos1 != null) return false;
        return pos2 != null ? pos2.equals(that.pos2) : that.pos2 == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pos1 != null ? pos1.hashCode() : 0);
        result = 31 * result + (pos2 != null ? pos2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CuboidRegion{" +
                "world=" + world +
                ", pos1=" + pos1 +
                ", pos2=" + pos2 +
                '}';
    }
}
