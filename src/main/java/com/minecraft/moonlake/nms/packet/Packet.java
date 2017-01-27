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
 
 
package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;

/**
 * <div>
 *     <h1>Net Minecraft Server Packet Library</h1>
 *     <p>By Month_Light Ver: 1.1</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简介</h1>
 *     <p>这个支持库的主要目的就是 Minecraft 的数据包系统</p>
 *     <p>包装成支持多版本的目的, 实现更厉害的功能等等.</p>
 *     <hr />
 *     <h2>更多数据包可以反编译服务端文件:</h2>
 *     <p>包路径: net.minecraft.server.服务端版本.Packet</p>
 *     <p>例如: {@link net.minecraft.server.v1_10_R1.Packet}</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简单的使用例子: 发送标题数据包</h1>
 *     <p><s>PacketPlayOutTitle packetPlayOutTitle = PacketFactory.get().instance(PacketPlayOutTitle.class);</s></p>
 *     <p>PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle();</p>
 *     <p>packetPlayOutTitle.getTitle().set("Minecraft Packet Library");</p>
 *     <p>packetPlayOutTitle.getSubTitle().set("> By Month_Light");</p>
 *     <p>packetPlayOutTitle.getFadeIn().set(20);</p>
 *     <p>packetPlayOutTitle.getStay().set(60);</p>
 *     <p>packetPlayOutTitle.getFadeOut().set(20);</p>
 *     <h2>发送给在线玩家或指定玩家:</h2>
 *     <p>packetPlayOutTitle.sendAll();</p>
 *     <p>packetPlayOutTitle.send("Notch", "Month_Light");</p>
 * </div>
 * <hr />
 * <p>不定时更新更多数据包的包装 233333</p>
 *
 * @version 1.1
 * @author Month_Light
 */
public interface Packet<T extends Packet> {

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(Player... players);

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(String... players);

    /**
     * 将此数据包发送到指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送错误则抛出异常
     */
    void send(MoonLakePlayer... players);

    /**
     * 将此数据包发送到服务器在线所有玩家
     *
     * @throws PacketException 如果发送错误则抛出异常
     */
    void sendAll();
}
