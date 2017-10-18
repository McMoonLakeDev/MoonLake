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
import com.minecraft.moonlake.api.player.PlayerInfo
import com.minecraft.moonlake.api.util.Enums
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.GameMode
import java.util.*

data class PacketOutPlayerInfo(var action: Action, var info: MutableList<PlayerInfo>) : PacketOutBukkitAbstract("PacketPlayOutPlayerInfo") {

    @Deprecated("")
    constructor() : this(Action.REMOVE_PLAYER, ArrayList())

    override fun read(data: PacketBuffer) { // TODO v1.13
        action = Enums.ofValuable(Action::class.java, data.readVarInt()) ?: Action.REMOVE_PLAYER
        val length = data.readVarInt()
        (0 until length).forEach {
            val id = data.readUUID()
            val profile = if(action == Action.ADD_PLAYER) GameProfile(id, data.readString()) else GameProfile(id, null)
            val playerInfo: PlayerInfo = when(action) {
                Action.ADD_PLAYER -> {
                    val properties = data.readVarInt()
                    (0 until properties).forEach {
                        val propertyName = data.readString()
                        profile.properties.put(propertyName, Property(propertyName, data.readString(), if(data.readBoolean()) data.readString() else null))
                    }
                    val mode = GameMode.getByValue(data.readVarInt())
                    val latency = data.readVarInt()
                    PlayerInfo(profile, if (data.readBoolean()) data.readChatComponent() else null, mode, latency)
                }
                Action.UPDATE_GAME_MODE -> PlayerInfo(profile, null, GameMode.getByValue(data.readVarInt()), 0)
                Action.UPDATE_LATENCY -> PlayerInfo(profile, null, null, data.readVarInt())
                Action.UPDATE_DISPLAY_NAME -> PlayerInfo(profile, if (data.readBoolean()) data.readChatComponent() else null, null, 0)
                Action.REMOVE_PLAYER -> PlayerInfo(profile, null, null, 0)
            }
            info.add(playerInfo)
        }
    }

    override fun write(data: PacketBuffer) { // TODO v1.13
        data.writeVarInt(action.value())
        data.writeVarInt(info.size)
        for(playerInfo in info) {
            val profile = playerInfo.profile
            data.writeUUID(profile.id ?: UUID.randomUUID())
            when(action) {
                Action.ADD_PLAYER -> {
                    data.writeString(profile.name ?: "<null>")
                    data.writeVarInt(profile.properties.size())
                    for(property in profile.properties.values()) {
                        data.writeString(property.name)
                        data.writeString(property.value)
                        data.writeBoolean(property.hasSignature())
                        if(property.hasSignature())
                            data.writeString(property.signature)
                    }
                    data.writeVarInt(playerInfo.mode?.value ?: 0)
                    data.writeVarInt(playerInfo.latency)
                    data.writeBoolean(playerInfo.displayName != null)
                    if(playerInfo.displayName != null)
                        data.writeChatComponent(playerInfo.displayName)
                }
                Action.UPDATE_GAME_MODE -> data.writeVarInt(playerInfo.mode?.value ?: 0)
                Action.UPDATE_LATENCY -> data.writeVarInt(playerInfo.latency)
                Action.UPDATE_DISPLAY_NAME -> {
                    data.writeBoolean(playerInfo.displayName != null)
                    if(playerInfo.displayName != null)
                        data.writeChatComponent(playerInfo.displayName)
                }
                Action.REMOVE_PLAYER -> { }
            }
        }
    }

    enum class Action(val value: Int) : Valuable<Int> {

        /**
         * PlayerInfo Action: Add Player (玩家信息交互: 添加玩家)
         */
        ADD_PLAYER(0),
        /**
         * PlayerInfo Action: Update Game Mode (玩家信息交互: 更新游戏模式)
         */
        UPDATE_GAME_MODE(1),
        /**
         * PlayerInfo Action: Update Latency (玩家信息交互: 更新延迟)
         */
        UPDATE_LATENCY(2),
        /**
         * PlayerInfo Action: Update Display Name (玩家信息交互: 更新显示名称)
         */
        UPDATE_DISPLAY_NAME(3),
        /**
         * PlayerInfo Action: Remove Player (玩家信息交互: 移除玩家)
         */
        REMOVE_PLAYER(4),
        ;

        override fun value(): Int
                = value
    }
}
