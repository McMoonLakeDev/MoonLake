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
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.ofValuableNotNull

data class PacketInAdvancements(
        var action: Action,
        var key: String?
) : PacketOutBukkitAbstract("PacketPlayInAdvancements"),
        PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(Action.CLOSED_SCREEN, null)

    override fun read(data: PacketBuffer) {
        action = ofValuableNotNull(data.readVarInt())
        if(action == Action.OPENED_TAB)
            key = data.readString()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.value())
        if(action == Action.OPENED_TAB)
            data.writeString(key.notNull())
    }

    enum class Action : Valuable<Int> {

        OPENED_TAB,
        CLOSED_SCREEN,
        ;

        override fun value(): Int
                = ordinal
    }
}
