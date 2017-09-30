/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

data class PacketInPayload(var channel: String, var data: PacketBuffer) : PacketInBukkitAbstract("PacketPlayInCustomPayload") {

    constructor() : this("MoonLake", PacketBuffer())

    override fun read(data: PacketBuffer) {
        channel = data.readString()
        val bytes = data.readableBytes()
        if(bytes < 0 || bytes > 32767)
            throw IllegalArgumentException("自定义数据不能小于 0 或大于 32767 字节长度.")
        this.data = PacketBuffer(data.readBytes(bytes))
    }

    override fun write(data: PacketBuffer) {
        data.writeString(channel)
        data.writeBytes(this.data)
    }
}
