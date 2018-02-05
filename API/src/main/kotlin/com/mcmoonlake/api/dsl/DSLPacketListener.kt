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

@file:JvmName("DSLPacketListener")

package com.mcmoonlake.api.dsl

import com.mcmoonlake.api.packet.*
import org.bukkit.plugin.Plugin

open class DSLPacketScope(val plugin: Plugin) {

    var priority: PacketListenerPriority = PacketListenerPriority.NORMAL

    protected var sending: (PacketEvent) -> Unit = {}
    fun onSending(block: PacketEvent.() -> Unit)
            { sending = block }

    protected var receiving: (PacketEvent) -> Unit = {}
    fun onReceiving(block: PacketEvent.() -> Unit)
            { receiving = block }

    protected var exception: (Exception) -> Unit = {}
    fun onException(block: Exception.() -> Unit)
            { exception = block }

    /**
     * * Not recommended for external calls.
     *
     * @see [buildPacketListener]
     * @see [buildPacketListenerSpecified]
     * @see [buildPacketListenerLegacy]
     */
    open fun get(): PacketListener = object: PacketListenerAnyAdapter(plugin, priority) {
        override fun onSending(event: PacketEvent)
                = sending(event)
        override fun onReceiving(event: PacketEvent)
                = receiving(event)
        override fun handlerException(ex: Exception)
                = exception(ex)
    }
}

class DSLPacketSpecifiedScope(plugin: Plugin) : DSLPacketScope(plugin) {

    var types: Array<Class<out Packet>> = emptyArray()

    override fun get(): PacketListener = object: PacketListenerAdapter(plugin, priority, *types) {
        override fun onSending(event: PacketEvent)
                = sending(event)
        override fun onReceiving(event: PacketEvent)
                = receiving(event)
        override fun handlerException(ex: Exception)
                = exception(ex)
    }
}

class DSLPacketLegacyScope<P: PacketBukkitLegacy, T>(
         plugin: Plugin
 ) : DSLPacketScope(plugin) where T: PacketBukkitLegacy, T: PacketLegacy {

     var adapter: PacketLegacyAdapter<P, T> = object: PacketLegacyAdapter<P, T>() {
         override val packet: Class<P>
             get() = throw INVALID_ADAPTER
         override val packetLegacy: Class<T>
             get() = throw INVALID_ADAPTER
         override val isLegacy: Boolean
             get() = throw INVALID_ADAPTER
     }

    companion object {
        private val INVALID_ADAPTER = IllegalStateException("Invalid adapter, not yet initialized.")
    }

    /**
     * * If the event's packet is legacy.
     */
    val PacketEvent.isLegacy: Boolean
        get() = adapter.isLegacy

    override fun get(): PacketListener = object: PacketListenerLegacyAdapter<P, T>(plugin, priority, adapter) {
        override fun onSending(event: PacketEvent)
                 = sending(event)
        override fun onReceiving(event: PacketEvent)
                 = receiving(event)
        override fun handlerException(ex: Exception)
                 = exception(ex)
    }
 }

inline fun Plugin.buildPacketListener(block: DSLPacketScope.() -> Unit): PacketListener
        = DSLPacketScope(this).also(block).get()

inline fun Plugin.buildPacketListenerSpecified(block: DSLPacketSpecifiedScope.() -> Unit): PacketListener
        = DSLPacketSpecifiedScope(this).also(block).get()

inline fun <P: PacketBukkitLegacy, T> Plugin.buildPacketListenerLegacy(block: DSLPacketLegacyScope<P, T>.() -> Unit): PacketListener where T : PacketBukkitLegacy, T: PacketLegacy
        = DSLPacketLegacyScope<P, T>(this).also(block).get()
