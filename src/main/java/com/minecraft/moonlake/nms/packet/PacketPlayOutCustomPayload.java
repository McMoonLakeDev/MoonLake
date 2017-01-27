/*
 * Copyright (C) 2017 The MoonLake Authors
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
import com.minecraft.moonlake.nms.packet.wrapped.PacketDataSerializer;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutCustomPayload</h1>
 * 数据包输出自定义通道数据（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutCustomPayload extends PacketAbstract<PacketPlayOutCustomPayload> {

    private final static Class<?> CLASS_PACKETPLAYOUTCUSTOMPAYLOAD;

    static {

        try {

            CLASS_PACKETPLAYOUTCUSTOMPAYLOAD = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutCustomPayload");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play custom pay load reflect raw initialize exception.", e);
        }
    }

    private StringProperty channel;
    private ObjectProperty<PacketDataSerializer> data;

    /**
     * 数据包输出自定义通道数据构造函数
     *
     * @deprecated 已过时, 请使用 {@link #PacketPlayOutCustomPayload(String, PacketDataSerializer)}
     */
    @Deprecated
    public PacketPlayOutCustomPayload() {

        this("MC", new PacketDataSerializer());
    }

    /**
     * 数据包输出自定义通道数据构造函数
     *
     * @param channel 通道
     * @param data 数据
     */
    public PacketPlayOutCustomPayload(String channel, PacketDataSerializer data) {

        this.channel = new SimpleStringProperty(channel);
        this.data = new SimpleObjectProperty<>(data);
    }

    /**
     * 获取此数据包输出自定义通道数据的通道
     *
     * @return 通道
     */
    public StringProperty getChannel() {

        return channel;
    }

    /**
     * 获取此数据包输出自定义通道数据的数据
     *
     * @return 数据
     */
    public ObjectProperty<PacketDataSerializer> getData() {

        return data;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTCUSTOMPAYLOAD, channel.get(), data.get().asNMS());

            PacketFactory.get().sendPacket(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out custom pay load send exception.", e);
        }
    }
}
