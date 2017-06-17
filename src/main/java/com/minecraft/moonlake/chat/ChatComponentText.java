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
 * <h1>ChatComponentText</h1>
 * 聊天文本组件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatComponentText extends ChatComponentBase {

    private String text;

    /**
     * 聊天文本组件类构造函数
     *
     * @param text 文本
     */
    public ChatComponentText(String text) {
        this.text = text;
    }

    /**
     * 聊天文本组件类构造函数
     *
     * @param text 文本组件
     * @throws IllegalArgumentException 如果文本组件对象为 {@code null} 则抛出异常
     */
    public ChatComponentText(ChatComponentText text) {
        this.text = Validate.checkNotNull(text).getText();
    }

    /**
     * 获取此聊天文本组件的文本内容
     *
     * @return 文本内容
     */
    public String getText() {
        return text;
    }

    /**
     * 设置此聊天文本组件的文本内容
     *
     * @param text 文本内容
     */
    public ChatComponentText setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatComponentText) {
            ChatComponentText other = (ChatComponentText) obj;
            return super.equals(obj) && text != null ? text.equals(other.text) : other.text == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatComponentText{" +
                "text='" + text + '\'' +
                ", style=" + getStyle() +
                ", extras=" + getExtras() +
                '}';
    }
}
