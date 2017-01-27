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

/**
 * <h1>RegionBlockVector</h1>
 * 区域方块矢量（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see RegionVector
 */
public class RegionBlockVector extends RegionVector {

    /**
     * 区域方块矢量 ZERO 值
     */
    public final static RegionBlockVector ZERO = new RegionBlockVector(0d, 0d, 0d);

    /**
     * 区域方块矢量构造函数
     *
     * @param x X
     * @param y Y
     * @param z Z
     */
    public RegionBlockVector(double x, double y, double z) {

        super(x, y, z);
    }

    /**
     * 区域方块矢量构造函数
     *
     * @param x X
     * @param y Y
     * @param z Z
     */
    public RegionBlockVector(float x, float y, float z) {

        super(x, y, z);
    }

    /**
     * 区域方块矢量构造函数
     *
     * @param x X
     * @param y Y
     * @param z Z
     */
    public RegionBlockVector(int x, int y, int z) {

        super(x, y, z);
    }

    /**
     * 区域方块矢量构造函数
     *
     * @param vector 区域矢量
     * @throws IllegalArgumentException 如果区域矢量对象为 {@code null} 则抛出异常
     */
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
