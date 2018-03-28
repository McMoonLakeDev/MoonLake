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

package com.mcmoonlake.api.item

import com.mcmoonlake.api.Valuable

/**
 * ## Generation (书本代)
 *
 * * Enumerate generations of books in Minecraft.
 * * 枚举 Minecraft 中书本的代.
 *
 * @see [Valuable]
 * @author lgou2w
 * @since 2.0
 */
enum class Generation : Valuable<Int> {

    /**
     * * Book Generation: Original
     * * 书本代: 原作
     */
    ORIGINAL,
    /**
     * * Book Generation: Copy of Original
     * * 书本代: 原作的副本
     */
    COPY_OF_ORIGINAL,
    /**
     * * Book Generation: Copy of Copy
     * * 书本代: 副本的副本
     */
    COPY_OF_COPY,
    /**
     * * Book Generation: Tattered
     * * 书本代: 破烂的
     */
    TATTERED,
    ;

    override fun value(): Int
            = ordinal
}
