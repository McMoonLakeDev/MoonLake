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

import com.minecraft.moonlake.api.anvil.AnvilWindowAbstract
import com.minecraft.moonlake.api.anvil.AnvilWindowClickEvent
import com.minecraft.moonlake.api.anvil.AnvilWindowSlot
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

open class AnvilWindowBase(plugin: Plugin) : AnvilWindowAbstract(plugin) {

    protected var handle: Any? = null
    private var allowMove: Boolean = true
    private var listener: MoonLakeListener? = null

    override fun isAllowMove(): Boolean
            = allowMove

    override fun setAllowMove(allowMove: Boolean) {
        this.allowMove = allowMove
        when(allowMove) {
            true -> releaseListener()
            else -> {
                val clickListener = object: MoonLakeListener {
                    @EventHandler
                    fun onClick(event: InventoryClickEvent) {
                        if(event.clickedInventory != getInventory() && !isAllowMove()) {
                            event.isCancelled = true
                            event.result = Event.Result.DENY
                        } else {
                            val clickSlot = AnvilWindowSlot.fromSlot(event.slot)
                            var clickEvent: AnvilWindowClickEvent? = null
                            if(clickSlot != null && clickHandler != null) {
                                val clickItemStack = event.currentItem ?: ItemStack(Material.AIR)
                                clickEvent = AnvilWindowClickEvent(this@AnvilWindowBase, getContainerAnvilPlayer(), clickSlot, clickItemStack)
                                try {
                                    clickHandler.notNull().execute(clickEvent)
                                } catch(e: Exception) {
                                    handleException(e)
                                }
                            }
                            if((clickEvent != null && clickEvent.isCancelled) || !isAllowMove()) {
                                event.isCancelled = true
                                event.result = Event.Result.DENY
                            }
                        }
                    }
                }
                listener = clickListener
            }
        }
    }

    override fun isOpened(): Boolean
            = handle != null

    override fun open(player: Player) {
        if(isOpened())
            throw MoonLakeException("铁砧窗口已经处于打开状态.")
        if(listener != null)
            listener.notNull().registerEvent(getPlugin())
    }

    override fun getItem(anvilWindowSlot: AnvilWindowSlot): ItemStack {
        if(!isOpened())
            throw MoonLakeException("铁砧窗口尚未处于打开状态.")
        return getInventory().getItem(anvilWindowSlot.value())
    }

    override fun setItem(anvilWindowSlot: AnvilWindowSlot, itemStack: ItemStack) {
        if(!isOpened())
            throw MoonLakeException("铁砧窗口尚未处于打开状态.")
        getInventory().setItem(anvilWindowSlot.value(), itemStack)
    }

    override fun clear() {
        if(isOpened())
            getInventory().clear()
    }

    override fun release() {
        super.release()
        releaseListener()
        handle = null
    }

    open protected fun releaseListener() {
        if(listener != null)
            InventoryClickEvent.getHandlerList().unregister(listener)
        listener = null
    }

    open protected fun getInventory(): Inventory {
        throw UnsupportedOperationException()
    }

    open protected fun handleException(e: Exception?)
            = e?.printStackTrace()

    /** implement */

    private val containerAnvilEntityHuman: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getMinecraftClass("ContainerAnvil"), MinecraftReflection.getEntityHumanClass(), true) }

    protected fun getContainerAnvilPlayerHandle(): Any
            = containerAnvilEntityHuman.get(handle)

    protected fun getContainerAnvilPlayer(): Player {
        val entityHuman = containerAnvilEntityHuman.get(handle)
        val converter = MinecraftConverters.getEntity(Player::class.java)
        return converter.getSpecific(entityHuman)
    }
}
