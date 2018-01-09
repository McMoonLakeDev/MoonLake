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

data class PacketInEntityAction(
        var entityId: Int,
        var action: Action,
        var jumpBoost: Int = 0
) : PacketInBukkitAbstract("PacketPlayInEntityAction") {

    @Deprecated("")
    constructor() : this(-1, Action.STOP_SNEAKING, 0)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        action = ofValuableNotNull(data.readVarInt())
        jumpBoost = data.readVarInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeVarInt(action.value())
        data.writeVarInt(jumpBoost)
    }

    enum class Action : Valuable<Int> {

        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        /**
         * Only Minecraft 1.9+
         */
        START_FALL_FLYING,
        ;

        override fun value(): Int
                = ordinal
    }
}
