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

enum class Operation(val value: Int) : Valuable<Int> {

    /**
     * Attribute Operation Mode: Add (属性运算模式: 增加)
     * - Increment X by Amount
     */
    ADD(0),
    /**
     * Attribute Operation Mode: Multiply (属性运算模式: 百分比)
     * - Increment Y by X * Amount
     */
    MULTIPLY(1),
    /**
     * Attribute Operation Mode: Multiply Inc (属性运算模式: 百分比增加)
     * - Y = Y * (1 + Amount) (equivalent to Increment Y by Y * Amount)
     */
    MULTIPLY_INC(2),
    ;

    override fun value(): Int
            = value
}
