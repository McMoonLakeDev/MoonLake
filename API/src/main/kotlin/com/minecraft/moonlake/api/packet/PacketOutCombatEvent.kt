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

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatComponentText
import com.minecraft.moonlake.api.util.Enums

data class PacketOutCombatEvent(
        var event: Event,
        var playerId: Int,
        var entityId: Int,
        var duration: Int,
        var deathMessage: ChatComponent?) : PacketOutBukkitAbstract("PacketPlayOutCombatEvent") {

    @Deprecated("")
    constructor() : this(Event.ENTER_COMBAT, -1, -1, -1, null)

    override fun read(data: PacketBuffer) {
        event = Enums.ofValuable(Event::class.java, data.readVarInt()) ?: Event.ENTER_COMBAT
        if(event == Event.END_COMBAT) {
            duration = data.readVarInt()
            entityId = data.readInt()
        } else if(event == Event.ENTITY_DIED) {
            playerId = data.readVarInt()
            entityId = data.readInt()
            deathMessage = data.readChatComponent()
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(event.value())
        if(event == Event.END_COMBAT) {
            data.writeVarInt(duration)
            data.writeInt(entityId)
        } else if(event == Event.ENTITY_DIED) {
            data.writeVarInt(playerId)
            data.writeInt(entityId)
            data.writeChatComponent(deathMessage ?: ChatComponentText())
        }
    }

    enum class Event(val value: Int) : Valuable<Int> {

        /**
         * Combat Event Type: Enter Combat (战斗事件类型: 进入战斗)
         */
        ENTER_COMBAT(0),
        /**
         * Combat Event Type: End Combat (战斗事件类型: 结束战斗)
         */
        END_COMBAT(1),
        /**
         * Combat Event Type: Entity Died (战斗事件类型: 实体死亡)
         */
        ENTITY_DIED(2),
        ;

        override fun value(): Int
                = value
    }
}
