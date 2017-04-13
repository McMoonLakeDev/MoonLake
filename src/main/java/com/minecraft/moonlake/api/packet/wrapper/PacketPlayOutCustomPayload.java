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

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutCustomPayload</h1>
 * 数据包输出自定义通道数据（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutCustomPayload extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTCUSTOMPAYLOAD;

    static {

        try {

            CLASS_PACKETPLAYOUTCUSTOMPAYLOAD = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutCustomPayload");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play custom pay load reflect raw initialize exception.", e);
        }
    }

    private StringProperty channel;
    private PacketDataSerializer.PacketDataSerializerProperty data;

    /**
     * 数据包输出自定义通道数据构造函数
     */
    public PacketPlayOutCustomPayload() {

        this("MC", new PacketDataSerializer());
    }

    /**
     * 数据包输出自定义通道数据构造函数
     *
     * @param channel 通道名
     * @param data 字节数据
     */
    public PacketPlayOutCustomPayload(String channel, byte[] data) {

        this(channel, new PacketDataSerializer(data));
    }

    /**
     * 数据包输出自定义通道数据构造函数
     *
     * @param channel 通道名
     * @param data 数据包数据串行器
     */
    public PacketPlayOutCustomPayload(String channel, PacketDataSerializer data) {

        this.channel = new SimpleStringProperty(channel);
        this.data = new PacketDataSerializer.PacketDataSerializerProperty(data);
    }

    /**
     * 获取此数据包输出自定义通道数据的通道名属性
     *
     * @return 通道名属性
     */
    public StringProperty channelProperty() {

        return channel;
    }

    /**
     * 获取此数据包输出自定义通道数据的数据串行器属性
     *
     * @return 数据串行器属性
     */
    public PacketDataSerializer.PacketDataSerializerProperty dataProperty() {

        return data;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTCUSTOMPAYLOAD;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String channel = channelProperty().get();
        PacketDataSerializer dataSerializer = dataProperty().get();
        Validate.notNull(channel, "The channel object is null.");
        Validate.notNull(dataSerializer, "The packet data serializer object is null.");
        Validate.isTrue(dataSerializer.getByteBuf().writerIndex() <= 1048576, "The packet data larger than 1048576 bytes.");

        try {
            // 先用调用 NMS 的 PacketPlayOutCustomPayload 构造函数, 参数 String, PacketDataSerializer
            // 进行反射实例发送
            MinecraftReflection.sendPacket(players, instantiateObject(CLASS_PACKETPLAYOUTCUSTOMPAYLOAD, channel, dataSerializer.asNMS()));
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutCustomPayload 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量大于等于 2 个的话就是有此方式
                // 这两个字段分别对应 String, PacketDataSerializer 的 2 个属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTCUSTOMPAYLOAD);
                Object[] values = { channel, dataSerializer.asNMS() };
                setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTCUSTOMPAYLOAD, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
