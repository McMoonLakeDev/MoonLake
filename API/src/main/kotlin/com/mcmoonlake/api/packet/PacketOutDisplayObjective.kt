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


data class PacketOutDisplayObjective(
        var slot: DisplaySlot,
        var name: String
) : PacketOutBukkitAbstract("PacketPlayOutScoreboardDisplayObjective") {

    @Deprecated("")
    constructor() : this(DisplaySlot.SIDEBAR, "Scoreboard")

    override fun read(data: PacketBuffer) {
        slot = ofValuableNotNull(data.readByte().toInt())
        name = data.readString()
    }

    override fun write(data: PacketBuffer) {
        data.writeByte(slot.value())
        data.writeString(name)
    }

    enum class DisplaySlot(val value: Int) : Valuable<Int> {

        PLAYER_LIST(0),
        SIDEBAR(1),
        BELOW_NAME(2),
        ;

        override fun value(): Int
                = value
    }
}
