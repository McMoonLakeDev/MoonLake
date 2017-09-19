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

package com.minecraft.moonlake.api.anvil

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

interface AnvilWindow {

    fun getPlugin(): Plugin

    fun handleOpen(openHandler: AnvilWindowEventHandler<AnvilWindowOpenEvent>?)

    fun handleOpen(openHandler: ((event: AnvilWindowOpenEvent) -> Unit)?)

    fun handleInput(inputHandler: AnvilWindowEventHandler<AnvilWindowInputEvent>?)

    fun handleInput(inputHandler: ((event: AnvilWindowInputEvent) -> Unit)?)

    fun handleClick(clickHandler: AnvilWindowEventHandler<AnvilWindowClickEvent>?)

    fun handleClick(clickHandler: ((event: AnvilWindowClickEvent) -> Unit)?)

    /** // TODO
     * If the server stops, this event does not fire.
     * Rewrite the container close function, because the class does not exist exception cannot be implemented.
     */
    fun handleClose(closeHandler: AnvilWindowEventHandler<AnvilWindowCloseEvent>?)

    /** // TODO
     * If the server stops, this event does not fire.
     * Rewrite the container close function, because the class does not exist exception cannot be implemented.
     */
    fun handleClose(closeHandler: ((event: AnvilWindowCloseEvent) -> Unit)?)

    fun isAllowMove(): Boolean

    fun setAllowMove(allowMove: Boolean)

    fun isOpened(): Boolean

    @Throws(MoonLakeException::class)
    fun open(player: Player)

    @Throws(MoonLakeException::class)
    fun open(player: MoonLakePlayer)

    @Throws(MoonLakeException::class)
    fun getItem(anvilWindowSlot: AnvilWindowSlot): ItemStack

    @Throws(MoonLakeException::class)
    fun setItem(anvilWindowSlot: AnvilWindowSlot, itemStack: ItemStack)

    fun clear()
}
