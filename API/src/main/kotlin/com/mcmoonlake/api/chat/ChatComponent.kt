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

interface ChatComponent {

    var style: ChatStyle

    fun setStyle(style: ChatStyle?): ChatComponent

    val extras: MutableList<ChatComponent>

    val extraSize: Int

    fun append(text: String): ChatComponent

    fun append(extra: ChatComponent): ChatComponent

    fun toJson(): String

    fun toRaw(color: Boolean = true): String

    companion object {
        /**
         * * Null of ChatComponent
         */
        val NULL: ChatComponent by lazy {
            object: ChatComponent {
                override var style: ChatStyle
                    get() = throw UnsupportedOperationException()
                    set(value) = throw UnsupportedOperationException()
                override fun setStyle(style: ChatStyle?): ChatComponent
                        = throw UnsupportedOperationException()
                override val extras: MutableList<ChatComponent>
                    get() = throw UnsupportedOperationException()
                override val extraSize: Int
                    get() = 0
                override fun append(text: String): ChatComponent
                        = throw UnsupportedOperationException()
                override fun append(extra: ChatComponent): ChatComponent
                        = throw UnsupportedOperationException()
                override fun toJson(): String
                        = throw UnsupportedOperationException()
                override fun toRaw(color: Boolean): String
                        = throw UnsupportedOperationException()
                override fun toString(): String
                        = "ChatComponent(NULL)"
                override fun hashCode(): Int
                        = 0
                override fun equals(other: Any?): Boolean
                        = other === NULL
            }
        }
    }
}
