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

abstract class PacketLegacyAdapter<P: PacketBukkitLegacy, T> where T: PacketBukkitLegacy, T: PacketLegacy {

    abstract val packet: Class<P>

    abstract val packetLegacy: Class<T>

    abstract val isLegacy: Boolean

    val result: Class<out PacketBukkitLegacy>
        get() = if(!isLegacy) packet else packetLegacy

    override fun hashCode(): Int {
        var result = packet.hashCode()
        result = 31 * result + packetLegacy.hashCode()
        result = 31 * result + isLegacy.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketLegacyAdapter<*, *>)
            return packet == other.packet && packetLegacy == other.packetLegacy && isLegacy == other.isLegacy
        return false
    }

    override fun toString(): String {
        return "PacketLegacyAdapter(packet=$packet, packetLegacy=$packetLegacy, isLegacy=$isLegacy)"
    }
}
