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

import com.mcmoonlake.api.chat.ChatHoverEvent.Action

/**
 * ## ChatHoverEvent (聊天移动事件)
 *
 * @see [ChatComponent]
 * @see [ChatStyle]
 * @see [ChatStyle.setHoverEvent]
 * @author lgou2w
 * @since 2.0
 * @param action Hover action type.
 * @param action 点击交互类型.
 * @param value Action value.
 * @param value 交互值.
 */
data class ChatHoverEvent(
        /**
         * * The action type of this chat hover event.
         * * 此聊天移动事件的交互类型.
         *
         * @see [Action]
         */
        val action: Action,
        /**
         * * The action value of this chat hover event.
         * * 此聊天移动事件的交互值.
         */
        val value: ChatComponent) {

    /**
     * ## Action (交互类型)
     *
     * @see [ChatHoverEvent]
     * @author lgou2w
     * @since 2.0
     */
    enum class Action {

        /**
         * * Chat Hover Event: Show Text
         * * 聊天移动事件: 显示文本
         */
        SHOW_TEXT,
        /**
         * * Chat Hover Event: Show Achievement
         * * 聊天移动事件: 显示成就
         */
        SHOW_ACHIEVEMENT,
        /**
         * * Chat Hover Event: Show Item
         * * 聊天移动事件: 显示物品栈
         */
        SHOW_ITEM,
        /**
         * * Chat Hover Event: Show Entity
         * * 聊天移动事件: 显示实体
         */
        SHOW_ENTITY,
        ;
    }
}
