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
 * ## ChatComponentScore (聊天组件分数)
 *
 * @see [ChatComponent]
 * @see [ChatComponentAbstract]
 * @author lgou2w
 * @since 2.0
 * @constructor ChatComponentScore
 * @param name Score name.
 * @param name 分数名.
 * @param objective Score Objective.
 * @param objective 分数目标.
 * @param value Score value.
 * @param value 分数值.
 */
open class ChatComponentScore(
        /**
         * * Gets or sets the score name object for this chat component score.
         * * 获取或设置此聊天组件分数的分数名对象.
         */
        var name: String,
        /**
         * * Gets or sets the score objective object for this chat component score.
         * * 获取或设置此聊天组件分数的分数目标对象.
         */
        var objective: String,
        /**
         * * Gets or sets the score value object for this chat component score.
         * * 获取或设置此聊天组件分数的分数值对象.
         */
        var value: String? = null

) : ChatComponentAbstract() {

    /**
     * @see [ChatComponentScore.name]
     */
    fun setName(name: String): ChatComponentScore
            { this.name = name; return this; }

    /**
     * @see [ChatComponentScore.objective]
     */
    fun setObjective(objective: String): ChatComponentScore
            { this.objective = objective; return this; }

    /**
     * @see [ChatComponentScore.value]
     */
    fun setValue(value: String?): ChatComponentScore
            { this.value = value; return this; }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentScore)
            return super.equals(other) && name == other.name && objective == other.objective && value == other.value
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + objective.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentScore(name='$name', objective='$objective', value=$value, style=$style, extras=$extras)"
    }
}
