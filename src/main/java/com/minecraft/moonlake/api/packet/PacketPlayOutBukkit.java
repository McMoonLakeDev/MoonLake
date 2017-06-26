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
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutBukkit</h1>
 * Minecraft 数据包输出 Bukkit
 *
 * @version 1.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketNameable
 */
public interface PacketPlayOutBukkit extends PacketPlayOut, PacketNameable {

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(Player... players) throws PacketException;

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(String... players) throws PacketException;

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(MoonLakePlayer... players) throws PacketException;

    /**
     * 将此数据包发送到服务器在线所有玩家
     *
     * @throws PacketException 如果发送错误则抛出异常
     */
    void sendAll() throws PacketException;

    /**
     * 创建此数据包输出 Bukkit 对应的 NMS 数据包实例对象
     *
     * @return NMS Packet
     */
    @Nullable
    Object packet();
}
