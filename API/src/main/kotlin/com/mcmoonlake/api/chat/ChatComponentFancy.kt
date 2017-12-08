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

import com.mcmoonlake.api.funs.Builder
import com.mcmoonlake.api.nbt.NBTFactory
import org.bukkit.inventory.ItemStack

/**
 * ## ChatComponentFancy (聊天组件花式)
 *
 * * Use this fancy component to quickly create multiple components for custom connections.
 * * 使用这个花式组件可以快速的创建多个组件进行自定义连接.
 *
 * - #### Sample:
 * - `ChatComponentFancy("Hello")`
 * - `.color(ChatColor.RED)`
 * - `.withBold()`
 * - `.then("World")`
 * - `.color(ChatColor.BLUE)`
 * - `.withItalic()`
 * - `.build()`
 * - `.toRaw() // §c§lHello§9§oWorld`
 *
 * @see [Builder]
 * @see [ChatComponent]
 * @author lgou2w
 * @since 2.0
 * @constructor ChatComponentFancy
 * @param component The first component.
 * @param component 第一个组件.
 */
open class ChatComponentFancy(component: ChatComponent) : Builder<ChatComponent> {

    /**
     * * Extra component list.
     */
    protected val extras: MutableList<ChatComponent>

    init {
        this.extras = ArrayList()
        this.then(component)
    }

    /**
     * @constructor ChatComponentFancy
     * @param text The first text.
     * @param text 第一个文本.
     */
    constructor(text: String) : this(ChatComponentText(text))

    /**
     * * Get the last component of the extra component list for this chat component fancy.
     * * 获取此聊天组件花式的附加组件列表的最后一个元素.
     *
     * @see [extras]
     */
    protected val last: ChatComponent
        get() = extras.last()

    /**
     * * Then the given string as a [ChatComponentText] to the list of fancy component.
     * * 然后将给定字符串以 [ChatComponentText] 添加到花式组件列表.
     *
     * @see [ChatComponentText]
     * @param text String.
     * @param text 字符串.
     */
    fun then(text: String): ChatComponentFancy
            = then(ChatComponentText(text))

    /**
     * * Then the given chat component to the list of fancy component.
     * * 然后将给定聊天组件添加到花式组件列表.
     *
     * @param component Chat component.
     * @param component 聊天组件.
     */
    fun then(component: ChatComponent): ChatComponentFancy
            { extras.add(component); return this; }

    /**
     * * Set the color style of the last component of this fancy component.
     * * 设置此花式组件最后一个组件的颜色样式.
     *
     * @see [ChatColor]
     * @param color Chat color.
     * @param color 聊天颜色.
     */
    fun color(color: ChatColor): ChatComponentFancy
            { last.style.setColor(color); return this; }

    /**
     * * The last component that sets this fancy component has a bold style.
     * * 设置此花式组件最后一个组件具有粗体样式.
     *
     * @see [ChatColor.BOLD]
     */
    fun withBold(): ChatComponentFancy
            { last.style.setBold(true); return this; }

    /**
     * * The last component that sets this fancy component has a italic style.
     * * 设置此花式组件最后一个组件具有斜体样式.
     *
     * @see [ChatColor.ITALIC]
     */
    fun withItalic(): ChatComponentFancy
            { last.style.setItalic(true); return this; }

    /**
     * * The last component that sets this fancy component has a underlined style.
     * * 设置此花式组件最后一个组件具有下划线样式.
     *
     * @see [ChatColor.UNDERLINE]
     */
    fun withUnderlined(): ChatComponentFancy
            { last.style.setUnderlined(true); return this; }

    /**
     * * The last component that sets this fancy component has a strikethrough style.
     * * 设置此花式组件最后一个组件具有删除线样式.
     *
     * @see [ChatColor.STRIKETHROUGH]
     */
    fun withStrikethrough(): ChatComponentFancy
            { last.style.setStrikethrough(true); return this; }

    /**
     * * The last component that sets this fancy component has a obfuscated style.
     * * 设置此花式组件最后一个组件具有随机字符样式.
     *
     * @see [ChatColor.OBFUSCATED]
     */
    fun withObfuscated(): ChatComponentFancy
            { last.style.setObfuscated(true); return this; }

    /**
     * * The last component that sets this fancy component has a insertion string.
     * * 设置此花式组件最后一个组件具有插入字符串.
     *
     * @see [ChatColor.OBFUSCATED]
     */
    fun withInsertion(insertion: String): ChatComponentFancy
            { last.style.setInsertion(insertion); return this; }

    /**
     * * The last component that sets this fancy component has a click event.
     * * 设置此花式组件最后一个组件具有点击事件.
     *
     * @see [ChatClickEvent]
     * @see [ChatClickEvent.Action]
     * @param action Action type.
     * @param action 交互类型.
     * @param value Action value.
     * @param value 交互值.
     */
    fun withClickEvent(action: ChatClickEvent.Action, value: String): ChatComponentFancy
            { last.style.setClickEvent(ChatClickEvent(action, value)); return this; }

    /**
     * * The last component that sets this fancy component has a hover event.
     * * 设置此花式组件最后一个组件具有移动事件.
     *
     * @see [ChatHoverEvent]
     * @see [ChatHoverEvent.Action]
     * @param action Action type.
     * @param action 交互类型.
     * @param value Action value.
     * @param value 交互值.
     */
    fun withHoverEvent(action: ChatHoverEvent.Action, value: ChatComponent): ChatComponentFancy
            { last.style.setHoverEvent(ChatHoverEvent(action, value)); return this; }

