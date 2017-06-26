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

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutHeldItemSlot</h1>
 * 数据包输出手持物品槽位（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutHeldItemSlot extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTHELDITEMSLOT;
    private static volatile ConstructorAccessor<?> packetPlayOutHeldItemSlotVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutHeldItemSlotConstructor;

    static {

        CLASS_PACKETPLAYOUTHELDITEMSLOT = MinecraftReflection.getMinecraftClass("PacketPlayOutHeldItemSlot");
        packetPlayOutHeldItemSlotVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTHELDITEMSLOT);
        packetPlayOutHeldItemSlotConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTHELDITEMSLOT, int.class);
    }

    private IntegerProperty heldItemSlot;

    /**
     * 数据包输出手持物品槽位构造函数
     */
    public PacketPlayOutHeldItemSlot() {

        this(0);
    }

    /**
     * 数据包输出手持物品槽位构造函数
     *
     * @param heldItemSlot 手持物品槽位 (0-8)
     */
    public PacketPlayOutHeldItemSlot(int heldItemSlot) {

        this.heldItemSlot = new SimpleIntegerProperty(heldItemSlot);
    }

    /**
     * 数据包输出手持物品槽位构造函数
     *
     * @param player 玩家
     */
    public PacketPlayOutHeldItemSlot(Player player) {

        this(player.getInventory().getHeldItemSlot());
    }

    /**
     * 获取此数据包输出手持物品槽位的手持物品槽位属性
     *
     * @return 手持物品槽位属性
     */
    public IntegerProperty heldItemSlotProperty() {

        return heldItemSlot;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTHELDITEMSLOT;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

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

        try {
            // 先用调用 NMS 的 PacketPlayOutHeldItemSlot 构造函数, 参数 int
            // 进行反射实例发送
            return packetPlayOutHeldItemSlotConstructor.invoke(heldItemSlot.get());

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutHeldItemSlot 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 1 个的话就是有此方式
                // 这个字段对应 int 属性
                Object packet = packetPlayOutHeldItemSlotVoidConstructor.invoke();
                Object[] values = { heldItemSlot.get() };
                return setFieldAccessibleAndValueGet(1, CLASS_PACKETPLAYOUTHELDITEMSLOT, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
