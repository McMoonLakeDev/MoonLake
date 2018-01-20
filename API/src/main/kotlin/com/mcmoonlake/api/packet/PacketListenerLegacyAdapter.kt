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

package com.mcmoonlake.api.packet

import org.bukkit.plugin.Plugin
import java.util.*

open class PacketListenerLegacyAdapter<P: PacketBukkitLegacy, T>(
        final override val plugin: Plugin,
        final override val priority: PacketListenerPriority,
        val legacyAdapter: PacketLegacyAdapter<P, T>
) : PacketListener where T: PacketBukkitLegacy, T: PacketLegacy {

    private val _sendingTypes: MutableSet<Class<out PacketOut>> = HashSet()
    private val _receivingTypes: MutableSet<Class<out PacketIn>> = HashSet()

    constructor(plugin: Plugin, legacyAdapter: PacketLegacyAdapter<P, T>) : this(plugin, PacketListenerPriority.NORMAL, legacyAdapter)

    init {
        val clazz = legacyAdapter.result
        if(!Packets.isRegisteredWrapped(clazz))
            throw IllegalArgumentException("待监听的数据包包装类 $clazz 没有注册.")
        when(PacketOut::class.java.isAssignableFrom(clazz)) {
            true -> _sendingTypes.add(clazz.asSubclass(PacketOut::class.java))
            false -> _receivingTypes.add(clazz.asSubclass(PacketIn::class.java))
        }
    }

    final override val sendingTypes: Set<Class<out PacketOut>>
        get() = if(_sendingTypes.isEmpty()) Collections.emptySet() else HashSet(_sendingTypes)

    final override val receivingTypes: Set<Class<out PacketIn>>
        get() = if(_receivingTypes.isEmpty()) Collections.emptySet() else HashSet(_receivingTypes)

    override fun onSending(event: PacketEvent) {}

    override fun onReceiving(event: PacketEvent) {}

    override fun handlerException(ex: Exception) { ex.printStackTrace() }

    final override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketListenerLegacyAdapter<*, *>)
            return plugin == other.plugin && priority == other.priority && legacyAdapter == other.legacyAdapter && _sendingTypes == other._sendingTypes && _receivingTypes == other._receivingTypes
        return false
    }

    final override fun hashCode(): Int {
        var result = plugin.hashCode()
        result = 31 * result + priority.hashCode()
        result = 31 * result + legacyAdapter.hashCode()
        result = 31 * result + _sendingTypes.hashCode()
        result = 31 * result + _receivingTypes.hashCode()
        return result
    }

    final override fun toString(): String {
        return "PacketListenerLegacy(plugin=$plugin, priority=$priority, legacyAdapter=$legacyAdapter, sendingTypes=$_sendingTypes, receivingTypes=$_receivingTypes)"
    }

    val PacketEvent.isLegacy: Boolean
        get() = legacyAdapter.isLegacy
}
