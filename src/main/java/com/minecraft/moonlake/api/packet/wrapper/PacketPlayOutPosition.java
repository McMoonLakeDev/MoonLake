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
import com.minecraft.moonlake.property.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPosition</h1>
 * 数据包输出位置（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutPosition extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION;
    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTPOSITION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition");
            CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "EnumPlayerTeleportFlags" : "PacketPlayOutPosition$EnumPlayerTeleportFlags");
            METHOD_VALUEOF = getMethod(CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS, "valueOf", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out position reflect raw initialize exception.", e);
        }
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty yaw;
    private FloatProperty pitch;
    private List<PlayerTeleportFlag> flagList;
    private IntegerProperty value;

    public PacketPlayOutPosition() {

        this(0d, 0d, 0d);
    }

    public PacketPlayOutPosition(double x, double y, double z) {

        this(x, y, z, 0f, 0f, 0);
    }

    public PacketPlayOutPosition(double x, double y, double z, int value) {

        this(x, y, z, 0f, 0f, value);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch) {

        this(x, y, z, yaw, pitch, new ArrayList<>(), 0);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, int value) {

        this(x, y, z, yaw, pitch, new ArrayList<>(), value);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, List<PlayerTeleportFlag> flagList) {

        this(x, y, z, yaw, pitch, flagList, 0);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, List<PlayerTeleportFlag> flagList, int value) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.yaw = new SimpleFloatProperty(yaw);
        this.pitch = new SimpleFloatProperty(pitch);
        this.flagList = flagList;
        this.value = new SimpleIntegerProperty(value);
    }

    public DoubleProperty xProperty() {

        return x;
    }

    public DoubleProperty yProperty() {

        return y;
    }

    public DoubleProperty zProperty() {


        return z;
    }

    public FloatProperty yawProperty() {

        return yaw;
    }

    public FloatProperty pitchProperty() {

        return pitch;
    }

    public List<PlayerTeleportFlag> getFlagList() {

        return flagList;
    }

    public IntegerProperty valueProperty() {

        return value;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        try {
            // 先用调用 NMS 的 PacketPlayOutPosition 构造函数
            // 参数 double, double, double, float, float, Set
            // 参数 double, double, double, float, float, Set, int
            // 进行反射实例发送
            Set<Object> nmsFlagSet = new HashSet<>();
            for(PlayerTeleportFlag flag : flagList)
                nmsFlagSet.add(METHOD_VALUEOF.invoke(null, flag.name()));

            if(getServerVersionNumber() <= 8) {
                // 1.8 版本少一个 int 参数
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION, x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet);
                sendPacket(players, packet);
            } else {
                // 1.9 以及更高的版本有 int 参数
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION, x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet, value.get());
                sendPacket(players, packet);
            }
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutPosition 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 貌似 1.8 版本总共有 6 个字段 double, double, double, float, float, Set
                // 而 1.9 以及更高的版本有 7 个字段  double, double, double, float, float, Set, int
                Set<Object> nmsFlagSet = new HashSet<>();
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION);
                for(PlayerTeleportFlag flag : flagList)
                    nmsFlagSet.add(METHOD_VALUEOF.invoke(null, flag.name()));

                if(getServerVersionNumber() <= 8) {
                    Object[] values = { x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet };
                    setFieldAccessibleAndValueSend(players, 6, CLASS_PACKETPLAYOUTPOSITION, packet, values);
                } else {
                    Object[] values = { x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet, value.get() };
                    setFieldAccessibleAndValueSend(players, 7, CLASS_PACKETPLAYOUTPOSITION, packet, values);
                }
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    /**
     * <h1>PlayerTeleportFlag</h1>
     * 玩家传送标示类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum PlayerTeleportFlag {

        /**
         * 玩家传送标示类型: X 坐标
         */
        X(0),
        /**
         * 玩家传送标示类型: Y 坐标
         */
        Y(1),
        /**
         * 玩家传送标示类型: Z 坐标
         */
        Z(2),
        /**
         * 玩家传送标示类型: Y 坐标旋转
         */
        Y_ROT(3),
        /**
         * 玩家传送标示类型: X 坐标旋转
         */
        X_ROT(4),
        ;

        private final int id;

        /**
         * 玩家传送标示类型构造函数
         *
         * @param id Id
         */
        PlayerTeleportFlag(int id) {

            this.id = id;
        }

        /**
         * 获取玩家传送标示类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }
    }
}
