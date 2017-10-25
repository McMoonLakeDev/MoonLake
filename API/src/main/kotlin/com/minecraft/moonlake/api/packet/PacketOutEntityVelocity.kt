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

data class PacketOutEntityVelocity(
        var entityId: Int,
        var velocityX: Int,
        var velocityY: Int,
        var velocityZ: Int) : PacketOutBukkitAbstract("PacketPlayOutEntityVelocity") {

    @Deprecated("")
    constructor() : this(-1, 0, 0, 0)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        velocityX = data.readShort().toInt()
        velocityY = data.readShort().toInt()
        velocityZ = data.readShort().toInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeShort(velocityX)
        data.writeShort(velocityY)
        data.writeShort(velocityZ)
    }
}
