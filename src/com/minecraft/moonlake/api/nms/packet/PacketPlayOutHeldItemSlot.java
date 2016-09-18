package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/22.
 */
public class PacketPlayOutHeldItemSlot extends PacketAbstract<PacketPlayOutHeldItemSlot> {

    private IntegerProperty heldItemSlot;

    public PacketPlayOutHeldItemSlot(int heldItemSlot) {

        this.heldItemSlot = new SimpleIntegerProperty(heldItemSlot);
    }

    public IntegerProperty getHeldItemSlot() {

        return heldItemSlot;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutHeldItemSlot = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutHeldItemSlot");

            Object ppohis = Reflect.instantiateObject(PacketPlayOutHeldItemSlot, heldItemSlot.get());

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

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppohis);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
