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
 
 
package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
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
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutEntityEquipment extends PacketPlayOutBukkitAbstract {

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
            CLASS_ENUMITEMSLOT = getServerVersionNumber() >= 9 ? PackageType.MINECRAFT_SERVER.getClass("EnumItemSlot") : null; // 1.8 没有这个类
            CLASS_ITEMSTACK = PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            METHOD_ASNMSCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asNMSCopy", ItemStack.class);
            METHOD_VALUEOF = CLASS_ENUMITEMSLOT != null ? getMethod(CLASS_ENUMITEMSLOT, "valueOf", String.class) : null;
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out entity equipment reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty entityId;
    private ObjectProperty<EquipmentSlot> equipmentSlot;
    private ObjectProperty<ItemStack> itemStack;

    public PacketPlayOutEntityEquipment() {

        this(0, null, null);
    }

    public PacketPlayOutEntityEquipment(Entity entity, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this(entity.getEntityId(), equipmentSlot, itemStack);
    }

    public PacketPlayOutEntityEquipment(int entityId, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.equipmentSlot = new SimpleObjectProperty<>(equipmentSlot);
        this.itemStack = new SimpleObjectProperty<>(itemStack);
    }

    public IntegerProperty entityIdProperty() {

        return entityId;
    }

    public ObjectProperty<EquipmentSlot> equipmentSlotProperty() {

        return equipmentSlot;
    }

    public ObjectProperty<ItemStack> itemStackProperty() {

        return itemStack;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        EquipmentSlot slot = equipmentSlotProperty().get();
        ItemStack itemStack = itemStackProperty().get();
        Validate.notNull(slot, "The equipment slot object is null.");
        Validate.notNull(itemStack, "The itemstack object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutEntityEquipment 构造函数
            // 1.8 参数 int, int, ItemStack
            // 1.9+ 参数 int, EquipmentSlot, ItemStack
            // 进行反射实例发送
            if(getServerVersionNumber() >= 9) {
                // 1.9+ 版本的发送方式
                Object enumItemSlot = METHOD_VALUEOF.invoke(null, slot.name());
                Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack);
                Constructor<?> packetConstructor = getConstructor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, CLASS_ENUMITEMSLOT, CLASS_ITEMSTACK);
                Object packet = packetConstructor.newInstance(entityId.get(), enumItemSlot, nmsItemStack);
                sendPacket(players, packet);
                return true;

            } else {
                // 1.8 版本的发送方式
                // 由于 1.8 是没有 EnumItemSlot 的所以不处理主副手
                if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) return true;
                Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack);
                // 1.8 版本第二个参数是 int 类型, 装备槽位的 Id
                Constructor<?> packetConstructor = getConstructor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, int.class, CLASS_ITEMSTACK);
                Object packet = packetConstructor.newInstance(entityId.get(), slot.getId(), nmsItemStack);
                sendPacket(players, packet);
                return true;
            }

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutEntityEquipment 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                if(getServerVersionNumber() >= 9) {
                    // 1.9+ 版本的发送方式
                    Object enumItemSlot = METHOD_VALUEOF.invoke(null, slot.name());
                    Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack);
                    Object[] values = { entityId.get(), enumItemSlot, nmsItemStack };
                    Object packet = instantiateObject(CLASS_PACKETPLAYOUTENTITYEQUIPMENT);
                    setFieldAccessibleAndValueSend(players, 3, CLASS_PACKETPLAYOUTENTITYEQUIPMENT, packet, values);
                    return true;

                } else {
                    // 1.8 版本的发送方式
                    // 由于 1.8 是没有 EnumItemSlot 的所以不处理主副手
                    if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) return true;
                    Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack);
                    Object[] values = { entityId.get(), slot.getId(), nmsItemStack };
                    Object packet = instantiateObject(CLASS_PACKETPLAYOUTENTITYEQUIPMENT);
                    setFieldAccessibleAndValueSend(players, 3, CLASS_PACKETPLAYOUTENTITYEQUIPMENT, packet, values);
                    return true;
                }

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    /**
     * <h1>EquipmentSlot</h1>
     * 装备槽位类型
     *
     * @version 2.0
     * @author Month_Light
     */
    public enum EquipmentSlot {

        /**
         * 装备槽位类型: 主手 (注: 1.8 版本无效果)
         */
        MAINHAND(0),
        /**
         * 装备槽位类型: 副手 (注: 1.8 版本无效果)
         */
        OFFHAND(1),
        /**
         * 装备槽位类型: 脚
         */
        FEET(0),
        /**
         * 装备槽位类型: 腿
         */
        LEGS(1),
        /**
         * 装备槽位类型: 胸
         */
        CHEST(2),
        /**
         * 装备槽位类型: 头
         */
        HEAD(3),
        ;

        private int id;

        /**
         * 装备槽位类型构造函数
         *
         * @param id Id
         */
        EquipmentSlot(int id) {

            this.id = id;
        }

        /**
         * 获取此装备槽位类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }
    }
}
