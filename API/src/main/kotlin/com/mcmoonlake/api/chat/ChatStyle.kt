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
 * ## ChatStyle (聊天样式)
 *
 * @author lgou2w
 * @since 2.0
 */
open class ChatStyle {

    /** member */

    private var parent: ChatStyle? = null

    internal var color: ChatColor? = null
    internal var bold: Boolean? = null
    internal var italic: Boolean? = null
    internal var underlined: Boolean? = null
    internal var strikethrough: Boolean? = null
    internal var obfuscated: Boolean? = null
    internal var clickEvent: ChatClickEvent? = null
    internal var hoverEvent: ChatHoverEvent? = null
    internal var insertion: String? = null

    /** static */

    companion object {

        @JvmStatic
        private val ROOT = object: ChatStyle() {
            override fun getColor(): ChatColor?
                    = null
            override fun getBold(): Boolean?
                    = false
            override fun getItalic(): Boolean?
                    = false
            override fun getStrikethrough(): Boolean?
                    = false
            override fun getUnderlined(): Boolean?
                    = false
            override fun getObfuscated(): Boolean?
                    = false
            override fun getClickEvent(): ChatClickEvent?
                    = null
            override fun getHoverEvent(): ChatHoverEvent?
                    = null
            override fun getInsertion(): String?
                    = null
            override fun setParent(parent: ChatStyle?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setColor(color: ChatColor?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setBold(bold: Boolean?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setItalic(italic: Boolean?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setStrikethrough(strikethrough: Boolean?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setUnderlined(underlined: Boolean?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setObfuscated(obfuscated: Boolean?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setClickEvent(clickEvent: ChatClickEvent?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setHoverEvent(hoverEvent: ChatHoverEvent?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun setInsertion(insertion: String?): ChatStyle
                    = throw UnsupportedOperationException()
            override fun toString(): String
                    = "ChatStyle.ROOT"
        }
    }

    private fun getParent(): ChatStyle
            = parent ?: ROOT

    open fun getColor(): ChatColor?
            = color ?: getParent().getColor()

    open fun getBold(): Boolean?
            = bold ?: getParent().getBold()

    open fun getItalic(): Boolean?
            = italic ?: getParent().getItalic()

    open fun getStrikethrough(): Boolean?
            = strikethrough ?: getParent().getStrikethrough()

    open fun getUnderlined(): Boolean?
            = underlined ?: getParent().getUnderlined()

    open fun getObfuscated(): Boolean?
            = obfuscated ?: getParent().getObfuscated()

    open fun getClickEvent(): ChatClickEvent?
            = clickEvent ?: getParent().getClickEvent()

    open fun getHoverEvent(): ChatHoverEvent?
            = hoverEvent ?: getParent().getHoverEvent()

    open fun getInsertion(): String?
            = insertion ?: getParent().getInsertion()

    open fun setParent(parent: ChatStyle?): ChatStyle
            { this.parent = parent; return this; }

    open fun setColor(color: ChatColor?): ChatStyle
            { this.color = color; return this; }

    open fun setBold(bold: Boolean?): ChatStyle
            { this.bold = bold; return this; }

    open fun setItalic(italic: Boolean?): ChatStyle
            { this.italic = italic; return this; }

    open fun setStrikethrough(strikethrough: Boolean?): ChatStyle
            { this.strikethrough = strikethrough; return this; }

    open fun setUnderlined(underlined: Boolean?): ChatStyle
            { this.underlined = underlined; return this; }

    open fun setObfuscated(obfuscated: Boolean?): ChatStyle
            { this.obfuscated = obfuscated; return this; }

    open fun setClickEvent(clickEvent: ChatClickEvent?): ChatStyle
            { this.clickEvent = clickEvent; return this; }

    open fun setHoverEvent(hoverEvent: ChatHoverEvent?): ChatStyle
            { this.hoverEvent = hoverEvent; return this; }

    open fun setInsertion(insertion: String?): ChatStyle
            { this.insertion = insertion; return this; }

    fun isEmpty(): Boolean
            = color == null && bold == null && italic == null && strikethrough == null && underlined == null && obfuscated == null && clickEvent == null && hoverEvent == null && insertion == null

    fun clone(): ChatStyle {
        val copy = ChatStyle()
        copy.color = color
        copy.bold = bold
        copy.italic = italic
        copy.strikethrough = strikethrough
        copy.underlined = underlined
        copy.obfuscated = obfuscated
        copy.clickEvent = clickEvent
        copy.hoverEvent = hoverEvent
        copy.insertion = insertion
        return copy
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatStyle) {
            if(parent != other.parent) return false
            if(color != other.color) return false
            if(bold != other.bold) return false
            if(italic != other.italic) return false
            if(underlined != other.underlined) return false
            if(strikethrough != other.strikethrough) return false
            if(obfuscated != other.obfuscated) return false
            if(clickEvent != other.clickEvent) return false
            if(hoverEvent != other.hoverEvent) return false
            if(insertion != other.insertion) return false
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = parent?.hashCode() ?: 0
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + (bold?.hashCode() ?: 0)
        result = 31 * result + (italic?.hashCode() ?: 0)
        result = 31 * result + (underlined?.hashCode() ?: 0)
        result = 31 * result + (strikethrough?.hashCode() ?: 0)
        result = 31 * result + (obfuscated?.hashCode() ?: 0)
        result = 31 * result + (clickEvent?.hashCode() ?: 0)
        result = 31 * result + (hoverEvent?.hashCode() ?: 0)
        result = 31 * result + (insertion?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ChatStyle(parent=$parent, color=$color, bold=$bold, italic=$italic, underlined=$underlined, strikethrough=$strikethrough, obfuscated=$obfuscated, clickEvent=$clickEvent, hoverEvent=$hoverEvent, insertion=$insertion)"
    }
}
