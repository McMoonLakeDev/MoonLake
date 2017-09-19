/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.anvil

import com.minecraft.moonlake.api.Valuable

enum class AnvilWindowSlot(val slot: Int) : Valuable<Int> {

    /** enums */

    INPUT_LEFT(0),          左输入栏(INPUT_LEFT),
    INPUT_RIGHT(1),       右输入栏(INPUT_RIGHT),
    OUTPUT(2),                 输出栏(OUTPUT),
    ;

    constructor(equivalent: AnvilWindowSlot) : this(equivalent.slot)

    override fun value(): Int
            = slot

    /** static */

    companion object {

        @JvmStatic
        @JvmName("fromSlot")
        fun fromSlot(slot: Int): AnvilWindowSlot? = when(slot) {
            0 -> INPUT_LEFT
            1 -> INPUT_RIGHT
            2 -> OUTPUT
            else -> null
        }
    }
}
