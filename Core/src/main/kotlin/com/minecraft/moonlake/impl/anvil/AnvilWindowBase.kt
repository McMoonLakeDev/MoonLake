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

package com.minecraft.moonlake.impl.anvil

import com.minecraft.moonlake.api.anvil.AnvilWindowAbstract
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

open class AnvilWindowBase(plugin: Plugin) : AnvilWindowAbstract(plugin) {

    protected var handle: Any? = null

    override fun isOpened(): Boolean
            = handle != null

    override fun open(player: Player) {
        throw UnsupportedOperationException()
    }

    override fun release() {
        super.release()
        handle = null
    }

    protected fun handleException(e: Exception?)
            = e?.printStackTrace()

    protected fun checkOpenState() {
        if(isOpened())
            throw MoonLakeException("铁砧窗口已经处于打开状态.")
    }

    private val containerAnvilEntityHuman: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getMinecraftClass("ContainerAnvil"), MinecraftReflection.getEntityHumanClass(), true) }

    protected fun getContainerAnvilPlayer(): Player {
        val entityHuman = containerAnvilEntityHuman.get(handle)
        val converter = MinecraftConverters.getEntity(Player::class.java)
        return converter.getSpecific(entityHuman)
    }
}
