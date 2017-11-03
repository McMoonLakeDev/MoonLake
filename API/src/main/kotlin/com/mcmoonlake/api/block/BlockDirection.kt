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

package com.mcmoonlake.api.block

import com.mcmoonlake.api.Valuable

enum class BlockDirection : Valuable<Int> {

    /**
     * Block Direction: Down (方块方向: 下面)
     */
    DOWN,
    /**
     * Block Direction: Up (方块方向: 上面)
     */
    UP,
    /**
     * Block Direction: North (方块方向: 北面)
     */
    NORTH,
    /**
     * Block Direction: SOUTH (方块方向: 南面)
     */
    SOUTH,
    /**
     * Block Direction: WEST (方块方向: 西面)
     */
    WEST,
    /**
     * Block Direction: EAST (方块方向: 东面)
     */
    EAST,
    ;

    override fun value(): Int
            = ordinal
}
