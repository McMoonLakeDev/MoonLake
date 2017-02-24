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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.PacketPlayOutBungee;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import org.bukkit.plugin.Plugin;

import java.net.InetSocketAddress;

/**
 * <h1>InternetPlayer</h1>
 * 玩家网络接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface InternetPlayer {

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     */
    int getPing();

    /**
     * 获取此玩家的网络套接字地址
     *
     * @return 网络套接字地址
     */
    InetSocketAddress getAddress();

    /**
     * 获取此玩家的网络套接字地址 IP
     *
     * @return 网络套接字地址 IP 如果未解析则返回 127.0.0.1
     */
    String getIp();

    /**
     * 获取此玩家的网络套接字地址端口号
     *
     * @return 网络套接字地址端口号
     */
    int getPort();

    /**
     * 给此玩家发送指定 Bukkit 数据包输出
     *
     * @param packet 数据包
     * @throws IllegalArgumentException 如果数据包对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBukkitPacket(PacketPlayOutBukkit packet);

    /**
     * 从此玩家发送蹦极数据包到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeePacket(Plugin plugin, PacketPlayOutBungee packet);

    /**
     * 从此玩家异步发送蹦极数据包到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeePacketAsync(Plugin plugin, PacketPlayOutBungee packet);

    /**
     * 从此玩家发送不安全蹦极数据包到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeePacketUnsafe(Plugin plugin, PacketPlayOutBungee packet);

    /**
     * 从此玩家异步发送不安全蹦极数据包到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeePacketUnsafeAsync(Plugin plugin, PacketPlayOutBungee packet);

    /**
     * 从此玩家发送蹦极连接数据包到蹦极代理
     *
     * @param plugin 插件
     * @param targetServer 目标服务器
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeeConnect(Plugin plugin, String targetServer);

    /**
     * 从此玩家异步发送蹦极连接数据包到蹦极代理
     *
     * @param plugin 插件
     * @param targetServer 目标服务器
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeeConnectAsync(Plugin plugin, String targetServer);

    /**
     * 从此玩家发送不安全蹦极连接数据包到蹦极代理
     *
     * @param plugin 插件
     * @param targetServer 目标服务器
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeeConnectUnsafe(Plugin plugin, String targetServer);

    /**
     * 从此玩家异步发送不安全蹦极连接数据包到蹦极代理
     *
     * @param plugin 插件
     * @param targetServer 目标服务器
     * @throws PacketException 如果发送时错误则抛出异常
     */
    void sendBungeeConnectUnsafeAsync(Plugin plugin, String targetServer);
}
