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

import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.particle.ParticleEffect;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import org.bukkit.entity.Player;

public class PacketPlayOutWorldParticles extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTWORLDPARTICLES;
    private static volatile ConstructorAccessor packetPlayOutWorldParticlesVoidConstructor;
    private static volatile ConstructorAccessor packetPlayOutWorldParticlesConstructor;

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

    public PacketPlayOutWorldParticles() {

        this(null, false, -1, -1, -1, 0, 0, 0, 0, 0);
    }

    public PacketPlayOutWorldParticles(ParticleEffect particle, boolean longDistance, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count, int... arguments) {

        this(particle, longDistance, (float) x, (float) y, (float) z, xOffset, yOffset, zOffset, speed, count, arguments);
    }

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

    public ObjectProperty<ParticleEffect> particleProperty() {
        return particle;
    }

    public FloatProperty xProperty() {
        return x;
    }

    public FloatProperty yProperty() {
        return y;
    }

    public FloatProperty zProperty() {
        return z;
    }

    public FloatProperty xOffsetProperty() {
        return xOffset;
    }

    public FloatProperty yOffsetProperty() {
        return yOffset;
    }

    public FloatProperty zOffsetProperty() {
        return zOffset;
    }

    public FloatProperty speedProperty() {
        return speed;
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public BooleanProperty longDistanceProperty() {
        return longDistance;
    }

    public int[] getArguments() {
        return arguments;
    }

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

        ParticleEffect particle = particleProperty().get();
        if(particle == null)
            particle = ParticleEffect.BARRIER;

        try {
            // 先用调用 NMS 的 PacketPlayOutWorldParticles 构造函数,
            // 参数 EnumParticles, boolean, float, float, float, float, float, float, float, int, int[]
            // 进行反射实例发送
            Object enumParticles = MinecraftReflection.enumParticleGetByParticle(particle);
            Object packet = packetPlayOutWorldParticlesConstructor.invoke(enumParticles, longDistance.get(), x.get(), y.get(), z.get(), xOffset.get(), yOffset.get(), zOffset.get(), speed.get(), count.get(), arguments);
            MinecraftReflection.sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            // 如果异常了说明 NMS 的 PacketPlayOutWorldParticles 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 11 个的话就是有此方式
                // 这两个字段分别对应 EnumParticles, boolean, float, float, float, float, float, float, float, int, int[] 的 11 个属性
                Object packet = packetPlayOutWorldParticlesVoidConstructor.invoke();
                Object enumParticles = MinecraftReflection.enumParticleGetByParticle(particle);
                Object[] values = { enumParticles, longDistance.get(), x.get(), y.get(), z.get(), xOffset.get(), yOffset.get(), zOffset.get(), speed.get(), count.get(), arguments };
                setFieldAccessibleAndValueSend(players, 11, CLASS_PACKETPLAYOUTWORLDPARTICLES, packet, values);
                return true;

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
