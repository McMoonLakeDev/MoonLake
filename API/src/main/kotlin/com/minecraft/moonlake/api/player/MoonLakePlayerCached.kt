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

package com.minecraft.moonlake.api.player

import com.minecraft.moonlake.api.cached.CachedWeakRef
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.getMoonLake
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.reflect.accessor.AccessorConstructor
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.toPlayer
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class MoonLakePlayerCached private constructor(): CachedWeakRef<UUID, MoonLakePlayer>() {

    /** static */

    companion object {

        private var instance: MoonLakePlayerCached? = null
        private var constructor: AccessorConstructor<MoonLakePlayer>? = null
        private val IMPLEMENT = "com.minecraft.moonlake.impl.player.MoonLakePlayerImpl_${MinecraftBukkitVersion.currentVersion().getVersion()}"

        @JvmStatic
        @JvmName("instance")
        fun instance(): MoonLakePlayerCached {
            if(instance == null) synchronized(MoonLakePlayerCached::class) {
                if(instance == null)
                    instance = MoonLakePlayerCached()
            }
            return instance.notNull()
        }

        @JvmStatic
        @JvmName("instanceConstructor")
        @Suppress("UNCHECKED_CAST")
        private fun instanceConstructor(): AccessorConstructor<MoonLakePlayer> {
            if(constructor == null) synchronized(MoonLakePlayerCached::class) {
                if(constructor == null)
                    constructor = Accessors.getAccessorConstructor((Class.forName(IMPLEMENT) as Class<MoonLakePlayer>), false, Player::class.java)
            }
            return constructor.notNull()
        }
    }

    /** initialize */

    init {
        registerEventService()
    }

    private fun registerEventService() {
        if(getMoonLake().isEnabled) {
            object: MoonLakeListener {
                @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
                fun onQuit(event: PlayerQuitEvent) {
                    removeCache(event.player.uniqueId)
                }
            }.registerEvent(getMoonLake())
        }
    }

    /** api */

    fun getCache(player: Player): MoonLakePlayer
            = super.getCache(player.uniqueId)

    /** override */

    override fun getCache(key: UUID): MoonLakePlayer
            = key.toPlayer().notNull().let { getCache(it) }

    /** implement */

    override fun produceValue(key: UUID): MoonLakePlayer
            = key.toPlayer().notNull().let { instanceConstructor().newInstance(it) }
}
