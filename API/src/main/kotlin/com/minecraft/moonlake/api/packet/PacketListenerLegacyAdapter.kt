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

package com.minecraft.moonlake.api.packet

import org.bukkit.plugin.Plugin
import java.util.*

open class PacketListenerLegacyAdapter<P: PacketBukkitLegacy, T>(
        private val _plugin: Plugin,
        private val _priority: PacketListenerPriority,
        private val _legacyAdapter: PacketLegacyAdapter<P, T>) : PacketListener where T: PacketBukkitLegacy, T: PacketLegacy {

    private val _sendingTypes: MutableSet<Class<out PacketOut>> = HashSet()
    private val _receivingTypes: MutableSet<Class<out PacketIn>> = HashSet()

    constructor(plugin: Plugin, legacyAdapter: PacketLegacyAdapter<P, T>) : this(plugin, PacketListenerPriority.NORMAL, legacyAdapter)

    init {
        val clazz = _legacyAdapter.result
        if(!Packets.isRegisteredWrapped(clazz))
            throw IllegalArgumentException("待监听的数据包包装类 $clazz 没有注册.")
        when(PacketOut::class.java.isAssignableFrom(clazz)) {
            true -> _sendingTypes.add(clazz.asSubclass(PacketOut::class.java))
            false -> _receivingTypes.add(clazz.asSubclass(PacketIn::class.java))
        }
    }

    override final val plugin: Plugin
        get() = _plugin

    override final val priority: PacketListenerPriority
        get() = _priority

    val legacyAdapter: PacketLegacyAdapter<P, T>
        get() = _legacyAdapter

    override final val sendingTypes: Set<Class<out PacketOut>>
        get() = if(_sendingTypes.isEmpty()) Collections.emptySet() else HashSet(_sendingTypes)

    override final val receivingTypes: Set<Class<out PacketIn>>
        get() = if(_receivingTypes.isEmpty()) Collections.emptySet() else HashSet(_receivingTypes)

    override fun onSending(event: PacketEvent) {}

    override fun onReceiving(event: PacketEvent) {}

    override fun handlerException(ex: Exception) { ex.printStackTrace() }

    override final fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketListenerLegacyAdapter<*, *>)
            return plugin == other.plugin && priority == other.priority && _legacyAdapter == other._legacyAdapter && _sendingTypes == other._sendingTypes && _receivingTypes == other._receivingTypes
        return false
    }

    override final fun hashCode(): Int {
        var result = _plugin.hashCode()
        result = 31 * result + _priority.hashCode()
        result = 31 * result + _legacyAdapter.hashCode()
        result = 31 * result + _sendingTypes.hashCode()
        result = 31 * result + _receivingTypes.hashCode()
        return result
    }

    override final fun toString(): String {
        return "PacketListenerLegacy(plugin=$plugin, priority=$priority, legacyAdapter=$_legacyAdapter, sendingTypes=$_sendingTypes, receivingTypes=$_receivingTypes)"
    }

    val PacketEvent.isLegacy: Boolean
        get() = _legacyAdapter.isLegacy
}
