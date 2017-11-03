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

package com.mcmoonlake.api.chat

import com.mcmoonlake.api.Valuable

enum class ChatAction(val value: Byte) : Valuable<Byte> {

    /**
     * Chat Action: Chat (聊天类型: 聊天)
     */
    CHAT(0),
    /**
     * Chat Action: System (聊天类型: 系统)
     */
    SYSTEM(1),
    /**
     * Chat Action: Action Bar (聊天类型: 交互栏)
     */
    ACTIONBAR(2),
    ;

    override fun value(): Byte
            = value

    /** static */

    companion object {

        @JvmStatic
        @JvmName("fromValue")
        fun fromValue(value: Byte): ChatAction = when(value.toInt()) {
            0 -> CHAT
            1 -> SYSTEM
            2 -> ACTIONBAR
            else -> CHAT // else default chat
        }
    }
}
