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

import com.mcmoonlake.api.packet.PacketOutBlockChange
import com.mcmoonlake.api.util.ComparisonChain

/**
 * ## BlockData (方块数据)
 *
 * @author lgou2w
 * @since 2.0
 * @constructor BlockData
 * @param type Type id of this block data.
 * @param type 此方块数据的类型 Id
 * @param data Data value of this block.
 * @param data 此方块数据的数据值.
 */
data class BlockData(
        /**
         * * The type id of this block data.
         * * 此方块数据的类型 Id.
         */
        val type: Int,
        /**
         * * The data value of this block data.
         * * 此方块数据的数据值.
         */
        val data: Int) : Comparable<BlockData> {

    override fun compareTo(other: BlockData): Int {
        return ComparisonChain.start()
                .compare(type, other.type)
                .compare(data, other.data)
                .result
    }

    /**
     * * Get the block data for the id of the packet.
     * * 获取此方块数据用于数据包的 Id.
     *
     * @see [PacketOutBlockChange]
     */
    fun toId(): Int
            = toId(this)

    companion object {

        /**
         * * Air Block Data.
         * * 空气方块数据.
         */
        @JvmField
        val AIR = BlockData(0, 0)

        /**
         * * Get the block data from the given id.
         * * 从给定的 Id 获取方块数据.
         *
         * @param id Id
         */
        @JvmStatic
        @JvmName("fromId")
        fun fromId(id: Int): BlockData
                = BlockData(id shr 4, id and 15)

        /**
         * * Get id from given block data.
         * * 从给定的方块数据获取 Id.
         *
         * @param data Block data.
         * @param data 方块数据.
         */
        @JvmStatic
        @JvmName("toId")
        fun toId(data: BlockData): Int
                = (data.type shl 4) or (data.data and 15)
    }
}
