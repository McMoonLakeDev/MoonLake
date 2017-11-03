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

package com.mcmoonlake.api.packet

import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatComponentText
import com.mcmoonlake.api.currentBukkitVersion
import com.mcmoonlake.api.isOrLater
import com.mcmoonlake.api.version.MinecraftBukkitVersion

data class PacketOutTitle(var action: Action, var title: ChatComponent?, var fadeIn: Int, var stay: Int, var fadeOut: Int) : PacketOutBukkitAbstract("PacketPlayOutTitle") {

    constructor(fadeIn: Int, stay: Int, fadeOut: Int) : this(Action.TIMES, null, fadeIn, stay, fadeOut)
    constructor(action: Action, title: ChatComponent?) : this(action, title, -1, -1, -1)

    @Deprecated("")
    constructor() : this(Action.CLEAR, null, -1, -1, -1)

    init {
        if(action == Action.ACTIONBAR && !currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_11_R1))
            throw IllegalArgumentException("标题交互类型 ${Action.ACTIONBAR} 不支持您的服务端版本, 最低需要 1.11.0 版本.")
    }

    override fun read(data: PacketBuffer) {
        action = Action.fromId(data.readVarInt())
        if(action == Action.TITLE || action == Action.SUBTITLE || action == Action.ACTIONBAR)
            title = data.readChatComponent()
        if(action == Action.TIMES) {
            fadeIn = data.readInt()
            stay = data.readInt()
            fadeOut = data.readInt()
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.getId())
        if(action == Action.TITLE || action == Action.SUBTITLE || action == Action.ACTIONBAR)
            data.writeChatComponent(title ?: ChatComponentText())
        if(action == Action.TIMES) {
            data.writeInt(fadeIn)
            data.writeInt(stay)
            data.writeInt(fadeOut)
        }
    }

    enum class Action {

        /**
         * Title Action: Main Title (标题交互: 主标题)
         */
        TITLE,
        /**
         * Title Action: Sub Title (标题交互: 子标题)
         */
        SUBTITLE,
        /**
         * Title Action: Action Bar (标题交互: 交互栏)
         * * Only valid at 1.11 or higher.
         * * 仅在 1.11 或更高版本有效.
         */
        ACTIONBAR,
        /**
         * Title Action: Times (标题交互: 时间)
         */
        TIMES,
        /**
         * Title Action: Clear (标题交互: 清除)
         */
        CLEAR,
        /**
         * Title Action: Reset (标题交互: 重置)
         */
        RESET,
        ;

        fun getId(): Int
                = ID_MAP.entries.first { it.value == this }.key

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
