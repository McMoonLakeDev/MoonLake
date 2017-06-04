/*
 * Copyright (C) 2016 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet.listener;

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.validate.Validate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.ArrayList;

import static com.minecraft.moonlake.reflect.Reflect.getField;

/**
 * <h1>PacketINCChannel</h1>
 * 数据包 INC 通道实现类 ({@code io.netty.channel})
 *
 * @version 1.0
 * @author Month_Light
 */
class PacketINCChannel extends PacketChannelAbstract {

    private final static Field FIELD_CHANNEL;

    static {

        try {

            FIELD_CHANNEL = getField(CLASS_NETWORKMANAGER, true, "channel");
        }
        catch (Exception e) {

            throw new NMSException("The packet listener channel reflect raw initialize exception.", e);
        }
    }

    /**
     * 数据包 INC 通道实现类构造函数
     *
     * @param packetListener 数据包监听器
     */
    public PacketINCChannel(PacketListener packetListener) {

        super(packetListener);
    }

    @Override
    public void addChannel(Player player) throws PacketException {

        Validate.notNull(player, "The player object is null.");

        try {

            final Channel channel = getChannel(player);

            getAddChannelExecutor().execute(new Runnable() {

                @Override
                public void run() {

                    try {

                        if(channel.pipeline().get(KEY_HANDLER) != null) {

                            channel.pipeline().addBefore(KEY_HANDLER, KEY_PLAYER, new ChannelHandler(player));
                        }
                    }
                    catch (Exception e) {

                        throw new PacketException("The failed to add channel for " + player.getName(), e);
                    }
                }
            });
        }
        catch (Exception e) {

            throw new PacketException("The failed to add channel for " + player.getName(), e);
        }
    }

    @Override
    public void removeChannel(Player player) throws PacketException {

        Validate.notNull(player, "The player object is null.");

        try {

            final Channel channel = getChannel(player);

            getRemoveChannelExecutor().execute(new Runnable() {

                @Override
                public void run() {

                    try {

                        if(channel.pipeline().get(KEY_PLAYER) != null) {

                            channel.pipeline().remove(KEY_PLAYER);
                        }
                    }
                    catch (Exception e) {

                        throw new PacketException("The failed to remove channel for " + player.getName(), e);
                    }
                }
            });
        }
        catch (Exception e) {

            throw new PacketException("The failed to remove channel for " + player.getName(), e);
        }
    }

    @Override
    public PacketListenerList newListenerList() {

        return new ListenerList();
    }

    /**
     * 获取指定玩家的 INC 通道对象
     *
     * @param player 玩家
     * @return Channel
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws NMSException 如果获取错误则抛出异常
     */
    private Channel getChannel(Player player) throws NMSException {

        Validate.notNull(player, "The player object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);
            Object nmsConnection = FIELD_PLAYERCONNECTION.get(nmsPlayer);

            return (Channel) FIELD_CHANNEL.get(FIELD_NETWORKMANAGER.get(nmsConnection));
        }
        catch (Exception e) {

            throw new NMSException("The get player network packet listener channel exception.", e);
        }
    }

    /**
     * <h1>ListenerList</h1>
     * 数据包监听器列表接口 INC 实现类
     *
     * @version 1.0
     * @author Month_Light
     */
    private class ListenerList<E> extends ArrayList<E> implements PacketListenerList<E> {

        /**
         * 数据包监听器列表接口 INC 实现类构造函数
         */
        ListenerList() {
        }

        @Override
        public boolean add(E e) {

            try {

                final E obj = e;

                getAddChannelExecutor().execute(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            Channel channel = null;

                            while(channel == null) {

                                channel = (Channel) FIELD_CHANNEL.get(obj);
                            }
                            if(channel.pipeline().get(KEY_SERVER) == null) {

                                channel.pipeline().addBefore(KEY_HANDLER, KEY_SERVER, new ChannelHandler(new ChannelWrapper(channel)));
                            }
                        }
                        catch (Exception ex) {
                        }
                    }
                });
            }
            catch (Exception ex) {
            }
            return super.add(e);
        }

        @Override
        public boolean remove(Object o) {

            try {

                final Object obj = o;

                getRemoveChannelExecutor().execute(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            Channel channel = null;

                            while(channel == null) {

                                channel = (Channel) FIELD_CHANNEL.get(obj);
                            }
                            if(channel.pipeline().get(KEY_SERVER) != null) {

                                channel.pipeline().remove(KEY_SERVER);
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                });
            }
            catch (Exception e) {
            }
            return super.remove(o);
        }
    }

    /**
     * <h1>ChannelHandler</h1>
     * 数据包通道处理接口 INC 实现类
     *
     * @version 1.0
     * @author Month_Light
     */
    private class ChannelHandler extends ChannelDuplexHandler implements PacketChannelHandler {

        private Object owner;

        /**
         * 数据包通道处理接口 INC 实现类构造函数
         *
         * @param player 玩家
         */
        public ChannelHandler(Player player) {

            this.owner = player;
        }

        /**
         * 数据包通道处理接口 INC 实现类构造函数
         *
         * @param channelWrapper 通道包装
         */
        public ChannelHandler(ChannelWrapper channelWrapper) {

            this.owner = channelWrapper;
        }

        @Override
        public void write(ChannelHandlerContext channelHandlerContext, Object o, ChannelPromise channelPromise) throws Exception {

            Object packet = o;
            Cancellable cancellable = new Cancellable();

            if(CLASS_PACKET.isAssignableFrom(o.getClass())) {

                packet = onPacketSend(owner, o, cancellable);
            }
            if(cancellable.isCancelled()) {

                return;
            }
            super.write(channelHandlerContext, packet, channelPromise);
        }

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

            Object packet = o;
            Cancellable cancellable = new Cancellable();

            if(CLASS_PACKET.isAssignableFrom(o.getClass())) {

                packet = onPacketReceive(owner, o, cancellable);
            }
            if(cancellable.isCancelled()) {

                return;
            }
            super.channelRead(channelHandlerContext, packet);
        }

        @Override
        public String toString() {

            return "PacketINCChannel$ChannelHandler@" + hashCode() + " (" + owner + ")";
        }
    }

    /**
     * <h1>ChannelWrapper</h1>
     * 数据包通道包装接口 INC 实现类
     *
     * @version 1.0
     * @author Month_Light
     */
    private class ChannelWrapper extends PacketChannelWrappedExpression<Channel> implements PacketChannelWrapper {

        /**
         * 数据包通道包装接口 INC 实现类构造函数
         *
         * @param channel 通道
         */
        public ChannelWrapper(Channel channel) {

            super(channel);
        }

        @Override
        public SocketAddress getRemoteAddress() {

            return channel().remoteAddress();
        }

        @Override
        public SocketAddress getLocalAddress() {

            return channel().localAddress();
        }
    }
}
