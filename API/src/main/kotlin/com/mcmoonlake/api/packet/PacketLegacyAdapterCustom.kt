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

abstract class PacketLegacyAdapterCustom<P: PacketBukkitLegacy, T>
    : PacketLegacyAdapter<P, T>() where T: PacketBukkitLegacy, T: PacketLegacy {

    abstract val packetName: String

    abstract val packetLegacyName: String

    val resultName: String
        get() = if(!isLegacy) packetName else packetLegacyName

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + packetName.hashCode()
        result = 31 * result + packetLegacyName.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketLegacyAdapterCustom<*, *>)
            return super.equals(other) && packetName == other.packetName && packetLegacyName == other.packetLegacyName
        return false
    }

    override fun toString(): String {
        return "PacketLegacyAdapterCustom(packet=$packet, packetName='$packetName', packetLegacy=$packetLegacy, packetLegacyName='$packetLegacyName', isLegacy=$isLegacy)"
    }
}
