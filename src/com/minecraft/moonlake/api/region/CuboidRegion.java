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

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CuboidRegion extends AbstractRegion implements FlatRegion {

    static {

        ConfigurationSerialization.registerClass(CuboidRegion.class);
    }

    private RegionVector pos1;
    private RegionVector pos2;

    public CuboidRegion(World world, RegionVector pos1, RegionVector pos2) {

        super(world);

        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public CuboidRegion(World world, Location pos1, Location pos2) {

        super(world);

        Validate.isTrue(pos1.getWorld().equals(world), "The pos1 location world not equals argument world object.");
        Validate.isTrue(pos2.getWorld().equals(world), "The pos2 location world not equals argument world object.");

        this.pos1 = new RegionVector(pos1.getX(), pos1.getY(), pos1.getZ());
        this.pos2 = new RegionVector(pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public RegionVector getPos1() {

        return pos1;
    }

    public void setPos1(RegionVector pos1) {

        this.pos1 = pos1;
    }

    public RegionVector getPos2() {

        return pos2;
    }

    public void setPos2(RegionVector pos2) {

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

        Validate.notNull(vector, "The vector object is null.");

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
