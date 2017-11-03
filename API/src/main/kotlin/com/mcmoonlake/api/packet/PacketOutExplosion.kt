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
import com.mcmoonlake.api.orFalse

data class PacketOutExplosion(
        var x: Double,
        var y: Double,
        var z: Double,
        var radius: Float,
        var affectedPositions: MutableList<BlockPosition>?,
        var velocityX: Float,
        var velocityY: Float,
        var velocityZ: Float) : PacketOutBukkitAbstract("PacketPlayOutExplosion") {

    @Deprecated("")
    constructor() : this(.0, .0, .0, 0f, null, 0f, 0f, 0f)

    override fun read(data: PacketBuffer) {
        x = data.readFloat().toDouble()
        y = data.readFloat().toDouble()
        z = data.readFloat().toDouble()
        radius = data.readFloat()
        val size = data.readInt()
        if(size > 0) {
            affectedPositions = ArrayList(size)
            val offsetX = x.toInt()
            val offsetY = y.toInt()
            val offsetZ = z.toInt()
            (0 until size).forEach {
                val blockX = data.readByte() + offsetX
                val blockY = data.readByte() + offsetY
                val blockZ = data.readByte() + offsetZ
                affectedPositions?.add(BlockPosition(blockX, blockY, blockZ))
            }
        }
        velocityX = data.readFloat()
        velocityY = data.readFloat()
        velocityZ = data.readFloat()
    }

    override fun write(data: PacketBuffer) {
        data.writeFloat(x.toFloat())
        data.writeFloat(y.toFloat())
        data.writeFloat(z.toFloat())
        data.writeFloat(radius)
        data.writeInt(affectedPositions?.size ?: 0)
        if(affectedPositions?.isNotEmpty().orFalse()) {
            val offsetX = x.toInt()
            val offsetY = y.toInt()
            val offsetZ = z.toInt()
            affectedPositions?.forEach {
                data.writeByte(it.x - offsetX)
                data.writeByte(it.y - offsetY)
                data.writeByte(it.z - offsetZ)
            }
        }
        data.writeFloat(velocityX)
        data.writeFloat(velocityY)
        data.writeFloat(velocityZ)
    }
}
