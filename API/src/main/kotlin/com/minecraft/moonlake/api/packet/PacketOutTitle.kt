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

package com.minecraft.moonlake.api.packet

import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatComponentText
import com.minecraft.moonlake.api.currentBukkitVersion
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion

data class PacketOutTitle(var action: Action, var title: ChatComponent?, var fadeIn: Int, var stay: Int, var fadeOut: Int) : PacketOutBukkitAbstract("PacketPlayOutTitle") {

    constructor(fadeIn: Int, stay: Int, fadeOut: Int) : this(Action.TIMES, null, fadeIn, stay, fadeOut)
    constructor(action: Action, title: ChatComponent?) : this(action, title, -1, -1, -1)
    constructor() : this(Action.CLEAR, null, -1, -1, -1)

    init {
        if(action == Action.ACTIONBAR && !currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_11_R1))
            throw IllegalArgumentException("标题交互类型 ${Action.ACTIONBAR} 不支持您的服务端版本, 最低需要 1.11.0 版本.")
    }

    override fun read(data: PacketBuffer) {
        action = Action.fromId(data.readVarInt())
        if(action.isTitle())
            title = data.readChatComponent()
        if(action.isTimes()) {
            fadeIn = data.readInt()
            stay = data.readInt()
            fadeOut = data.readInt()
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.getId())
        if(action.isTitle())
            data.writeChatComponent(title ?: ChatComponentText("null"))
        if(action.isTimes()) {
            data.writeInt(fadeIn)
            data.writeInt(stay)
            data.writeInt(fadeOut)
        }
    }

    enum class Action(private val equivalent: Action? = null) {
        TITLE,                      主标题(TITLE),
        SUBTITLE,             子标题(SUBTITLE),
        ACTIONBAR,       交互栏(ACTIONBAR),
        TIMES,                    时间(TIMES),
        CLEAR,                  清除(CLEAR),
        RESET,                   重置(RESET),
        ;

        fun getId(): Int
                = equivalent?.getId() ?: ID_MAP.entries.first { it.value == this }.key

        fun isTitle(): Boolean
                = equivalent?.isTitle() ?: (this == TITLE || this == SUBTITLE)

        fun isTimes(): Boolean
                = equivalent?.isTimes() ?: (this == TIMES)

        companion object {

            @JvmStatic
            private val ID_MAP: MutableMap<Int, Action> = HashMap()

            init {
                val v11 = currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_11_R1)
                ID_MAP.put(0, TITLE)
                ID_MAP.put(1, SUBTITLE)
                ID_MAP.put(2, if(v11) ACTIONBAR else TIMES)
                ID_MAP.put(3, if(v11) TIMES else CLEAR)
                ID_MAP.put(4, if(v11) CLEAR else RESET)
                if(v11) ID_MAP.put(5, RESET)
            }

            @JvmStatic
            @JvmName("fromId")
            fun fromId(id: Int): Action
                    = ID_MAP[id] ?: CLEAR
        }
    }
}
