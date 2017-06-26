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
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.particle.ParticleEffect;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutWorldParticles</h1>
 * 数据包输出世界粒子效果（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutWorldParticles extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTWORLDPARTICLES;
    private static volatile ConstructorAccessor<?> packetPlayOutWorldParticlesVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutWorldParticlesConstructor;

    static {

        CLASS_PACKETPLAYOUTWORLDPARTICLES = MinecraftReflection.getMinecraftClass("PacketPlayOutWorldParticles");
        Class<?> enumParticlesClass = MinecraftReflection.getMinecraftEnumParticleClass();
        packetPlayOutWorldParticlesVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTWORLDPARTICLES);
        packetPlayOutWorldParticlesConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTWORLDPARTICLES, enumParticlesClass, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
    }

    private ObjectProperty<ParticleEffect> particle;
    private FloatProperty x;
    private FloatProperty y;
    private FloatProperty z;
    private FloatProperty xOffset;
    private FloatProperty yOffset;;
    private FloatProperty zOffset;;
    private FloatProperty speed;
    private IntegerProperty count;
    private BooleanProperty longDistance;
    private int[] arguments;

    /**
     * 数据包输出世界粒子效果构造函数
     */
    public PacketPlayOutWorldParticles() {

        this(null, false, -1, -1, -1, 0, 0, 0, 0, 0);
    }

    /**
     * 数据包输出世界粒子效果构造函数
     *
     * @param particle 粒子效果
     * @param longDistance 长距离
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param xOffset X 偏移量
     * @param yOffset Y 偏移量
     * @param zOffset Z 偏移量
     * @param speed 速度 (0.0 - 1.0)
     * @param count 数量
     * @param arguments 参数
     */
    public PacketPlayOutWorldParticles(ParticleEffect particle, boolean longDistance, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count, int... arguments) {

        this(particle, longDistance, (float) x, (float) y, (float) z, xOffset, yOffset, zOffset, speed, count, arguments);
    }

    /**
     * 数据包输出世界粒子效果构造函数
     *
     * @param particle 粒子效果
     * @param longDistance 长距离
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param xOffset X 偏移量
     * @param yOffset Y 偏移量
     * @param zOffset Z 偏移量
     * @param speed 速度 (0.0 - 1.0)
     * @param count 数量
     * @param arguments 参数
     */
    public PacketPlayOutWorldParticles(ParticleEffect particle, boolean longDistance, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int count, int... arguments) {

        this.particle = new SimpleObjectProperty<>(particle == null ? ParticleEffect.BARRIER : particle);
        this.longDistance = new SimpleBooleanProperty(longDistance);
        this.x = new SimpleFloatProperty(x);
        this.y = new SimpleFloatProperty(y);
        this.z = new SimpleFloatProperty(z);
        this.xOffset = new SimpleFloatProperty(xOffset);
        this.yOffset = new SimpleFloatProperty(yOffset);
        this.zOffset = new SimpleFloatProperty(zOffset);
        this.speed = new SimpleFloatProperty(speed);
        this.count = new SimpleIntegerProperty(count);
        this.arguments = arguments;
    }

    /**
     * 获取此数据包输出世界粒子效果的粒子效果属性
     *
     * @return 粒子效果属性
     */
    public ObjectProperty<ParticleEffect> particleProperty() {
        return particle;
    }

    /**
     * 获取此数据包输出世界粒子效果的 X 坐标属性
     *
     * @return X 坐标属性
     */
    public FloatProperty xProperty() {
        return x;
    }

    /**
     * 获取此数据包输出世界粒子效果的 Y 坐标属性
     *
     * @return Y 坐标属性
     */
    public FloatProperty yProperty() {
        return y;
    }

    /**
     * 获取此数据包输出世界粒子效果的 Z 坐标属性
     *
     * @return Z 坐标属性
     */
    public FloatProperty zProperty() {
        return z;
    }

    /**
     * 获取此数据包输出世界粒子效果的 X 偏移量属性
     *
     * @return X 偏移量属性
     */
    public FloatProperty xOffsetProperty() {
        return xOffset;
    }

    /**
     * 获取此数据包输出世界粒子效果的 Y 偏移量属性
     *
     * @return Y 偏移量属性
     */
    public FloatProperty yOffsetProperty() {
        return yOffset;
    }

    /**
     * 获取此数据包输出世界粒子效果的 Z 偏移量属性
     *
     * @return Z 偏移量属性
     */
    public FloatProperty zOffsetProperty() {
        return zOffset;
    }

    /**
     * 获取此数据包输出世界粒子效果的速度属性
     *
     * @return 速度属性
     */
    public FloatProperty speedProperty() {
        return speed;
    }

    /**
     * 获取此数据包输出世界粒子效果的数量属性
     *
     * @return 数量属性
     */
    public IntegerProperty countProperty() {
        return count;
    }

    /**
     * 获取此数据包输出世界粒子效果的是否长距离属性
     *
     * @return 是否长距离属性
     */
    public BooleanProperty longDistanceProperty() {
        return longDistance;
    }

    /**
     * 获取此数据包输出世界粒子效果的参数属性
     *
     * @return 参数属性
     */
    public int[] getArguments() {
        return arguments;
    }

    /**
     * 设置此数据包输出世界粒子效果的参数属性
     *
     * @param arguments 参数属性
     */
    public void setArguments(int[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTWORLDPARTICLES;
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

        ParticleEffect particle = particleProperty().get();
        if(particle == null)
            particle = ParticleEffect.BARRIER;

        try {
            // 先用调用 NMS 的 PacketPlayOutWorldParticles 构造函数,
            // 参数 EnumParticles, boolean, float, float, float, float, float, float, float, int, int[]
            // 进行反射实例发送
            Object enumParticles = MinecraftReflection.enumParticleGetByParticle(particle);
            return packetPlayOutWorldParticlesConstructor.invoke(enumParticles, longDistance.get(), x.get(), y.get(), z.get(), xOffset.get(), yOffset.get(), zOffset.get(), speed.get(), count.get(), arguments);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutWorldParticles 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 11 个的话就是有此方式
                // 这两个字段分别对应 EnumParticles, boolean, float, float, float, float, float, float, float, int, int[] 的 11 个属性
                Object packet = packetPlayOutWorldParticlesVoidConstructor.invoke();
                Object enumParticles = MinecraftReflection.enumParticleGetByParticle(particle);
                Object[] values = { enumParticles, longDistance.get(), x.get(), y.get(), z.get(), xOffset.get(), yOffset.get(), zOffset.get(), speed.get(), count.get(), arguments };
                return setFieldAccessibleAndValueGet(11, CLASS_PACKETPLAYOUTWORLDPARTICLES, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
