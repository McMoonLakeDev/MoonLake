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
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutNamedEntitySpawn</h1>
 * 数据包输出名称实体生成（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutNamedEntitySpawn extends PacketAbstract<PacketPlayOutNamedEntitySpawn> {

    private final static Class<?> CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN;

    static {

        try {

            CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutNamedEntitySpawn");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out named entity spawn reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<Player> entity;

    /**
     * 数据包输出名称实体生成类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutNamedEntitySpawn(Player)}
     * @see #PacketPlayOutNamedEntitySpawn(Player)
     */
    @Deprecated
    public PacketPlayOutNamedEntitySpawn() {

        this(null);
    }

    /**
     * 数据包输出名称实体生成类构造函数
     *
     * @param entity 玩家
     */
    public PacketPlayOutNamedEntitySpawn(Player entity) {

        this.entity = new SimpleObjectProperty<>(entity);
    }

    public ObjectProperty<Player> getEntity() {

        return entity;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object nmsEntity = PacketReflect.get().getNMSPlayer(entity.get());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN, nmsEntity);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out named entity spawn send exception.", e);
        }
    }
}
