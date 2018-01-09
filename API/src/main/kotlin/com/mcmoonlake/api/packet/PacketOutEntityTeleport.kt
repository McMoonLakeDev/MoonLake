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

import com.mcmoonlake.api.isCombatOrLaterVer

data class PacketOutEntityTeleport(
        var entityId: Int,
        var x: Double,
        var y: Double,
        var z: Double,
        var yaw: Float,
        var pitch: Float,
        var isOnGround: Boolean
) : PacketOutBukkitAbstract("PacketPlayOutEntityTeleport") {

    @Deprecated("")
    constructor() : this(-1, .0, .0, .0, 0f, 0f, false)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        x = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        y = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        z = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        yaw = data.readByte() / 256f * 360f
        pitch = data.readByte() / 256f * 360f
        isOnGround = data.readBoolean()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        if(!isCombatOrLaterVer) data.writeInt(Math.floor(x * 32.0).toInt()) else data.writeDouble(x)
        if(!isCombatOrLaterVer) data.writeInt(Math.floor(y * 32.0).toInt()) else data.writeDouble(y)
        if(!isCombatOrLaterVer) data.writeInt(Math.floor(z * 32.0).toInt()) else data.writeDouble(z)
        data.writeByte((yaw * 256f / 360f).toInt())
        data.writeByte((pitch * 256f / 360f).toInt())
        data.writeBoolean(isOnGround)
    }
}
