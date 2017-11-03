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

import com.mcmoonlake.api.notNull

abstract class ChatComponentAbstract : ChatComponent {

    private var _style: ChatStyle? = null
    private var _extras: MutableList<ChatComponent> = ArrayList()

    override var style: ChatStyle
        get() {
            if(_style == null) {
                _style = ChatStyle()
                extras.forEach { it.style.setParent(style) }
            }
            return _style.notNull()
        }
        set(value) {
            _style = value
            extras.forEach { it.style.setParent(style) }
        }

    override fun setStyle(style: ChatStyle?): ChatComponent {
        _style = style
        extras.forEach { it.style.setParent(style) }
        return this
    }

    override val extras: MutableList<ChatComponent>
        get() = _extras

    override val extraSize: Int
        get() = extras.size

    override fun append(text: String): ChatComponent
            = append(ChatComponentText(text))

    override fun append(extra: ChatComponent): ChatComponent {
        extra.style.setParent(style)
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
            return style == other.style && extras == other.extras
        return false
    }

    override fun hashCode(): Int {
        var result = _style?.hashCode() ?: 0
        result = 31 * result + extras.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentAbstract(style=$style, extras=$extras)"
    }
}
