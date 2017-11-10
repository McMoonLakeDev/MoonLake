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
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatComponentText
import com.mcmoonlake.api.util.Enums
import com.mcmoonlake.api.wrapper.BarColor
import com.mcmoonlake.api.wrapper.BarStyle
import java.io.IOException
import java.util.*

data class PacketOutBossBar(
        var uuid: UUID,
        var action: Action,
        var name: ChatComponent,
        var health: Float,
        var color: BarColor,
        var style: BarStyle,
        var darkenSky: Boolean,
        var playEndBossMusic: Boolean,
        var createFog: Boolean) : PacketOutBukkitAbstract("PacketPlayOutBoss"), PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(UUID.randomUUID(), Action.ADD, ChatComponentText(), 1f, BarColor.PINK, BarStyle.PROGRESS, false, false, false)

    override fun read(data: PacketBuffer) {
        uuid = data.readUUID()
        action = Enums.ofValuable(Action::class.java, data.readVarInt()) ?: throw IOException("Unknown Action Type.")
        when(action) {
            Action.ADD -> {
                name = data.readChatComponent()
                health = data.readFloat()
                color = Enums.ofValuable(BarColor::class.java, data.readVarInt()) ?: BarColor.PINK
                style = Enums.ofValuable(BarStyle::class.java, data.readVarInt()) ?: BarStyle.PROGRESS
                setFlags(data.readUnsignedByte().toInt())
            }
            Action.REMOVE -> {}
            Action.UPDATE_PCT -> health = data.readFloat()
            Action.UPDATE_NAME -> name = data.readChatComponent()
            Action.UPDATE_STYLE -> {
                color = Enums.ofValuable(BarColor::class.java, data.readVarInt()) ?: BarColor.PINK
                style = Enums.ofValuable(BarStyle::class.java, data.readVarInt()) ?: BarStyle.PROGRESS
            }
            Action.UPDATE_PROPERTIES -> setFlags(data.readUnsignedByte().toInt())
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeUUID(uuid)
        data.writeVarInt(action.value())
        when(action) {
            Action.ADD -> {
                data.writeChatComponent(name)
                data.writeFloat(health)
                data.writeVarInt(color.value())
                data.writeVarInt(style.value())
                data.writeByte(getFlags())
            }
            Action.REMOVE -> {}
            Action.UPDATE_PCT -> data.writeFloat(health)
            Action.UPDATE_NAME -> data.writeChatComponent(name)
            Action.UPDATE_STYLE -> {
                data.writeVarInt(color.value())
                data.writeVarInt(style.value())
            }
            Action.UPDATE_PROPERTIES -> data.writeByte(getFlags())
        }
    }

    private fun setFlags(flag: Int) {
        darkenSky = (flag and 1) > 0
        playEndBossMusic = (flag and 2) > 0
        createFog = (flag and 2) > 0
    }

    private fun getFlags(): Int {
        var flag = 0
        if(darkenSky)
            flag = flag or 1
        if(playEndBossMusic)
            flag = flag or 2
        if(createFog)
            flag = flag or 2
        return flag
    }

    enum class Action(val value: Int) : Valuable<Int> {

        ADD(0),
        REMOVE(1),
        UPDATE_PCT(2),
        UPDATE_NAME(3),
        UPDATE_STYLE(4),
        UPDATE_PROPERTIES(5),
        ;

        override fun value(): Int
                = value
    }
}
