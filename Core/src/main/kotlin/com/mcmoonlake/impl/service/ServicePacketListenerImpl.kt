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

package com.mcmoonlake.impl.service

import com.mcmoonlake.api.*
import com.mcmoonlake.api.event.MoonLakeListener
import com.mcmoonlake.api.packet.PacketEvent
import com.mcmoonlake.api.packet.PacketListener
import com.mcmoonlake.api.packet.Packets
import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.accessor.AccessorField
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.service.ServiceConfig
import com.mcmoonlake.api.service.ServicePacketListener
import com.mcmoonlake.api.utility.MinecraftConverters
import com.mcmoonlake.api.utility.MinecraftPlayerMembers
import com.mcmoonlake.api.utility.MinecraftReflection
import io.netty.channel.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.logging.Level

open class ServicePacketListenerImpl : ServiceAbstractCore(), ServicePacketListener {

    private var eventListener: MoonLakeListener? = null

    override fun onInitialized() {
        val listener = object: MoonLakeListener {
            @EventHandler(priority = EventPriority.MONITOR)
            fun onJoin(event: PlayerJoinEvent)
                    = injectChannelPlayer(event.player)
        }
        eventListener = listener
        eventListener?.registerEvent(getMoonLake())
        var result = false
        try {
            injectChannelServer()
            injectOnlinePlayers()
            result = true
        } catch(e: Exception) {
            // If you fail again try again
            getMoonLake().runTask {
                result = try {
                    injectChannelServer()
                    injectOnlinePlayers()
                    true
                } catch(e: Exception) {
                    handlerException(e)
                    false
                }
            }
        }
        if(result) getMoonLake().logger.info("数据包监听器服务初始化工作完成.")
        else getMoonLake().logger.warning("数据包监听器服务初始化工作异常, 请提交异常栈信息.")
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

    private val listeners: MutableList<PacketListener> by lazy {
        ArrayList<PacketListener>() }

    override fun registerListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.add(listener).also { if(it) sortListeners() } })

    override fun unregisterListener(listener: PacketListener): Boolean
            = synchronized(listeners, { listeners.remove(listener).also { if(it) sortListeners() } })

    override fun unregisterListener(plugin: Plugin): Boolean
            = synchronized(listeners, { val removeList = listeners.filter { it.plugin == plugin }; listeners.removeAll(removeList).also { if(it) sortListeners() } })

    override fun unregisterListenerAll()
            = synchronized(listeners, { listeners.clear() })

    /** implements */

    private fun sortListeners()
            = Collections.sort(listeners, { o1, o2 -> o2.priority.compareTo(o1.priority) })

    private fun sortListenersSync()
            = synchronized(listeners, { Collections.sort(listeners, { o1, o2 -> o2.priority.compareTo(o1.priority) }) })

    private fun unloadEventListener() {
        if(eventListener != null) {
            eventListener?.unregisterAll()
            eventListener = null
        }
    }

    private val serverChannels: MutableList<Channel> by lazy {
        ArrayList<Channel>() }
    private val networkManagers: MutableList<Any> by lazy {
        val mcServer = MinecraftConverters.getServer().getGeneric(Bukkit.getServer())
        val serverConnection = minecraftServerConnection.get(mcServer) ?: throw IllegalStateException("Null of Minecraft Server Connection.")
        @Suppress("UNCHECKED_CAST")
        Accessors.getAccessorMethod(serverConnection::class.java, List::class.java, true, arrayOf(serverConnection::class.java))
                .invoke(serverConnection, serverConnection) as MutableList<Any> }
    private val minecraftServerConnection: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getMinecraftServerClass(), MinecraftReflection.getServerConnectionClass(), true) }

    private fun injectChannelServer() {
        val mcServer = MinecraftConverters.getServer().getGeneric(Bukkit.getServer())
        val serverConnection = minecraftServerConnection.get(mcServer) ?: return
        for(field in FuzzyReflect.fromClass(serverConnection::class.java, true).getFieldListByType(List::class.java)) {
            field.isAccessible = true
            val list = field.get(serverConnection) as MutableList<*>
            for(item in list) {
                if(!ChannelFuture::class.java.isInstance(item))
                    break
                val serverChannel = (item as ChannelFuture).channel()
                serverChannels.add(serverChannel)
                serverChannel.pipeline().addFirst(channelServerHandler)
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
                synchronized(networkManagers) {
                    channel.eventLoop().submit { injectChannel(channel) }
                }
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

    open internal fun onReceivingAsync(sender: Player?, channel: Channel, packet: Any): Any?
            = onExecuteAndFilterPacketAsync(Direction.IN, sender, channel, packet)

    open internal fun onSendingAsync(receiver: Player?, channel: Channel, packet: Any): Any?
            = onExecuteAndFilterPacketAsync(Direction.OUT, receiver, channel, packet)

    private fun onExecuteAndFilterPacketAsync(direction: Direction, player: Player?, channel: Channel, packet: Any): Any? {
        val wrapped = Packets.createBufferPacketSafe(packet) ?: return packet
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
                    it.plugin.logger.log(Level.SEVERE, "[MoonLake] 插件 ${it.plugin} 的数据包监听器执行 $direction 时异常.")
                    it.handlerException(e)
                }
            }
        }
        return if(event.isCancelled) null else Packets.createBufferPacket(event.packet) // create new nms packet instance
    }

    internal enum class Direction {

        IN { override fun toString(): String = "接收[onReceiving()]" },
        OUT { override fun toString(): String = "发送[onSending()]" },
        ;
    }

    internal class ChannelPacketListenerHandler(private val service: ServicePacketListenerImpl) : ChannelDuplexHandler() {

        @Volatile
        @JvmField
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
        private const val HANDLER = "packet_handler"
        private const val NAME = "packet_moonlake"
    }
}
