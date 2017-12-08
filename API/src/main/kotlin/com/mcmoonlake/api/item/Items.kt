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

package com.mcmoonlake.api.item

import com.mcmoonlake.api.converter.ConverterEquivalent
import com.mcmoonlake.api.nbt.NBTCompound
import com.mcmoonlake.api.nbt.NBTFactory
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.utility.MinecraftConverters
import com.mcmoonlake.api.utility.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.Modifier

object Items {

    /**
     * * Air of ItemStack.
     */
    @JvmField
    val AIR = ItemStack(Material.AIR)

    @JvmStatic
    private val itemGetById: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getItemClass(), "getById", true, Int::class.java) }

    @JvmStatic
    @JvmName("getItemById")
    @Deprecated("Material Id")
    fun getItemById(id: Int): Any?
            = itemGetById.invoke(null, id) // TODO v1.13

    @JvmStatic
    @JvmName("getItemByType")
    @Deprecated("Material Id")
    fun getItemByType(type: Material): Any?
            = itemGetById.invoke(null, type.id) // TODO v1.13

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

    @JvmStatic
    @JvmName("toMojangson")
    @Deprecated("The future version 1.13 may be subject to change.")
    fun toMojangson(itemStack: ItemStack): String
            = NBTFactory.readStackNBT(itemStack).toMojangson() // Because it is compatible with MojangsonParser // TODO v1.13

    @JvmStatic
    @JvmName("toBase64")
    @Throws(IOException::class)
    fun toBase64(itemStack: ItemStack): String
            = NBTFactory.writeDataBase64(NBTFactory.readStackNBT(itemStack))

    @JvmStatic
    @JvmName("toCompoundFile")
    @Throws(IOException::class)
    fun toCompoundFile(itemStack: ItemStack, outFile: File, compress: Boolean = true)
            = NBTFactory.writeDataCompoundFile(NBTFactory.readStackNBT(itemStack), outFile, compress)

    @JvmStatic
    private val mojangsonParser: AccessorMethod by lazy {
        Accessors.getAccessorMethod(
                FuzzyReflect.fromClass(MinecraftReflection.getMinecraftClass("MojangsonParser"), true)
                        .getMethodListByParameters(MinecraftReflection.getNBTTagCompoundClass(), arrayOf(String::class.java))
                        .first { Modifier.isStatic(it.modifiers) }, true) }

    @JvmStatic
    @JvmName("fromMojangson")
    @Throws(IllegalArgumentException::class)
    @Deprecated("The future version 1.13 may be subject to change.") // TODO v1.13
    fun fromMojangson(value: String): ItemStack {
        val handle = mojangsonParser.invoke(null, value) ?: throw IllegalArgumentException("Null of Mojangson Parser.")
        val wrapped = NBTFactory.fromNMS<NBTCompound>(handle) as NBTCompound
        return NBTFactory.createStackNBT(wrapped)
    }

    @JvmStatic
    @JvmName("fromBase64")
    @Throws(IOException::class)
    fun fromBase64(value: String): ItemStack
            = NBTFactory.createStackNBT(NBTFactory.readDataBase64Compound(value).notNull())

    @JvmStatic
    @JvmName("fromCompoundFile")
    @Throws(IOException::class)
    fun fromCompoundFile(inFile: File, compress: Boolean = true): ItemStack
            = if(!inFile.exists() || inFile.isDirectory) throw FileNotFoundException(inFile.absolutePath)
            else NBTFactory.createStackNBT(NBTFactory.readDataCompoundFile(inFile, compress).notNull())
}
