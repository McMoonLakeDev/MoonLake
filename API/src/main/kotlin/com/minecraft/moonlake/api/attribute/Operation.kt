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

package com.minecraft.moonlake.api.attribute

import com.minecraft.moonlake.api.Valuable

enum class Operation(val value: Int) : Valuable<Int> {

    ADD(0), 						增加(ADD),
    MULTIPLY(1), 				百分比(MULTIPLY),
    MULTIPLY_INC(2), 		百分比增加(MULTIPLY_INC),
    ;

    constructor(equivalent: Operation) : this(equivalent.value)

    override fun value(): Int
            = value
}