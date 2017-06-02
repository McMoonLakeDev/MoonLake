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

import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannel;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.validate.Validate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <h1>PacketChannelAbstract</h1>
 * 数据包通道接口抽象类（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
abstract class PacketChannelAbstract implements PacketChannel {

    final static String KEY_HANDLER;
    final static String KEY_PLAYER;
    final static String KEY_SERVER;

    static {

        KEY_HANDLER = "packet_handler";
        KEY_PLAYER = "moonlake_packet_listener_player";
        KEY_SERVER = "moonlake_packet_listener_server";
    }

    private final Executor addChannelExecutor;
    private final Executor removeChannelExecutor;
    private PacketListener packetListener;

    /**
     * 数据包通道接口抽象类构造函数
     *
     * @param packetListener 数据包监听器
     */
    public PacketChannelAbstract(PacketListener packetListener) {

        this.packetListener = packetListener;
        this.addChannelExecutor = Executors.newSingleThreadExecutor();
        this.removeChannelExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addChannel(MoonLakePlayer moonLakePlayer) throws PacketException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        addChannel(moonLakePlayer.getBukkitPlayer());
    }

    @Override
    public void removeChannel(MoonLakePlayer moonLakePlayer) throws PacketException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        removeChannel(moonLakePlayer.getBukkitPlayer());
    }

    @Override
    public void addServerChannel() throws PacketException {

        try {

            List currentList = MinecraftReflection.getNetworkManagerList();
            List newList = Collections.synchronizedList(newListenerList());

            for(Object obj : currentList) {

                newList.add(obj);
            }
            MinecraftReflection.getServerConnectionNetworkManagerListField().set(MinecraftReflection.getMinecraftServerConnection(), newList);
        }
        catch (Exception e) {

            throw new PacketException("The add server packet listener channel exception.", e);
        }
    }

    /**
     * 获取此数据包通道的添加通道执行器
     *
     * @return 添加通道执行器
     */
    protected final Executor getAddChannelExecutor() {

        return addChannelExecutor;
    }

    /**
     * 获取此数据包通道的删除通道执行器
     *
     * @return 删除通道执行器
     */
    protected final Executor getRemoveChannelExecutor() {

        return removeChannelExecutor;
    }

    /**
     * 处理此数据包通道的数据包发送
     *
     * @param receiver 接收者
     * @param packet 数据包
     * @param cancellable 阻止器
     * @return 数据包
     */
    protected final Object onPacketSend(Object receiver, Object packet, Cancellable cancellable) {

        return packetListener.onPacketSend(receiver, packet, cancellable);
    }

    /**
     * 处理此数据包通道的数据包接收
     *
     * @param sender 发送者
     * @param packet 数据包
     * @param cancellable 阻止器
     * @return 数据包
     */
    protected final Object onPacketReceive(Object sender, Object packet, Cancellable cancellable) {

        return packetListener.onPacketReceive(sender, packet, cancellable);
    }
}
