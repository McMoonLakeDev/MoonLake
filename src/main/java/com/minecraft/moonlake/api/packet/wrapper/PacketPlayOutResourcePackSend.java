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

import com.google.common.io.BaseEncoding;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutResourcePackSend</h1>
 * 数据包输出材质包发送（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutResourcePackSend extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTRESOURCEPACKSEND;
    private static volatile ConstructorAccessor<?> packetPlayOutResourcePackSendVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutResourcePackSendConstructor;

    static {
        CLASS_PACKETPLAYOUTRESOURCEPACKSEND = MinecraftReflection.getMinecraftClass("PacketPlayOutResourcePackSend");
        packetPlayOutResourcePackSendVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTRESOURCEPACKSEND);
        packetPlayOutResourcePackSendConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTRESOURCEPACKSEND, String.class, String.class);
    }

    private StringProperty url;
    private StringProperty hash;

    /**
     * 数据包输出材质包发送构造函数
     */
    public PacketPlayOutResourcePackSend() {
        this("");
    }

    /**
     * 数据包输出材质包发送构造函数
     *
     * @param url 材质包地址
     */
    public PacketPlayOutResourcePackSend(String url) {
        this(url, "null");
    }

    /**
     * 数据包输出材质包发送构造函数
     *
     * @param url 材质包地址
     * @param hash 材质包哈希值 (20字节长度)
     */
    public PacketPlayOutResourcePackSend(String url, byte[] hash) {
        this(url, BaseEncoding.base16().lowerCase().encode(hash));
    }

    /**
     * 数据包输出材质包发送构造函数
     *
     * @param url 材质包地址
     * @param hash 材质包哈希值 (40字符长度)
     */
    public PacketPlayOutResourcePackSend(String url, String hash) {
        this.url = new SimpleStringProperty(url);
        this.hash = new SimpleStringProperty(hash);
    }

    /**
     * 数据包输出材质包发送的材质包地址属性
     *
     * @return 材质包地址属性
     */
    public StringProperty urlProperty() {
        return url;
    }

    /**
     * 数据包输出材质包发送的材质包哈希值属性
     *
     * @return 材质包哈希值属性
     */
    public StringProperty hashProperty() {
        return hash;
    }

    @Override
    public Class<?> getPacketClass() {
        return CLASS_PACKETPLAYOUTRESOURCEPACKSEND;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String url = urlProperty().get();
        String hash = hashProperty().get();
        Validate.notNull(url, "The resource pack url string object is null.");
        Validate.notNull(hash, "The resource pack hash value object is null.");
        Validate.isTrue(hash.length() <= 40, "The resource pack hash value length is too long. (max: 40, was " + hash.length() + ")");

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

        String url = urlProperty().get();
        String hash = hashProperty().get();
        Validate.notNull(url, "The resource pack url string object is null.");
        Validate.notNull(hash, "The resource pack hash value object is null.");
        Validate.isTrue(hash.length() <= 40, "The resource pack hash value length is too long. (max: 40, was " + hash.length() + ")");

        try {
            // 先用调用 NMS 的 PacketPlayOutResourcePackSend 构造函数
            // 参数 String, String 进行反射实例发送
            return packetPlayOutResourcePackSendConstructor.invoke(url, hash);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutResourcePackSend 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 2 个的话就是有此方式
                // 这两个字段分别对应 String, String 的 2 个属性
                Object[] values = { url, hash };
                Object packet = packetPlayOutResourcePackSendVoidConstructor.invoke();
                return setFieldAccessibleAndValueGet(2, CLASS_PACKETPLAYOUTRESOURCEPACKSEND, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
