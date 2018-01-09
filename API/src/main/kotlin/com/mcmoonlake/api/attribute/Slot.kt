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

import com.mcmoonlake.api.Valuable

/**
 * ## Slot (槽位)
 *
 * * Enumeration can be used for attribute item stack modifier takes effect in which slot.
 * * 枚举可用于属性物品修改器的生效槽位.
 *
 * @see [AttributeItemModifier]
 * @author lgou2w
 * @since 2.0
 */
enum class Slot(
        /**
         * * Enum type name.
         * * 枚举类型名称.
         */
        val type: String

) : Valuable<String> {

    /**
     * * Attribute Slot: Main hand
     * * 属性部位: 主手
     */
    MAIN_HAND("mainhand"),
    /**
     * * Attribute Slot: Off hand
     * * 属性部位: 副手
     */
    OFF_HAND("offhand"),
    /**
     * * Attribute Slot: Head
     * * 属性部位: 头
     */
    HEAD("head"),
    /**
     * * Attribute Slot: Legs
     * * 属性部位: 腿
     */
    LEGS("legs"),
    /**
     * * Attribute Slot: Chest
     * * 属性部位: 胸
     */
    CHEST("chest"),
    /**
     * * Attribute Slot: Feet
     * * 属性部位: 脚
     */
    FEET("feet"),
    ;

    override fun value(): String
            = type
}
