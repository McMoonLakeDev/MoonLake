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


package com.minecraft.moonlake.api.packet.listener.handler;

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannelWrapped;
import org.bukkit.entity.Player;

/**
 * <h1>PacketHandlerAbstract</h1>
 * 数据包处理器抽象接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PacketHandlerAbstract {

    /**
     * 设置此数据包是否阻止
     *
     * @param cancel 是否阻止
     */
    void setCancelled(boolean cancel);

    /**
     * 获取此数据包是否阻止
     *
     * @return 是否阻止
     */
    boolean isCancelled();

    /**
     * 获取此数据包的发送玩家
     *
     * @return 玩家
     */
    Player getPlayer();

    /**
     * 获取此数据包是否拥有玩家
     *
     * @return 是否拥有玩家
     */
    boolean hasPlayer();

    /**
     * 获取此数据包的通道
     *
     * @return 数据包的通道
     */
    PacketChannelWrapped<?> getChannel();

    /**
     * 获取此数据包是否拥有通道
     *
     * @return 是否拥有通道
     */
    boolean hasChannel();

    /**
     * 获取此数据包的玩家名称
     *
     * @return 玩家名称
     */
    String getPlayerName();

    /**
     * 设置此数据包对象
     *
     * @param packet 数据包
     */
    void setPacket(Object packet);

    /**
     * 获取此数据包对象
     *
     * @return 数据包
     */
    Object getPacket();

    /**
     * 获取此数据包的名称
     *
     * @return 名称
     */
    String getPacketName();

    @Override
    String toString();

    /**
     * 设置此数据包对象的字段值
     *
     * @param field 字段名
     * @param value 字段值
     * @throws NMSException 如果设置错误则抛出异常
     */
    void setPacketValue(String field, Object value) throws NMSException;

    /**
     * 获取此数据包对象的字段值
     *
     * @param field 字段名
     * @return 字段值
     * @throws NMSException 如果获取错误则抛出异常
     */
    Object getPacketValue(String field) throws NMSException;
}
