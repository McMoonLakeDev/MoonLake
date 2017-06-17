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


package com.minecraft.moonlake.api.chat.adapter;

import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.validate.Validate;

/**
 * <h1>ChatObjectAdapter</h1>
 * 聊天组件对象适配器类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatObjectAdapter implements ChatAdapter {

    private final Object obj;

    /**
     * 聊天组件对象适配器类构造函数
     *
     * @param chatComponent 聊天组件对象
     */
    public ChatObjectAdapter(ChatComponent chatComponent) {
        this.obj = chatComponent;
    }

    /**
     * 聊天组件对象适配器类构造函数
     *
     * @param icbc IChatBaseComponent
     * @throws IllegalArgumentException 如果对象不是 {@code IChatBaseComponent} 的实例则抛出异常
     */
    public ChatObjectAdapter(Object icbc) {
        Validate.isTrue(MinecraftReflection.isICBC(icbc), "The object is not IChatBaseComponent instance: " + icbc);
        this.obj = icbc;
    }

    @Override
    public ChatComponent toChatComponent() {
        return obj instanceof ChatComponent ? (ChatComponent) obj : ChatSerializer.chatComponentFromICBC(obj);
    }

    @Override
    public Object toIChatBaseComponent() {
        return obj instanceof ChatComponent ? ChatSerializer.iCBCFromChatComponent((ChatComponent) obj) : obj;
    }
}
