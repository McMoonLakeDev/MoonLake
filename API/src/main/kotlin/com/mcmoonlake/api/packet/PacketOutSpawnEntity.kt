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
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.util.clamp
import java.util.*

data class PacketOutSpawnEntity(
        var entityId: Int,
        var type: Int,
        var x: Double,
        var y: Double,
        var z: Double,
        var yaw: Float,
        var pitch: Float,
        var velocityX: Int,
        var velocityY: Int,
        var velocityZ: Int,
        var data: Int,
        /**
         * * Valid only in version 1.9 or later.
         * * 仅在 1.9 或更高版本有效.
         */
        var uuid: UUID?) : PacketOutBukkitAbstract("PacketPlayOutSpawnEntity") {

    @Deprecated("")
    constructor() : this(-1, -1, .0, .0, .0, 0f, 0f, -1, 0, 0, 0, null)

    init {
        velocityX = (clamp(velocityX.toDouble(), -3.9, 3.9) * 8000.0).toInt()
        velocityY = (clamp(velocityY.toDouble(), -3.9, 3.9) * 8000.0).toInt()
        velocityZ = (clamp(velocityZ.toDouble(), -3.9, 3.9) * 8000.0).toInt()
    }

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        if(isCombatOrLaterVer)
            uuid = data.readUUID()
        type = data.readByte().toInt()
        x = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        y = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        z = if(!isCombatOrLaterVer) data.readInt() / 32.0 else data.readDouble()
        yaw = data.readByte() / 256f * 360f
        pitch = data.readByte() / 256f * 360f
        this.data = data.readInt()
        if(isCombatOrLaterVer || (!isCombatOrLaterVer && this.data > 0)) {
            velocityX = data.readShort().toInt()
            velocityY = data.readShort().toInt()
            velocityZ = data.readShort().toInt()
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        if(isCombatOrLaterVer)
            data.writeUUID(uuid.notNull())
        data.writeByte(type)
        if(!isCombatOrLaterVer) data.writeInt((x * 32.0).toInt()) else data.writeDouble(x)
        if(!isCombatOrLaterVer) data.writeInt((y * 32.0).toInt()) else data.writeDouble(y)
        if(!isCombatOrLaterVer) data.writeInt((z * 32.0).toInt()) else data.writeDouble(z)
        data.writeByte((yaw * 256f / 360f).toInt())
        data.writeByte((pitch * 256f / 360f).toInt())
        data.writeInt(this.data)
        if(isCombatOrLaterVer || (!isCombatOrLaterVer && this.data > 0)) {
            data.writeShort(velocityX)
            data.writeShort(velocityY)
            data.writeShort(velocityZ)
        }
    }
}
