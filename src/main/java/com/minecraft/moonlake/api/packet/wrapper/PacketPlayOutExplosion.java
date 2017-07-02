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

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>PacketPlayOutExplosion</h1>
 * 数据包输出爆炸（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutExplosion extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTEXPLOSION;
    private static volatile ConstructorAccessor<?> packetPlayOutExplosionVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutExplosionConstructor;
    private static volatile ConstructorAccessor<?> vec3DConstructor;

    static {

        CLASS_PACKETPLAYOUTEXPLOSION = MinecraftReflection.getMinecraftClass("PacketPlayOutExplosion");
        Class<?> vec3DClass = MinecraftReflection.getMinecraftVec3DClass();
        packetPlayOutExplosionVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTEXPLOSION);
        packetPlayOutExplosionConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTEXPLOSION, double.class, double.class, double.class, float.class, List.class, vec3DClass);
        vec3DConstructor = Accessors.getConstructorAccessor(vec3DClass, double.class, double.class, double.class);
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty radius;
    private List<BlockPosition> records;
    private ObjectProperty<Vector> vector;

    /**
     * 数据包输出爆炸构造函数
     */
    public PacketPlayOutExplosion() {

        this(0d, 0d, 0d);
    }

    /**
     * 数据包输出爆炸构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public PacketPlayOutExplosion(double x, double y, double z) {

        this(x, y, z, 0f, null, null);
    }

    /**
     * 数据包输出爆炸构造函数
     *
     * @param location 位置
     * @param radius 半径
     */
    public PacketPlayOutExplosion(Location location, float radius) {

        this(location.getX(), location.getY(), location.getZ(), radius, null, null);
    }

    /**
     * 数据包输出爆炸构造函数
     *
     * @param location 位置
     * @param radius 半径
     * @param records 方块位置记录
     * @param vector 矢量
     */
    public PacketPlayOutExplosion(Location location, float radius, List<BlockPosition> records, Vector vector) {

        this(location.getX(), location.getY(), location.getZ(), radius, records, vector);
    }

    /**
     * 数据包输出爆炸构造函数
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
     * 数据包输出爆炸构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param radius 半径
     * @param records 方块位置记录
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
     * 获取此数据包输出爆炸的 X 坐标属性
     *
     * @return X 坐标属性
     */
    public DoubleProperty xProperty() {

        return x;
    }

    /**
     * 获取此数据包输出爆炸的 Y 坐标属性
     *
     * @return Y 坐标属性
     */
    public DoubleProperty yProperty() {

        return y;
    }

    /**
     * 获取此数据包输出爆炸的 Z 坐标属性
     *
     * @return Z 坐标属性
     */
    public DoubleProperty zProperty() {

        return z;
    }

    /**
     * 获取此数据包输出爆炸的半径属性
     *
     * @return 半径属性
     */
    public FloatProperty radiusProperty() {

        return radius;
    }

    /**
     * 获取此数据包输出爆炸的方块位置记录属性
     *
     * @return 方块位置记录属性
     */
    public List<BlockPosition> recordsProperty() {

        return records;
    }

    /**
     * 获取此数据包输出爆炸的矢量属性
     *
     * @return 矢量属性
     */
    public ObjectProperty<Vector> vectorProperty() {

        return vector;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTEXPLOSION;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        Vector vector = vectorProperty().get();
        Validate.notNull(vector, "The vector object is null.");

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

        Vector vector = vectorProperty().get();
        Validate.notNull(vector, "The vector object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutExplosion 构造函数, 参数 double, double, double, float, List, Vec3D
            // 进行反射实例发送
            List<Object> nmsBlockPositionList = new ArrayList<>();
            if(records != null && !records.isEmpty())
                for(BlockPosition blockPosition : records)
                    nmsBlockPositionList.add(blockPosition.asNMS());
            // 实例化其他参数值
            Object nmsVec3D = vec3DConstructor.invoke(vector.getX(), vector.getY(), vector.getZ());
            return packetPlayOutExplosionConstructor.invoke(x.get(), y.get(), z.get(), radius.get(), nmsBlockPositionList, nmsVec3D);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutExplosion 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 8 个的话就是有此方式
                // 这八个字段分别对应 double, double, double, float, List, double, double, double 的 8 个属性
                List<Object> nmsBlockPositionList = new ArrayList<>();
                if(records != null && !records.isEmpty())
                    for(BlockPosition blockPosition : records)
                        nmsBlockPositionList.add(blockPosition.asNMS());

                Object packet = packetPlayOutExplosionVoidConstructor.invoke();
                Object[] values = { x.get(), y.get(), z.get(), radius.get(), nmsBlockPositionList, vector.getX(), vector.getY(), vector.getZ()};
                return setFieldAccessibleAndValueGet(8, CLASS_PACKETPLAYOUTEXPLOSION, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
