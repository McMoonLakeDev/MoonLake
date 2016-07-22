package com.minecraft.moonlake.api.nms.packet;


import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/22.
 */
public class PacketPlayOutEntityEquipment extends PacketAbstract<PacketPlayOutEntityEquipment> {

    private int entityId;
    private EquipmentSlot slot;
    private ItemStack itemStack;

    public PacketPlayOutEntityEquipment(int entityId, EquipmentSlot slot, ItemStack itemStack) {

        this.entityId = entityId;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public PacketPlayOutEntityEquipment(Entity entity, EquipmentSlot slot, ItemStack itemStack) {

        this(entity.getEntityId(), slot, itemStack);
    }

    public int getEntityId() {

        return entityId;
    }

    public void setEntityId(int entityId) {

        this.entityId = entityId;
    }

    public EquipmentSlot getSlot() {

        return slot;
    }

    public void setSlot(EquipmentSlot slot) {

        this.slot = slot;
    }

    public ItemStack getItemStack() {

        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {

        this.itemStack = itemStack;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutEntityEquipment = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityEquipment");
            Class<?> EnumItemSlot = Reflect.PackageType.MINECRAFT_SERVER.getClass("EnumItemSlot");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");

            Object instanceSlot = Reflect.getMethod(EnumItemSlot, "a", String.class).invoke(null, slot.name().toLowerCase());
            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, itemStack);
            Object ppoee = Reflect.instantiateObject(PacketPlayOutEntityEquipment, entityId, instanceSlot, NMSItemStack);

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

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppoee);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public enum EquipmentSlot {

        MAINHAND,
        OFFHAND,
        FEET,
        LEGS,
        CHEST,
        HEAD,;
    }
}
