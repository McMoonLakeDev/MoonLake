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

package com.minecraft.moonlake.impl.anvil

import com.minecraft.moonlake.api.anvil.AnvilWindowCloseEvent
import com.minecraft.moonlake.api.anvil.AnvilWindowInputEvent
import com.minecraft.moonlake.api.anvil.AnvilWindowOpenEvent
import com.minecraft.moonlake.api.notNull
import net.minecraft.server.v1_8_R3.*
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

open class AnvilWindowImpl_v1_8_R3(plugin: Plugin) : AnvilWindowBase(plugin) {

    override fun open(player: Player) {
        super.open(player)
        val playerHandle = (player as CraftPlayer).handle
        val anvilTileContainer = AnvilWindowTileEntity(playerHandle.world)
        playerHandle.openTileEntity(anvilTileContainer)
    }

    override fun getInventory(): Inventory {
        val containerAnvil = handle as ContainerAnvil
        return containerAnvil.bukkitView.topInventory
    }

    private inner class AnvilWindowTileEntity(world: World) : BlockAnvil.TileEntityContainerAnvil(world, BlockPosition.ZERO) {
        override fun createContainer(playerInventory: PlayerInventory, opener: EntityHuman): Container? {
            val containerAnvil = object: ContainerAnvil(playerInventory, opener.world, BlockPosition.ZERO, opener) {
                override fun a(entityHuman: EntityHuman?): Boolean
                        = true
                override fun a(value: String?) {
                    if(inputHandler == null) {
                        super.a(value)
                        return
                    }

                    /** anvil input event handle */
                    val event = AnvilWindowInputEvent(this@AnvilWindowImpl_v1_8_R3, getContainerAnvilPlayer(), value)
                    try {
                        inputHandler.notNull().execute(event)
                    } catch(e: Exception) {
                        handleException(e)
                        super.a(value)
                        return
                    }
                    if(!event.isCancelled)
                        super.a(event.input)
                }

                override fun b(entityHuman: EntityHuman?) {
                    if(closeHandler == null) {
                        release()
                        super.b(entityHuman)
                        return
                    }

                    /** anvil close event handle */
                    val event = AnvilWindowCloseEvent(this@AnvilWindowImpl_v1_8_R3, getContainerAnvilPlayer())
                    try {
                        closeHandler.notNull().execute(event)
                    } catch(e: Exception) {
                        handleException(e)
                    }
                    release()
                    super.b(entityHuman)
                }
            }
            handle = containerAnvil

            /** anvil open event handle */
            if(openHandler != null) {
                val event = AnvilWindowOpenEvent(this@AnvilWindowImpl_v1_8_R3, getContainerAnvilPlayer())
                try {
                    openHandler.notNull().execute(event)
                } catch(e: Exception) {
                    handleException(e)
                }
            }

            return containerAnvil
        }
    }
}
