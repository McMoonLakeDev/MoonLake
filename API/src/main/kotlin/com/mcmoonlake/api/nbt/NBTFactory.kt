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

package com.mcmoonlake.api.nbt

import com.mcmoonlake.api.*
import com.mcmoonlake.api.entity.Entities
import com.mcmoonlake.api.exception.MoonLakeException
import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.StructureModifier
import com.mcmoonlake.api.reflect.accessor.AccessorConstructor
import com.mcmoonlake.api.reflect.accessor.AccessorField
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.security.base64DString
import com.mcmoonlake.api.security.base64EString
import com.mcmoonlake.api.utility.MinecraftConverters
import com.mcmoonlake.api.utility.MinecraftReflection
import com.mcmoonlake.api.version.MinecraftBukkitVersion
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object NBTFactory {

    @JvmStatic
    private val nbtBaseCreateTag: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.nbtBaseClass, "createTag", true, Byte::class.java) }
    @JvmStatic
    private val nbtStreamClass: Class<*> by lazy {
        MinecraftReflection.getMinecraftClass("NBTCompressedStreamTools") }
    @JvmStatic
    private val nbtReadLimiterClass: Class<*> by lazy {
        MinecraftReflection.getMinecraftClass("NBTReadLimiter") }
    @JvmStatic
    private val nbtReadLimiterInstance: Any by lazy {
        Accessors.getAccessorField(FuzzyReflect.fromClass(nbtReadLimiterClass, true)
                .getFieldByType("instance", nbtReadLimiterClass), true).get(null).notNull() }
    @JvmStatic
    private val nbtStreamRead: AccessorMethod by lazy {
        Accessors.getAccessorMethod(FuzzyReflect.fromClass(nbtStreamClass, true)
                .getMethodByParameters("read", MinecraftReflection.nbtBaseClass, arrayOf(DataInput::class.java, Int::class.java, nbtReadLimiterClass)), true) }
    @JvmStatic
    private val nbtStreamWrite: AccessorMethod by lazy {
        Accessors.getAccessorMethod(FuzzyReflect.fromClass(nbtStreamClass, true)
                .getMethodByParameters("write", Void::class.java, arrayOf(MinecraftReflection.nbtBaseClass, DataOutput::class.java)), true) }
    @JvmStatic
    private val itemStackModifier: StructureModifier<*> by lazy {
        StructureModifier.of(MinecraftReflection.itemStackClass, Object::class.java) }
    @JvmStatic
    private val itemStackConstructor: AccessorConstructor<out Any> by lazy {
        Accessors.getAccessorConstructor(MinecraftReflection.itemStackClass, false, MinecraftReflection.nbtTagCompoundClass) }
    @JvmStatic
    private val craftItemStackHandle: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.craftItemStackClass, MinecraftReflection.itemStackClass, true) }
    @JvmStatic
    private val entitySave: AccessorMethod by lazy {
        val nbtClazz = MinecraftReflection.nbtTagCompoundClass
        if(!currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_9_R2))
            Accessors.getAccessorMethod(MinecraftReflection.entityClass, "e", false, nbtClazz)
        Accessors.getAccessorMethod(FuzzyReflect.fromClass(MinecraftReflection.entityClass).getMethodByParameters("save", nbtClazz, arrayOf(nbtClazz))) }
    @JvmStatic
    private val entityRead: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.entityClass, "f", false, MinecraftReflection.nbtTagCompoundClass) }
    @JvmStatic
    private val entityLivingSave: AccessorMethod by lazy {
            Accessors.getAccessorMethod(MinecraftReflection.entityLivingClass, "a", false, MinecraftReflection.nbtTagCompoundClass) }
    @JvmStatic
    private val entityLivingRead: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.entityLivingClass, "b", false, MinecraftReflection.nbtTagCompoundClass) }

    @JvmStatic
    @JvmName("fromBase")
    @Suppress("UNCHECKED_CAST")
    fun <T> fromBase(base: NBTBase<T>): NBTWrapper<T> {
        return when(base is NBTWrapper) {
            true -> base as NBTWrapper
            else -> return when(base.type) {
                NBTType.TAG_COMPOUND -> {
                    val compound = ofCompound(base.name)
                    compound.value = base.value as MutableMap<String, NBTBase<*>>
                    return compound as NBTWrapper<T>
                }
                NBTType.TAG_LIST -> {
                    val list = ofList<T>(base.name)
                    list.value = base.value as MutableList<NBTBase<T>>
                    return list as NBTWrapper<T>
                }
                else -> ofWrapper(base.type, base.name, base.value)
            }
        }
    }

    @JvmStatic
    @JvmName("fromNMS")
    @Suppress("UNCHECKED_CAST")
    fun <T> fromNMS(handle: Any, name: String = ""): NBTWrapper<T> {
        val element = NBTWrappedElement<T>(handle, name)
        return when(element.type) {
            NBTType.TAG_COMPOUND -> NBTWrappedCompound(handle, name) as NBTWrapper<T>
            NBTType.TAG_LIST -> NBTWrappedList<T>(handle, name) as NBTWrapper<T>
            else -> element
        }
    }

    @JvmStatic
    @JvmName("of")
    fun of(type: NBTType): Any
            = nbtBaseCreateTag.invoke(null, type.rawId.toByte()) as Any

    @JvmStatic
    @JvmName("ofWrapper")
    @Suppress("UNCHECKED_CAST")
    fun <T> ofWrapper(type: NBTType, name: String): NBTWrapper<T> {
        val handle = of(type)
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
        wrapper.value = value
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
    @JvmOverloads
    fun ofCompound(name: String = ""): NBTCompound
            = ofWrapper<MutableMap<String, NBTBase<*>>>(NBTType.TAG_COMPOUND, name) as NBTCompound

    @JvmStatic
    @JvmName("ofList")
    @Suppress("UNCHECKED_CAST")
    @JvmOverloads
    fun <T> ofList(name: String = ""): NBTList<T>
            = ofWrapper<MutableList<NBTBase<T>>>(NBTType.TAG_LIST, name) as NBTList<T>

    @JvmStatic
    @JvmName("readStackTag")
    fun readStackTag(itemStack: ItemStack): NBTCompound? {
        return when(MinecraftReflection.craftItemStackClass.isInstance(itemStack)) {
            true -> getCraftStackTag(itemStack)
            else -> getOriginStackTag(itemStack)
        }
    }

    @JvmStatic
    @JvmName("readStackTagSafe")
    fun readStackTagSafe(itemStack: ItemStack): NBTCompound
            = readStackTag(itemStack) ?: ofCompound("tag")

    @JvmStatic
    @JvmName("writeStackTag")
    fun writeStackTag(itemStack: ItemStack, tag: NBTCompound?): ItemStack {
        when(MinecraftReflection.craftItemStackClass.isInstance(itemStack)) {
            true -> setCraftStackTag(itemStack, tag)
            else -> setOriginStackTag(itemStack, tag)
        }
        return itemStack
    }

    @JvmStatic
    @JvmName("createStack")
    @JvmOverloads
    fun createStack(type: Material, amount: Int = 1, durability: Int = 0, tag: NBTCompound? = null): ItemStack {
        val nbt = ofCompound()
        nbt.putString("id", "minecraft:${type.name.toLowerCase()}")
        nbt.putByte("Count", amount)
        nbt.putShort("Damage", durability)
        if(tag != null) nbt.put("tag", tag)
        val nmsItemStack = itemStackConstructor.newInstance(fromBase(nbt).handle)
        return MinecraftConverters.itemStack.getSpecific(nmsItemStack) as ItemStack
    }

    @JvmStatic
    @JvmName("createStackNBT")
    fun createStackNBT(nbt: NBTCompound): ItemStack {
        val type = nbt.getString("id").replaceFirst("minecraft:", "", true)
        val count = nbt.getByteOrNull("Count") ?: 1
        val durability = nbt.getShortOrNull("Damage") ?: 0
        val tag = nbt.getCompoundOrNull("tag")
        val itemStack = ItemStack(Material.matchMaterial(type), count.toInt(), durability)
        return writeStackTag(itemStack, tag)
    }

    @JvmStatic
    @JvmName("readStackNBT")
    fun readStackNBT(itemStack: ItemStack): NBTCompound {
        val nbt = ofCompound()
        val tag = readStackTag(itemStack)
        nbt.putString("id", "minecraft:${itemStack.type.name.toLowerCase()}")
        nbt.putByte("Count", itemStack.amount)
        nbt.putShort("Damage", itemStack.durability)
        if(tag != null) nbt.put("tag", tag)
        return nbt
    }

    @JvmStatic
    @JvmName("readEntityTag")
    fun <T: Entity> readEntityTag(entity: T): NBTCompound {
        val handle = of(NBTType.TAG_COMPOUND)
        when(entity) {
            is LivingEntity -> entityLivingRead.invoke(Entities.asNMSEntity(entity), handle)
            else -> entityRead.invoke(Entities.asNMSEntity(entity), handle)
        }
        return fromNMS<NBTCompound>(handle, "EntityTag") as NBTCompound
    }

    @JvmStatic
    @JvmName("writeEntityTag")
    fun <T: Entity> writeEntityTag(entity: T, tag: NBTCompound): T {
        val handle = fromBase(tag).handle
        when(entity) {
            is LivingEntity -> entityLivingSave.invoke(Entities.asNMSEntity(entity), handle)
            else -> entitySave.invoke(Entities.asNMSEntity(entity), handle)
        }
        return entity
    }

    /** nbt io */

    @JvmStatic
    @JvmName("writeData")
    @Throws(MoonLakeException::class)
    fun writeData(base: NBTBase<*>, data: DataOutput) = try {
        nbtStreamWrite.invoke(null, fromBase(base).handle, data)
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @JvmStatic
    @JvmName("readData")
    @Throws(MoonLakeException::class)
    fun <T> readData(data: DataInput): NBTWrapper<T>? {
        try {
            val handle = nbtStreamRead.invoke(null, data, 0, nbtReadLimiterInstance) ?: return null
            return fromNMS(handle)
        } catch(e: Exception) {
            e.throwMoonLake()
        }
    }

    @JvmStatic
    @JvmName("readDataCompound")
    @Throws(MoonLakeException::class)
    fun readDataCompound(data: DataInput): NBTCompound? = try {
        readData<NBTCompound>(data) as NBTCompound?
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @JvmStatic
    @JvmName("readDataList")
    @Throws(MoonLakeException::class)
    fun <T> readDataList(data: DataInput): NBTList<T>? = try {
        @Suppress("UNCHECKED_CAST")
        readData<NBTList<T>>(data) as NBTList<T>?
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @JvmStatic
    @JvmName("writeDataCompoundFile")
    @Throws(IOException::class)
    @JvmOverloads
    fun writeDataCompoundFile(compound: NBTCompound, file: File, compress: Boolean = true) {
        var stream: FileOutputStream? = null
        var output: DataOutputStream? = null
        var swallow = true
        try {
            stream = FileOutputStream(file)
            output = if(compress) DataOutputStream(GZIPOutputStream(stream)) else DataOutputStream(stream)
            writeData(compound, output)
            swallow = false
        } finally {
            if(output != null) output.ioClose(swallow)
            else if(stream != null) stream.ioClose(swallow)
        }
    }

    @JvmStatic
    @JvmName("readDataCompoundFile")
    @Throws(IOException::class)
    @JvmOverloads
    fun readDataCompoundFile(file: File, compress: Boolean = true): NBTCompound? {
        if(!file.exists() || file.isDirectory)
            return null
        var stream: FileInputStream? = null
        var input: DataInputStream? = null
        var swallow = true
        try {
            stream = FileInputStream(file)
            input = if(compress) DataInputStream(GZIPInputStream(stream)) else DataInputStream(stream)
            val result = readDataCompound(input)
            swallow = false
            return result
        } finally {
            if(input != null) input.ioClose(swallow)
            else if(stream != null) stream.ioClose(swallow)
        }
    }

    @JvmStatic
    @JvmName("writeDataBase64")
    @Throws(MoonLakeException::class)
    fun writeDataBase64(base: NBTBase<*>): String {
        val stream = ByteArrayOutputStream()
        val output = DataOutputStream(stream)
        writeData(base, output)
        return base64EString(stream.toByteArray())
    }

    @JvmStatic
    @JvmName("readDataBase64")
    @Throws(MoonLakeException::class)
    fun <T> readDataBase64(value: String): NBTWrapper<T>? {
        val stream = ByteArrayInputStream(base64DString(value))
        val input = DataInputStream(stream)
        return readData(input)
    }

    @JvmStatic
    @JvmName("readDataBase64Compound")
    @Throws(MoonLakeException::class)
    fun readDataBase64Compound(value: String): NBTCompound? = try {
        readDataBase64<NBTCompound>(value) as NBTCompound?
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @JvmStatic
    @JvmName("readDataBase64List")
    @Throws(MoonLakeException::class)
    fun <T> readDataBase64List(value: String): NBTList<T>? = try {
        @Suppress("UNCHECKED_CAST")
        readDataBase64<NBTList<T>>(value) as NBTList<T>?
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    /** implement */

    @JvmStatic
    @JvmName("getCraftStackTag")
    private fun getCraftStackTag(itemStack: ItemStack): NBTCompound? {
        val nmsItemStack = craftItemStackHandle.get(itemStack)
        val modifier = itemStackModifier.withTarget<Any>(nmsItemStack)
                .withType(MinecraftReflection.nbtBaseClass, MinecraftConverters.nbt)
        return modifier.read(0) as NBTCompound?
    }

    @JvmStatic
    @JvmName("setCraftStackTag")
    private fun setCraftStackTag(itemStack: ItemStack, tag: NBTCompound?) {
        val nmsItemStack = craftItemStackHandle.get(itemStack)
        val modifier = itemStackModifier.withTarget<Any>(nmsItemStack)
                .withType(MinecraftReflection.nbtBaseClass, MinecraftConverters.nbt)
        modifier.write(0, tag)
    }

    @JvmStatic
    @JvmName("getOriginStackTag")
    private fun getOriginStackTag(itemStack: ItemStack): NBTCompound? {
        val itemStackConverter = MinecraftConverters.itemStack
        val copyItemStack = itemStackConverter.getSpecific(itemStackConverter.getGeneric(itemStack)) as ItemStack
        copyItemStack.itemMeta = itemStack.itemMeta
        return getCraftStackTag(copyItemStack)
    }

    @JvmStatic
    @JvmName("setOriginStackTag")
    private fun setOriginStackTag(itemStack: ItemStack, tag: NBTCompound?) {
        if(tag == null) {
            itemStack.itemMeta = null
        } else {
            val itemStackConverter = MinecraftConverters.itemStack
            val copyItemStack = itemStackConverter.getSpecific(itemStackConverter.getGeneric(itemStack)) as ItemStack
            setCraftStackTag(copyItemStack, tag)
            itemStack.itemMeta = copyItemStack.itemMeta
        }
    }
}
