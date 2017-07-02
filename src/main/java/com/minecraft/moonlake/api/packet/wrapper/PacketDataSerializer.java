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

import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.ObjectPropertyBase;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.UUID;

/**
 * <h1>PacketDataSerializer</h1>
 * 数据包数据串行器包装类（详细doc待补充...）
 *
 * @version 1.2
 * @author Month_Light
 */
public class PacketDataSerializer {

    private static volatile ConstructorAccessor<?> packetDataSerializerConstructor;
    private static volatile FieldAccessor packetDataSerializerByteBufField;

    static {

        Class<?> packetDataSerializer = MinecraftReflection.getPacketDataSerializerClass();
        packetDataSerializerConstructor = Accessors.getConstructorAccessor(packetDataSerializer, ByteBuf.class);
        packetDataSerializerByteBufField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(packetDataSerializer, true).getFieldByType("byteBuf", ByteBuf.class));
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
     * 将指定 NMS 的 PacketDataSerializer 对象转换到此 PacketDataSerializer 对象
     *
     * @param nmsPacketDataSerializer NMS PacketDataSerializer
     * @return PacketDataSerializer
     * @throws IllegalArgumentException 如果 NMS PacketDataSerializer 对象为 {@code null} 或不是实例则抛出异常
     * @throws NMSException 如果转换错误则抛出异常
     */
    public static PacketDataSerializer fromNMS(Object nmsPacketDataSerializer) throws NMSException {

        Validate.notNull(nmsPacketDataSerializer, "The nms packet data serializer object is null.");
        Validate.isTrue(MinecraftReflection.is(MinecraftReflection.getPacketDataSerializerClass(), nmsPacketDataSerializer), "The nms packet data serializer object is not instance.");

        try {

            ByteBuf wrapped = Unpooled.wrappedBuffer((ByteBuf) packetDataSerializerByteBufField.get(nmsPacketDataSerializer));
            return new PacketDataSerializer(wrapped);

        } catch (Exception e) {

            throw new NMSException("The from nms packet data serializer exception.", e);
        }
    }

    /**
     * 将指定可变长度整数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeVarInt(int value) {
        // 详情查看 http://wiki.vg/Protocol#VarInt_and_VarLong
        while((value & ~0x7F) != 0) {
            writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        return writeByte(value);
    }

    /**
     * 将指定可变长度长整数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeVarLong(long value) {
        // 详情查看 http://wiki.vg/Protocol#VarInt_and_VarLong
        while((value & ~0x7F) != 0) {
            writeByte((int) (value & 0x7F) | 0x80);
            value >>>= 7;
        }
        return writeByte((int) value);
    }

    /**
     * 将指定字节数组写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeBytes(byte[] value) {
        Validate.notNull(value, "The byte[] object is null.");
        getByteBuf().writeBytes(value);
        return this;
    }

    /**
     * 将指定字节写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeByte(int value) {
        getByteBuf().writeByte(value);
        return this;
    }

    /**
     * 将指定短整数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeShort(int value) {
        getByteBuf().writeShort(value);
        return this;
    }

    /**
     * 将指定整数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeInt(int value) {
        getByteBuf().writeInt(value);
        return this;
    }

    /**
     * 将指定长整数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeLong(long value) {
        getByteBuf().writeLong(value);
        return this;
    }

    /**
     * 将指定单精度浮点数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeFloat(float value) {
        getByteBuf().writeFloat(value);
        return this;
    }

    /**
     * 将指定双精度浮点数写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeDouble(double value) {
        getByteBuf().writeDouble(value);
        return this;
    }

    /**
     * 将指定字符串写入到此数据包数据串行器
     *
     * @param value 值
     * @throws IllegalArgumentException 如果字符串字节长度 {@code > 32767} 则抛出异常
     */
    public PacketDataSerializer writeString(String value) {
        Validate.notNull(value, "The string value object is null.");
        byte[] bytes = value.getBytes(StringUtil.UTF_8);
        if(bytes.length > 32767)
            throw new IllegalArgumentException("字符串值字节长度太大, 最大只能为 32767.");
        writeVarInt(bytes.length);
        writeBytes(bytes);
        return this;
    }

