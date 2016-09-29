package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
 */
public interface Packet<T extends Packet> {

    void send(Player... players);

    void send(String... players);

    void send(MoonLakePlayer... players);

    void sendAll();
}
