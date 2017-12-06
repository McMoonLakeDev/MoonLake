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

import com.mcmoonlake.api.packet.PacketOutChat
import com.mcmoonlake.api.player.MoonLakePlayer
import org.bukkit.entity.Player

/**
 * ## ChatComponent (聊天组件)
 *
 * * `JSON` operations component for minecraft client and server chat communication. [Details](https://minecraft.gamepedia.com/Commands#Raw_JSON_text)
 * * Minecraft 客户端和服务端聊天通信的 `JSON` 操作组件. [详情](https://minecraft.gamepedia.com/Commands#Raw_JSON_text)
 *
 * @see [ChatComponentFancy]
 * @author lgou2w
 * @since 2.0
 */
interface ChatComponent {

    /**
     * * Gets or sets the style of this chat component.
     * * 获取或设置此聊天组件的样式.
     *
     * @see [setStyle]
     * @see [ChatStyle]
     */
    var style: ChatStyle

    /**
     * * Sets the style of this chat component. If `null` then use the default.
     * * 获取或设置此聊天组件的样式. 如果为 `null` 则使用默认.
     *
     * @param style Chat style.
     * @param style 聊天样式.
     * @see [ChatStyle]
     */
    fun setStyle(style: ChatStyle?): ChatComponent

    /**
     * * Get a list of extra component for this chat component.
     * * 获取此聊天组件的附加组件列表.
     */
    val extras: MutableList<ChatComponent>

    /**
     * * Get a size of extra component for this chat component.
     * * 获取此聊天组件的附加组件大小.
     */
    val extraSize: Int

    /**
     * * Appends the given string as a [ChatComponentText] to the list of extra component.
     * * 将给定字符串以 [ChatComponentText] 追加到附加组件列表.
     *
     * @see [ChatComponentText]
     * @param text Append string.
     * @param text 追加字符串.
     */
    fun append(text: String): ChatComponent

    /**
     * * Appends the given chat component to the list of extra component.
     * * 将给定聊天组件追加到附加组件列表.
     *
     * @param extra Extra component.
     * @param extra 附加组件.
     */
    fun append(extra: ChatComponent): ChatComponent

    /**
     * * Convert this chat component to a `JSON` string.
     * * 将此聊天组件转换为 `JSON` 字符串.
     *
     * @see [ChatSerializer.toJson]
     */
    fun toJson(): String

    /**
     * * Convert this chat component to a raw string.
     * * 将此聊天组件转换为源字符串.
     *
     * @see [ChatSerializer.toRaw]
     * @param color Whether it has a color.
     * @param color 是否拥有颜色.
     */
    fun toRaw(color: Boolean = true): String

    /**
     * * Send this chat component to a given player.
     * * 将此聊天组件发送到给定的玩家.
     *
     * @see [PacketOutChat]
     * @param player Player.
     * @param player 玩家.
     * @param action Chat action.
     * @param action 聊天交互.
     */
    fun send(player: Player, action: ChatAction = ChatAction.CHAT)

    /**
     * * Send this chat component to a given moonlake player.
     * * 将此聊天组件发送到给定的月色之湖玩家.
     *
     * @see [send]
     * @see [PacketOutChat]
     * @param player MoonLake player.
     * @param player 月色之湖玩家.
     * @param action Chat action.
     * @param action 聊天交互.
     */
    fun send(player: MoonLakePlayer, action: ChatAction = ChatAction.CHAT)

    /**
     * @see [append]
     */
    operator fun plus(text: String): ChatComponent

    /**
     * @see [append]
     */
    operator fun plus(extra: ChatComponent): ChatComponent

    companion object {
        /**
         * * ### Null of ChatComponent
         *
         * - #### Sample:
         * - `println(ChatComponent.NULL == null) // false`
         * - `println(ChatComponent.NULL == ChatComponent) // false`
         * - `println(ChatComponent.NULL is ChatComponent) // true`
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
                override fun send(player: MoonLakePlayer, action: ChatAction)
                        = throw UnsupportedOperationException()
                override fun send(player: Player, action: ChatAction)
                        = throw UnsupportedOperationException()
                override fun plus(text: String): ChatComponent
                        = throw UnsupportedOperationException()
                override fun plus(extra: ChatComponent): ChatComponent
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