    /**
     * 将指定字符串数组写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeStrings(String[] value) {
        Validate.notNull(value, "The string[] value object is null.");
        for(String str : value)
            writeString(str);
        return this;
    }

    /**
     * 将指定 UUID 写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeUUID(UUID value) {
        writeLong(value.getMostSignificantBits());
        writeLong(value.getLeastSignificantBits());
        return this;
    }

    /**
     * 将指定聊天组件写入到此数据包数据串行器
     *
     * @param value 值
     */
    public PacketDataSerializer writeChatComponent(ChatComponent value) {
        return writeString(value.toJson());
    }

    /**
     * 从此数据包数据串行器读取指定长度的字节数组
     *
     * @param length 长度
     * @return 读取到的字节数组
     * @throws IllegalArgumentException 如果待读取的长度 {@code < 0} 则抛出异常
     */
    public byte[] readBytes(int length) {
        Validate.isTrue(length >= 0, "待读取的数组长度不能小于 0.");
        byte[] bytes = new byte[length];
        getByteBuf().readBytes(bytes);
        return bytes;
    }

    /**
     * 从此数据包数据串行器读取字节值
     *
     * @return 值
     */
    public byte readByte() {
        return getByteBuf().readByte();
    }

    /**
     * 从此数据包数据串行器读取短整数值
     *
     * @return 值
     */
    public short readShort() {
        return getByteBuf().readShort();
    }

    /**
     * 从此数据包数据串行器读取整数值
     *
     * @return 值
     */
    public int readInt() {
        return getByteBuf().readInt();
    }

    /**
     * 从此数据包数据串行器读取可变长度整数值
     *
     * @return 值
     * @throws IllegalArgumentException 如果值数据的长度 {@code > 5} 则抛出异常
     */
    public int readVarInt() {
        // 详情查看 http://wiki.vg/Protocol#VarInt_and_VarLong
        int value = 0, size = 0, b;
        while(((b = readByte()) & 0x80) == 0x80) {
            value |= (b & 0x7F) << (size++ * 7);
            if(size > 5)
                throw new IllegalStateException("VarInt 值数据太大, 长度必须小于或等于 5.");
        }
        return value | ((b & 0x7F) << (size * 7));
    }

    /**
     * 从此数据包数据串行器读取长整数值
     *
     * @return 值
     */
    public long readLong() {
        return getByteBuf().readLong();
    }

    /**
     * 从此数据包数据串行器读取可变长度长整数值
     *
     * @return 值
     * @throws IllegalArgumentException 如果值数据的长度 {@code > 10} 则抛出异常
     */
    public long readVarLong() {
        // 详情查看 http://wiki.vg/Protocol#VarInt_and_VarLong
        long value = 0;
        int size = 0, b;
        while(((b = readByte()) & 0x80) == 0x80) {
            value |= (long) (b & 0x7F) << (size++ * 7);
            if(size > 10)
                throw new IllegalStateException("VarLong 值数据太大, 长度必须小于或等于 10.");
        }
        return value | ((long) (b & 0x7F) << (size * 7));
    }

    /**
     * 从此数据包数据串行器读取单精度浮点数值
     *
     * @return 值
     */
    public float readFloat() {
        return getByteBuf().readFloat();
    }

    /**
     * 从此数据包数据串行器读取双精度浮点数值
     *
     * @return 值
     */
    public double readDouble() {
        return getByteBuf().readDouble();
    }

    /**
     * 从此数据包数据串行器读取字符串值
     *
     * @return 值
     */
    public String readString() {
        int length = readVarInt();
        byte[] bytes = readBytes(length);
        return new String(bytes, StringUtil.UTF_8);
    }

    /**
     * 从此数据包数据串行器读取 UUID 值
     *
     * @return 值
     */
    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    /**
     * 从此数据包数据串行器读取聊天组件值
     *
     * @return 值
     */
    public ChatComponent readChatComponent() {
        return ChatSerializer.fromJson(readString());
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
