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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>PacketPlayOutPosition</h1>
 * 数据包输出位置（详细doc待补充...）
 *
 * @version 2.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutPosition extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION;
    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS;
    private static volatile ConstructorAccessor<?> packetPlayOutPositionVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutPositionConstructor;

    static {

        CLASS_PACKETPLAYOUTPOSITION = MinecraftReflection.getMinecraftClass("PacketPlayOutPosition");
        CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS = MinecraftReflection.getMinecraftClassBuilder(new SingleParamBuilder<Class<?>, MinecraftBukkitVersion>() {
            @Override
            public Class<?> build(MinecraftBukkitVersion param) {
                if(param.equals(MinecraftBukkitVersion.V1_8_R1))
                    return MinecraftReflection.getMinecraftClass("EnumPlayerTeleportFlags");
                return MinecraftReflection.getMinecraftClass("PacketPlayOutPosition$EnumPlayerTeleportFlags");
            }
        });
        packetPlayOutPositionVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTPOSITION);
        packetPlayOutPositionConstructor = Accessors.getConstructorAccessorBuilderMCVer(new SingleParamBuilder<ConstructorAccessor<?>, MinecraftVersion>() {
            @Override
            public ConstructorAccessor<?> build(MinecraftVersion param) {
                if(!param.isOrLater(MinecraftVersion.V1_9))
                    return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTPOSITION, double.class, double.class, double.class, float.class, float.class, Set.class);
                return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTPOSITION, double.class, double.class, double.class, float.class, float.class, Set.class, int.class);
            }
        });
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty yaw;
    private FloatProperty pitch;
    private List<PlayerTeleportFlag> flagList;
    private IntegerProperty value;

    /**
     * 数据包输出位置构造函数
     */
    public PacketPlayOutPosition() {

        this(0d, 0d, 0d);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public PacketPlayOutPosition(double x, double y, double z) {

        this(x, y, z, 0f, 0f, 0);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param value 值
     */
    public PacketPlayOutPosition(double x, double y, double z, int value) {

        this(x, y, z, 0f, 0f, value);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch) {

        this(x, y, z, yaw, pitch, new ArrayList<>(), 0);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     * @param value 值
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, int value) {

        this(x, y, z, yaw, pitch, new ArrayList<>(), value);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     * @param flagList 传送标示列表
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, List<PlayerTeleportFlag> flagList) {

        this(x, y, z, yaw, pitch, flagList, 0);
    }

    /**
     * 数据包输出位置构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     * @param flagList 传送标示列表
     * @param value 值
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, List<PlayerTeleportFlag> flagList, int value) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.yaw = new SimpleFloatProperty(yaw);
        this.pitch = new SimpleFloatProperty(pitch);
        this.flagList = flagList;
        this.value = new SimpleIntegerProperty(value);
    }

    /**
     * 获取此数据包输出位置的 X 坐标属性
     *
     * @return X 坐标属性
     */
    public DoubleProperty xProperty() {

        return x;
    }

    /**
     * 获取此数据包输出位置的 Y 坐标属性
     *
     * @return Y 坐标属性
     */
    public DoubleProperty yProperty() {

        return y;
    }

    /**
     * 获取此数据包输出位置的 Z 坐标属性
     *
     * @return Z 坐标属性
     */
    public DoubleProperty zProperty() {

        return z;
    }

    /**
     * 获取此数据包输出位置的 Yaw 偏航角属性
     *
     * @return Yaw 偏航角属性
     */
    public FloatProperty yawProperty() {

        return yaw;
    }

    /**
     * 获取此数据包输出位置的 Pitch 俯仰角属性
     *
     * @return Pitch 俯仰角属性
     */
    public FloatProperty pitchProperty() {

        return pitch;
    }

    /**
     * 获取此数据包输出位置的传送标示列表属性
     *
     * @return 传送标示列表属性
     */
    public List<PlayerTeleportFlag> getFlagList() {

        return flagList;
    }

    /**
     * 获取此数据包输出位置的值属性
     *
     * @return 值属性
     */
    public IntegerProperty valueProperty() {

        return value;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTPOSITION;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

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

        try {
            // 先用调用 NMS 的 PacketPlayOutPosition 构造函数
            // 参数 double, double, double, float, float, Set
            // 参数 double, double, double, float, float, Set, int
            // 进行反射实例发送
            Set<Object> nmsFlagSet = new HashSet<>();
            for(PlayerTeleportFlag flag : flagList)
                nmsFlagSet.add(MinecraftReflection.enumOfNameAny(CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS, flag.name()));

            if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {
                // 1.8 版本少一个 int 参数
                return packetPlayOutPositionConstructor.invoke(x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet);
            } else {
                // 1.9 以及更高的版本有 int 参数
                return packetPlayOutPositionConstructor.invoke(x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet, value.get());
            }
        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutPosition 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 貌似 1.8 版本总共有 6 个字段 double, double, double, float, float, Set
                // 而 1.9 以及更高的版本有 7 个字段  double, double, double, float, float, Set, int
                Set<Object> nmsFlagSet = new HashSet<>();
                Object packet = packetPlayOutPositionVoidConstructor.invoke();
                for(PlayerTeleportFlag flag : flagList)
                    nmsFlagSet.add(MinecraftReflection.enumOfNameAny(CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS, flag.name()));

                if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {
                    Object[] values = { x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet };
                    return setFieldAccessibleAndValueGet(6, CLASS_PACKETPLAYOUTPOSITION, packet, values);
                } else {
                    Object[] values = { x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlagSet, value.get() };
                    return setFieldAccessibleAndValueGet(7, CLASS_PACKETPLAYOUTPOSITION, packet, values);
                }
            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
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
