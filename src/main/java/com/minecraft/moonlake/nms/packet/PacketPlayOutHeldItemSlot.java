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
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutHeldItemSlot</h1>
 * 数据包输出手持物品槽位（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutHeldItemSlot}
 */
@Deprecated
public class PacketPlayOutHeldItemSlot extends PacketAbstract<PacketPlayOutHeldItemSlot> {

    private final static Class<?> CLASS_PACKETPLAYOUTHELDITEMSLOT;

    static {

        try {

            CLASS_PACKETPLAYOUTHELDITEMSLOT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutHeldItemSlot");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out held item slot reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty heldItemSlot;

    /**
     * 数据包输出手持物品槽位构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutHeldItemSlot(int)}
     * @see #PacketPlayOutHeldItemSlot(int)
     */
    @Deprecated
    public PacketPlayOutHeldItemSlot() {

        this(0);
    }

    /**
     * 数据包输出手持物品槽位构造函数
     *
     * @param heldItemSlot 物品栏槽位
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

        this.heldItemSlot = new SimpleIntegerProperty(player.getInventory().getHeldItemSlot());
    }

    /**
     * 获取此数据包输出手持物品槽位的槽位
     *
     * @return 槽位
     */
    public IntegerProperty getHeldItemSlot() {

        return heldItemSlot;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTHELDITEMSLOT, heldItemSlot.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out held item slot send exception.", e);
        }
    }
}
