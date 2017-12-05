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

/**
 * ## ChatAction (聊天交互)
 *
 * * Enum minecraft chat action position.
 * * 枚举 Minecraft 聊天交互位置.
 *
 * @see [Valuable]
 * @author lgou2w
 * @since 2.0
 */
enum class ChatAction(
        /**
         * * Enum value.
         * * 枚举值.
         */
        val value: Int) : Valuable<Int> {

    /**
     * * Chat Action: Chat
     * * 聊天交互: 聊天栏
     */
    CHAT(0),
    /**
     * * Chat Action: System
     * * 聊天交互: 系统栏
     */
    SYSTEM(1),
    /**
     * * Chat Action: Action Bar
     * * 聊天交互: 交互栏
     */
    ACTIONBAR(2),
    ;

    override fun value(): Int
            = value

    /** static */

    companion object {

        /**
         *
         */
        @JvmStatic
        @JvmName("fromValue")
        fun fromValue(value: Int): ChatAction = when(value) {
            0 -> CHAT
            1 -> SYSTEM
            2 -> ACTIONBAR
            else -> CHAT // else default chat
        }
    }
}
