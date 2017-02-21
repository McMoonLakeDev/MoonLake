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
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutHeldItemSlot</h1>
 * 数据包输出手持物品槽位（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutHeldItemSlot extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTHELDITEMSLOT;

    static {

        try {

            CLASS_PACKETPLAYOUTHELDITEMSLOT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutHeldItemSlot");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out held item slot reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty heldItemSlot;

    public PacketPlayOutHeldItemSlot() {

        this(0);
    }

    public PacketPlayOutHeldItemSlot(int heldItemSlot) {

        this.heldItemSlot = new SimpleIntegerProperty(heldItemSlot);
    }

    public PacketPlayOutHeldItemSlot(Player player) {

        this(player.getInventory().getHeldItemSlot());
    }

    public IntegerProperty heldItemSlotProperty() {

        return heldItemSlot;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        try {
            // 先用调用 NMS 的 PacketPlayOutHeldItemSlot 构造函数, 参数 int
            // 进行反射实例发送
            sendPacket(players, instantiateObject(CLASS_PACKETPLAYOUTHELDITEMSLOT, heldItemSlot.get()));
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutHeldItemSlot 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 1 个的话就是有此方式
                // 这个字段对应 int 属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTHELDITEMSLOT);
                Object[] values = { heldItemSlot.get() };
                setFieldAccessibleAndValueSend(players, 1, CLASS_PACKETPLAYOUTHELDITEMSLOT, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
