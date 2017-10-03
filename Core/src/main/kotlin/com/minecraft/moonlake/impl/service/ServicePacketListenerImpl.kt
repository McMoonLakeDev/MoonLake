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

package com.minecraft.moonlake.impl.service

import com.minecraft.moonlake.api.*
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.packet.PacketEvent
import com.minecraft.moonlake.api.packet.PacketListener
import com.minecraft.moonlake.api.packet.Packets
import com.minecraft.moonlake.api.reflect.FuzzyReflect
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.service.ServiceConfig
import com.minecraft.moonlake.api.service.ServicePacketListener
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.utility.MinecraftPlayerMembers
import com.minecraft.moonlake.api.utility.MinecraftReflection
import io.netty.channel.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import java.util.TreeSet
import java.util.logging.Level
import kotlin.Comparator
import kotlin.collections.ArrayList

class ServicePacketListenerImpl : ServiceAbstractCore(), ServicePacketListener {

    private var eventListener: MoonLakeListener? = null

    override fun onInitialized() {
        val listener = object: MoonLakeListener {
            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
            fun onJoin(event: PlayerJoinEvent)
                    = injectChannelPlayer(event.player)
        }
        eventListener = listener
        eventListener?.registerEvent(getMoonLake())
        var result = false
        try {
            injectOnlinePlayers()
            injectChannelServer()
            result = true
        } catch(e: Exception) {
            // If you fail again try again
            getMoonLake().runTask { try {
                injectOnlinePlayers()
                injectChannelServer()
                result = true
            } catch(e: Exception) {
                handlerException(e)
            } }
        }
        if(result)
            getMoonLake().logger.info("数据包监听器服务初始化工作完成.")
    }

    override fun onUnloaded() {
        unloadEventListener()
        unregisterListenerAll()
        unInjectOnlinePlayers()
        unInjectChannelServer()
    }

    override fun registrable(): Boolean
            = getMoonLake().serviceManager.getService(ServiceConfig::class.java).hasPacketListener()

    /** api */

    private val listeners: MutableSet<PacketListener> by lazy {
        TreeSet(Comparator<PacketListener> { o1, o2 -> o2.priority.compareTo(o1.priority) })
    }

