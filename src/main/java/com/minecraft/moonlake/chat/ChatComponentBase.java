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

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ChatComponentBase</h1>
 * 聊天组件基础抽象实现类
 *
 * @version 1.0
 * @author Month_Light
 * @see ChatComponent
 */
public abstract class ChatComponentBase implements ChatComponent {

    private ChatStyle style;
    private List<ChatComponent> extras;

    /**
     * 聊天组件基础抽象实现类构造函数
     */
    ChatComponentBase() {
        this.extras = new ArrayList<>();
    }

    @Override
    public ChatComponent setStyle(ChatStyle style) {
        Validate.notNull(style, "The chat style object is null.");
        this.style = style;
        for(ChatComponent component : extras)
            component.getStyle().setParent(getStyle());
        return this;
    }

    @Override
    public ChatStyle getStyle() {
        if(style == null) {
            this.style = new ChatStyle();
            for(ChatComponent component : extras)
                component.getStyle().setParent(style);
        }
        return style;
    }

    @Override
    public final List<ChatComponent> getExtras() {
        return extras;
    }

    @Override
    public final int getExtraSize() {
        return extras.size();
    }

    @Override
    public ChatComponent append(String text) {
        Validate.notNull(text, "The text object is null.");
        return append(new ChatComponentText(text));
    }

    @Override
    public ChatComponent append(ChatComponent extra) {
        Validate.notNull(extra, "The extra component object is null.");
        extra.getStyle().setParent(getStyle());
        extras.add(extra);
        return this;
    }

    @Override
    public String toJson() {
        return ChatSerializer.toJson(this);
    }

    @Override
    public String toRaw() {
        return ChatSerializer.toRaw(this);
    }

    @Override
    public String toRaw(boolean color) {
        return ChatSerializer.toRaw(this, color);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatComponentBase) {
            ChatComponentBase other = (ChatComponentBase) obj;
            return getStyle().equals(other.getStyle()) && extras.equals(other.extras);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = style != null ? style.hashCode() : 0;
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatComponentBase{" +
                "style=" + style +
                ", extras=" + extras +
                '}';
    }
}
