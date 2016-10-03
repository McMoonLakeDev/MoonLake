package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutEntityEquipment extends PacketAbstract<PacketPlayOutEntityEquipment> {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYEQUIPMENT;
    private final static Class<?> CLASS_CRAFTITEMSTACK;
    private final static Class<?> CLASS_ENUMITEMSLOT;
    private final static Class<?> CLASS_ITEMSTACK;
    private final static Method METHOD_ASNMSCOPY;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTENTITYEQUIPMENT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityEquipment");
            CLASS_CRAFTITEMSTACK = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            CLASS_ENUMITEMSLOT = PackageType.MINECRAFT_SERVER.getClass("EnumItemSlot");
            CLASS_ITEMSTACK = PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            METHOD_ASNMSCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asNMSCopy", ItemStack.class);
            METHOD_VALUEOF = getMethod(CLASS_ENUMITEMSLOT, "valueOf", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out entity equipment reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty entityId;
    private ObjectProperty<EquipmentSlot> equipmentSolt;
    private ObjectProperty<ItemStack> itemStack;

    @Deprecated
    public PacketPlayOutEntityEquipment() {

        this(0, EquipmentSlot.MAINHAND, null);
    }

    public PacketPlayOutEntityEquipment(Entity entity, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this(entity.getEntityId(), equipmentSlot, itemStack);
    }

    public PacketPlayOutEntityEquipment(int entityId, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.equipmentSolt = new SimpleObjectProperty<>(equipmentSlot);
        this.itemStack = new SimpleObjectProperty<>(itemStack);
    }

    public IntegerProperty getEntityId() {

        return entityId;
    }

    public ObjectProperty<EquipmentSlot> getEquipmentSolt() {

        return equipmentSolt;
    }

    public ObjectProperty<ItemStack> getItemStack() {

        return itemStack;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object enumItemSolt = METHOD_VALUEOF.invoke(null, equipmentSolt.get().name());
            Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack.get());

            Constructor<?> packetConstructor = getConstructor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, CLASS_ENUMITEMSLOT, CLASS_ITEMSTACK);
            Object packet = packetConstructor.newInstance(entityId.get(), enumItemSolt, nmsItemStack);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out entity equipment send exception.", e);
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
