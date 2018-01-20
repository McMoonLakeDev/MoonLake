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

import com.mcmoonlake.api.player.MoonLakePlayer
import org.bukkit.plugin.Plugin

/**
 * ## AnvilWindowAbstract (铁砧窗口抽象类)
 *
 * @see [AnvilWindow]
 * @see [AnvilWindows]
 * @author lgou2w
 * @since 2.0
 * @constructor AnvilWindowAbstract
 * @param plugin The current anvil window plugin object.
 * @param plugin 当前铁砧窗口的插件对象.
 */
abstract class AnvilWindowAbstract(
        final override val plugin: Plugin
) : AnvilWindow {

    protected var openHandler: AnvilWindowEventHandler<AnvilWindowOpenEvent>? = null
    protected var inputHandler: AnvilWindowEventHandler<AnvilWindowInputEvent>? = null
    protected var clickHandler: AnvilWindowEventHandler<AnvilWindowClickEvent>? = null
    protected var closeHandler: AnvilWindowEventHandler<AnvilWindowCloseEvent>? = null

    override fun handleOpen(openHandler: AnvilWindowEventHandler<AnvilWindowOpenEvent>?)
            { this.openHandler = openHandler }

    override fun handleOpen(openHandler: ((event: AnvilWindowOpenEvent) -> Unit)?) {
        this.openHandler = if(openHandler == null) null else object: AnvilWindowEventHandler<AnvilWindowOpenEvent> {
            override fun execute(param: AnvilWindowOpenEvent)
                    = openHandler(param)
        }
    }

    override fun handleInput(inputHandler: AnvilWindowEventHandler<AnvilWindowInputEvent>?)
            { this.inputHandler = inputHandler }

    override fun handleInput(inputHandler: ((event: AnvilWindowInputEvent) -> Unit)?) {
        this.inputHandler = if(inputHandler == null) null else object: AnvilWindowEventHandler<AnvilWindowInputEvent> {
            override fun execute(param: AnvilWindowInputEvent)
                    = inputHandler(param)
        }
    }

    override fun handleClick(clickHandler: AnvilWindowEventHandler<AnvilWindowClickEvent>?)
            { this.clickHandler = clickHandler }

    override fun handleClick(clickHandler: ((event: AnvilWindowClickEvent) -> Unit)?) {
        this.clickHandler = if(clickHandler == null) null else object: AnvilWindowEventHandler<AnvilWindowClickEvent> {
            override fun execute(param: AnvilWindowClickEvent)
                    = clickHandler(param)
        }
    }

    override fun handleClose(closeHandler: AnvilWindowEventHandler<AnvilWindowCloseEvent>?)
            { this.closeHandler = closeHandler }

    override fun handleClose(closeHandler: ((event: AnvilWindowCloseEvent) -> Unit)?) {
        this.closeHandler = if(closeHandler == null) null else object: AnvilWindowEventHandler<AnvilWindowCloseEvent> {
            override fun execute(param: AnvilWindowCloseEvent)
                    = closeHandler(param)
        }
    }

    override fun open(player: MoonLakePlayer)
            = open(player.bukkitPlayer)

    /**
     * * Release the current anvil window can be `null` member variables and resources.
     * * 释放当前铁砧窗口可 `null` 成员变量和资源.
     */
    protected open fun release() {
        openHandler = null
        inputHandler = null
        clickHandler = null
        closeHandler = null
    }

    /**
     * * Add the id of the current anvil window to [AnvilWindows].
     * * 将当前铁砧窗口的 Id 添加到 [AnvilWindows].
     *
     * @param windowId Current anvil window Id.
     * @param windowId 当前铁砧窗口 Id.
     */
    protected open fun addWindowId(windowId: Int) {
        AnvilWindows.windowIds.add(windowId)
    }

    /**
     * * Remove the id of the current anvil window from [AnvilWindows].
     * * 从 [AnvilWindows] 中移除当前铁砧窗口的 Id.
     *
     * @param windowId Current anvil window Id.
     * @param windowId 当前铁砧窗口 Id.
     */
    protected open fun removeWindowId(windowId: Int) {
        AnvilWindows.windowIds.remove(windowId)
    }
}
