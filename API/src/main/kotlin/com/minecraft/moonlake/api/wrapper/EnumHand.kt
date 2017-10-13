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

package com.minecraft.moonlake.api.wrapper

import com.minecraft.moonlake.api.Valuable

/**
 * Only applies to Minecraft 1.9+
 */
enum class EnumHand(val value: Int) : Valuable<Int> {

    /**
     * Enum Hand: Main (枚举手: 主)
     */
    MAIN(0),
    /**
     * Enum Hand: Off (枚举手: 副)
     */
    OFF(1),
    ;

    override fun value(): Int
            = value
}
