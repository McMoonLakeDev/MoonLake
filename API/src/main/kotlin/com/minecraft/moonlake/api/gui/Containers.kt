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

package com.minecraft.moonlake.api.gui

import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftReflection

object Containers {

    @JvmStatic
    private val containerWindowId: AccessorField by lazy {
        Accessors.getAccessorField(containerClass, "windowId", true) }

    @JvmStatic
    val containerClass: Class<*>
        get() = MinecraftReflection.getMinecraftClass("Container")

    @JvmStatic
    @JvmName("getContainerWindowId")
    @Throws(IllegalArgumentException::class)
    fun getWindowId(container: Any?): Int
            = if(containerClass.isInstance(container)) (containerWindowId.get(container) as Int?) ?: -1 else throw IllegalArgumentException("参数 $container 不是容器实例.")
}
