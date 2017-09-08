/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */

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

package com.minecraft.moonlake.api.nbt

import com.minecraft.moonlake.api.converter.ConverterEquivalent
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.reflect.StructureModifier
import com.minecraft.moonlake.api.reflect.accessor.AccessorConstructor
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NBTFactory {

    @JvmStatic
    private val nbtBaseCreateTag: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getNBTBaseClass(), "createTag", true, Byte::class.java) }
    @JvmStatic
    private val itemStackModifier: StructureModifier<*> by lazy {
        StructureModifier.of(MinecraftReflection.getItemStackClass(), Object::class.java) }
    @JvmStatic
    private val itemStackConverter: ConverterEquivalent<ItemStack> by lazy {
        MinecraftConverters.getItemStack() }
    @JvmStatic
    private val itemStackConstructor: AccessorConstructor<out Any> by lazy {
        Accessors.getAccessorConstructor(MinecraftReflection.getItemStackClass(), false, MinecraftReflection.getNBTTagCompoundClass()) }
    @JvmStatic
    private val craftItemStackHandle: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getCraftItemStackClass(), MinecraftReflection.getItemStackClass(), true) }

    @JvmStatic
    @JvmName("fromBase")
    @Suppress("UNCHECKED_CAST")
    fun <T> fromBase(base: NBTBase<T>): NBTWrapper<T> {
        return when(base is NBTWrapper) {
            true -> base as NBTWrapper
            else -> return when(base.getType()) {
                NBTType.TAG_COMPOUND -> {
                    val compound = ofCompound(base.getName())
                    compound.setValue(base.getValue() as MutableMap<String, NBTBase<*>>)
                    return compound as NBTWrapper<T>
                }
                NBTType.TAG_LIST -> {
                    val list = ofList<T>(base.getName())
                    list.setValue(base.getValue() as MutableList<NBTBase<T>>)
                    return list as NBTWrapper<T>
                }
                else -> ofWrapper(base.getType(), base.getName(), base.getValue())
            }
        }
    }

    @JvmStatic
    @JvmName("fromNMS")
    @Suppress("UNCHECKED_CAST")
    fun <T> fromNMS(handle: Any, name: String = ""): NBTWrapper<T> {
        val element = NBTWrappedElement<T>(handle, name)
        return when(element.getType()) {
            NBTType.TAG_COMPOUND -> NBTWrappedCompound(handle, name) as NBTWrapper<T>
            NBTType.TAG_LIST -> NBTWrappedList<T>(handle, name) as NBTWrapper<T>
            else -> element
        }
    }

    @JvmStatic
    @JvmName("ofWrapper")
    @Suppress("UNCHECKED_CAST")
    fun <T> ofWrapper(type: NBTType, name: String): NBTWrapper<T> {
        val handle = nbtBaseCreateTag.invoke(null, type.rawId.toByte())
        return when(type) {
            NBTType.TAG_COMPOUND -> NBTWrappedCompound(handle, name) as NBTWrapper<T>
            NBTType.TAG_LIST -> NBTWrappedList<T>(handle, name) as NBTWrapper<T>
            else -> NBTWrappedElement(handle, name)
        }
    }

    @JvmStatic
    @JvmName("ofWrapper")
    fun <T> ofWrapper(type: NBTType, name: String, value: T): NBTWrapper<T> {
        val wrapper = ofWrapper<T>(type, name)
        wrapper.setValue(value)
        return wrapper
    }

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: String): NBTBase<String>
            = ofWrapper(NBTType.TAG_STRING, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Byte): NBTBase<Byte>
            = ofWrapper(NBTType.TAG_BYTE, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Short): NBTBase<Short>
            = ofWrapper(NBTType.TAG_SHORT, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Int): NBTBase<Int>
            = ofWrapper(NBTType.TAG_INT, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Long): NBTBase<Long>
            = ofWrapper(NBTType.TAG_LONG, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Float): NBTBase<Float>
            = ofWrapper(NBTType.TAG_FLOAT, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: Double): NBTBase<Double>
            = ofWrapper(NBTType.TAG_DOUBLE, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: ByteArray): NBTBase<ByteArray>
            = ofWrapper(NBTType.TAG_BYTE_ARRAY, name, value)

    @JvmStatic
    @JvmName("of")
    fun of(name: String, value: IntArray): NBTBase<IntArray>
            = ofWrapper(NBTType.TAG_INT_ARRAY, name, value)

    @JvmStatic
    @JvmName("ofCompound")
    fun ofCompound(name: String): NBTCompound
            = ofWrapper<MutableMap<String, NBTBase<*>>>(NBTType.TAG_COMPOUND, name) as NBTCompound

    @JvmStatic
    @JvmName("ofList")
    @Suppress("UNCHECKED_CAST")
    fun <T> ofList(name: String): NBTList<T>
            = ofWrapper<MutableList<NBTBase<T>>>(NBTType.TAG_LIST, name) as NBTList<T>

    @JvmStatic
    @JvmName("readStackTag")
    fun readStackTag(itemStack: ItemStack): NBTCompound? {
        return when(MinecraftReflection.getCraftItemStackClass().isInstance(itemStack)) {
            true -> getCraftStackTag(itemStack)
            else -> getOriginStackTag(itemStack)
        }
    }

    @JvmStatic
    @JvmName("readSafeStackTag")
    fun readSafeStackTag(itemStack: ItemStack): NBTCompound
            = readStackTag(itemStack) ?: ofCompound("tag")

    @JvmStatic
    @JvmName("writeStackTag")
    fun writeStackTag(itemStack: ItemStack, tag: NBTCompound?) {
        when(MinecraftReflection.getCraftItemStackClass().isInstance(itemStack)) {
            true -> setCraftStackTag(itemStack, tag)
            else -> setOriginStackTag(itemStack, tag)
        }
    }

    @JvmStatic
    @JvmName("createStack")
    fun createStack(type: Material, amount: Int, durability: Int, tag: NBTCompound): ItemStack {
        val nbt = ofCompound("")
        nbt.putString("id", "minecraft:${type.name.toLowerCase()}")
        nbt.putByte("Count", amount)
        nbt.putShort("Damage", durability)
        nbt.put("tag", tag)
        val nmsItemStack = itemStackConstructor.newInstance(fromBase(nbt).getHandle())
        return itemStackConverter.getSpecific(nmsItemStack) as ItemStack
    }

    /** implement */

    @JvmStatic
    @JvmName("getCraftStackTag")
    private fun getCraftStackTag(itemStack: ItemStack): NBTCompound? {
        val nmsItemStack = craftItemStackHandle.get(itemStack)
        val modifier = itemStackModifier.withTarget<Any>(nmsItemStack)
                .withType(MinecraftReflection.getNBTBaseClass(), MinecraftConverters.getNBT())
        return modifier.read(0) as NBTCompound?
    }

    @JvmStatic
    @JvmName("setCraftStackTag")
    private fun setCraftStackTag(itemStack: ItemStack, tag: NBTCompound?) {
        val nmsItemStack = craftItemStackHandle.get(itemStack)
        val modifier = itemStackModifier.withTarget<Any>(nmsItemStack)
                .withType(MinecraftReflection.getNBTBaseClass(), MinecraftConverters.getNBT())
        modifier.write(0, tag)
    }

    @JvmStatic
    @JvmName("getOriginStackTag")
    private fun getOriginStackTag(itemStack: ItemStack): NBTCompound? {
        val copyItemStack = itemStackConverter.getSpecific(itemStackConverter.getGeneric(itemStack)).notNull()
        copyItemStack.itemMeta = itemStack.itemMeta
        return getCraftStackTag(copyItemStack)
    }

    @JvmStatic
    @JvmName("setOriginStackTag")
    private fun setOriginStackTag(itemStack: ItemStack, tag: NBTCompound?) {
        if(tag == null) {
            itemStack.itemMeta = null
        } else {
            val copyItemStack = itemStackConverter.getSpecific(itemStackConverter.getGeneric(itemStack)).notNull()
            setCraftStackTag(copyItemStack, tag)
            itemStack.itemMeta = copyItemStack.itemMeta
        }
    }
}
