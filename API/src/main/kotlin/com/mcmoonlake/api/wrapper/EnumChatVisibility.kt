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

package com.mcmoonlake.api.wrapper

import com.mcmoonlake.api.Valuable

enum class EnumChatVisibility(
        val value: Int
) : Valuable<Int> {

    /**
     * Enum Chat Visibility: Full (聊天可见度: 全部)
     */
    FULL(0),
    /**
     * Enum Chat Visibility: System (聊天可见度: 系统)
     */
    SYSTEM(1),
    /**
     * Enum Chat Visibility: Hidden (聊天可见度: 隐藏)
     */
    HIDDEN(2),
    ;

    override fun value(): Int
            = value
}
