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

package com.minecraft.moonlake.api.packet

import com.minecraft.moonlake.api.copyHashSetOrEmpty
import org.bukkit.plugin.Plugin

abstract class PacketListenerAdapter(private val plugin: Plugin, private val priority: PacketListenerPriority = PacketListenerPriority.NORMAL, vararg types: Class<out Packet>) : PacketListener {

    private val sendingTypes: MutableSet<Class<out PacketOut>> = HashSet()
    private val receivingTypes: MutableSet<Class<out PacketIn>> = HashSet()

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
                true -> sendingTypes.add(it.asSubclass(PacketOut::class.java))
                else -> receivingTypes.add(it.asSubclass(PacketIn::class.java))
            }
        }
    }

    override final fun getPlugin(): Plugin
            = plugin

    override final fun getPriority(): PacketListenerPriority
            = priority

    override final fun getSendingTypes(): Set<Class<out PacketOut>>
            = sendingTypes.copyHashSetOrEmpty()

    override final fun getReceivingTypes(): Set<Class<out PacketIn>>
            = receivingTypes.copyHashSetOrEmpty()

    override abstract fun onSending(event: PacketEvent)

    override abstract fun onReceiving(event: PacketEvent)

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketListenerAdapter)
            return plugin == other.plugin && sendingTypes == other.sendingTypes && receivingTypes == other.sendingTypes
        return false
    }

    override fun hashCode(): Int {
        var result = plugin.hashCode()
        result = 31 * result + sendingTypes.hashCode()
        result = 31 * result + receivingTypes.hashCode()
        return result
    }

    override fun toString(): String {
        return "PacketListener(plugin=$plugin, sendingTypes=$sendingTypes, receivingTypes=$receivingTypes)"
    }
}
