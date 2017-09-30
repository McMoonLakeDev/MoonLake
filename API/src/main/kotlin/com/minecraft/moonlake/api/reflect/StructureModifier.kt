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

package com.minecraft.moonlake.api.reflect

import com.minecraft.moonlake.api.converter.ConverterEquivalent
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.funs.Function
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.throwMoonLake
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class StructureModifier<T> {

    private var target: Any? = null
    private var targetType: Class<*>
    private var fieldType: Class<*>
    private var fieldData: MutableList<Field>
    private var converter: ConverterEquivalent<T>?

    constructor(targetType: Class<*>, fieldType: Class<*> = Object::class.java, superClassExclude: Class<*>? = null, converter: ConverterEquivalent<T>? = null) {
        this.targetType = targetType
        this.fieldType = fieldType
        this.fieldData = FuzzyReflect.fromClass(targetType, true).getDeclaredFields(superClassExclude)
                .filter { !Modifier.isStatic(it.modifiers) && (superClassExclude == null || it.declaringClass != superClassExclude) }
                .toMutableList()
        this.converter = converter
    }

    constructor(targetType: Class<*>, fieldType: Class<*> = Object::class.java,
                fieldData: MutableList<Field> = ArrayList(), converter: ConverterEquivalent<T>? = null) {
        this.targetType = targetType
        this.fieldType = fieldType
        this.fieldData = fieldData
        this.converter = converter
    }

    /** api */

    fun getTarget(): Any?
            = target

    fun getTargetType(): Class<*>
            = targetType

    fun getFieldType(): Class<*>
            = fieldType

    fun getFieldDatas(): List<Field>
            = fieldData.toList()

    @Throws(MoonLakeException::class)
    fun read(fieldIndex: Int): T? = try {
        readInternal(fieldIndex)
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @Throws(MoonLakeException::class)
    fun readSafe(fieldIndex: Int): T? = when(fieldIndex >= 0 && fieldIndex < fieldData.size) {
        true -> read(fieldIndex)
        else -> null
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(MoonLakeException::class)
    private fun readInternal(fieldIndex: Int): T? {
        if(target == null)
            throw IllegalStateException("不能从 null 目标读取值.")
        if(fieldIndex < 0)
            throw MoonLakeException("字段索引不能为负数: $fieldIndex")
        if(fieldData.isEmpty())
            throw MoonLakeException("目标类 $target 没有存在类型为 $fieldType 的字段.")
        if(fieldIndex >= fieldData.size)
            throw MoonLakeException("字段索引越界. (Index: $fieldIndex, Size: ${fieldData.size})")
        return try {
            val result = Accessors.setAccessorField(fieldData[fieldIndex], true).get(target)
            if(converter != null)
                converter.notNull().getSpecific(result)
            else
                result as T
        } catch(e: IllegalAccessException) {
            throw MoonLakeException("由于安全限制, 无法读取字段值.", e)
        }
    }

    @Throws(MoonLakeException::class)
    fun write(fieldIndex: Int, value: T?): StructureModifier<T> = try {
        writeInternal(fieldIndex, value)
    } catch(e: Exception) {
        e.throwMoonLake()
    }

    @Throws(MoonLakeException::class)
    fun writeSafe(fieldIndex: Int, value: T?): StructureModifier<T> = when(fieldIndex >= 0 && fieldIndex < fieldData.size) {
        true -> write(fieldIndex, value)
        else -> this
    }

    @Throws(MoonLakeException::class)
    private fun writeInternal(fieldIndex: Int, value: T?): StructureModifier<T> {
        if(target == null)
            throw IllegalStateException("不能从 null 目标读取值.")
        if(fieldIndex < 0)
            throw MoonLakeException("字段索引不能为负数: $fieldIndex")
        if(fieldData.isEmpty())
            throw MoonLakeException("目标类 $target 没有存在类型为 $fieldType 的字段.")
        if(fieldIndex >= fieldData.size)
            throw MoonLakeException("字段索引越界. (Index: $fieldIndex, Size: ${fieldData.size})")
        val result = if(converter != null) converter.notNull().getGeneric(value) else value
        try {
            Accessors.setAccessorField(fieldData[fieldIndex], true).set(target, result)
        } catch(e: IllegalAccessException) {
            throw MoonLakeException("由于安全限制, 无法读取字段值.", e)
        }
        return this
    }

    fun modify(fieldIndex: Int, select: Function<T?, T>): StructureModifier<T> {
        val value = read(fieldIndex)
        return write(fieldIndex, select.apply(value))
    }

    fun modify(fieldIndex: Int, select: (value: T?) -> T): StructureModifier<T> {
        val value = read(fieldIndex)
        return write(fieldIndex, select(value))
    }

    fun <T> withTarget(target: Any?): StructureModifier<T> {
        val result = StructureModifier<T>(targetType, fieldType, fieldData, null)
        result.target = target
        return result
    }

    fun <T> withConverter(converter: ConverterEquivalent<T>?): StructureModifier<T> {
        val result = withTarget<T>(target)
        result.converter = converter
        return result
    }

    fun <T> withType(fieldType: Class<*>, converter: ConverterEquivalent<T>? = null): StructureModifier<T> {
        val result = StructureModifier(targetType, fieldType, fieldData.filter { fieldType.isAssignableFrom(it.type) }.toMutableList(), converter)
        result.target = target
        return result
    }

    override fun toString(): String {
        return "StructureModifier(fieldType=$fieldType, fieldData=$fieldData)"
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("of")
        fun of(targetType: Class<*>, superClassExclude: Class<*>? = null, converter: ConverterEquivalent<*>? = null): StructureModifier<out Any?>
                = StructureModifier(targetType, Object::class.java, superClassExclude, converter)

        @JvmStatic
        @JvmName("of")
        fun <T> of(targetType: Class<*>, superClassExclude: Class<*>? = null, fieldType: Class<T>, converter: ConverterEquivalent<T>? = null): StructureModifier<T>
                = StructureModifier(targetType, Object::class.java, superClassExclude, converter).withType(fieldType, converter)
    }
}
