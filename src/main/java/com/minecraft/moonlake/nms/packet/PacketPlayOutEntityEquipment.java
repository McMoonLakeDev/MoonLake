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

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
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
import java.util.ArrayList;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutEntityEquipment</h1>
 * 数据包输出实体装备（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutEntityEquipment}
 */
@Deprecated
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
            CLASS_ENUMITEMSLOT = getServerVersionNumber() >= 9 ? PackageType.MINECRAFT_SERVER.getClass("EnumItemSlot") : null; // 1.8 没有这个类
            CLASS_ITEMSTACK = PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            METHOD_ASNMSCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asNMSCopy", ItemStack.class);
            METHOD_VALUEOF = CLASS_ENUMITEMSLOT != null ? getMethod(CLASS_ENUMITEMSLOT, "valueOf", String.class) : null;
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out entity equipment reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty entityId;
    private ObjectProperty<EquipmentSlot> equipmentSlot;
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
        this.equipmentSlot = new SimpleObjectProperty<>(equipmentSlot);
        this.itemStack = new SimpleObjectProperty<>(itemStack);
    }

    /**
     * 获取此数据包输出实体装备的实体 Id
     *
     * @return 实体 Id
     */
    public IntegerProperty getEntityId() {

        return entityId;
    }

    /**
     * 获取此数据包输出实体装备的装备槽位
     *
     * @return 装备槽位
     */
    public ObjectProperty<EquipmentSlot> getEquipmentSlot() {

        return equipmentSlot;
    }

    /**
     * 获取此数据包输出实体装备的物品栈
     *
     * @return 物品栈
     */
    public ObjectProperty<ItemStack> getItemStack() {

        return itemStack;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            if(getServerVersionNumber() >= 9) {
                // 1.9 以及更高的发送
                Object enumItemSolt = METHOD_VALUEOF.invoke(null, equipmentSlot.get().name());
                Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack.get());

                Constructor<?> packetConstructor = getConstructor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, CLASS_ENUMITEMSLOT, CLASS_ITEMSTACK);
                Object packet = packetConstructor.newInstance(entityId.get(), enumItemSolt, nmsItemStack);

                PacketReflect.get().send(players, packet);
            } else {
                // 由于 1.8 是没有 EnumItemSlot 的所以不处理副手
                EquipmentSlot slot = equipmentSlot.get();
                if(slot == EquipmentSlot.OFFHAND) return;

                // 低版本的发送
                Object nmsItemStack = METHOD_ASNMSCOPY.invoke(null, itemStack.get());

                // 获取最终的装备槽位 Id
                // 如果槽位不为主手的话, 那么由于 1.8 没有副手, 这个枚举占用一个值, 则减去 1
                int slotId = slot != EquipmentSlot.MAINHAND ? slot.getId() - 1 : slot.getId();

                // 低版本第二个参数是 int 类型, 装备槽位的 Id
                Constructor<?> packetConstructor = getConstructor(CLASS_PACKETPLAYOUTENTITYEQUIPMENT, int.class, int.class, CLASS_ITEMSTACK);
                Object packet = packetConstructor.newInstance(entityId.get(), slotId, nmsItemStack);

                PacketReflect.get().send(players, packet);
            }
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out entity equipment send exception.", e);
        }
    }

    /**
     * 更新并发送指定玩家的所有装备数据包
     *
     * @param player 玩家
     * @param recipients 接收者
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果接收者对象为 {@code null} 则抛出异常
     */
    public static void updateEquipments(Player player, Player[] recipients) {

        Validate.notNull(player, "The player object is null.");
        Validate.notNull(recipients, "The recipient players object is null.");

        // 转换为 MoonLakePlayer 好做兼容性工作 23333
        MoonLakePlayer moonLakePlayer = PlayerManager.adapter(player);
        List<PacketPlayOutEntityEquipment> ppoeeList = new ArrayList<>();

        ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.MAINHAND, moonLakePlayer.getItemInHand()));

        if(getServerVersionNumber() >= 9) // 1.9+ 可以发送副手
            ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.OFFHAND, moonLakePlayer.getItemInOffHand()));

        ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.HEAD, moonLakePlayer.getInventory().getHelmet()));
        ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.CHEST, moonLakePlayer.getInventory().getChestplate()));
        ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.LEGS, moonLakePlayer.getInventory().getLeggings()));
        ppoeeList.add(new PacketPlayOutEntityEquipment(player, EquipmentSlot.FEET, moonLakePlayer.getInventory().getBoots()));

        // 最后发送
        for(Packet<?> packet : ppoeeList)
            packet.send(recipients);
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
        MAINHAND(0),
        /**
         * 装备槽位类型: 副手
         */
        OFFHAND(1),
        /**
         * 装备槽位类型: 脚
         */
        FEET(2),
        /**
         * 装备槽位类型: 腿
         */
        LEGS(3),
        /**
         * 装备槽位类型: 胸
         */
        CHEST(4),
        /**
         * 装备槽位类型: 头
         */
        HEAD(5),
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
