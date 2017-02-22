/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet;

import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutCustomPayload;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * <h1>PacketPlayOutBungee</h1>
 * Minecraft 数据包输出蹦极（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 */
public interface PacketPlayOutBungee extends PacketPlayOut, AutoCloseable {

    /**
     * BungeeCord Channel Name
     */
    String CHANNEL = "BungeeCord";

    /**
     * 获取此蹦极数据包的数据字节数组
     *
     * @return 数组字节数组
     */
    byte[] getDataByteArray();

    /**
     * 将此蹦极数据包从指定玩家发送到蹦极代理
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link #CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacket(Plugin plugin, Player... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link #CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketAsync(Plugin plugin, Player... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家发送到蹦极代理 (不安全: 自定义通道数据 {@link PacketPlayOutCustomPayload})
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketUnsafe(Plugin plugin, Player... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家异步发送到蹦极代理 (不安全: 自定义通道数据 {@link PacketPlayOutCustomPayload})
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketUnsafeAsync(Plugin plugin, Player... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家发送到蹦极代理
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link #CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacket(Plugin plugin, MoonLakePlayer... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link #CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketAsync(Plugin plugin, MoonLakePlayer... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家发送到蹦极代理 (不安全: 自定义通道数据 {@link PacketPlayOutCustomPayload})
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketUnsafe(Plugin plugin, MoonLakePlayer... senders) throws PacketException;

    /**
     * 将此蹦极数据包从指定玩家异步发送到蹦极代理 (不安全: 自定义通道数据 {@link PacketPlayOutCustomPayload})
     *
     * @param plugin 插件
     * @param senders 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendPacketUnsafeAsync(Plugin plugin, MoonLakePlayer... senders) throws PacketException;
}
