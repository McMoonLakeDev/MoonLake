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
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.util.Vector;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegionVector implements VectorConvertible,  ConfigurationSerializable, Comparable<RegionVector> {

    public final static RegionVector ZERO = new RegionVector(0d, 0d, 0d);

    static {

        ConfigurationSerialization.registerClass(RegionVector.class);
    }

    protected final double x;
    protected final double y;
    protected final double z;

    public RegionVector(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RegionVector(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RegionVector(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RegionVector(RegionVector other) {

        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public RegionVector() {

        this.x = 0d;
        this.y = 0d;
        this.z = 0d;
    }

    public double getX() {

        return x;
    }

    public double getY() {

        return y;
    }

    public double getZ() {

        return z;
    }

    public int getBlockX() {

        return (int) Math.round(x);
    }

    public int getBlockY() {

        return (int) Math.round(y);
    }

    public int getBlockZ() {

        return (int) Math.round(z);
    }

    public RegionVector setX(double x) {

        return new RegionVector(x, y, z);
    }

    public RegionVector setY(double y) {

        return new RegionVector(x, y, z);
    }

    public RegionVector setZ(double z) {

        return new RegionVector(x, y, z);
    }

    public RegionVector setX(int x) {

        return new RegionVector(x, y, z);
    }

    public RegionVector setY(int y) {

        return new RegionVector(x, y, z);
    }

    public RegionVector setZ(int z) {

        return new RegionVector(x, y, z);
    }

    public RegionVector add(RegionVector vector) {

        return new RegionVector(x + vector.x, y + vector.y, z + vector.z);
    }

    public RegionVector add(double x, double y, double z) {

        return new RegionVector(this.x + x, this.y + y, this.z + z);
    }

    public RegionVector add(int x, int y, int z) {

        return new RegionVector(this.x + x, this.y + y, this.z + z);
    }

    public RegionVector subtract(RegionVector vector) {

        return new RegionVector(x - vector.x, y - vector.y, z - vector.z);
    }

    public RegionVector subtract(double x, double y, double z) {

        return new RegionVector(this.x - x, this.y - y, this.z - z);
    }

    public RegionVector subtract(int x, int y, int z) {

        return new RegionVector(this.x - x, this.y - y, this.z - z);
    }

    public RegionVector multiply(RegionVector vector) {

        return new RegionVector(x * vector.x, y * vector.y, z * vector.z);
    }

    public RegionVector multiply(double x, double y, double z) {

        return new RegionVector(this.x * x, this.y * y, this.z * z);
    }

    public RegionVector multiply(int x, int y, int z) {

        return new RegionVector(this.x * x, this.y * y, this.z * z);
    }

    public RegionVector multiply(double n) {

        return new RegionVector(this.x * n, this.y * n, this.z * n);
    }

    public RegionVector multiply(float n) {

        return new RegionVector(this.x * n, this.y * n, this.z * n);
    }

    public RegionVector multiply(int n) {

        return new RegionVector(this.x * n, this.y * n, this.z * n);
    }

    public RegionVector divide(RegionVector vector) {

        return new RegionVector(x / vector.x, y / vector.y, z / vector.z);
    }

    public RegionVector divide(double x, double y, double z) {

        return new RegionVector(this.x / x, this.y / y, this.z / z);
    }

    public RegionVector divide(int x, int y, int z) {

        return new RegionVector(this.x / x, this.y / y, this.z / z);
    }

    public RegionVector divide(double n) {

        return new RegionVector(this.x / n, this.y / n, this.z / n);
    }

    public RegionVector divide(float n) {

        return new RegionVector(this.x / n, this.y / n, this.z / n);
    }

    public RegionVector divide(int n) {

        return new RegionVector(this.x / n, this.y / n, this.z / n);
    }

    public double length() {

        return Math.sqrt(lengthSq());
    }

    public double lengthSq() {

        return x * x + y * y + z * z;
    }

    public double distance(RegionVector vector) {

        return Math.sqrt(distanceSq(vector));
    }

    public double distanceSq(RegionVector vector) {

        return Math.pow(x - vector.x, 2d) + Math.pow(y - vector.y, 2d) + Math.pow(z - vector.z, 2d);
    }

    public RegionVector normalize() {

        return divide(length());
    }

    public RegionVector2D toRegionVector2D() {

        return new RegionVector2D(x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionVector that = (RegionVector) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        return Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RegionVector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int compareTo(RegionVector o) {

        if(x != o.x)
            return Double.compare(x, o.x);
        if(y != o.y)
            return Double.compare(y, o.y);
        if(z != o.z)
            return Double.compare(z, o.z);
        return 0;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        return result;
    }

    public static RegionVector deserialize(Map<String, Object> args) {

        double x = StringUtil.parseDouble(args.get("x"));
        double y = StringUtil.parseDouble(args.get("y"));
        double z = StringUtil.parseDouble(args.get("z"));
        return new RegionVector(x, y, z);
    }

    @Override
    public Vector asBukkitVector() {

        return new Vector(x, y, z);
    }

    @Override
    public WorldEditVector asWorldEditVector() {

        return new WorldEditVector(x, y, z);
    }

    @Override
    public RegionVector asRegionVector() {

        return new RegionVector(this);
    }

    @Override
    public RegionBlockVector asRegionBlockVector() {

        return new RegionBlockVector(this);
    }
}
