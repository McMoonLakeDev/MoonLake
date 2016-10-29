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
import com.minecraft.moonlake.nms.packet.wrapped.BlockPosition;
import com.minecraft.moonlake.property.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.PackageType;
import static com.minecraft.moonlake.reflect.Reflect.instantiateObject;

/**
 * <h1>PacketPlayOutExplosion</h1>
 * 数据包输出爆炸（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutExplosion extends PacketAbstract<PacketPlayOutExplosion> {

    private final static Class<?> CLASS_PACKETPLAYOUTEXPLOSION;
    private final static Class<?> CLASS_VEC3D;

    static {

        try {

            CLASS_PACKETPLAYOUTEXPLOSION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutExplosion");
            CLASS_VEC3D = PackageType.MINECRAFT_SERVER.getClass("Vec3D");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out explosion reflect raw initialize exception.", e);
        }
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty radius;
    private List<BlockPosition> records;
    private ObjectProperty<Vector> vector;

    /**
     * 数据包输出爆炸类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutExplosion(Location, float, List, Vector)}
     * @see #PacketPlayOutExplosion(Location, float, List, Vector)
     */
    @Deprecated
    public PacketPlayOutExplosion() {

        this(0d, 0d, 0d);
    }

    /**
     * 数据包输出爆炸类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public PacketPlayOutExplosion(double x, double y, double z) {

        this(x, y, z, 0f, null, null);
    }

    /**
     * 数据包输出爆炸类构造函数
     *
     * @param location 位置对象
     * @param radius 半径
     */
    public PacketPlayOutExplosion(Location location, float radius) {

        this(location.getX(), location.getY(), location.getZ(), radius, null, null);
    }

    /**
     * 数据包输出爆炸类构造函数
     *
     * @param location 位置对象
     * @param radius 半径
     * @param records 方块记录
     * @param vector 矢量
     */
    public PacketPlayOutExplosion(Location location, float radius, List<BlockPosition> records, Vector vector) {

        this(location.getX(), location.getY(), location.getZ(), radius, records, vector);
    }

    /**
     * 数据包输出爆炸类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param radius 半径
     */
    public PacketPlayOutExplosion(double x, double y, double z, float radius) {

        this(x, y, z, radius, null, null);
    }

    /**
     * 数据包输出爆炸类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param radius 半径
     * @param records 方块记录
     * @param vector 矢量
     */
    public PacketPlayOutExplosion(double x, double y, double z, float radius, List<BlockPosition> records, Vector vector) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.radius = new SimpleFloatProperty(radius);
        this.records = records == null ? new ArrayList<>() : records;
        this.vector = new SimpleObjectProperty<>(vector == null ? new Vector() : vector);
    }

    /**
     * 获取此数据包输出爆炸的 X 坐标
     *
     * @return X 坐标
     */
    public DoubleProperty getX() {

        return x;
    }

    /**
     * 获取此数据包输出爆炸的 Y 坐标
     *
     * @return Y 坐标
     */
    public DoubleProperty getY() {

        return y;
    }

    /**
     * 获取此数据包输出爆炸的 Z 坐标
     *
     * @return Z 坐标
     */
    public DoubleProperty getZ() {

        return z;
    }

    /**
     * 获取此数据包输出爆炸的半径
     *
     * @return 半径
     */
    public FloatProperty getRadius() {

        return radius;
    }

    /**
     * 获取此数据包输出爆炸的方块位置记录
     *
     * @return 方块位置记录
     */
    public List<BlockPosition> getRecords() {

        return records;
    }

    /**
     * 获取此数据包输出爆炸的矢量
     *
     * @return 矢量
     */
    public ObjectProperty<Vector> getVector() {

        return vector;
    }

    public void setRecords(List<BlockPosition> records) {

        this.records = records == null ? new ArrayList<>() : records;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            List<Object> nmsBlockPosition = new ArrayList<>();

            if(records != null && records.size() > 0) {

                for(final BlockPosition blockPosition : records) {

                    nmsBlockPosition.add(blockPosition.asNMS());
                }
            }
            Object nmsVec3D = instantiateObject(CLASS_VEC3D, vector.get().getX(), vector.get().getY(), vector.get().getZ());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTEXPLOSION, x.get(), y.get(), z.get(), radius.get(), nmsBlockPosition, nmsVec3D);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out explosion send exception.", e);
        }
    }
}
