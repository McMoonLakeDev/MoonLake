package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
 */
public abstract class PacketAbstract<T extends Packet> implements Packet<T> {

    public PacketAbstract() {

    }

    @Override
    public abstract void send(Player... players) throws PacketException;

    @Override
    public void send(String... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        send(PlayerManager.adapter(players));
    }

    @Override
    public void send(MoonLakePlayer... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        send(PlayerManager.adapter(players));
    }

    @Override
    public void sendAll() throws PacketException {

        send(PlayerManager.getOnlines());
    }
}
