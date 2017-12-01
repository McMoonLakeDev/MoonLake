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

/**
 * ## Attribute (属性)
 *
 * @see [Attributable]
 * @author lgou2w
 * @since 2.0
 */
interface Attribute {

    /**
     * * Get the type of this attribute.
     * * 获取此属性的类型.
     *
     * @see [AttributeType]
     */
    val type: AttributeType

    /**
     * * Get the default value of this attribute.
     * * 获取此属性的默认值
     *
     * @see [AttributeType.def]
     */
    val defValue: Double

    /**
     * * Gets or sets the base value of this attribute.
     * * 获取或设置此属性的基础值.
     */
    var baseValue: Double

    /**
     * * Get the value of this attribute after all associated modifiers have been applied.
     * * 在应用了所有关联的修改器后, 获取此属性的最终值.
     */
    val value: Double

    /**
     * * Get all modifiers present on this attribute.
     * * 获取此属性的所有修改器.
     */
    val modifiers: Collection<AttributeModifier>

    /**
     * * Add a modifier to this attribute.
     * * 添加一个修改器到此属性.
     *
     * @param modifier Modifier.
     * @param modifier 修改器.
     * @see [AttributeModifier]
     */
    fun addModifier(modifier: AttributeModifier)

    /**
     * * Remove a modifier from this attribute.
     * * 从此属性删除一个修改器.
     *
     * @param modifier Modifier.
     * @param modifier 修改器.
     * @see [AttributeModifier]
     */
    fun removeModifier(modifier: AttributeModifier)
}
