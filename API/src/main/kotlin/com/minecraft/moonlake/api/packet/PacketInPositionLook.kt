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

package com.minecraft.moonlake.api.packet

data class PacketInPositionLook(
        var x: Double,
        var y: Double,
        var z: Double,
        var yaw: Float,
        var pitch: Float,
        var isOnGround: Boolean) : PacketInBukkitAbstract("PacketPlayInFlying\$PacketPlayInPositionLook", "PacketPlayInPositionLook") {

    @Deprecated("")
    constructor() : this(.0, .0, .0, 0f, 0f, false)

    override fun read(data: PacketBuffer) {
        this.x = data.readDouble()
        this.y = data.readDouble()
        this.z = data.readDouble()
        this.yaw = data.readFloat()
        this.pitch = data.readFloat()
        this.isOnGround = data.readUnsignedByte().toInt() != 0
    }

    override fun write(data: PacketBuffer) {
        data.writeDouble(x)
        data.writeDouble(y)
        data.writeDouble(z)
        data.writeFloat(yaw)
        data.writeFloat(pitch)
        data.writeByte(if(isOnGround) 1 else 0)
    }
}
