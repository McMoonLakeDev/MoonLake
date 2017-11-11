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

data class PacketInSpectate(
        var target: UUID) : PacketInBukkitAbstract("PacketPlayInSpectate") {

    @Deprecated("")
    constructor() : this(UUID.randomUUID())

    override fun read(data: PacketBuffer) {
        target = data.readUUID()
    }

    override fun write(data: PacketBuffer) {
        data.writeUUID(target)
    }
}
