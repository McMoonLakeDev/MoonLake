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

package com.minecraft.moonlake.api.anvil

import com.minecraft.moonlake.api.currentMCVersion
import com.minecraft.moonlake.api.getMoonLake
import com.minecraft.moonlake.api.getOnlinePlayers
import com.minecraft.moonlake.api.gui.Containers
import com.minecraft.moonlake.api.reflect.accessor.AccessorConstructor
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftPlayerMembers
import org.bukkit.plugin.Plugin
import java.util.*

object AnvilWindows {

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    private val anvilWindowConstructor: AccessorConstructor<AnvilWindow> by lazy {
        val clazz = Class.forName("com.minecraft.moonlake.impl.anvil.AnvilWindowImpl_${currentMCVersion().bukkitVersion.version}") as Class<AnvilWindow>
        getMoonLake().logger.fine("Anvil Window Using: ${clazz.canonicalName}")
        Accessors.getAccessorConstructor(clazz, false, Plugin::class.java) }

    @JvmStatic
    @JvmName("create")
    fun create(plugin: Plugin): AnvilWindow
            = anvilWindowConstructor.newInstance(plugin)

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
