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

import com.minecraft.moonlake.api.Valuable

enum class ChatAction(val value: Byte) : Valuable<Byte> {

    CHAT(0),                 	聊天栏(CHAT),
    SYSTEM(1),            	系统聊天栏(SYSTEM),
    ACTIONBAR(2),   		交互栏(ACTIONBAR),
    ;

    constructor(equivalent: ChatAction) : this(equivalent.value)

    override fun value(): Byte
            = value
}
