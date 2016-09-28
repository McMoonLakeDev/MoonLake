package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/6/23.
 */
public interface Packet<T extends Packet> {

    // update nms packet

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    void send(String... names);

    /**
     * 将此数据包发送给指定玩家
     *
     * @param players 玩家
     */
    void send(Player... players);

    /**
     * 将此数据包发送给指定玩家
     *
     * @param players 玩家
     */
    void send(MoonLakePlayer... players);

    /**
     * 将此数据包发送给在线所有玩家
     */
    void sendAll();
}
