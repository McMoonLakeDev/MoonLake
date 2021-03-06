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

/**
 * ## ConverterEquivalentIgnoreNull (忽略 Null 等量转换器)
 *
 * @see [Converter]
 * @see [ConverterEquivalent]
 * @see [ConverterEquivalentOptional]
 * @author ProtocolLib, lgou2w
 * @since 2.0
 */
interface ConverterEquivalentIgnoreNull<T> : ConverterEquivalent<T> {

    override fun getGeneric(specific: T?): Any?
            = if(specific == null) null else getGenericValue(specific)

    override fun getSpecific(generic: Any?): T?
            = if(generic == null) null else getSpecificValue(generic)

    /**
     * @see [getGeneric]
     */
    fun getGenericValue(specific: T): Any

    /**
     * @see [getSpecific]
     */
    fun getSpecificValue(generic: Any): T
}