    /**
     * * The last component that sets this fancy component has a click open file event.
     * * 设置此花式组件最后一个组件具有点击打开文件事件.
     *
     * @see [ChatClickEvent.Action.OPEN_FILE]
     * @param path File path.
     * @param path 文件路径.
     */
    fun file(path: String): ChatComponentFancy
            { last.style.setClickEvent(ChatClickEvent(ChatClickEvent.Action.OPEN_FILE, path)); return this; }

    /**
     * * The last component that sets this fancy component has a click open url event.
     * * 设置此花式组件最后一个组件具有点击打开链接事件.
     *
     * @see [ChatClickEvent.Action.OPEN_URL]
     * @param url Web url.
     * @param url 网络链接.
     */
    fun link(url: String): ChatComponentFancy
            { last.style.setClickEvent(ChatClickEvent(ChatClickEvent.Action.OPEN_URL, url)); return this; }

    /**
     * * The last component that sets this fancy component has a click suggest command event.
     * * 设置此花式组件最后一个组件具有点击提示命令事件.
     *
     * @see [ChatClickEvent.Action.SUGGEST_COMMAND]
     * @param command Command.
     * @param command 命令.
     */
    fun suggest(command: String): ChatComponentFancy
            { last.style.setClickEvent(ChatClickEvent(ChatClickEvent.Action.SUGGEST_COMMAND, command)); return this }

    /**
     * * The last component that sets this fancy component has a click run command event.
     * * 设置此花式组件最后一个组件具有点击执行命令事件.
     *
     * @see [ChatClickEvent.Action.RUN_COMMAND]
     * @param command Command.
     * @param command 命令.
     */
    fun command(command: String): ChatComponentFancy
            { last.style.setClickEvent(ChatClickEvent(ChatClickEvent.Action.RUN_COMMAND, command)); return this; }

    /**
     * * The last component that sets this fancy component has a hover tooltip text event.
     * * 设置此花式组件最后一个组件具有移动显示文本事件.
     *
     * @see [tooltipTexts]
     * @see [tooltipComponent]
     * @see [ChatHoverEvent.Action.SHOW_TEXT]
     * @param text Text. Can use `\n` newline.
     * @param text 文本. 可用 `\n` 换行.
     */
    fun tooltipText(text: String): ChatComponentFancy
            = tooltipComponent(ChatSerializer.fromRaw(text))

    /**
     * * The last component that sets this fancy component has a hover tooltip multi-line text event.
     * * 设置此花式组件最后一个组件具有移动显示多行文本事件.
     *
     * @see [ChatHoverEvent.Action.SHOW_TEXT]
     * @param texts Text array.
     * @param texts 文本数组.
     */
    fun tooltipTexts(vararg texts: String): ChatComponentFancy {
        val lastIndex = texts.size - 1
        val text = texts.mapIndexed { index, str -> if(index != lastIndex) str + '\n' else str }
        return tooltipText(text.joinToString(separator = ""))
    }

    /**
     * * The last component that sets this fancy component has a hover tooltip text event.
     * * 设置此花式组件最后一个组件具有移动显示文本事件.
     *
     * @see [ChatHoverEvent.Action.SHOW_TEXT]
     * @param component Chat component.
     * @param component 聊天组件.
     */
    fun tooltipComponent(component: ChatComponent): ChatComponentFancy
            { last.style.setHoverEvent(ChatHoverEvent(ChatHoverEvent.Action.SHOW_TEXT, component)); return this; }

    /**
     * * The last component that sets this fancy component has a hover tooltip item stack event.
     * * 设置此花式组件最后一个组件具有移动显示物品栈事件.
     *
     * @see [ChatHoverEvent.Action.SHOW_ITEM]
     * @param item Item stack nbt tag.
     * @param item 物品栈 NBT Tag.
     */
    fun tooltipItem(item: String): ChatComponentFancy
            { last.style.setHoverEvent(ChatHoverEvent(ChatHoverEvent.Action.SHOW_ITEM, ChatSerializer.ChatComponentRaw(item))); return this; }

    /**
     * * The last component that sets this fancy component has a hover tooltip item stack event.
     * * 设置此花式组件最后一个组件具有移动显示物品栈事件.
     *
     * @see [ChatHoverEvent.Action.SHOW_ITEM]
     * @param itemStack Item stack.
     * @param itemStack 物品栈.
     */
    fun tooltipItem(itemStack: ItemStack): ChatComponentFancy
            = tooltipItem(NBTFactory.readStackNBT(itemStack).toMojangson()) // TODO v1.13

    /**
     * * Add the given fancy component to this fancy component.
     * * 将给定的花式组件加入到此花式组件中.
     *
     * @param componentFancy Chat component fancy.
     * @param componentFancy 聊天组件花式.
     */
    fun join(componentFancy: ChatComponentFancy): ChatComponentFancy
            { extras.addAll(componentFancy.extras); return this; }

    /**
     * * Gets the extra component list size for this fancy component.
     * * 获取此花式组件的附加组件列表大小.
     */
    val size: Int
            = extras.size

    /**
     * * Clear all the extra component for this fancy component.
     * * 将此花式组件的附加组件全部清除.
     */
    fun clear(): ChatComponentFancy
            { extras.clear(); return this; }

    override fun build(): ChatComponent {
        val chatComponent = ChatComponentText("")
        chatComponent.extras.addAll(extras)
        return chatComponent
    }
}
