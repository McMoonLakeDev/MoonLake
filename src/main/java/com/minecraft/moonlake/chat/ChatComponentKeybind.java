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
 * <h1>ChatComponentKeybind</h1>
 * 聊天热键绑定类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatComponentKeybind extends ChatComponentBase {

    private String keybind;

    /**
     * 聊天热键绑定类构造函数
     */
    public ChatComponentKeybind() {
    }

    /**
     * 聊天热键绑定类构造函数
     *
     * @param keybind 热键绑定
     */
    public ChatComponentKeybind(String keybind) {
        this.keybind = keybind;
    }

    /**
     * 获取此聊天热键绑定组件的热键绑定
     *
     * @return 热键绑定
     */
    public String getKeybind() {
        return keybind;
    }

    /**
     * 设置此聊天热键绑定组件的热键绑定
     *
     * @param keyBind 热键绑定
     */
    public ChatComponentKeybind setKeybind(String keyBind) {
        this.keybind = keyBind;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatComponentKeybind) {
            ChatComponentKeybind other = (ChatComponentKeybind) obj;
            return super.equals(obj) && keybind != null ? keybind.equals(other.keybind) : other.keybind == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (keybind != null ? keybind.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatComponentText{" +
                "keybind='" + keybind + '\'' +
                ", style=" + getStyle() +
                ", extras=" + getExtras() +
                '}';
    }
}
