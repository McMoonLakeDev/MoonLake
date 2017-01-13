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

public class RegionBlockVector extends RegionVector {

    public final static RegionBlockVector ZERO = new RegionBlockVector(0d, 0d, 0d);

    public RegionBlockVector(double x, double y, double z) {

        super(x, y, z);
    }

    public RegionBlockVector(float x, float y, float z) {

        super(x, y, z);
    }

    public RegionBlockVector(int x, int y, int z) {

        super(x, y, z);
    }

    public RegionBlockVector(RegionVector vector) {

        super(vector);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof RegionVector))
            return false;
        RegionVector vector = (RegionVector) o;
        return ((int) vector.x == x) && ((int) vector.y == y) && ((int) vector.z == z);
    }

    @Override
    public int hashCode() {
        return (int) x << 19  ^ (int) y << 12 ^ (int) z;
    }

    @Override
    public String toString() {
        return "RegionBlockVector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
