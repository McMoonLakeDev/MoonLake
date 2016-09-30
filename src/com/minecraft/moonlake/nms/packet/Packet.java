package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
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
