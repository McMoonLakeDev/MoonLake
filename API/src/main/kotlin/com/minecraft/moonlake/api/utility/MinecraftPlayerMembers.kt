/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.utility

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.converter.ConverterEquivalent
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import io.netty.channel.Channel
import org.bukkit.entity.Player

enum class MinecraftPlayerMembers(val clazz: Class<*>) : Valuable<String> {

    PING(Int::class.java) {
        override fun value(): String
                = "ping"
    },
    LOCALE(String::class.java) {
        override fun value(): String
                = "locale"
    },
    CONNECTION(MinecraftReflection.getPlayerConnectionClass()) {
        override fun value(): String
                = "playerConnection"

        override fun get(): () -> AccessorField {
            return try {
                super.get()
            } catch(e: NoSuchFieldException) {
                { Accessors.getAccessorField(MinecraftReflection.getEntityPlayerClass(), clazz, true) }
            }
        }
    },
    NETWORK_MANAGER(MinecraftReflection.getNetworkManagerClass()) { // parent = connection
        override fun value(): String
                = "networkManager"
        override fun get(): () -> AccessorField
                = { Accessors.getAccessorField(MinecraftReflection.getPlayerConnectionClass(), clazz, true) }
        override fun get(player: Player): Any?
                = field.get(CONNECTION.get(player))
        override fun set(player: Player, value: Any?)
                = field.set(CONNECTION.get(player), value)
    },
    CHANNEL(Channel::class.java) { // parent = networkManager
        override fun value(): String
                = "channel"
        override fun get(): () -> AccessorField
                = { Accessors.getAccessorField(MinecraftReflection.getNetworkManagerClass(), clazz, true) }
        override fun get(player: Player): Any?
                = field.get(NETWORK_MANAGER.get(player))
        override fun set(player: Player, value: Any?)
                = field.set(NETWORK_MANAGER.get(player), value)
    }
    ;

    protected val field: AccessorField
        get() = cachedMap.getOrPut(this, get())

    open fun get(player: Player): Any?
            = field.get(converter.getGeneric(player))

    open fun set(player: Player, value: Any?)
            = field.set(converter.getGeneric(player), value)

    open protected fun get(): () -> AccessorField
            = { Accessors.getAccessorField(MinecraftReflection.getEntityPlayerClass(), value(), true) }

    companion object {

        @JvmStatic
        private val cachedMap: MutableMap<MinecraftPlayerMembers, AccessorField> by lazy {
            HashMap<MinecraftPlayerMembers, AccessorField>() }

        @JvmStatic
        private val converter: ConverterEquivalent<Player> by lazy {
            MinecraftConverters.getEntity(Player::class.java) }
    }
}
