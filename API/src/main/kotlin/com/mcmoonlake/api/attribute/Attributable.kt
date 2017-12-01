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

package com.mcmoonlake.api.attribute

import com.mcmoonlake.api.player.MoonLakePlayer

/**
 * ## Attributable (可属性能力)
 *
 * * Get the attribute object from the given attribute type.
 * * 从给定的属性类型获取属性对象.
 *
 * @see [Attribute]
 * @see [AttributeType]
 * @see [MoonLakePlayer]
 * @author lgou2w
 * @since 2.0
 */
interface Attributable {

    /**
     * * Get the attribute object from the given attribute type.
     * * 从给定的属性类型获取属性对象.
     *
     * @param type Attribute type.
     * @param type 属性类型.
     */
    fun getAttribute(type: AttributeType): Attribute
}
