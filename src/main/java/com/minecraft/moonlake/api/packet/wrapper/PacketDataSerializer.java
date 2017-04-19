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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.ObjectPropertyBase;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <h1>PacketDataSerializer</h1>
 * 数据包数据串行器包装类（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public class PacketDataSerializer {

    private static volatile ConstructorAccessor<?> packetDataSerializerConstructor;

    static {

        Class<?> packetDataSerializer = MinecraftReflection.getPacketDataSerializerClass();
        packetDataSerializerConstructor = Accessors.getConstructorAccessor(packetDataSerializer, ByteBuf.class);
    }

    private ByteBufProperty byteBuf;

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

        this.byteBuf = new ByteBufProperty(byteBuf);
    }

    /**
     * 获取此数据包数据串行器的数据属性
     *
     * @return 数据属性
     */
    public ByteBufProperty byteBufProperty() {

        return byteBuf;
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
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof PacketDataSerializer) {
            PacketDataSerializer other = (PacketDataSerializer) obj;
            return getByteBuf().equals(other.getByteBuf());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return byteBuf != null ? byteBuf.hashCode() : 0;
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

            return packetDataSerializerConstructor.invoke(getByteBuf());
        }
        catch (Exception e) {

            throw new NMSException("The as nms packet data serializer exception.", e);
        }
    }

    /**
     * <h1>PacketDataSerializerProperty</h1>
     * 数据包数据串行器包装属性类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class PacketDataSerializerProperty extends ObjectPropertyBase<PacketDataSerializer> {

        /**
         * 数据包数据串行器包装属性类构造函数
         */
        public PacketDataSerializerProperty() {
        }

        /**
         * 数据包数据串行器包装属性类构造函数
         *
         * @param data 数据包数据串行器对象
         */
        public PacketDataSerializerProperty(PacketDataSerializer data) {

            super(data);
        }

        /**
         * 数据包数据串行器包装属性类构造函数
         *
         * @param byteBuf 数据包数据串行器数据
         */
        public PacketDataSerializerProperty(byte[] byteBuf) {

            super(new PacketDataSerializer(byteBuf));
        }

        /**
         * 数据包数据串行器包装属性类构造函数
         *
         * @param byteBuf 数据包数据串行器数据
         */
        public PacketDataSerializerProperty(ByteBuf byteBuf) {

            super(new PacketDataSerializer(byteBuf));
        }

        /**
         * 获取此数据包数据串行器的数据属性
         *
         * @return 数据属性
         */
        public ByteBufProperty byteBufProperty() {

            return get().byteBuf;
        }

        @Override
        public String toString() {
            return "PacketDataSerializerProperty [value: " + get() + "]";
        }
    }

    /**
     * <h1>ByteBufProperty</h1>
     * 字节数据缓存属性类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class ByteBufProperty extends ObjectPropertyBase<ByteBuf> {

        /**
         * 字节数据缓存属性类构造函数
         */
        public ByteBufProperty() {
        }

        /**
         * 字节数据缓存属性类构造函数
         *
         * @param byteBuf 字节数据缓存
         */
        public ByteBufProperty(byte[] byteBuf) {

            super(Unpooled.wrappedBuffer(byteBuf));
        }

        /**
         * 字节数据缓存属性类构造函数
         *
         * @param byteBuf 字节数据缓存
         */
        public ByteBufProperty(ByteBuf byteBuf) {

            super(byteBuf);
        }

        @Override
        public String toString() {
            return "ByteBufProperty [value: " + get() + "]";
        }
    }
}
