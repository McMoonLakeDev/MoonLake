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

import com.google.common.collect.MapMaker
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.getMoonLake
import com.minecraft.moonlake.api.getOnlinePlayers
import com.minecraft.moonlake.api.reflect.FuzzyReflect
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.utility.MinecraftPlayerMembers
import com.minecraft.moonlake.api.utility.MinecraftReflection
import io.netty.channel.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.logging.Level

object PacketListeners {

    @JvmStatic
    private val handlerName = "packet_moonlake"

    @JvmStatic
    private val lookupChannel: MutableMap<String, Channel> by lazy {
        MapMaker().weakValues().makeMap<String, Channel>() }
    @JvmStatic
    private val serverChannels: MutableList<Channel> by lazy {
        ArrayList<Channel>() }
    @JvmStatic
    private val unInjectedChannels: MutableSet<Channel> by lazy {
        Collections.newSetFromMap(MapMaker().weakKeys().makeMap<Channel, Boolean>()) }
    @JvmStatic
    private val listeners: MutableSet<PacketListener> by lazy {
        TreeSet(Comparator<PacketListener> { o1, o2 -> o2.getPriority().compareTo(o1.getPriority()) }) }
    @JvmStatic
    private val craftServerMinecraft: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getCraftServerClass(), MinecraftReflection.getMinecraftServerClass(), true) }
    @JvmStatic
    private val minecraftServerConnection: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getMinecraftServerClass(), MinecraftReflection.getServerConnectionClass(), true) }
    @JvmStatic
    private val endInitProtocol: ChannelInitializer<Channel> by lazy {
        object: ChannelInitializer<Channel>() {
            override fun initChannel(channel: Channel) {
                try {
                    channel.eventLoop().submit { injectChannel(channel) }
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    @JvmStatic
    private val beginInitProtocol: ChannelInitializer<Channel> by lazy {
        object: ChannelInitializer<Channel>() {
            override fun initChannel(channel: Channel) {
                channel.pipeline().addLast(endInitProtocol)
            }
        }
    }
    @JvmStatic
    private val serverChannelHandler: ChannelInboundHandlerAdapter by lazy {
        object: ChannelInboundHandlerAdapter() {
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                val serverChannel = msg as Channel
                serverChannels.add(serverChannel)
                serverChannel.pipeline().addFirst(beginInitProtocol)
                ctx.fireChannelRead(msg)
            }
        }
    }

    @JvmStatic
    @JvmName("injectServerChannel")
    private fun injectServerChannel() {
        val mcServer = craftServerMinecraft.get(Bukkit.getServer())
        val serverConnection = minecraftServerConnection.get(mcServer) ?: return
        main@for(field in FuzzyReflect.fromClass(serverConnection::class.java).getFieldListByType(List::class.java)) {
            val list = field.get(serverConnection) as List<*>
            for(item in list) {
                if(!ChannelFuture::class.java.isInstance(item))
                    break
                val channel = (item as ChannelFuture).channel()
                channel.pipeline().addFirst(serverChannelHandler)
                break@main
            }
        }
    }

    /** api */

    @JvmStatic
    @JvmName("registerListener")
    fun registerListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.add(listener) })

    @JvmStatic
    @JvmName("unregisterListener")
    fun unregisterListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.remove(listener) })

    @JvmStatic
    @JvmName("unregisterListener")
    fun unregisterListener(plugin: Plugin): Boolean
            = synchronized(listeners, { val removeList = listeners.filter { it.getPlugin() == plugin }; listeners.removeAll(removeList) })

    @JvmStatic
    @JvmName("unregisterListenerAll")
    fun unregisterListenerAll()
            = synchronized(listeners, { listeners.clear() })

    @JvmStatic
    @JvmName("close")
    fun close() {
        unregisterListenerAll()
        getOnlinePlayers().forEach { unInjectPlayerChannel(it) }
        unInjectServerChannel()
    }

    /** private implements */

    init {
        object: MoonLakeListener {
            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
            fun onJoin(event: PlayerJoinEvent) {
                val channel = getPlayerChannel(event.player)
                if(!unInjectedChannels.contains(channel))
                    injectPlayerChannel(event.player)
            }
        }.registerEvent(getMoonLake())
        try {
            injectServerChannel()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @JvmName("onSendingAsync")
    internal fun onSendingAsync(receiver: Player?, channel: Channel, packet: Any): Any? {
        val wrapped = Packets.createReadPacketSafe(packet) ?: return packet
        val event = PacketEvent(packet, wrapped, receiver)
        synchronized(listeners) {
            if(listeners.isNotEmpty()) listeners.forEach {
                if(it.getSendingTypes().find { it.isInstance(wrapped) } != null) try {
                    it.onSending(event)
                } catch(e: Exception) {
                    it.getPlugin().logger.log(Level.SEVERE, "[MoonLake] 插件 ${it.getPlugin()} 的监听器执行数据包发送 onSending() 时异常:", e)
                }
            }
        }
        return if(event.isCancelled) null else event.packet.handle
    }

    @JvmStatic
    @JvmName("onReceivingAsync")
    internal fun onReceivingAsync(sender: Player?, channel: Channel, packet: Any): Any? {
        val wrapped = Packets.createReadPacketSafe(packet) ?: return packet
        val event = PacketEvent(packet, wrapped, sender)
        synchronized(listeners) {
            if(listeners.isNotEmpty()) listeners.forEach {
                if(it.getReceivingTypes().find { it.isInstance(wrapped) } != null) try {
                    it.onReceiving(event)
                } catch(e: Exception) {
                    it.getPlugin().logger.log(Level.SEVERE, "[MoonLake] 插件 ${it.getPlugin()} 的监听器执行数据包接收 onReceiving() 时异常:", e)
                }
            }
        }
        return if(event.isCancelled) null else event.packet.handle
    }

    @JvmStatic
    @JvmName("unInjectServerChannel")
    internal fun unInjectServerChannel() {
        serverChannels.forEach {
            val pipeline = it.pipeline()
            it.eventLoop().execute {
                try {
                    pipeline.remove(serverChannelHandler)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    @JvmStatic
    @JvmName("getPlayerChannel")
    internal fun getPlayerChannel(player: Player): Channel {
        var channel = lookupChannel[player.name]
        if(channel == null)
            lookupChannel.put(player.name, (MinecraftPlayerMembers.CHANNEL.get(player) as Channel).apply { channel = this })
        return channel as Channel
    }

    @JvmStatic
    @JvmName("injectChannel")
    internal fun injectChannel(channel: Channel): PacketListenerHandler = try {
        var handler = channel.pipeline()[handlerName] as PacketListenerHandler?
        if(handler == null) {
            handler = PacketListenerHandler()
            channel.pipeline().addBefore("packet_handler", handlerName, handler)
            unInjectedChannels.remove(channel)
        }
        handler
    } catch(e: IllegalArgumentException) {
        channel.pipeline()[handlerName] as PacketListenerHandler
    }

    @JvmStatic
    @JvmName("injectPlayerChannel")
    internal fun injectPlayerChannel(player: Player)
            { injectChannel(getPlayerChannel(player)).player = player }

    @JvmStatic
    @JvmName("unInjectPlayerChannel")
    internal fun unInjectPlayerChannel(player: Player) {
        val channel = getPlayerChannel(player)
        unInjectedChannels.add(channel)
        channel.eventLoop().execute { channel.pipeline().remove(handlerName) }
    }

    internal class PacketListenerHandler : ChannelDuplexHandler() {

        @Volatile
        internal var player: Player? = null

        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
            val channel = ctx.channel()
            var packet: Any? = null
            try {
                packet = onReceivingAsync(player, channel, msg)
            } catch(e: Exception) {
                e.printStackTrace()
            }
            if(packet != null)
                super.channelRead(ctx, packet)
        }

        override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
            val channel = ctx.channel()
            var packet: Any? = null
            try {
                packet = onSendingAsync(player, channel, msg)
            } catch(e: Exception) {
                e.printStackTrace()
            }
            if(packet != null)
                super.write(ctx, msg, promise)
        }
    }
}
