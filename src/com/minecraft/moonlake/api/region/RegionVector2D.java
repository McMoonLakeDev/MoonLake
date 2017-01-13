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

import com.minecraft.moonlake.util.StringUtil;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegionVector2D implements ConfigurationSerializable, Comparable<RegionVector2D> {

    public final static RegionVector2D ZERO = new RegionVector2D(0d, 0d);

    static {

        ConfigurationSerialization.registerClass(RegionVector2D.class);
    }

    protected final double x;
    protected final double z;

    public RegionVector2D(double x, double z) {

        this.x = x;
        this.z = z;
    }

    public RegionVector2D(float x, float z) {

        this.x = x;
        this.z = z;
    }

    public RegionVector2D(int x, int z) {

        this.x = x;
        this.z = z;
    }

    public RegionVector2D(RegionVector2D other) {

        this.x = other.x;
        this.z = other.z;
    }

    public RegionVector2D() {

        this.x = 0d;
        this.z = 0d;
    }

    public double getX() {

        return x;
    }

    public double getZ() {

        return z;
    }

    public int getBlockX() {

        return (int) Math.round(x);
    }

    public int getBlockZ() {

        return (int) Math.round(z);
    }

    public RegionVector2D setX(double x) {

        return new RegionVector2D(x, z);
    }

    public RegionVector2D setZ(double z) {

        return new RegionVector2D(x, z);
    }

    public RegionVector2D setX(int x) {

        return new RegionVector2D(x, z);
    }

    public RegionVector2D setZ(int z) {

        return new RegionVector2D(x, z);
    }

    public RegionVector2D add(RegionVector2D vector) {

        return new RegionVector2D(x + vector.x, z + vector.z);
    }

    public RegionVector2D add(double x, double z) {

        return new RegionVector2D(this.x + x, this.z + z);
    }

    public RegionVector2D add(int x, int z) {

        return new RegionVector2D(this.x + x,  this.z + z);
    }

    public RegionVector2D subtract(RegionVector2D vector) {

        return new RegionVector2D(x - vector.x, z - vector.z);
    }

    public RegionVector2D subtract(double x, double z) {

        return new RegionVector2D(this.x - x, this.z - z);
    }

    public RegionVector2D subtract(int x, int z) {

        return new RegionVector2D(this.x - x, this.z - z);
    }

    public RegionVector2D multiply(RegionVector2D vector) {

        return new RegionVector2D(x * vector.x, z * vector.z);
    }

    public RegionVector2D multiply(double x, double z) {

        return new RegionVector2D(this.x * x, this.z * z);
    }

    public RegionVector2D multiply(int x, int z) {

        return new RegionVector2D(this.x * x, this.z * z);
    }

    public RegionVector2D multiply(double n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    public RegionVector2D multiply(float n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    public RegionVector2D multiply(int n) {

        return new RegionVector2D(this.x * n, this.z * n);
    }

    public RegionVector2D divide(RegionVector2D vector) {

        return new RegionVector2D(x / vector.x, z / vector.z);
    }

    public RegionVector2D divide(double x, double z) {

        return new RegionVector2D(this.x / x, this.z / z);
    }

    public RegionVector2D divide(int x, int z) {

        return new RegionVector2D(this.x / x, this.z / z);
    }

    public RegionVector2D divide(double n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    public RegionVector2D divide(float n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    public RegionVector2D divide(int n) {

        return new RegionVector2D(this.x / n, this.z / n);
    }

    public double length() {

        return Math.sqrt(lengthSq());
    }

    public double lengthSq() {

        return x * x + z * z;
    }

    public double distance(RegionVector2D vector) {

        return Math.sqrt(distanceSq(vector));
    }

    public double distanceSq(RegionVector2D vector) {

        return Math.pow(x - vector.x, 2d) + Math.pow(z - vector.z, 2d);
    }

    public RegionVector2D normalize() {

        return divide(length());
    }

    public RegionVector toRegionVector() {

        return toRegionVector(0d);
    }

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
}
