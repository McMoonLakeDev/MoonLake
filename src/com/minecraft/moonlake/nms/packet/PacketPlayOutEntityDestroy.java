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
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutEntityDestroy</h1>
 * 数据包输出实体破坏（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutEntityDestroy extends PacketAbstract<PacketPlayOutEntityDestroy> {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYDESTROY;

    static {

        try {

            CLASS_PACKETPLAYOUTENTITYDESTROY = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityDestroy");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out entity destroy reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<int[]> entityIds;

    /**
     * 数据包输出实体破坏类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutEntityDestroy(Entity...)}
     * @see #PacketPlayOutEntityDestroy(Entity...)
     */
    @Deprecated
    public PacketPlayOutEntityDestroy() {

        this(0);
    }

    /**
     * 数据包输出实体破坏类构造函数
     *
     * @param entities 实体
     */
    public PacketPlayOutEntityDestroy(Entity... entities) {

        int index = 0;
        int[] entityIds = new int[entities.length];

        for(final Entity entity : entities) {

            entityIds[index++] = entity.getEntityId();
        }
        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    /**
     * 数据包输出实体破坏类构造函数
     *
     * @param entityIds 实体 Id
     */
    public PacketPlayOutEntityDestroy(int... entityIds) {

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    public ObjectProperty<int[]> getEntityIds() {

        return entityIds;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTENTITYDESTROY, new Object[] { entityIds.get() });

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out entity destroy send exception.", e);
        }
    }
}
