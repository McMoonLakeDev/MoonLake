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

package com.mcmoonlake.api.utility

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.entity.Entities
import com.mcmoonlake.api.gui.Containers
import com.mcmoonlake.api.reflect.accessor.AccessorField
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mojang.authlib.GameProfile
import io.netty.channel.Channel
import org.bukkit.entity.Player

enum class MinecraftPlayerMembers(
        val clazz: Class<*>
) : Valuable<String> {

    /**
     * Minecraft Entity Player Field Member: Ping (实体玩家字段成员: 延迟)
     */
    PING(Int::class.java) {
        override fun value(): String
                = "ping"
    },
    /**
     * Minecraft Entity Player Field Member: Locale (实体玩家字段成员: 本地化)
     */
    LOCALE(String::class.java) {
        override fun value(): String
                = "locale"
    },
    /**
     * Minecraft Entity Player Field Member: Connection (实体玩家字段成员: 连接)
     */
    CONNECTION(MinecraftReflection.playerConnectionClass) {
        override fun value(): String
                = "playerConnection"

        override fun get(): () -> AccessorField {
            return try {
                super.get()
            } catch(e: NoSuchFieldException) {
                { Accessors.getAccessorField(MinecraftReflection.entityPlayerClass, clazz, true) }
            }
        }
    },
    /**
     * Minecraft Entity Player Field Member: Network Manager (实体玩家字段成员: 网络管理器)
     * - Parent: [CONNECTION]
     */
    NETWORK_MANAGER(MinecraftReflection.networkManagerClass) { // parent = connection
        override fun value(): String
                = "networkManager"
        override fun get(): () -> AccessorField
                = { Accessors.getAccessorField(MinecraftReflection.playerConnectionClass, clazz, true) }
        override fun get(player: Player): Any?
                = field.get(CONNECTION.get(player))
        override fun set(player: Player, value: Any?)
                = field.set(CONNECTION.get(player), value)
    },
    /**
     * Minecraft Entity Player Field Member: Channel (实体玩家字段成员: 通道)
     * - Parent: [NETWORK_MANAGER]
     */
    CHANNEL(Channel::class.java) { // parent = networkManager
        override fun value(): String
                = "channel"
        override fun get(): () -> AccessorField
                = { Accessors.getAccessorField(MinecraftReflection.networkManagerClass, clazz, true) }
        override fun get(player: Player): Any?
                = field.get(NETWORK_MANAGER.get(player))
        override fun set(player: Player, value: Any?)
                = field.set(NETWORK_MANAGER.get(player), value)
    },
    /**
     * Minecraft Entity Player Field Member: Profile (实体玩家字段成员: 简介)
     */
    PROFILE(GameProfile::class.java) {
        override fun value(): String
                = "profile"
        override fun get(): () -> AccessorField
                = { Accessors.getAccessorField(MinecraftReflection.entityHumanClass, clazz, true) }
    },
    /**
     * Minecraft Entity Player Field Member: Active Container (实体玩家字段成员: 交互容器)
     */
    ACTIVE_CONTAINER(Containers.containerClass) {
        override fun value(): String
                = "activeContainer"
    }
    ;

    protected val field: AccessorField
        get() = cachedMap.getOrPut(this, get())

    open fun get(player: Player): Any?
            = field.get(Entities.asNMSEntity(player))

    open fun set(player: Player, value: Any?)
            = field.set(Entities.asNMSEntity(player), value)

    protected open fun get(): () -> AccessorField
            = { Accessors.getAccessorField(MinecraftReflection.entityPlayerClass, value(), true) }

    companion object {

        @JvmStatic
        private val cachedMap: MutableMap<MinecraftPlayerMembers, AccessorField> by lazy {
            HashMap<MinecraftPlayerMembers, AccessorField>() }
    }
}
