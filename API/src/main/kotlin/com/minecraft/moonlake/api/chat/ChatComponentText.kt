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

open class ChatComponentText : ChatComponentAbstract {

    private var text: String

    constructor() : this("null")

    constructor(text: String) : super() {
        this.text = text
    }

    constructor(text: ChatComponentText) : super() {
        this.text = text.text
    }

    fun getText(): String
            = text

    fun setText(text: String): ChatComponentText
            { this.text = text; return this; }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentText)
            return super.equals(other) && text == other.text
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentText(text='$text', style=${getStyle()}, extras=${getExtras()})"
    }
}
