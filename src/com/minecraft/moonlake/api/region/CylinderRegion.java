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

import com.minecraft.moonlake.api.region.iterator.FlatRegion3DIterator;
import com.minecraft.moonlake.api.region.iterator.FlatRegionIterator;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h1>CylinderRegion</h1>
 * 圆柱区域（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Region
 * @see FlatRegion
 */
public class CylinderRegion extends AbstractRegion implements FlatRegion {

    static {

        ConfigurationSerialization.registerClass(CylinderRegion.class);
    }

    private RegionVector2D center;
    private RegionVector2D radius;
    private int minY = 0, maxY = 0;
    private boolean hasY = false;

    /**
     * 圆柱区域构造函数
     *
     * @param world 世界
     * @param center 中心点
     * @param radius 半径
     * @param minY 最小 Y
     * @param maxY 最大 Y
     */
    public CylinderRegion(World world, RegionVector2D center, RegionVector2D radius, int minY, int maxY) {

        super(world);

        this.setCenter(center);
        this.setRadius(radius);
        this.minY = minY;
        this.maxY = maxY;
        this.hasY = true;
    }

    /**
     * 圆柱区域构造函数
     *
     * @param world 世界
     * @param center 中心点
     * @param radius 半径
     */
    public CylinderRegion(World world, RegionVector2D center, RegionVector2D radius) {

        this(world, center, radius, 0, 0);
    }

    /**
     * 圆柱区域构造函数
     *
     * @param world 世界
     * @param center 中心点
     * @param radius 半径
     * @param minY 最小 Y
     * @param maxY 最大 Y
     */
    public CylinderRegion(World world, RegionVector center, RegionVector2D radius, int minY, int maxY) {

        super(world);

        this.setCenter(center.toRegionVector2D());
        this.setRadius(radius);
        this.minY = minY;
        this.maxY = maxY;
        this.hasY = true;
    }

    /**
     * 圆柱区域构造函数
     *
     * @param world 世界
     * @param center 中心点
     * @param radius 半径
     */
    public CylinderRegion(World world, RegionVector center, RegionVector2D radius) {

        this(world, center, radius, 0, 0);
    }

    /**
     * 获取此圆柱区域的最小 Y
     *
     * @return 最小 Y
     */
    @Override
    public int getMinimumY() {

        return minY;
    }

    /**
     * 获取此圆柱区域的最大 Y
     *
     * @return 最大 Y
     */
    @Override
    public int getMaximumY() {

        return maxY;
    }

    /**
     * 设置此圆柱区域的最小 Y
     *
     * @param minY 最小 Y
     */
    public void setMinimumY(int minY) {

        this.minY = minY;
        this.hasY = true;
    }

    /**
     * 设置此圆柱区域的最大 Y
     *
     * @param maxY
     */
    public void setMaximumY(int maxY) {

        this.maxY = maxY;
        this.hasY = true;
    }

    @Override
    public RegionVector getCenter() {

        return center.toRegionVector((maxY + minY) / 2);
    }

    /**
     * 设置此圆柱区域的中心点
     *
     * @param center 中心点
     * @throws IllegalArgumentException 如果中心点对象为 {@code null} 则抛出异常
     */
    public void setCenter(RegionVector2D center) {

        Validate.notNull(center, "The center vector object is null.");

        this.center = center;
    }

    /**
     * 获取此圆柱区域的半径矢量 2D
     *
     * @return 半径矢量 2D
     */
    public RegionVector2D getRadius() {

        return radius.subtract(0.5d, 0.5d);
    }

    /**
     * 设置此圆柱区域的半径
     *
     * @param radius 半径矢量 2D
     * @throws IllegalArgumentException 如果半径矢量2D对象为 {@code null} 则抛出异常
     */
    public void setRadius(RegionVector2D radius) {

        Validate.notNull(radius, "The radius vector object is null.");

        this.radius = radius.add(0.5d, 0.5d);
    }

    /**
     * 设置此圆柱区域的 Y
     *
     * @param y Y
     * @return 是否成功
     */
    public boolean setY(int y) {

        if(!hasY) {

            maxY = minY = y;
            return hasY = true;
        }
        if(y < minY) {

            minY = y;
            return true;
        }
        if(y > maxY) {

            maxY = y;
            return true;
        }
        return false;
    }

    @Override
    public RegionVector getMinimumPoint() {

        return center.subtract(getRadius()).toRegionVector(minY);
    }

    @Override
    public RegionVector getMaximumPoint() {

        return center.add(getRadius()).toRegionVector(maxY);
    }

    @Override
    public int getArea() {

        return (int) Math.floor(radius.getX() * radius.getZ() * Math.PI * getHeight());
    }

    @Override
    public int getWidth() {

        return (int) (radius.getX() * 2d);
    }

    @Override
    public int getHeight() {

        return maxY - minY + 1;
    }

    @Override
    public int getLength() {

        return (int) (radius.getZ() * 2d);
    }

    @Override
    public CylinderRegion clone() {

        return (CylinderRegion) super.clone();
    }

    @Override
    public boolean contains(RegionVector vector) {

        if(vector == null) return false;

        int blockY = vector.getBlockY();

        if(blockY < minY || blockY > maxY)
            return false;

        return vector.toRegionVector2D().subtract(center).divide(radius).lengthSq() <= 1d;
    }

    @Override
    public Iterator<RegionBlockVector> iterator() {

        return new FlatRegion3DIterator(this);
    }

    @Override
    public Iterable<RegionVector2D> asFlatRegion() {

        return new Iterable<RegionVector2D>() {

            @Override
            public Iterator<RegionVector2D> iterator() {

                return new FlatRegionIterator(CylinderRegion.this);
            }
        };
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("world", world.getName());
        result.put("center", center.serialize());
        result.put("radius", radius.serialize());
        result.put("minY", minY);
        result.put("maxY", maxY);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static CylinderRegion deserialize(Map<String, Object> args) {

        World world = Bukkit.getServer().getWorld((String) args.get("world"));

        if(world == null)
            throw new IllegalArgumentException("unknown world");

        RegionVector2D center = RegionVector2D.deserialize((Map<String, Object>) args.get("center"));
        RegionVector2D radius = RegionVector2D.deserialize((Map<String, Object>) args.get("radius"));
        int minY = StringUtil.parseInt(args.get("minY"));
        int maxY = StringUtil.parseInt(args.get("maxY"));
        return new CylinderRegion(world, center, radius, minY, maxY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CylinderRegion that = (CylinderRegion) o;

        if (minY != that.minY) return false;
        if (maxY != that.maxY) return false;
        if (hasY != that.hasY) return false;
        if (center != null ? !center.equals(that.center) : that.center != null) return false;
        return radius != null ? radius.equals(that.radius) : that.radius == null;
    }

    @Override
    public int hashCode() {
        int result = center != null ? center.hashCode() : 0;
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + minY;
        result = 31 * result + maxY;
        result = 31 * result + (hasY ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CylinderRegion{" +
                "world=" + world +
                ", center=" + center +
                ", radius=" + radius +
                ", minY=" + minY +
                ", maxY=" + maxY +
                ", hasY=" + hasY +
                '}';
    }
}
