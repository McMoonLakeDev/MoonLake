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

enum class Slot(val type: String) : Valuable<String> {

    MAIN_HAND("mainhand"), 		主手(MAIN_HAND),
    OFF_HAND("offhand"), 			副手(OFF_HAND),
    HEAD("head"), 							头部(HEAD),
    LEGS("legs"), 							腿部(LEGS),
    CHEST("chest"), 						胸部(CHEST),
    FEET("feet"), 							脚部(FEET),
    ;

    constructor(equivalent: Slot) : this(equivalent.type)

    override fun value(): String
            = type
}
