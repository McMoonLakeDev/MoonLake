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


package com.minecraft.moonlake.nms.packet.wrapped;

import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <h1>PacketDataSerializer</h1>
 * 数据包数据串行器包装类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketDataSerializer}
 */
@Deprecated
public class PacketDataSerializer {

    private final static Class<?> CLASS_PACKETDATASERIALIZER;

    static {

        try {

            CLASS_PACKETDATASERIALIZER = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketDataSerializer");
        }
        catch (Exception e) {

            throw new NMSException("The packet data serializer initialize exception.", e);
        }
    }

    private ObjectProperty<ByteBuf> byteBuf;

    /**
     * 数据包数据串行器包装类构造函数
     */
    public PacketDataSerializer() {

        this(Unpooled.buffer());
    }

    /**
     * 数据包数据串行器包装类构造函数
     *
     * @param byteBuf 数据
     */
    public PacketDataSerializer(byte[] byteBuf) {

        this(Unpooled.wrappedBuffer(byteBuf));
    }

    /**
     * 数据包数据串行器包装类构造函数
     *
     * @param byteBuf 数据
     */
    public PacketDataSerializer(ByteBuf byteBuf) {

        this.byteBuf = new SimpleObjectProperty<>(byteBuf);
    }

    /**
     * 获取此数据包数据串行器的数据
     *
     * @return 数据
     */
    public ByteBuf getByteBuf() {

        return byteBuf.get();
    }

    /**
     * 设置此数据包数据串行器的数据
     *
     * @param byteBuf 数据
     */
    public void setByteBuf(ByteBuf byteBuf) {

        this.byteBuf.set(byteBuf);
    }

    /**
     * 设置此数据包数据串行器的数据
     *
     * @param byteBuf 数据
     */
    public void setByteBuf(byte[] byteBuf) {

        this.byteBuf.set(Unpooled.wrappedBuffer(byteBuf));
    }

    @Override
    public String toString() {
        return "PacketDataSerializer{" +
                "byteBuf=" + byteBuf.get() +
                '}';
    }

    /**
     * 将此 PacketDataSerializer 转换到 NMS 的 PacketDataSerializer 对象
     *
     * @return NMS PacketDataSerializer
     * @throws NMSException 如果转换错误则抛出异常
     */
    public Object asNMS() throws NMSException {

        try {

            return Reflect.instantiateObject(CLASS_PACKETDATASERIALIZER, getByteBuf());
        }
        catch (Exception e) {

            throw new NMSException();
        }
    }
}
