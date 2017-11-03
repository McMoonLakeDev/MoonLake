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

open class ChatComponentScore(private var name: String, private var objective: String) : ChatComponentAbstract() {

    private var value: String? = null

    fun getName(): String
            = name

    fun setName(name: String): ChatComponentScore
            { this.name = name; return this; }

    fun getObjective(): String
            = objective

    fun setObjective(objective: String): ChatComponentScore
            { this.objective = objective; return this; }

    fun getValue(): String?
            = value

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