    override fun registerListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.add(listener) })

    override fun unregisterListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.remove(listener) })

    override fun unregisterListener(plugin: Plugin): Boolean
            = synchronized(listeners, { val removeList = listeners.filter { it.plugin == plugin }; listeners.removeAll(removeList) })

    override fun unregisterListenerAll()
            = synchronized(listeners, { listeners.clear() })

    /** implements */

    private fun unloadEventListener() {
        if(eventListener != null) {
            eventListener?.unregisterAll()
            eventListener = null
        }
    }

    private val serverChannels: MutableList<Channel> by lazy { ArrayList<Channel>() }
    private val minecraftServerConnection: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getMinecraftServerClass(), MinecraftReflection.getServerConnectionClass(), true) }

    private fun injectChannelServer() {
        val mcServer = MinecraftConverters.getServer().getGeneric(Bukkit.getServer())
        val serverConnection = minecraftServerConnection.get(mcServer) ?: return
        var lookup = false
        for(field in FuzzyReflect.fromClass(serverConnection::class.java).getFieldListByType(List::class.java)) {
            if(lookup)
                break
            val list = field.get(serverConnection) as List<*>
            for(item in list) {
                if(!ChannelFuture::class.java.isInstance(item))
                    break
                val serverChannel = (item as ChannelFuture).channel()
                serverChannels.add(serverChannel)
                serverChannel.pipeline().addFirst(channelServerHandler)
                lookup = true
            }
        }
    }

    private fun unInjectChannelServer() {
        serverChannels.forEach {
            val pipeline = it.pipeline()
            it.eventLoop().execute {
                try {
                    pipeline.remove(channelServerHandler)
                } catch(e: Exception) {
                    //handlerException(e) // ignoreEx
                }
            }
        }
    }

    private fun getChannelPlayer(player: Player): Channel
            = MinecraftPlayerMembers.CHANNEL.get(player) as Channel

    private fun injectChannelPlayer(player: Player)
            { injectChannel(getChannelPlayer(player)).player = player }

    private fun unInjectChannelPlayer(player: Player) {
        val channel = getChannelPlayer(player)
        channel.eventLoop().execute { channel.pipeline().remove(NAME) }
    }

    private fun injectOnlinePlayers()
            { getOnlinePlayers().forEach { injectChannelPlayer(it) } }

    private fun unInjectOnlinePlayers()
            { getOnlinePlayers().forEach { unInjectChannelPlayer(it) } }

    private fun injectChannel(channel: Channel): ChannelPacketListenerHandler = try {
        var handler = channel.pipeline()[NAME] as ChannelPacketListenerHandler?
        if(handler == null) {
            handler = ChannelPacketListenerHandler(this)
            channel.pipeline().addBefore(HANDLER, NAME, handler)
        }
        handler
    } catch(e: Exception) {
        channel.pipeline()[NAME] as ChannelPacketListenerHandler
    }

    private val channelEndInitializer = object: ChannelInitializer<Channel>() {
        override fun initChannel(channel: Channel) {
            try {
                channel.eventLoop().submit { injectChannel(channel) }
            } catch(e: Exception) {
                handlerException(e)
            }
        }
    }

    private val channelBeginInitializer = object: ChannelInitializer<Channel>() {
        override fun initChannel(channel: Channel) {
            channel.pipeline().addLast(channelEndInitializer)
        }
    }

    private val channelServerHandler = object: ChannelInboundHandlerAdapter() {
        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
            val serverChannel = msg as Channel
            serverChannel.pipeline().addFirst(channelBeginInitializer)
            ctx.fireChannelRead(msg)
        }
    }

    internal fun handlerException(ex: Exception?) {
        ex?.printStackTrace()
    }

    internal fun onReceivingAsync(sender: Player?, channel: Channel, packet: Any): Any?
            = onExecuteAndFilterPacketAsync(Direction.IN, sender, channel, packet)

    internal fun onSendingAsync(receiver: Player?, channel: Channel, packet: Any): Any?
            = onExecuteAndFilterPacketAsync(Direction.OUT, receiver, channel, packet)

    private fun onExecuteAndFilterPacketAsync(direction: Direction, player: Player?, channel: Channel, packet: Any): Any? {
        val wrapped = Packets.createReadPacketSafe(packet) ?: return packet
        val event = PacketEvent(packet, wrapped, player)
        synchronized(listeners) {
            if(listeners.isNotEmpty()) listeners.forEach {
                val filter = if(direction == Direction.IN) it.receivingTypes else it.sendingTypes
                if(filter.find { it.isInstance(wrapped) } != null) try {
                    when(direction) {
                        Direction.IN -> it.onReceiving(event)
                        else -> it.onSending(event)
                    }
                } catch(e: Exception) {
                    it.plugin.logger.log(Level.SEVERE, "[MoonLake] 插件 ${it.plugin} 的数据包监听器执行 $direction 时异常:", e)
                }
            }
        }
        return if(event.isCancelled) null else Packets.createReadPacket(event.packet) // create new nms packet instance
    }

    internal enum class Direction {

        IN { override fun toString(): String = "接收[onReceiving()]" },
        OUT { override fun toString(): String = "发送[onSending()]" },
        ;
    }

    internal class ChannelPacketListenerHandler(private val service: ServicePacketListenerImpl) : ChannelDuplexHandler() {

        @Volatile
        internal var player: Player? = null

        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
            val channel = ctx.channel()
            var packet: Any? = null
            try {
                packet = service.onReceivingAsync(player, channel, msg)
            } catch(e: Exception) {
                service.handlerException(e)
            }
            if(packet != null)
                super.channelRead(ctx, packet)
        }

        override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
            val channel = ctx.channel()
            var packet: Any? = null
            try {
                packet = service.onSendingAsync(player, channel, msg)
            } catch(e: Exception) {
                service.handlerException(e)
            }
            if(packet != null)
                super.write(ctx, packet, promise)
        }
    }

    companion object {

        @JvmField
        internal val HANDLER = "packet_handler"

        @JvmField
        internal val NAME = "packet_moonlake"
    }
}
