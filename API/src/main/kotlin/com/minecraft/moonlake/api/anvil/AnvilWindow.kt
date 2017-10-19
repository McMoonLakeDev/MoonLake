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

package com.minecraft.moonlake.api.anvil

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

interface AnvilWindow {

    /**
     * * Get the plugin of this anvil window.
     * * 获取此铁砧窗口的插件.
     */
    val plugin: Plugin

    /**
     * * Handle with when the anvil window is opened.
     * * 当此铁砧窗口被打开时处理.
     *
     * @param openHandler Handler. If it's `null` then Remove.
     * @param openHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleOpen(openHandler: AnvilWindowEventHandler<AnvilWindowOpenEvent>?)

    /**
     * * Handle with when the anvil window is opened.
     * * 当此铁砧窗口被打开时处理.
     *
     * @param openHandler Handler. If it's `null` then Remove.
     * @param openHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleOpen(openHandler: ((event: AnvilWindowOpenEvent) -> Unit)?)

    /**
     * * Handle with when the anvil window is by the player input.
     * * 当此铁砧窗口在被玩家输入时处理.
     *
     * @param inputHandler Handler. If it's `null` then Remove.
     * @param inputHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleInput(inputHandler: AnvilWindowEventHandler<AnvilWindowInputEvent>?)

    /**
     * * Handle with when the anvil window is by the player input.
     * * 当此铁砧窗口在被玩家输入时处理.
     *
     * @param inputHandler Handler. If it's `null` then Remove.
     * @param inputHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleInput(inputHandler: ((event: AnvilWindowInputEvent) -> Unit)?)

    /**
     * * Handle with when the anvil window is by the player clicked item stack.
     * * 当此铁砧窗口在被玩家点击物品栈时处理.
     *
     * @param clickHandler Handler. If it's `null` then Remove.
     * @param clickHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleClick(clickHandler: AnvilWindowEventHandler<AnvilWindowClickEvent>?)

    /**
     * * Handle with when the anvil window is by the player clicked item stack.
     * * 当此铁砧窗口在被玩家点击物品栈时处理.
     *
     * @param clickHandler Handler. If it's `null` then Remove.
     * @param clickHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleClick(clickHandler: ((event: AnvilWindowClickEvent) -> Unit)?)

    /**
     * * Handle with when the anvil window is closed.
     * * 当此铁砧窗口被关闭时处理.
     *
     * @param closeHandler Handler. If it's `null` then Remove.
     * @param closeHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleClose(closeHandler: AnvilWindowEventHandler<AnvilWindowCloseEvent>?)

    /**
     * * Handle with when the anvil window is closed.
     * * 当此铁砧窗口被关闭时处理.
     *
     * @param closeHandler Handler. If it's `null` then Remove.
     * @param closeHandler 处理器. 如果为 `null` 则移除.
     */
    fun handleClose(closeHandler: ((event: AnvilWindowCloseEvent) -> Unit)?)

    /**
     * * Get or set whether this anvil window allows the player to move the item stack.
     * * 获取或设置此铁砧窗口是否允许玩家移动物品栈.
     */
    var isAllowMove: Boolean

    /**
     * * Get whether this anvil window has been opened.
     * * 获取此铁砧窗口是否已经被打开.
     */
    val isOpened: Boolean

    /**
     * * Open the anvil window to the specified player.
     * * 将此铁砧窗口打开给指定玩家.
     *
     * @throws MoonLakeException If the anvil window has been opened.
     * @throws MoonLakeException 如果此铁砧窗口已经被打开.
     * @see isOpened
     */
    @Throws(MoonLakeException::class)
    fun open(player: Player)

    /**
     * * Open the anvil window to the specified player.
     * * 将此铁砧窗口打开给指定玩家.
     *
     * @throws MoonLakeException If the anvil window has been opened.
     * @throws MoonLakeException 如果此铁砧窗口已经被打开.
     * @see open
     * @see isOpened
     */
    @Throws(MoonLakeException::class)
    fun open(player: MoonLakePlayer)

    /**
     * * Get the item stack of the anvil window for a specific slot.
     * * 获取此铁砧窗口指定槽位的物品栈.
     *
     * @throws MoonLakeException If the anvil window is not opened.
     * @throws MoonLakeException 如果此铁砧窗口没有被打开.
     */
    @Throws(MoonLakeException::class)
    fun getItem(anvilWindowSlot: AnvilWindowSlot): ItemStack

    /**
     * * Set the item stack of the anvil window for a specific slot.
     * * 设置此铁砧窗口指定槽位的物品栈.
     *
     * @throws MoonLakeException If the anvil window is not opened.
     * @throws MoonLakeException 如果此铁砧窗口没有被打开.
     */
    @Throws(MoonLakeException::class)
    fun setItem(anvilWindowSlot: AnvilWindowSlot, itemStack: ItemStack)

    /**
     * * Clear all item stacks in this anvil window.
     * * 清除此铁砧窗口内的所有物品栈.
     */
    fun clear()
}
