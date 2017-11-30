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

package com.mcmoonlake.api.anvil

import com.mcmoonlake.api.currentMCVersion
import com.mcmoonlake.api.getMoonLake
import com.mcmoonlake.api.getOnlinePlayers
import com.mcmoonlake.api.gui.Containers
import com.mcmoonlake.api.reflect.accessor.AccessorConstructor
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.utility.MinecraftPlayerMembers
import org.bukkit.plugin.Plugin
import java.util.*

/**
 * ## AnvilWindows
 *
 * * Used to create an [AnvilWindow] interface instance.
 * * 用于创建 [AnvilWindow] 接口实例.
 *
 * @see [AnvilWindows.create]
 * @author lgou2w
 * @since 2.0
 */
object AnvilWindows {

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    private val anvilWindowConstructor: AccessorConstructor<AnvilWindow> by lazy {
        val clazz = Class.forName("com.mcmoonlake.impl.anvil.AnvilWindowImpl_${currentMCVersion().bukkitVersion.version}") as Class<AnvilWindow>
        getMoonLake().logger.fine("Anvil Window Using: ${clazz.canonicalName}")
        Accessors.getAccessorConstructor(clazz, false, Plugin::class.java) }

    /**
     * * Create a new instance of the [AnvilWindow] interface using the specified [plugin] object.
     * * 使用给定的 [plugin] 对象创建一个 [AnvilWindow] 接口的新实例.
     *
     * @see [com.mcmoonlake.api.newAnvilWindow]
     */
    @JvmStatic
    @JvmName("create")
    fun create(plugin: Plugin): AnvilWindow
            = anvilWindowConstructor.newInstance(plugin)

    /**
     * * Releases and close any [AnvilWindow] Id references currently stored on [AnvilWindows].
     * * 释放并关闭当前在 [AnvilWindows] 存储的所有 [AnvilWindow] Id 引用.
     *
     * ### It is not recommended to call this function.
     */
    @JvmStatic
    @JvmName("releaseAll")
    fun releaseAll() {
        val map = getOnlinePlayers().associateBy { Containers.getWindowId(MinecraftPlayerMembers.ACTIVE_CONTAINER.get(it)) }
        val source = ArrayList(windowIds)
        source.retainAll(map.keys)
        source.forEach { map[it]?.closeInventory() }
        if(source.isNotEmpty() || windowIds.isNotEmpty())
            windowIds.removeAll(source)
    }

    @JvmStatic
    internal val windowIds: MutableList<Int> by lazy {
        Collections.synchronizedList(ArrayList<Int>()) }
}
