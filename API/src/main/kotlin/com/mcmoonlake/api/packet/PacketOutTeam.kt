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
import com.mcmoonlake.api.isCombatOrLaterVer
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.ofValuableNotNull

data class PacketOutTeam(
        var name: String,
        var action: Action,
        var displayName: String?,
        var prefix: String?,
        var suffix: String?,
        var nameTagVisibility: String?,
        var color: Int?,
        var players: MutableCollection<String>?,
        var friendlyFlags: Int?,
        /**
         * * Collision rules, Valid only in version 1.9 or later.
         * * 碰撞规则, 仅在 1.9 或更高版本有效.
         */
        var collisionRule: String?

) : PacketOutBukkitAbstract("PacketPlayOutScoreboardTeam") {

    @Deprecated("")
    constructor() : this("Team", Action.REMOVE, null, null, null, null, null, null, null, null)

    override fun read(data: PacketBuffer) {
        name = data.readString()
        action = ofValuableNotNull(data.readByte().toInt())
        if(action == Action.CREATE || action == Action.UPDATE) {
            displayName = data.readString()
            prefix = data.readString()
            suffix = data.readString()
            friendlyFlags = data.readByte().toInt()
            nameTagVisibility = data.readString()
            if(isCombatOrLaterVer)
                collisionRule = data.readString()
            color = data.readByte().toInt()
        }
        if(action == Action.CREATE || action == Action.ADD_PLAYER || action == Action.REMOVE_PLAYER) {
            val playerSize = data.readVarInt()
            players = ArrayList(playerSize)
            (0 until playerSize).forEach { players?.add(data.readString()) }
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeString(name)
        data.writeByte(action.value())
        if(action == Action.CREATE || action == Action.UPDATE) {
            data.writeString(displayName.notNull())
            data.writeString(prefix.notNull())
            data.writeString(suffix.notNull())
            data.writeByte(friendlyFlags.notNull())
            data.writeString(nameTagVisibility.notNull())
            if(isCombatOrLaterVer)
                data.writeString(collisionRule.notNull())
            data.writeByte(color.notNull())
        }
        if(action == Action.CREATE || action == Action.ADD_PLAYER || action == Action.REMOVE_PLAYER) {
            data.writeVarInt(players?.size.notNull())
            players.notNull().forEach { data.writeString(it) }
        }
    }

    enum class Action(
            val value: Int
    ) : Valuable<Int> {

        CREATE(0),
        REMOVE(1),
        UPDATE(2),
        ADD_PLAYER(3),
        REMOVE_PLAYER(4),
        ;

        override fun value(): Int
                = value
    }
}
