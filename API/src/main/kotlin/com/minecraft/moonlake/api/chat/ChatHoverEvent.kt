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

package com.minecraft.moonlake.api.chat

data class ChatHoverEvent(val action: Action, val value: ChatComponent) {

    enum class Action {

        /**
         * Chat Hover Event: Show Text (聊天移动事件: 显示文本)
         */
        SHOW_TEXT,
        /**
         * Chat Hover Event: Show Achievement (聊天移动事件: 显示成就)
         */
        SHOW_ACHIEVEMENT,
        /**
         * Chat Hover Event: Show Item (聊天移动事件: 显示物品栈)
         */
        SHOW_ITEM,
        /**
         * Chat Hover Event: Show Entity (聊天移动事件: 显示实体)
         */
        SHOW_ENTITY,
        ;
    }
}
