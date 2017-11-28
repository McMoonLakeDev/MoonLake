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
import com.mcmoonlake.api.ofValuableNotNull

data class PacketInClientStatus(
        var status: Status) : PacketInBukkitAbstract("PacketPlayInClientCommand") {

    @Deprecated("")
    constructor() : this(Status.PERFORM_RESPAWN)

    override fun read(data: PacketBuffer) {
        status = ofValuableNotNull(data.readVarInt())
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(status.value())
    }

    enum class Status : Valuable<Int> {

        PERFORM_RESPAWN,
        REQUEST_STATS,
        ;

        override fun value(): Int
                = ordinal
    }
}
