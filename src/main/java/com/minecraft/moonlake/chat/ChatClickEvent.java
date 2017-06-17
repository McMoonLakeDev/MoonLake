/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.chat;

/**
 * <h1>ChatClickEvent</h1>
 * 聊天组件点击事件
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatClickEvent {

    private final Action action;
    private final String value;

    /**
     * 聊天组件点击事件构造函数
     *
     * @param action 交互类型
     * @param value 值
     */
    public ChatClickEvent(Action action, String value) {
        this.action = action;
        this.value = value;
    }

    /**
     * 获取此聊天组件点击事件的交互类型
     *
     * @return 交互类型
     */
    public Action getAction() {
        return action;
    }

    /**
     * 获取此聊天组件点击事件的值
     *
     * @return 值
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatClickEvent) {
            ChatClickEvent other = (ChatClickEvent) obj;
            return action == other.action && value != null ? value.equals(other.value) : other.value == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatClickEvent{" +
                "action=" + action +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * <h1>Action</h1>
     * 聊天组件点击事件交互类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum Action {

        /**
         * 交互类型: 打开链接
         */
        OPEN_URL,
        /**
         * 交互类型: 打开文件
         */
        OPEN_FILE,
        /**
         * 交互类型: 执行命令
         */
        RUN_COMMAND,
        /**
         * 交互类型: 替换命令
         */
        SUGGEST_COMMAND,
        /**
         * 交互类型: 改变页面
         */
        CHANGE_PAGE,
        ;

        /**
         * 从名称返回聊天组件点击事件交互类型
         *
         * @param name 名称
         * @return Action
         */
        public static Action fromName(String name) {
            return Action.valueOf(name.toUpperCase());
        }
    }
}
