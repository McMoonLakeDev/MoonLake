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

data class PacketInHandshake(
        var version: Int,
        var host: String,
        var port: Int,
        var nextState: Int) : PacketInBukkitAbstract("PacketHandshakingInSetProtocol"), PacketHandshaking {

    @Deprecated("")
    constructor() : this(-1, "127.0.0.1", 25565, -1)

    override fun read(data: PacketBuffer) {
        version = data.readVarInt()
        host = data.readString()
        port = data.readUnsignedShort()
        nextState = data.readVarInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(version)
        data.writeString(host)
        data.writeShort(port)
        data.writeVarInt(nextState)
    }
}
