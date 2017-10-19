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

import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.plugin.Plugin

abstract class AnvilWindowAbstract(private val _plugin: Plugin) : AnvilWindow {

    protected var openHandler: AnvilWindowEventHandler<AnvilWindowOpenEvent>? = null
    protected var inputHandler: AnvilWindowEventHandler<AnvilWindowInputEvent>? = null
    protected var clickHandler: AnvilWindowEventHandler<AnvilWindowClickEvent>? = null
    protected var closeHandler: AnvilWindowEventHandler<AnvilWindowCloseEvent>? = null

    override final val plugin: Plugin
        get() = _plugin

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

    open protected fun release() {
        openHandler = null
        inputHandler = null
        clickHandler = null
        closeHandler = null
    }

    open protected fun addWindowId(windowId: Int) {
        AnvilWindows.windowIds.add(windowId)
    }

    open protected fun removeWindowId(windowId: Int) {
        AnvilWindows.windowIds.remove(windowId)
    }
}
