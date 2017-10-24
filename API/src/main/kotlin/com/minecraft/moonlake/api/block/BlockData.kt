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

package com.minecraft.moonlake.api.block

import com.minecraft.moonlake.api.util.ComparisonChain

data class BlockData(val type: Int, val data: Int) : Comparable<BlockData> {

    override fun compareTo(other: BlockData): Int {
        return ComparisonChain.start()
                .compare(type, other.type)
                .compare(data, other.data)
                .result
    }

    val toId: Int
            = toId(this)

    companion object {

        @JvmField
        val AIR = BlockData(0, 0)

        @JvmStatic
        @JvmName("fromId")
        fun fromId(id: Int): BlockData
                = BlockData(id shr 4, id and 15)

        @JvmStatic
        @JvmName("toId")
        fun toId(data: BlockData): Int
                = (data.type shl 4) or (data.data and 15)
    }
}
