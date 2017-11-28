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

data class PacketOutScore(
        var name: String,
        var objective: String,
        var action: Action,
        var value: Int?) : PacketOutBukkitAbstract("PacketPlayOutScoreboardScore") {

    @Deprecated("")
    constructor() : this("Scoreboard", "Objective", Action.CHANGE, 0)

    override fun read(data: PacketBuffer) {
        name = data.readString()
        action = ofValuableNotNull(data.readByte().toInt())
        objective = data.readString()
        if(action != Action.REMOVE)
            value = data.readVarInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeString(name)
        data.writeByte(action.value())
        data.writeString(objective)
        if(action != Action.REMOVE)
            data.writeVarInt(value.notNull())
    }

    enum class Action(val value: Int) : Valuable<Int> {

        CHANGE(0),
        REMOVE(1),
        ;

        override fun value(): Int
                = value
    }
}
