/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.mcmoonlake.api.wrapper

import com.mcmoonlake.api.util.ComparisonChain

data class Coordinate(val x: Int, val y: Int, val z: Int) : Comparable<Coordinate> {

    override fun compareTo(other: Coordinate): Int {
        return ComparisonChain.start()
                .compare(x, other.x)
                .compare(y, other.y)
                .compare(z, other.z)
                .result
    }

    fun to2D() = Coordinate2D(x, y)
}

data class Coordinate3F(val x: Float, val y: Float, val z: Float) : Comparable<Coordinate3F> {

    override fun compareTo(other: Coordinate3F): Int {
        return ComparisonChain.start()
                .compare(x, other.x)
                .compare(y, other.y)
                .compare(z, other.z)
                .result
    }

    fun to2D() = Coordinate2D(x.toInt(), y.toInt())
}

data class Coordinate2D(val x: Int, val y: Int) : Comparable<Coordinate2D> {

    override fun compareTo(other: Coordinate2D): Int {
        return ComparisonChain.start()
                .compare(x, other.x)
                .compare(y, other.y)
                .result
    }

    @JvmOverloads
    fun to3D(z: Int = 0) = Coordinate(x, y, z)

    /**
     * @see [org.bukkit.inventory.Inventory]
     */
    fun toGui() = (y * 9) - (9 - x) - 1

    companion object {
        /**
         * @see [org.bukkit.inventory.Inventory]
         */
        @JvmStatic
        @JvmName("fromGui")
        fun fromGui(index: Int): Coordinate2D {
            val x = (index + 1) % 9
            val y = (index + (9 - x) + 1) / 9
            return Coordinate2D(x, y)
        }
    }
}
