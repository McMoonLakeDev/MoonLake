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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutEntityEquipment</h1>
 * 数据包输出实体装备（详细doc待补充...）
 *
 * @version 2.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutEntityEquipment extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYEQUIPMENT;
    private final static Class<?> CLASS_ENUMITEMSLOT;
    private static volatile ConstructorAccessor<?> packetPlayOutEntityEquipmentVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutEntityEquipmentConstructor;

    static {

        CLASS_PACKETPLAYOUTENTITYEQUIPMENT = MinecraftReflection.getMinecraftClass("PacketPlayOutEntityEquipment");
        CLASS_ENUMITEMSLOT = MinecraftReflection.getMinecraftClassOrLater(MinecraftVersion.V1_9, "EnumItemSlot"); // 1.9+ 才有这个类
        packetPlayOutEntityEquipmentVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT);
        packetPlayOutEntityEquipmentConstructor = Accessors.getConstructorAccessorBuilderMCVer(new SingleParamBuilder<ConstructorAccessor<?>, MinecraftVersion>() {
            @Override
            public ConstructorAccessor<?> build(MinecraftVersion param) {
                Class<?> itemStackClass = MinecraftReflection.getMinecraftItemStackClass();
                if(param.isOrLater(MinecraftVersion.V1_9))
                    return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, CLASS_ENUMITEMSLOT, itemStackClass);
                return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, int.class, itemStackClass);
            }
        });
    }

    private IntegerProperty entityId;
    private ObjectProperty<EquipmentSlot> equipmentSlot;
    private ObjectProperty<ItemStack> itemStack;

    /**
     * 数据包输出实体装备构造函数
     */
    public PacketPlayOutEntityEquipment() {

        this(0, null, null);
    }

    /**
     * 数据包输出实体装备构造函数
     *
     * @param entity 实体
     * @param equipmentSlot 装备槽位
     * @param itemStack 物品栈
     */
    public PacketPlayOutEntityEquipment(Entity entity, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this(entity.getEntityId(), equipmentSlot, itemStack);
    }

    /**
     * 数据包输出实体装备构造函数
     *
     * @param entityId 实体 Id
     * @param equipmentSlot 装备槽位
     * @param itemStack 物品栈
     */
    public PacketPlayOutEntityEquipment(int entityId, EquipmentSlot equipmentSlot, ItemStack itemStack) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.equipmentSlot = new SimpleObjectProperty<>(equipmentSlot);
        this.itemStack = new SimpleObjectProperty<>(itemStack);
    }

    /**
     * 获取此数据包输出实体装备的实体 Id 属性
     *
     * @return 实体 Id 属性
     */
    public IntegerProperty entityIdProperty() {

        return entityId;
    }

    /**
     * 获取此数据包输出实体装备的装备槽位属性
     *
     * @return 装备槽位属性
     */
    public ObjectProperty<EquipmentSlot> equipmentSlotProperty() {

        return equipmentSlot;
    }

    /**
     * 获取此数据包输出实体装备的物品栈属性
     *
     * @return 物品栈属性
     */
    public ObjectProperty<ItemStack> itemStackProperty() {

        return itemStack;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTENTITYEQUIPMENT;
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
            MinecraftReflection.sendPacket(players, packet());
            return true;
        } catch (Exception e) {
            printException(e);
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    @Nullable
    @Override
    public Object packet() {

        EquipmentSlot slot = equipmentSlotProperty().get();
        ItemStack itemStack = itemStackProperty().get();
        Validate.notNull(slot, "The equipment slot object is null.");
        Validate.notNull(itemStack, "The itemstack object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutEntityEquipment 构造函数
            // 1.8 参数 int, int, ItemStack
            // 1.9+ 参数 int, EquipmentSlot, ItemStack
            // 进行反射实例发送
            if(MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {
                // 1.9+ 版本的发送方式
                Object enumItemSlot = MinecraftReflection.enumOfNameAny(CLASS_ENUMITEMSLOT, slot.name());
                Object nmsItemStack = MinecraftReflection.asNMSCopy(itemStack);
                return packetPlayOutEntityEquipmentConstructor.invoke(entityId.get(), enumItemSlot, nmsItemStack);

            } else {
                // 1.8 版本的发送方式
                // 由于 1.8 是没有 EnumItemSlot 的所以不处理主副手
                if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) return true;
                Object nmsItemStack = MinecraftReflection.asNMSCopy(itemStack);
                // 1.8 版本第二个参数是 int 类型, 装备槽位的 Id
                return packetPlayOutEntityEquipmentConstructor.invoke(entityId.get(), slot.getId(), nmsItemStack);
            }

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutEntityEquipment 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                if(MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {
                    // 1.9+ 版本的发送方式
                    Object enumItemSlot = MinecraftReflection.enumOfNameAny(CLASS_ENUMITEMSLOT, slot.name());
                    Object nmsItemStack = MinecraftReflection.asNMSCopy(itemStack);
                    Object[] values = { entityId.get(), enumItemSlot, nmsItemStack };
                    Object packet = packetPlayOutEntityEquipmentVoidConstructor.invoke();
                    setFieldAccessibleAndValueGet(3, CLASS_PACKETPLAYOUTENTITYEQUIPMENT, packet, values);
                    return true;

                } else {
                    // 1.8 版本的发送方式
                    // 由于 1.8 是没有 EnumItemSlot 的所以不处理主副手
                    if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) return true;
                    Object nmsItemStack = MinecraftReflection.asNMSCopy(itemStack);
                    Object[] values = { entityId.get(), slot.getId(), nmsItemStack };
                    Object packet = packetPlayOutEntityEquipmentVoidConstructor.invoke();
                    setFieldAccessibleAndValueGet(3, CLASS_PACKETPLAYOUTENTITYEQUIPMENT, packet, values);
                    return true;
                }

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
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
