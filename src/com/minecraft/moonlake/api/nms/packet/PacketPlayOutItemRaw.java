package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/6/24.
 */
public class PacketPlayOutItemRaw implements Packet<PacketPlayOutItemRaw> {

    private ItemStack item;
    private String start;
    private String end;

    public PacketPlayOutItemRaw(ItemStack item) {

        this(null, item);
    }

    public PacketPlayOutItemRaw(String start, ItemStack item) {

        this(start, item, null);
    }

    public PacketPlayOutItemRaw(String start, ItemStack item, String end) {

        this.start = start;
        this.item = item;
        this.end = end;
    }

    public String getStart() {

        return start;
    }

    public void setStart(String start) {

        this.start = start;
    }

    public String getEnd() {

        return end;
    }

    public void setEnd(String end) {

        this.end = end;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> PacketPlayOutChat = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> IChatBaseComponent = Reflect.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");
            Class<?> ChatSerializer = Reflect.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");

            Method ChatSerializerA = Reflect.getMethod(ChatSerializer, "a", String.class);
            Object icbc1 = start != null ? ChatSerializerA.invoke(null, "{\"text\": \"" + start + "\"}") : null;
            Object icbc3 = end != null ? ChatSerializerA.invoke(null, "{\"text\": \"" + end + "\"}") : null;

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, item);
            Object icbc2 = Reflect.getMethod(ItemStack, "B").invoke(NMSItemStack);

            Object ppoc = Reflect.instantiateObject(PacketPlayOutChat, icbc2, (byte)1);

            if(icbc1 != null) {

                Reflect.getMethod(IChatBaseComponent, "addSibling", IChatBaseComponent).invoke(icbc1, icbc2);

                if(icbc3 != null) {

                    Reflect.getMethod(IChatBaseComponent, "addSibling", IChatBaseComponent).invoke(icbc1, icbc3);
                }
                ppoc = Reflect.instantiateObject(PacketPlayOutChat, icbc1, (byte)1);
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

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppoc);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
