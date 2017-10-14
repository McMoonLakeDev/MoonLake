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

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.isCombatOrLaterVer

data class PacketOutPosition(
        var x: Double,
        var y: Double,
        var z: Double,
        var yaw: Float,
        var pitch: Float,
        /**
         * Only valid in version 1.9 or later.
         */
        var teleportId: Int,
        var teleportFlags: MutableSet<Flag>) : PacketOutBukkitAbstract("PacketPlayOutPosition") {

    constructor(x: Double, y: Double, z: Double, yaw: Float, pitch: Float) : this(x, y, z, yaw, pitch, -1, HashSet())
    constructor(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, teleportFlags: MutableSet<Flag>) : this(x, y, z, yaw, pitch, -1, teleportFlags)

    @Deprecated("")
    constructor() : this(.0, .0, .0, 0f, 0f, -1, HashSet())

    override fun read(data: PacketBuffer) {
        x = data.readDouble()
        y = data.readDouble()
        z = data.readDouble()
        yaw = data.readFloat()
        pitch = data.readFloat()
        val flag = data.readUnsignedByte().toInt()
        Flag.values().forEach {
            val value = 1 shl it.value
            if(flag and value == value)
                teleportFlags.add(it)
        }
        if(isCombatOrLaterVer)
            teleportId = data.readVarInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeDouble(x)
        data.writeDouble(y)
        data.writeDouble(z)
        data.writeFloat(yaw)
        data.writeFloat(pitch)
        var flag = 0
        teleportFlags.forEach { flag = flag or (1 shl it.value) }
        data.writeByte(flag)
        if(isCombatOrLaterVer)
            data.writeVarInt(teleportId)
    }

    enum class Flag(var value: Int) : Valuable<Int> {

        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4),
        ;

        override fun value(): Int
                = value
    }
}
