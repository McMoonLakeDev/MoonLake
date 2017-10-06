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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.Material

object Items {

    @JvmStatic
    private val itemGetById: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getItemClass(), "getById", true, Int::class.java) }

    init {
        println(itemGetById)
        println(itemGetById.accessor)
    }

    @JvmStatic
    @JvmName("getItemById")
    fun getItemById(id: Int): Any?
            = itemGetById.invoke(null, id)

    @JvmStatic
    @JvmName("getItemByType")
    fun getItemByType(type: Material): Any?
            = itemGetById.invoke(null, type.id)
}
