package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/21.
 */
public class PacketPlayOutNamedEntitySpawn extends PacketAbstract<PacketPlayOutNamedEntitySpawn> {

    private ObjectProperty<Player> entity;

    public PacketPlayOutNamedEntitySpawn(Player entity) {

        this.entity = new SimpleObjectProperty<>(entity);
    }

    public ObjectProperty<Player> getEntity() {

        return entity;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutNamedEntitySpawn = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutNamedEntitySpawn");
            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Player[] players = PacketManager.getPlayersfromNames(names);

            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            Object NMSEntity = getHandle.invoke(entity.get());
            Object ppones = Reflect.instantiateObject(PacketPlayOutNamedEntitySpawn, NMSEntity);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppones);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
