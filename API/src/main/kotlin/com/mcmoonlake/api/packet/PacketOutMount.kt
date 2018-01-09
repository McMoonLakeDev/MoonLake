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

import java.util.*

data class PacketOutMount(
        var entityId: Int,
        var passengers: IntArray
) : PacketOutBukkitAbstract("PacketPlayOutMount"),
        PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(-1, intArrayOf())

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        passengers = IntArray(data.readVarInt())
        (0 until passengers.size).forEach { passengers[it] = data.readVarInt() }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeVarInt(passengers.size)
        (0 until passengers.size).forEach { data.writeVarInt(passengers[it]) }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = result * 31 + entityId.hashCode()
        result = result * 31 + Arrays.hashCode(passengers)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketOutMount)
            return super.equals(other) && entityId == other.entityId && Arrays.equals(passengers, other.passengers)
        return false
    }
}
