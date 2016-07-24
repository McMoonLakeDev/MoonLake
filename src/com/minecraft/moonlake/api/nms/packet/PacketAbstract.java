package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/7/22.
 */
public abstract class PacketAbstract<T extends Packet> implements Packet<T> {

    public PacketAbstract() {

    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    public abstract void send(String... names);

    /**
     * 将此数据包发送给指定玩家
     *
     * @param players 玩家
     */
    @Override
    public final void send(Player... players) {

        send(PacketManager.getNamesfromPlayers(players));
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param players 玩家
     */
    @Override
    public final void send(MoonLakePlayer... players) {

        send(PacketManager.getNamesfromPlayers(players));
    }

    /**
     * 将此数据包发送给在线所有玩家
     */
    @Override
    public final void sendAll() {

        send(PlayerManager.getOnlines());
    }
}
