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

package com.mcmoonlake.api.packet

import com.mcmoonlake.api.block.BlockPosition
import java.util.*

data class PacketInUpdateSign(
        var blockPosition: BlockPosition,
        var lines: Array<String>) : PacketInBukkitAbstract("PacketPlayInUpdateSign") {

    @Deprecated("")
    constructor() : this(BlockPosition.ZERO, arrayOf())

    override fun read(data: PacketBuffer) {
        blockPosition = data.readBlockPosition()
        lines = Array(4, { "" })
        (0 until 4).forEach { lines[it] = data.readString() }
    }

    override fun write(data: PacketBuffer) {
        data.writeBlockPosition(blockPosition)
        (0 until 4).forEach { data.writeString(lines[it]) }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + blockPosition.hashCode()
        result = 31 * result + Arrays.hashCode(lines)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketInUpdateSign)
            return super.equals(other) && blockPosition == other.blockPosition && Arrays.equals(lines, other.lines)
        return false
    }
}
