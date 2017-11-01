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

data class ChatClickEvent(val action: Action, val value: String) {

    enum class Action {

        /**
         * Chat Click Event: Open URL (聊天点击事件: 打开链接)
         */
        OPEN_URL,
        /**
         * Chat Click Event: Open File (聊天点击事件: 打开文件)
         */
        OPEN_FILE,
        /**
         * Chat Click Event: Suggest Command (聊天点击事件: 提示命令)
         */
        SUGGEST_COMMAND,
        /**
         * Chat Click Event: Run Command (聊天点击事件: 执行命令)
         */
        RUN_COMMAND,
        /**
         * Chat Click Event: Change Page (聊天点击事件: 改变页面)
         */
        CHANGE_PAGE,
        ;
    }
}
