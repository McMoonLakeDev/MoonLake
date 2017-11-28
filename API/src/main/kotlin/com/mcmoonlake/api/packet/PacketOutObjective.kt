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
import com.mcmoonlake.api.ofValuable

data class PacketOutObjective(
        var name: String,
        var mode: Int,
        var value: String?,
        var type: Type?) : PacketOutBukkitAbstract("PacketPlayOutScoreboardObjective") {

    @Deprecated("")
    constructor() : this("Scoreboard", -1, null, null)

    override fun read(data: PacketBuffer) {
        name = data.readString()
        mode = data.readByte().toInt()
        if(mode == 0 || mode == 2) {
            value = data.readString()
            type = ofValuable(data.readString())
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeString(name)
        data.writeByte(mode)
        if(mode == 0 || mode == 2) {
            data.writeString(value.notNull())
            data.writeString(type?.value().notNull())
        }
    }

    enum class Type(val value: String) : Valuable<String> {

        INTEGER("integer"),
        HEARTS("hearts"),
        ;

        override fun value(): String
                = value
    }
}
