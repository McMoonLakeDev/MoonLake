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
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h1>EllipsoidRegion</h1>
 * 椭圆区域（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Region
 */
public class EllipsoidRegion extends AbstractRegion {

    static {

        ConfigurationSerialization.registerClass(EllipsoidRegion.class);
    }

    private RegionVector center;
    private RegionVector radius;

    /**
     * 椭圆区域构造函数
     *
     * @param world 世界
     * @param center 中心点
     * @param radius 半径
     */
    public EllipsoidRegion(World world, RegionVector center, RegionVector radius) {

        super(world);

        this.setCenter(center);
        this.setRadius(radius);
    }

    @Override
    public RegionVector getMinimumPoint() {

        return center.subtract(getRadius());
    }

    @Override
    public RegionVector getMaximumPoint() {

        return center.add(getRadius());
    }

    @Override
    public boolean contains(RegionVector vector) {

        return vector != null && vector.subtract(center).divide(radius).lengthSq() <= 1d;
    }

    @Override
    public int getArea() {

        return (int) Math.floor(4.1887902047863905d * radius.getX() * radius.getY() * radius.getZ());
    }

    @Override
    public int getWidth() {

        return (int) (radius.getX() * 2d);
    }

    @Override
    public int getHeight() {

        return (int) (radius.getY() * 2d);
    }

    @Override
    public int getLength() {

        return (int) (radius.getZ() * 2d);
    }

    @Override
    public RegionVector getCenter() {

        return center;
    }

    @Override
    public EllipsoidRegion clone() {

        return (EllipsoidRegion) super.clone();
    }

    /**
     * 设置此椭圆区域的中心点
     *
     * @param center 中心点
     * @throws IllegalArgumentException 如果中心点对象为 {@code null} 则抛出异常
     */
    public void setCenter(RegionVector center) {

        Validate.notNull(center, "The center vector object is null.");

        this.center = center;
    }

    /**
     * 获取此椭圆区域的半径矢量
     *
     * @return 半径矢量
     */
    public RegionVector getRadius() {

        return radius.subtract(0.5d, 0.5d, 0.5d);
    }

    /**
     * 设置此椭圆区域的半径矢量
     *
     * @param radius 半径矢量
     * @throws IllegalArgumentException 如果半径矢量对象为 {@code null} 则抛出异常
     */
    public void setRadius(RegionVector radius) {

        Validate.notNull(radius, "The radius vector object is null.");

        this.radius = radius.add(0.5d, 0.5d, 0.5d);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("world", world.getName());
        result.put("center", center.serialize());
        result.put("radius", radius.serialize());
        return result;
    }

    @SuppressWarnings("unchecked")
    public static EllipsoidRegion deserialize(Map<String, Object> args) {

        World world = Bukkit.getServer().getWorld((String) args.get("world"));

        if(world == null)
            throw new IllegalArgumentException("unknown world");

        RegionVector center = RegionVector.deserialize((Map<String, Object>) args.get("center"));
        RegionVector radius = RegionVector.deserialize((Map<String, Object>) args.get("radius"));
        return new EllipsoidRegion(world, center, radius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EllipsoidRegion that = (EllipsoidRegion) o;

        if (!center.equals(that.center)) return false;
        return radius.equals(that.radius);
    }

    @Override
    public int hashCode() {
        int result = center.hashCode();
        result = 31 * result + radius.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EllipsoidRegion{" +
                "world=" + world +
                ", center=" + center +
                ", radius=" + radius +
                '}';
    }
}
