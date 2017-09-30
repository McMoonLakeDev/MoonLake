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

import com.minecraft.moonlake.api.copyHashSetOrEmpty
import org.bukkit.plugin.Plugin

open class PacketListenerAdapter(private val _plugin: Plugin, private val _priority: PacketListenerPriority = PacketListenerPriority.NORMAL, vararg types: Class<out Packet>) : PacketListener {

    private val _sendingTypes: MutableSet<Class<out PacketOut>> = HashSet()
    private val _receivingTypes: MutableSet<Class<out PacketIn>> = HashSet()

    init {
        if(types.isEmpty())
            throw IllegalArgumentException("待监听的数据包类型为空, 无法进行构造.")
        types.filter {
            val registered = Packets.isRegisteredWrapped(it)
            if(!registered)
                plugin.logger.warning("[MoonLake] 警告: 待监听的数据包包装类 $it 没有注册, 此监听器已将之过滤.")
            registered
        }.forEach {
            when(PacketOut::class.java.isAssignableFrom(it)) {
                true -> _sendingTypes.add(it.asSubclass(PacketOut::class.java))
                else -> _receivingTypes.add(it.asSubclass(PacketIn::class.java))
            }
        }
    }

    override final val plugin: Plugin
        get() = _plugin

    override final val priority: PacketListenerPriority
        get() = _priority

    override final val sendingTypes: Set<Class<out PacketOut>>
        get() = _sendingTypes.copyHashSetOrEmpty()

    override final val receivingTypes: Set<Class<out PacketIn>>
        get() = _receivingTypes.copyHashSetOrEmpty()

    /**
     * Execute in non-main threads.
     */
    override fun onSending(event: PacketEvent) {}

    /**
     * Execute in non-main threads.
     */
    override fun onReceiving(event: PacketEvent) {}

    override final fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketListenerAdapter)
            return plugin == other.plugin && priority == other.priority && _sendingTypes == other._sendingTypes && _receivingTypes == other._receivingTypes
        return false
    }

    override final fun hashCode(): Int {
        var result = _plugin.hashCode()
        result = 31 * result + _priority.hashCode()
        result = 31 * result + _sendingTypes.hashCode()
        result = 31 * result + _receivingTypes.hashCode()
        return result
    }

    override final fun toString(): String {
        return "PacketListener(plugin=$plugin, priority=$priority, sendingTypes=$_sendingTypes, receivingTypes=$_receivingTypes)"
    }
}
