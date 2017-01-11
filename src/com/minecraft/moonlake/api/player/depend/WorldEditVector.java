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


package com.minecraft.moonlake.api.player.depend;

public class WorldEditVector implements Comparable<WorldEditVector> {

    public final static WorldEditVector ZERO = new WorldEditVector(0d, 0d, 0d);

    protected final double x;
    protected final double y;
    protected final double z;

    public WorldEditVector(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WorldEditVector(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WorldEditVector(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WorldEditVector(WorldEditVector other) {

        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public WorldEditVector() {

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

    public WorldEditVector setX(double x) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector setY(double y) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector setZ(double z) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector setX(int x) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector setY(int y) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector setZ(int z) {

        return new WorldEditVector(x, y, z);
    }

    public WorldEditVector add(WorldEditVector vector) {

        return new WorldEditVector(x + vector.x, y + vector.y, z + vector.z);
    }

    public WorldEditVector add(double x, double y, double z) {

        return new WorldEditVector(this.x + x, this.y + y, this.z + z);
    }

    public WorldEditVector add(int x, int y, int z) {

        return new WorldEditVector(this.x + x, this.y + y, this.z + z);
    }

    public WorldEditVector subtract(WorldEditVector vector) {

        return new WorldEditVector(x - vector.x, y - vector.y, z - vector.z);
    }

    public WorldEditVector subtract(double x, double y, double z) {

        return new WorldEditVector(this.x - x, this.y - y, this.z - z);
    }

    public WorldEditVector subtract(int x, int y, int z) {

        return new WorldEditVector(this.x - x, this.y - y, this.z - z);
    }

    public WorldEditVector multiply(WorldEditVector vector) {

        return new WorldEditVector(x * vector.x, y * vector.y, z * vector.z);
    }

    public WorldEditVector multiply(double x, double y, double z) {

        return new WorldEditVector(this.x * x, this.y * y, this.z * z);
    }

    public WorldEditVector multiply(int x, int y, int z) {

        return new WorldEditVector(this.x * x, this.y * y, this.z * z);
    }

    public WorldEditVector multiply(double n) {

        return new WorldEditVector(this.x * n, this.y * n, this.z * n);
    }

    public WorldEditVector multiply(float n) {

        return new WorldEditVector(this.x * n, this.y * n, this.z * n);
    }

    public WorldEditVector multiply(int n) {

        return new WorldEditVector(this.x * n, this.y * n, this.z * n);
    }

    public WorldEditVector divide(WorldEditVector vector) {

        return new WorldEditVector(x / vector.x, y / vector.y, z / vector.z);
    }

    public WorldEditVector divide(double x, double y, double z) {

        return new WorldEditVector(this.x / x, this.y / y, this.z / z);
    }

    public WorldEditVector divide(int x, int y, int z) {

        return new WorldEditVector(this.x / x, this.y / y, this.z / z);
    }

    public WorldEditVector divide(double n) {

        return new WorldEditVector(this.x / n, this.y / n, this.z / n);
    }

    public WorldEditVector divide(float n) {

        return new WorldEditVector(this.x / n, this.y / n, this.z / n);
    }

    public WorldEditVector divide(int n) {

        return new WorldEditVector(this.x / n, this.y / n, this.z / n);
    }

    public double length() {

        return Math.sqrt(lengthSq());
    }

    public double lengthSq() {

        return x * x + y * y + z * z;
    }

    public double distance(WorldEditVector vector) {

        return Math.sqrt(distanceSq(vector));
    }

    public double distanceSq(WorldEditVector vector) {

        return Math.pow(x - vector.x, 2d) + Math.pow(y - vector.y, 2d) + Math.pow(z - vector.z, 2d);
    }

    public WorldEditVector normalize() {

        return divide(length());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorldEditVector that = (WorldEditVector) o;

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
        return "WorldEditVector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int compareTo(WorldEditVector o) {

        if(x != o.x)
            return Double.compare(x, o.x);
        if(y != o.y)
            return Double.compare(y, o.y);
        if(z != o.z)
            return Double.compare(z, o.z);
        return 0;
    }
}
