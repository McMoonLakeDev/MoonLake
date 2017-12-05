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

package com.mcmoonlake.api.converter

import java.util.*

/**
 * ## ConverterEquivalentOptional (可选等量转换器)
 *
 * @see [Converter]
 * @see [ConverterEquivalent]
 * @see [ConverterEquivalentIgnoreNull]
 * @author ProtocolLib, lgou2w
 * @since 2.0
 * @param T Specific type.
 * @param T 具体类型.
 */
interface ConverterEquivalentOptional<T> : Converter {

    /**
     * * Converts a given specific type object to a optional generic object.
     * * 将给定的具体类型对象转换为可选通用对象.
     *
     * @param specific Specific object.
     * @param specific 具体对象.
     */
    fun getGeneric(specific: T?): Optional<Any>

    /**
     * * Converts a given generic type object to a optional specific object.
     * * 将给定的通用类型对象转换为可选具体对象.
     *
     * @param generic Generic object.
     * @param generic 通用对象.
     */
    fun getSpecific(generic: Any?): Optional<T>

    /**
     * * Get the specific type class for this equivalent converter.
     * * 获取此等量转换器的具体类型类.
     */
    fun getSpecificType(): Class<T>
}
