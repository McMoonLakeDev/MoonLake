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


package com.minecraft.moonlake.api.packet.listener.channel;

import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.listener.PacketListenerList;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;

/**
 * <h1>PacketChannel</h1>
 * 数据包通道接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PacketChannel {

    /**
     * 给指定玩家添加数据包通道
     *
     * @param player 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果添加错误则抛出异常
     */
    void addChannel(Player player) throws PacketException;

    /**
     * 给指定玩家删除数据包通道
     *
     * @param player 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果删除错误则抛出异常
     */
    void removeChannel(Player player) throws PacketException;

    /**
     * 给指定玩家添加数据包通道
     *
     * @param moonLakePlayer 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果添加错误则抛出异常
     */
    void addChannel(MoonLakePlayer moonLakePlayer) throws PacketException;

    /**
     * 给指定玩家删除数据包通道
     *
     * @param moonLakePlayer 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果删除错误则抛出异常
     */
    void removeChannel(MoonLakePlayer moonLakePlayer) throws PacketException;

    /**
     * 给服务器添加数据包通道
     *
     * @throws PacketException 如果添加错误则抛出异常
     */
    void addServerChannel() throws PacketException;

    /**
     * 获取数据包通道监听器列表
     *
     * @return 数据包通道监听器列表
     */
    PacketListenerList newListenerList();

    /**
     * <h1>PacketChannelWrapper</h1>
     * 数据包通道包装接口（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    interface PacketChannelWrapper {}

    /**
     * <h1>PacketChannelHandler</h1>
     * 数据包通道处理接口（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    interface PacketChannelHandler {}
}
