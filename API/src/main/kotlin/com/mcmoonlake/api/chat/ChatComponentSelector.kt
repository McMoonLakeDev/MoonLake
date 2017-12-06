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
 * ## ChatComponentSelector (聊天组件选择器)
 *
 * @see [ChatComponent]
 * @see [ChatComponentAbstract]
 * @author lgou2w
 * @since 2.0
 * @constructor ChatComponentSelector
 * @param selector Selector.
 * @param selector 选择器.
 */
open class ChatComponentSelector(
        /**
         * * Gets or sets the selector object for this chat component selector.
         * * 获取或设置此聊天组件选择器的选择器对象.
         */
        var selector: String) : ChatComponentAbstract() {

    /**
     * @see [ChatComponentSelector.selector]
     */
    fun setSelector(selector: String): ChatComponentSelector
            { this.selector = selector; return this; }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentSelector)
            return super.equals(other) && selector == other.selector
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + selector.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentSelector(selector='$selector', style=$style, extras=$extras)"
    }
}
