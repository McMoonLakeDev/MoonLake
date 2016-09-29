package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutAbilities extends PacketAbstract<PacketPlayOutAbilities> {

    @Deprecated
    public PacketPlayOutAbilities() {

    }

    @Override
    public void send(Player... players) throws PacketException {

    }
}
