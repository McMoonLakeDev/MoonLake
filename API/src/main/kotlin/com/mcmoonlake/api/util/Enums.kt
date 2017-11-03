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

package com.mcmoonlake.api.util

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors

object Enums {

    @JvmStatic
    private val classEnumConstantDirectory: AccessorMethod by lazy {
        Accessors.getAccessorMethod(Class::class.java, "enumConstantDirectory", true) }

    @JvmStatic
    @JvmName("enumMap")
    @Suppress("UNCHECKED_CAST")
    private fun <T: Enum<T>> enumMap(clazz: Class<T>): Map<String, T>?
            = classEnumConstantDirectory.invoke(clazz) as Map<String, T>?

    @JvmStatic
    @JvmName("enumMapOf")
    private fun <T: Enum<T>> enumMapOf(clazz: Class<T>, block: (type: T) -> Boolean, def: T? = null): T? {
        val enumMap = enumMap(clazz)
        if(enumMap == null || enumMap.isEmpty())
            return def
        val iterator = enumMap.values.iterator()
        while(iterator.hasNext()) {
            val type = iterator.next()
            if(block(type))
                return type
        }
        return def
    }

    @JvmStatic
    @JvmName("ofName")
    fun <T: Enum<T>> ofName(clazz: Class<T>, name: String, def: T? = null): T?
            = enumMapOf(clazz, { it.name == name }, def)

    @JvmStatic
    @JvmName("ofOrigin")
    fun <T: Enum<T>> ofOrigin(clazz: Class<T>, origin: Int, def: T? = null): T?
            = enumMapOf(clazz, { it.ordinal == origin }, def)

    @JvmStatic
    @JvmName("ofValuable")
    fun <V, T> ofValuable(clazz: Class<T>, value: V, def: T? = null): T? where T: Enum<T>, T: Valuable<V>
            = enumMapOf(clazz, { it.value() == value }, def)
}
