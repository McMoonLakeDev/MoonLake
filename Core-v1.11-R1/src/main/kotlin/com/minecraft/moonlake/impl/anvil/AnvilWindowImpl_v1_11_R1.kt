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

package com.minecraft.moonlake.impl.anvil

import net.minecraft.server.v1_11_R1.*
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

open class AnvilWindowImpl_v1_11_R1(plugin: Plugin) : AnvilWindowBase(plugin) {

    override fun open(player: Player) {
        super.open(player)
        val playerHandle = (player as CraftPlayer).handle
        val anvilTileContainer = AnvilWindowTileEntity(playerHandle.world)
        playerHandle.openTileEntity(anvilTileContainer)
        // add this anvil window id to list
        addWindowId(playerHandle.activeContainer.windowId)
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
                    val event = callAnvilEvent(inputHandler, value)
                    if(event != null && !event.isCancelled)
                        super.a(event.input)
                }
                override fun b(entityHuman: EntityHuman?) {
                    callAnvilEvent(closeHandler)
                    release()
                    super.b(entityHuman)
                }
            }
            handle = containerAnvil
            callAnvilEvent(openHandler)
            return containerAnvil
        }
    }
}
