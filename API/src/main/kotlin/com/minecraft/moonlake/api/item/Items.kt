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

import com.minecraft.moonlake.api.converter.ConverterEquivalent
import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {

    @JvmStatic
    private val itemGetById: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getItemClass(), "getById", true, Int::class.java) }

    @JvmStatic
    @JvmName("getItemById")
    @Deprecated("Material Id")
    fun getItemById(id: Int): Any?
            = itemGetById.invoke(null, id)

    @JvmStatic
    @JvmName("getItemByType")
    @Deprecated("Material Id")
    fun getItemByType(type: Material): Any?
            = itemGetById.invoke(null, type.id)

    @JvmStatic
    private val itemStackConverter: ConverterEquivalent<ItemStack> by lazy {
        MinecraftConverters.getItemStack() }

    @JvmStatic
    @JvmName("asNMSCopy")
    fun asNMSCopy(itemStack: ItemStack?): Any?
            = itemStackConverter.getGeneric(itemStack)

    @JvmStatic
    @JvmName("asBukkitCopy")
    fun asBukkitCopy(nmsItemStack: Any?): ItemStack?
            = itemStackConverter.getSpecific(nmsItemStack)
}
