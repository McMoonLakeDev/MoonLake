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

import com.minecraft.moonlake.api.funs.Builder
import com.minecraft.moonlake.api.nbt.NBTFactory
import org.bukkit.inventory.ItemStack

open class ChatComponentFancy : Builder<ChatComponent> {

    private val extras: MutableList<ChatComponent>

    constructor(text: String) {
        this.extras = ArrayList()
        this.then(text)
    }

    protected fun getLast(): ChatComponent
            = extras.last()

    fun then(text: String): ChatComponentFancy
            = then(ChatComponentText(text))

    fun then(chatComponent: ChatComponent): ChatComponentFancy
            { extras.add(chatComponent); return this; }

    fun color(color: ChatColor): ChatComponentFancy
            { getLast().getStyle().setColor(color); return this; }

    fun withBold(): ChatComponentFancy
            { getLast().getStyle().setBold(true); return this; }

    fun withItalic(): ChatComponentFancy
            { getLast().getStyle().setItalic(true); return this; }

    fun withUnderlined(): ChatComponentFancy
            { getLast().getStyle().setUnderlined(true); return this; }

    fun withStrikethrough(): ChatComponentFancy
            { getLast().getStyle().setStrikethrough(true); return this; }

    fun withObfuscated(): ChatComponentFancy
            { getLast().getStyle().setObfuscated(true); return this; }

    fun withInsertion(insertion: String): ChatComponentFancy
            { getLast().getStyle().setInsertion(insertion); return this; }

    fun withClickEvent(action: ChatClickEvent.Action, value: String): ChatComponentFancy
            { getLast().getStyle().setClickEvent(ChatClickEvent(action, value)); return this; }

    fun withHoverEvent(action: ChatHoverEvent.Action, value: ChatComponent): ChatComponentFancy
            { getLast().getStyle().setHoverEvent(ChatHoverEvent(action, value)); return this; }

    fun file(path: String): ChatComponentFancy
            { getLast().getStyle().setClickEvent(ChatClickEvent(ChatClickEvent.Action.OPEN_FILE, path)); return this; }

    fun link(url: String): ChatComponentFancy
            { getLast().getStyle().setClickEvent(ChatClickEvent(ChatClickEvent.Action.OPEN_URL, url)); return this; }

    fun suggest(command: String): ChatComponentFancy
            { getLast().getStyle().setClickEvent(ChatClickEvent(ChatClickEvent.Action.SUGGEST_COMMAND, command)); return this }

    fun command(command: String): ChatComponentFancy
            { getLast().getStyle().setClickEvent(ChatClickEvent(ChatClickEvent.Action.RUN_COMMAND, command)); return this; }

    fun tooltipText(text: String): ChatComponentFancy
            { getLast().getStyle().setHoverEvent(ChatHoverEvent(ChatHoverEvent.Action.SHOW_TEXT, ChatComponentText(text))); return this; }

    fun tooltipTexts(vararg texts: String): ChatComponentFancy {
        val lastIndex = texts.size - 1
        val text = texts.mapIndexed { index, str -> if(index != lastIndex) str + '\n' else str }
        return tooltipText(text.joinToString(separator = ""))
    }

    fun tooltipItem(item: String): ChatComponentFancy
            { getLast().getStyle().setHoverEvent(ChatHoverEvent(ChatHoverEvent.Action.SHOW_ITEM, ChatComponentText(item))); return this; }

    fun tooltipItem(itemStack: ItemStack): ChatComponentFancy
            = tooltipItem(NBTFactory.getStackNBT(itemStack).toString())

    fun join(chatComponentFancy: ChatComponentFancy): ChatComponentFancy
            { extras.addAll(chatComponentFancy.extras); return this; }

    override fun build(): ChatComponent {
        val chatComponent = ChatComponentText("")
        chatComponent.getExtras().addAll(extras)
        return chatComponent
    }
}
