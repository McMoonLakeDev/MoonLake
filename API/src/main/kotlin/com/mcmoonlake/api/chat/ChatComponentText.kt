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
 * ## ChatComponentText (聊天组件文本)
 *
 * @see [ChatComponent]
 * @see [ChatComponentAbstract]
 * @author lgou2w
 * @since 2.0
 * @constructor ChatComponentText
 * @param text Text.
 * @param text 文本.
 */
open class ChatComponentText(
        /**
         * * Gets or sets the text object for this chat component text.
         * * 获取或设置此聊天组件文本的文本对象.
         */
        var text: String) : ChatComponentAbstract() {

    /**
     * @constructor ChatComponentText
     *
     * * Using `null` string.
     * * 使用 `null` 字符串.
     */
    constructor() : this("null")

    /**
     * @constructor ChatComponentText
     *
     * @param text Chat component text.
     * @param text 聊天组件文本.
     */
    constructor(text: ChatComponentText) : this(text.text)

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
        return "ChatComponentText(text='$text', style=$style, extras=$extras)"
    }
}
