/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
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
 * <h1>PacketPlayOutEntityEquipment</h1>
 * 数据包输出实体装备（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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

    /**
     * 数据包输出实体装备类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutEntityEquipment(Entity, EquipmentSlot, ItemStack)}
     * @see #PacketPlayOutEntityEquipment(Entity, EquipmentSlot, ItemStack)
     */
    @Deprecated
    public PacketPlayOutEntityEquipment() {

        this(0, EquipmentSlot.MAINHAND, null);
    }

    /**
     * 数据包输出实体装备类构造函数
     *
     * @param entity 实体
     * @param equipmentSlot 装备槽位
     * @param itemStack 物品栈
     */
    public PacketPlayOutEntityEquipment(Entity entity, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this(entity.getEntityId(), equipmentSlot, itemStack);
    }

    /**
     * 数据包输出实体装备类构造函数
     *
     * @param entityId 实体 Id
     * @param equipmentSlot 装备槽位
     * @param itemStack 物品栈
     */
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

        if(super.fireEvent(this, players)) return;

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

    /**
     * <h1>EquipmentSlot</h1>
     * 装备槽位类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum EquipmentSlot {

        /**
         * 装备槽位类型: 主手
         */
        MAINHAND,
        /**
         * 装备槽位类型: 副手
         */
        OFFHAND,
        /**
         * 装备槽位类型: 脚
         */
        FEET,
        /**
         * 装备槽位类型: 腿
         */
        LEGS,
        /**
         * 装备槽位类型: 胸
         */
        CHEST,
        /**
         * 装备槽位类型: 头
         */
        HEAD,
        ;
    }
}
