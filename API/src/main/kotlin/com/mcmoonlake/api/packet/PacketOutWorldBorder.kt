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

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.util.Enums
import java.io.IOException

data class PacketOutWorldBorder(
        var action: Action,
        var size: Double,
        var oldSize: Double,
        var newSize: Double,
        var speed: Long,
        var centerX: Double,
        var centerZ: Double,
        var portalTeleportBoundary: Int,
        var warningTime: Int,
        var warningBlocks: Int) : PacketOutBukkitAbstract("PacketPlayOutWorldBorder") {

    @Deprecated("")
    constructor() : this(Action.INITIALIZE, .0, .0, .0, 0L, .0, .0, 0, 0, 0)

    override fun read(data: PacketBuffer) {
        action = Enums.ofValuable(Action::class.java, data.readVarInt()) ?: throw IOException("Unknown Action Type.")
        when(action) {
            Action.SET_SIZE -> size = data.readDouble()
            Action.LERP_SIZE -> {
                oldSize = data.readDouble()
                newSize = data.readDouble()
                speed = data.readVarLong()
            }
            Action.SET_CENTER -> {
                centerX = data.readDouble()
                centerZ = data.readDouble()
            }
            Action.INITIALIZE -> {
                centerX = data.readDouble()
                centerZ = data.readDouble()
                oldSize = data.readDouble()
                newSize = data.readDouble()
                speed = data.readVarLong()
                portalTeleportBoundary = data.readVarInt()
                warningTime = data.readVarInt()
                warningBlocks = data.readVarInt()
            }
            Action.SET_WARNING_TIME -> warningTime = data.readVarInt()
            Action.SET_WARNING_BLOCKS -> warningBlocks = data.readVarInt()
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.value())
        when(action) {
            Action.SET_SIZE -> data.writeDouble(size)
            Action.LERP_SIZE -> {
                data.writeDouble(oldSize)
                data.writeDouble(newSize)
                data.writeVarLong(speed)
            }
            Action.SET_CENTER -> {
                data.writeDouble(centerX)
                data.writeDouble(centerZ)
            }
            Action.INITIALIZE -> {
                data.writeDouble(centerX)
                data.writeDouble(centerZ)
                data.writeDouble(oldSize)
                data.writeDouble(newSize)
                data.writeVarLong(speed)
                data.writeVarInt(portalTeleportBoundary)
                data.writeVarInt(warningTime)
                data.writeVarInt(warningBlocks)
            }
            Action.SET_WARNING_TIME -> data.writeVarInt(warningTime)
            Action.SET_WARNING_BLOCKS -> data.writeVarInt(warningBlocks)
        }
    }

    enum class Action(val value: Int) : Valuable<Int> {

        SET_SIZE(0),
        LERP_SIZE(1),
        SET_CENTER(2),
        INITIALIZE(3),
        SET_WARNING_TIME(4),
        SET_WARNING_BLOCKS(5),
        ;

        override fun value(): Int
                = value
    }
}
