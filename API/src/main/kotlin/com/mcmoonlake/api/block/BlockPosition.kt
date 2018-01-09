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

package com.mcmoonlake.api.block

import com.mcmoonlake.api.parseInt
import com.mcmoonlake.api.util.ComparisonChain
import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * ## BlockPosition (方块位置)
 *
 * * Stores the coordinates of a block in three-dimensional space.
 * * 存储在三维空间中方块的坐标.
 *
 * @author lgou2w
 * @since 2.0
 * @constructor BlockPosition
 * @param x X coordinate.
 * @param x X 坐标
 * @param y Y coordinate.
 * @param y Y 坐标.
 * @param z Z coordinate.
 * @param z Z 坐标.
 */
data class BlockPosition(
        /**
         * * The X coordinate value of this block.
         * * 此方块的 X 坐标值.
         */
        val x: Int,
        /**
         * * The Y coordinate value of this block.
         * * 此方块的 Y 坐标值.
         */
        val y: Int,
        /**
         * * The Z coordinate value of this block.
         * * 此方块的 Z 坐标值.
         */
        val z: Int

) : ConfigurationSerializable,
        Comparable<BlockPosition> {

    override fun compareTo(other: BlockPosition): Int
            = ComparisonChain.start()
            .compare(x, other.x)
            .compare(y, other.y)
            .compare(z, other.z)
            .result

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("x", x)
        result.put("y", y)
        result.put("z", z)
        return result
    }

    /** static */

    companion object {

        /**
         * * A block position that represents a value of `0` coordinates.
         * * 代表一个 `0` 坐标值的方块位置.
         */
        @JvmField
        val ZERO = BlockPosition(0, 0, 0)

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): BlockPosition {
            val x = args["x"]?.parseInt() ?: 0
            val y = args["y"]?.parseInt() ?: 0
            val z = args["z"]?.parseInt() ?: 0
            return BlockPosition(x, y, z)
        }
    }
}
