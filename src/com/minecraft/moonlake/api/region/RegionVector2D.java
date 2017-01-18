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

import com.minecraft.moonlake.api.VectorConvertible;
import com.minecraft.moonlake.api.player.depend.WorldEditVector;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.util.Vector;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h1>RegionVector2D</h1>
 * 区域矢量 2D（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class RegionVector2D implements VectorConvertible, ConfigurationSerializable, Comparable<RegionVector2D> {

    /**
     * 区域矢量 2D ZERO 值
     */
    public final static RegionVector2D ZERO = new RegionVector2D(0d, 0d);

    static {

        ConfigurationSerialization.registerClass(RegionVector2D.class);
    }

    protected final double x;
    protected final double z;

    /**
     * 区域矢量 2D 构造函数
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D(double x, double z) {

        this.x = x;
        this.z = z;
    }

    /**
     * 区域矢量 2D 构造函数
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D(float x, float z) {

        this.x = x;
        this.z = z;
    }

    /**
     * 区域矢量 2D 构造函数
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D(int x, int z) {

        this.x = x;
        this.z = z;
    }

    /**
     * 区域矢量 2D 构造函数
     *
     * @param other 区域矢量 2D
     * @throws IllegalArgumentException 如果区域矢量 2D 对象为 {@code null} 则抛出异常
     */
    public RegionVector2D(RegionVector2D other) {

        Validate.notNull(other, "The other vector object is null.");

        this.x = other.x;
        this.z = other.z;
    }

    /**
     * 区域矢量 2D 构造函数
     */
    public RegionVector2D() {

        this.x = 0d;
        this.z = 0d;
    }

    /**
     * 获取此区域矢量 2D 的 X
     *
     * @return X
     */
    public double getX() {

        return x;
    }

    /**
     * 获取此区域矢量 2D 的 Z
     *
     * @return Z
     */
    public double getZ() {

        return z;
    }

    /**
     * 获取此区域矢量 2D 的方块 X
     *
     * @return 方块 X
     */
    public int getBlockX() {

        return (int) Math.round(x);
    }

    /**
     * 获取此区域矢量 2D 的方块 Z
     *
     * @return 方块 Z
     */
    public int getBlockZ() {

        return (int) Math.round(z);
    }

    /**
     * 设置此区域矢量 2D 的 X
     *
     * @param x X
     */
    public RegionVector2D setX(double x) {

        return new RegionVector2D(x, z);
    }

    /**
     * 设置此区域矢量 2D 的 Z
     *
     * @param z Z
     */
    public RegionVector2D setZ(double z) {

        return new RegionVector2D(x, z);
    }

    /**
     * 设置此区域矢量 2D 的 X
     *
     * @param x X
     */
    public RegionVector2D setX(int x) {

        return new RegionVector2D(x, z);
    }

    /**
     * 设置此区域矢量 2D 的 Z
     *
     * @param z Z
     */
    public RegionVector2D setZ(int z) {

        return new RegionVector2D(x, z);
    }

    /**
     * 将指定区域矢量 2D 和此区域矢量 2D 相加
     *
     * @param vector 区域矢量 2D
     */
    public RegionVector2D add(RegionVector2D vector) {

        return new RegionVector2D(x + vector.x, z + vector.z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相加
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D add(double x, double z) {

        return new RegionVector2D(this.x + x, this.z + z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相加
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D add(int x, int z) {

        return new RegionVector2D(this.x + x,  this.z + z);
    }

    /**
     * 将指定区域矢量 2D 和此区域矢量 2D 相减
     *
     * @param vector 区域矢量 2D
     */
    public RegionVector2D subtract(RegionVector2D vector) {

        return new RegionVector2D(x - vector.x, z - vector.z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相减
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D subtract(double x, double z) {

        return new RegionVector2D(this.x - x, this.z - z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相减
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D subtract(int x, int z) {

        return new RegionVector2D(this.x - x, this.z - z);
    }

    /**
     * 将指定区域矢量 2D 和此区域矢量 2D 相乘
     *
     * @param vector 区域矢量 2D
     */
    public RegionVector2D multiply(RegionVector2D vector) {

        return new RegionVector2D(x * vector.x, z * vector.z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相乘
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D multiply(double x, double z) {

        return new RegionVector2D(this.x * x, this.z * z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相乘
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D multiply(int x, int z) {

        return new RegionVector2D(this.x * x, this.z * z);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相乘
     *
     * @param n N
     */
    public RegionVector2D multiply(double n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相乘
     *
     * @param n N
     */
    public RegionVector2D multiply(float n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相乘
     *
     * @param n N
     */
    public RegionVector2D multiply(int n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    /**
     * 将指定区域矢量 2D 和此区域矢量 2D 相除
     *
     * @param vector 区域矢量 2D
     */
    public RegionVector2D divide(RegionVector2D vector) {

        return new RegionVector2D(x / vector.x, z / vector.z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相除
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D divide(double x, double z) {

        return new RegionVector2D(this.x / x, this.z / z);
    }

    /**
     * 将指定 X Z 和此区域矢量 2D 相除
     *
     * @param x X
     * @param z Z
     */
    public RegionVector2D divide(int x, int z) {

        return new RegionVector2D(this.x / x, this.z / z);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相除
     *
     * @param n N
     */
    public RegionVector2D divide(double n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相除
     *
     * @param n N
     */
    public RegionVector2D divide(float n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    /**
     * 将指定 N 和此区域矢量 2D 相除
     *
     * @param n N
     */
    public RegionVector2D divide(int n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    /**
     * 获取此区域矢量 2D 的长度
     *
     * @return 长度
     */
    public double length() {

        return Math.sqrt(lengthSq());
    }

    /**
     * 获取此区域矢量 2D 的长度平方
     *
     * @return 长度平方
     */
    public double lengthSq() {

        return x * x + z * z;
    }

    /**
     * 获取指定区域矢量 2D 和此区域矢量 2D 的距离
     *
     * @param vector 区域矢量 2D
     * @return 距离
     */
    public double distance(RegionVector2D vector) {

        return Math.sqrt(distanceSq(vector));
    }

    /**
     * 获取指定区域矢量 2D 和此区域矢量 2D 的距离平方
     *
     * @param vector 区域矢量 2D
     * @return 距离平方
     */
    public double distanceSq(RegionVector2D vector) {

        return Math.pow(x - vector.x, 2d) + Math.pow(z - vector.z, 2d);
    }

    /**
     * 获取此区域矢量 2D 的归一化
     *
     * @return 使归一化的区域矢量 2D
     */
    public RegionVector2D normalize() {

        return divide(length());
    }

    /**
     * 将此区域矢量 2D 转换为区域矢量
     *
     * @return 区域矢量
     */
    public RegionVector toRegionVector() {

        return toRegionVector(0d);
    }

    /**
     * 将此区域矢量 2D 转换为区域矢量
     *
     * @param y Y
     * @return 区域矢量
     */
    public RegionVector toRegionVector(double y) {

        return new RegionVector(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionVector2D that = (RegionVector2D) o;

        if (Double.compare(that.x, x) != 0) return false;
        return Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RegionVector2D{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    @Override
    public int compareTo(RegionVector2D o) {

        if(x != o.x)
            return Double.compare(x, o.x);
        if(z != o.z)
            return Double.compare(z, o.z);
        return 0;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("x", x);
        result.put("z", z);
        return result;
    }

    public static RegionVector2D deserialize(Map<String, Object> args) {

        double x = StringUtil.parseDouble(args.get("x"));
        double z = StringUtil.parseDouble(args.get("z"));
        return new RegionVector2D(x, z);
    }

    @Override
    public Vector asBukkitVector() {

        return new Vector(x, 0d, z);
    }

    @Override
    public WorldEditVector asWorldEditVector() {

        return new WorldEditVector(x, 0d, z);
    }

    @Override
    public RegionVector asRegionVector() {

        return new RegionVector(x, 0d, z);
    }

    @Override
    public RegionBlockVector asRegionBlockVector() {

        return new RegionBlockVector(x, 0d, z);
    }
}
