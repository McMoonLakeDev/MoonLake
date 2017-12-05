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

/**
 * ## ChatClickEvent (聊天点击事件)
 *
 * @see [ChatComponent]
 * @see [ChatStyle]
 * @see [ChatStyle.setClickEvent]
 * @author lgou2w
 * @since 2.0
 * @param action Click action type.
 * @param action 点击交互类型.
 * @param value Action value.
 * @param value 交互值.
 */
data class ChatClickEvent(
        /**
         * * The action type of this chat click event.
         * * 此聊天点击事件的交互类型.
         *
         * @see [Action]
         */
        val action: Action,
        /**
         * * The action value of this chat click event.
         * * 此聊天点击事件的交互值.
         */
        val value: String) {

    /**
     * ## Action (交互类型)
     *
     * @see [ChatClickEvent]
     * @author lgou2w
     * @since 2.0
     */
    enum class Action {

        /**
         * * Chat Click Type: Open URL
         * * 聊天点击类型: 打开链接
         */
        OPEN_URL,
        /**
         * * Chat Click Type: Open File
         * * 聊天点击类型: 打开文件
         */
        OPEN_FILE,
        /**
         * * Chat Click Type: Suggest Command
         * * 聊天点击类型: 提示命令
         */
        SUGGEST_COMMAND,
        /**
         * * Chat Click Type: Run Command
         * * 聊天点击类型: 执行命令
         */
        RUN_COMMAND,
        /**
         * * Chat Click Type: Change Page
         * * 聊天点击类型: 改变页面
         */
        CHANGE_PAGE,
        ;
    }
}
