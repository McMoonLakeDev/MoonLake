package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class PacketPlayOutPlayerListHeaderFooter implements Packet<PacketPlayOutPlayerListHeaderFooter> {

    private String header;
    private String footer;

    public PacketPlayOutPlayerListHeaderFooter(String header) {

        this(header, null);
    }

    public PacketPlayOutPlayerListHeaderFooter(String header, String footer) {

        this.header = header;
        this.footer = footer;
    }

    public String getHeader() {

        return header;
    }

    public void setHeader(String header) {

        this.header = header;
    }

    public String getFooter() {

        return footer;
    }

    public void setFooter(String footer) {

        this.footer = footer;
    }

    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutPlayerListHeaderFooter = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerListHeaderFooter");
            Class<?> ChatSerializer = Reflect.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");

            Method ChatSerializerA = Reflect.getMethod(ChatSerializer, "a", String.class);
            Object icbc = ChatSerializerA.invoke(null, "{\"text\": \"" + header + "\"}");
            Object icbc2 = footer != null ? ChatSerializerA.invoke(null, "{\"text\": \"" + footer + "\"}") : null;

            Object ppoplhf = Reflect.instantiateObject(PacketPlayOutPlayerListHeaderFooter, icbc);

            if(icbc2 != null) {

                Field b = Reflect.getField(PacketPlayOutPlayerListHeaderFooter, true, "b");
                b.set(ppoplhf, icbc2);
            }
            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Player[] players = PacketManager.getPlayersfromNames(names);

            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppoplhf);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
