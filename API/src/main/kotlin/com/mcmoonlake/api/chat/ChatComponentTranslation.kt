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
 * ## ChatComponentTranslation (聊天组件翻译)
 *
 * @see [ChatComponent]
 * @see [ChatComponentAbstract]
 * @author lgou2w
 * @since 2.0
 * @constructor ChatComponentTranslation
 * @param key Key.
 * @param key 键.
 * @param withs Parameters.
 * @param withs 参数.
 */
open class ChatComponentTranslation(
        /**
         * * Gets or sets the key object for this chat component translation.
         * * 获取或设置此聊天组件翻译的键对象.
         */
        var key: String,
        /**
         * * Gets or sets the withs list object for this chat component translation.
         * * 获取或设置此聊天组件翻译的参数列表对象.
         */
        var withs: MutableList<Any> = ArrayList()

) : ChatComponentAbstract() {

    /**
     * @see [ChatComponentTranslation.key]
     */
    fun setKey(key: String): ChatComponentTranslation
            { this.key = key; return this; }

    /**
     * @see [ChatComponentTranslation.withs]
     */
    fun addWiths(withs: Array<Any>): ChatComponentTranslation
            { this.withs.addAll(withs); return this; }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentTranslation)
            return super.equals(other) && key == other.key && withs == other.withs
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + key.hashCode()
        result = 31 * result + withs.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentTranslation(key='$key', withs=$withs, style=$style, extras=$extras)"
    }
}
