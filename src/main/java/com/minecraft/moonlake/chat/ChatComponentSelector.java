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

import com.minecraft.moonlake.validate.Validate;

/**
 * <h1>ChatComponentSelector</h1>
 * 聊天选择器组件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatComponentSelector extends ChatComponentBase {

    private String selector;

    /**
     * 聊天选择器组件类构造函数
     */
    public ChatComponentSelector() {
    }

    /**
     * 聊天选择器组件类构造函数
     *
     * @param selector 选择器
     */
    public ChatComponentSelector(String selector) {
        this.selector = selector;
    }

    /**
     * 获取此聊天选择器组件的选择器
     *
     * @return 选择器
     */
    public String getSelector() {
        return selector;
    }

    /**
     * 设置此聊天选择器组件的选择器
     *
     * @param selector 选择器
     * @throws IllegalArgumentException 如果选择器对象为 {@code null} 则抛出异常
     */
    public ChatComponentSelector setSelector(String selector) {
        if(Validate.checkNotNull(selector).startsWith("@"))
            this.selector = selector;
        else
            this.selector = "@" + selector;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatComponentSelector) {
            ChatComponentSelector other = (ChatComponentSelector) obj;
            return super.equals(obj) && selector != null ? selector.equals(other.selector) : other.selector == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (selector != null ? selector.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatComponentScore{" +
                "selector='" + selector + '\'' +
                ", style=" + getStyle() +
                ", extras=" + getExtras() +
                '}';
    }
}
