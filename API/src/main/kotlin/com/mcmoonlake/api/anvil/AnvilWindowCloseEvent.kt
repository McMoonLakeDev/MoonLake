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

package com.mcmoonlake.api.anvil

import org.bukkit.entity.Player

/**
 * ## AnvilWindowCloseEvent (铁砧窗口关闭事件)
 *
 * * Called when the anvil window is closed.
 * * 当此铁砧窗口被关闭时触发.
 *
 * @see [AnvilWindow]
 * @see [AnvilWindow.handleClose]
 * @author lgou2w
 * @since 2.0
 * @constructor AnvilWindowCloseEvent
 * @param anvilWindow Anvil window object for event.
 * @param anvilWindow 事件的铁砧窗口对象.
 * @param player Player object for event.
 * @param player 事件的玩家对象.
 */
class AnvilWindowCloseEvent(
        anvilWindow: AnvilWindow,
        player: Player) : AnvilWindowEvent(anvilWindow, player) {
}
