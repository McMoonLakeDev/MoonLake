/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

import com.minecraft.moonlake.api.notNull

abstract class ChatComponentAbstract : ChatComponent {

    private var style: ChatStyle? = null
    private var extras: MutableList<ChatComponent> = ArrayList()

    override fun getStyle(): ChatStyle {
        if(style == null) {
            style = ChatStyle()
            extras.forEach { it.getStyle().setParent(style) }
        }
        return style.notNull()
    }

    override fun setStyle(style: ChatStyle?): ChatComponent {
        this.style = style
        extras.forEach { it.getStyle().setParent(getStyle()) }
        return this
    }

    override final fun getExtras(): MutableList<ChatComponent>
            = extras

    override final fun getExtraSize(): Int
            = extras.size

    override fun append(text: String): ChatComponent
            = append(ChatComponentText(text))

    override fun append(extra: ChatComponent): ChatComponent {
        extra.getStyle().setParent(getStyle())
        extras.add(extra)
        return this
    }

    override fun toJson(): String
            = ChatSerializer.toJson(this)

    override fun toRaw(color: Boolean): String
            = ChatSerializer.toRaw(this, color)

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentAbstract)
            return getStyle() == other.getStyle() && extras == other.extras
        return false
    }

    override fun hashCode(): Int {
        var result = style?.hashCode() ?: 0
        result = 31 * result + extras.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentAbstract(style=$style, extras=$extras)"
    }
}
