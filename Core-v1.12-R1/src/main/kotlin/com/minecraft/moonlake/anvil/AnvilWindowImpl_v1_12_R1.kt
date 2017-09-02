package com.minecraft.moonlake.anvil

import com.minecraft.moonlake.api.anvil.AnvilWindowInputEvent
import com.minecraft.moonlake.api.anvil.AnvilWindowOpenEvent
import com.minecraft.moonlake.api.notNull
import net.minecraft.server.v1_12_R1.*
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

open class AnvilWindowImpl_v1_12_R1(plugin: Plugin) : AnvilWindowBase(plugin) {

    override fun open(player: Player) {
        checkOpenState()
        val playerHandle = (player as CraftPlayer).handle
        val anvilTileContainer = AnvilWindowTileEntity(playerHandle.world)
        playerHandle.openTileEntity(anvilTileContainer)
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
                    val event = AnvilWindowInputEvent(this@AnvilWindowImpl_v1_12_R1, getContainerAnvilPlayer(), value)
                    try {
                        inputHandler.notNull().execute(event)
                    } catch(e: Exception) {
                        handleException(e)
                        super.a(value)
                        return
                    }
                    if(!event.isCancelled && event.input != null)
                        super.a(event.input)
                }
            }
            handle = containerAnvil

            /** anvil open event handle */
            if(openHandler != null) {
                val event = AnvilWindowOpenEvent(this@AnvilWindowImpl_v1_12_R1, getContainerAnvilPlayer())
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