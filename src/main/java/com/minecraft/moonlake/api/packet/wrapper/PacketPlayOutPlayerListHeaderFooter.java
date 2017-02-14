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
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPlayerListHeaderFooter</h1>
 * 数据包输出玩家列表头脚文本（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutPlayerListHeaderFooter extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;

    static {

        try {

            CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerListHeaderFooter");
            CLASS_CHATSERIALIZER =  PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out player list header footer reflect raw initialize exception.", e);
        }
    }

    private StringProperty header;
    private StringProperty footer;

    public PacketPlayOutPlayerListHeaderFooter() {

        this(null, null);
    }

    public PacketPlayOutPlayerListHeaderFooter(String header) {

        this(header, null);
    }

    public PacketPlayOutPlayerListHeaderFooter(String header, String footer) {

        this.header = new SimpleStringProperty(header);
        this.footer = new SimpleStringProperty(footer);
    }

    public StringProperty headerProperty() {

        return header;
    }

    public StringProperty footerProperty() {

        return footer;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        String header = headerProperty().get();
        String footer = footerProperty().get();
        Validate.notNull(header, "The header object is null.");
        Validate.notNull(footer, "The footer object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数, 参数 IChatBaseComponent
            // 进行反射实例发送
            Object nmsHeader = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + header + "\"}");
            Object nmsFooter = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + footer + "\"}");
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, nmsHeader);
            setFieldAccessibleAndValueSend(players, 1, 2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsFooter);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量大于等于 2 个的话就是有此方式
                // 这两个字段分别对应 IChatBaseComponent, IChatBaseComponent 的 2 个属性
                Object nmsHeader = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + header + "\"}");
                Object nmsFooter = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + footer + "\"}");
                Object[] values = { nmsHeader, nmsFooter };
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER);
                setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}