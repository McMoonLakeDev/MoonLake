package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/24.
 */
public class PacketPlayOutPlayerInfo_v1_9_R2 extends PacketPlayOutPlayerInfo {

    public PacketPlayOutPlayerInfo_v1_9_R2(PlayerInfoAction action, Player player) {

        super(action, player);
    }

    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutPlayerInfo = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo");
            Class<?> EnumPlayerInfoAction = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Method valueOf = Reflect.getMethod(EnumPlayerInfoAction, "valueOf", String.class);

            Object EnumPlayerInfoAction0 = valueOf.invoke(null, getAction().name());
            Object NMSPlayerSource = getHandle.invoke(getPlayer());

            Constructor<?> Constructor = Reflect.getConstructor(PacketPlayOutPlayerInfo, EnumPlayerInfoAction, net.minecraft.server.v1_9_R2.EntityPlayer[].class);
            Object ppopi = Constructor.newInstance(EnumPlayerInfoAction0, new net.minecraft.server.v1_9_R2.EntityPlayer[] {(net.minecraft.server.v1_9_R2.EntityPlayer) NMSPlayerSource});

            Player[] players = PacketManager.getPlayersfromNames(names);
            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppopi);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
