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
import com.minecraft.moonlake.property.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPosition</h1>
 * 数据包输出位置（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutPosition}
 */
@Deprecated
public class PacketPlayOutPosition extends PacketAbstract<PacketPlayOutPosition> {

    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION;
    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTPOSITION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition");
            CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition$EnumPlayerTeleportFlags");
            METHOD_VALUEOF = getMethod(CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS, "valueOf", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out position reflect raw initialize exception.", e);
        }
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty yaw;
    private FloatProperty pitch;
    private Set<PlayerTeleportFlag> flags;
    private IntegerProperty g;

    /**
     * 数据包输出位置类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutPosition(Location, Set, int)}
     * @see #PacketPlayOutPosition(Location, Set, int)
     */
    @Deprecated
    public PacketPlayOutPosition() {

        this(0d, 0d, 0d);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param location 位置
     */
    public PacketPlayOutPosition(Location location) {

        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), null, 0);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param location 位置
     * @param flags 玩家传送标示
     * @param g g
     */
    public PacketPlayOutPosition(Location location, Set<PlayerTeleportFlag> flags, int g) {

        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), flags, g);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public PacketPlayOutPosition(double x, double y, double z) {

        this(x, y, z, 0f, 0f);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch) {

        this(x, y, z, yaw, pitch, null, 0);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     * @param flags 玩家传送标示
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<PlayerTeleportFlag> flags) {

        this(x, y, z, yaw, pitch, flags, 0);
    }

    /**
     * 数据包输出位置类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param yaw Yaw 偏航角
     * @param pitch Pitch 俯仰角
     * @param flags 玩家传送标示
     * @param g g
     */
    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<PlayerTeleportFlag> flags, int g) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.yaw = new SimpleFloatProperty(yaw);
        this.pitch = new SimpleFloatProperty(pitch);
        this.flags = flags;
        this.g = new SimpleIntegerProperty(g);
    }

    /**
     * 获取此数据包输出位置的 X 坐标
     *
     * @return X 坐标
     */
    public DoubleProperty getX() {

        return x;
    }

    /**
     * 获取此数据包输出位置的 Y 坐标
     *
     * @return Y 坐标
     */
    public DoubleProperty getY() {

        return y;
    }

    /**
     * 获取此数据包输出位置的 Z 坐标
     *
     * @return Z 坐标
     */
    public DoubleProperty getZ() {

        return z;
    }

    /**
     * 获取此数据包输出位置的 Yaw 偏航角
     *
     * @return Yaw 偏航角
     */
    public FloatProperty getYaw() {

        return yaw;
    }

    /**
     * 获取此数据包输出位置的 Pitch 俯仰角
     *
     * @return Pitch 俯仰角
     */
    public FloatProperty getPitch() {

        return pitch;
    }

    /**
     * 获取此数据包输出位置的玩家传送标示
     *
     * @return 玩家传送标示
     */
    public Set<PlayerTeleportFlag> getFlags() {

        return flags;
    }

    /**
     * 玩家此数据包输出位置的 G
     *
     * @return G
     */
    public IntegerProperty getG() {

        return g;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Set<Object> nmsFlags = new HashSet<>();

            if(flags != null) {

                for(final PlayerTeleportFlag flag : flags) {

                    nmsFlags.add(METHOD_VALUEOF.invoke(null, flag.name()));
                }
            }
            Object packet;

            if(getServerVersionNumber() <= 8) // 1.8 版本没有 g 这个 int 参数
                packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION, x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlags);
            else
                packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION, x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlags, g.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out position send exception.", e);
        }
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
