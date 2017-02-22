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
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutAbilities</h1>
 * 数据包输出玩家能力（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutAbilities extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTABILITIES;
    private final static Class<?> CLASS_PLAYERABILITIES;
    private final static Class<?> CLASS_ENTITYHUMAN;
    private final static Field FIELD_ABILITIES;
    private final static Field FIELD_ISINVULNERABLE;
    private final static Field FIELD_ISFLYING;
    private final static Field FIELD_CANFLY;
    private final static Field FIELD_CANINSTANTLYBUILD;
    private final static Field FIELD_MAYBUILD;
    private final static Field FIELD_FLYSPEED;
    private final static Field FIELD_WALKSPEED;

    static {

        try {

            CLASS_PACKETPLAYOUTABILITIES = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutAbilities");
            CLASS_PLAYERABILITIES = PackageType.MINECRAFT_SERVER.getClass("PlayerAbilities");
            CLASS_ENTITYHUMAN = PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            FIELD_ABILITIES = getField(CLASS_ENTITYHUMAN, true, "abilities");
            FIELD_ISINVULNERABLE = getField(CLASS_PLAYERABILITIES, true, "isInvulnerable");
            FIELD_ISFLYING = getField(CLASS_PLAYERABILITIES, true, "isFlying");
            FIELD_CANFLY = getField(CLASS_PLAYERABILITIES, true, "canFly");
            FIELD_CANINSTANTLYBUILD = getField(CLASS_PLAYERABILITIES, true, "canInstantlyBuild");
            FIELD_MAYBUILD = getField(CLASS_PLAYERABILITIES, true, "mayBuild");
            FIELD_FLYSPEED = getField(CLASS_PLAYERABILITIES, true, "flySpeed");
            FIELD_WALKSPEED = getField(CLASS_PLAYERABILITIES, true, "walkSpeed");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out abilities reflect raw initialize exception.", e);
        }
    }

    private final PlayerAbilitiesProperty playerAbilities;

    /**
     * 数据包输出玩家能力构造函数
     */
    public PacketPlayOutAbilities() {

        this((PlayerAbilities) null);
    }

    /**
     * 数据包输出玩家能力构造函数
     *
     * @param abilities 玩家能力对象
     */
    public PacketPlayOutAbilities(PlayerAbilities abilities) {

        this.playerAbilities = new PlayerAbilitiesProperty(abilities);
    }

    /**
     * 数据包输出玩家能力构造函数
     *
     * @param player 玩家对象
     */
    public PacketPlayOutAbilities(Player player) {

        this(new PlayerAbilities(player));
    }

    /**
     * 数据包输出玩家能力构造函数
     *
     * @param player 月色之湖玩家对象
     */
    public PacketPlayOutAbilities(MoonLakePlayer player) {

        this(player.getBukkitPlayer());
    }

    /**
     * 数据包输出玩家能力构造函数
     *
     * @param isInvulnerable 是否坚不可摧
     * @param isFlying 是否飞行中
     * @param canFly 是否可以飞行
     * @param canInstantlyBuild 是否可以快速建造
     * @param mayBuild 是否可以建造
     * @param flySpeed 飞行速度
     * @param walkSpeed 移动速度
     */
    public PacketPlayOutAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

        this(new PlayerAbilities(isInvulnerable, isFlying, canFly, canInstantlyBuild, mayBuild, flySpeed, walkSpeed));
    }

    /**
     * 获取此数据包输出玩家能力的玩家能力属性
     *
     * @return 玩家能力属性
     */
    public PlayerAbilitiesProperty playerAbilitiesProperty() {

        return playerAbilities;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        PlayerAbilities playerAbilities = playerAbilitiesProperty().getValue();
        Validate.notNull(playerAbilities, "The player playerAbilities object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutAbilities 构造函数, 参数 PlayerAbilities
            // 进行反射实例发送
            Object nmsAbilities = instantiateObject(CLASS_PLAYERABILITIES);
            FIELD_ISINVULNERABLE.set(nmsAbilities, playerAbilities.isInvulnerable.get());
            FIELD_ISFLYING.set(nmsAbilities, playerAbilities.isFlying.get());
            FIELD_CANFLY.set(nmsAbilities, playerAbilities.canFly.get());
            FIELD_CANINSTANTLYBUILD.set(nmsAbilities, playerAbilities.canInstantlyBuild.get());
            FIELD_MAYBUILD.set(nmsAbilities, playerAbilities.mayBuild.get());
            FIELD_FLYSPEED.set(nmsAbilities, playerAbilities.flySpeed.get());
            FIELD_WALKSPEED.set(nmsAbilities, playerAbilities.walkSpeed.get());
            sendPacket(players, instantiateObject(CLASS_PACKETPLAYOUTABILITIES, nmsAbilities));
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutAbilities 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 6 个的话就是有此方式
                // 这六个字段分别对应 PlayerAbilities 的 6 个属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTABILITIES);
                Object[] values = {
                        playerAbilities.isInvulnerable.get(),
                        playerAbilities.isFlying.get(),
                        playerAbilities.canFly.get(),
                        playerAbilities.canInstantlyBuild.get(),
                        playerAbilities.flySpeed.get(),
                        playerAbilities.walkSpeed.get()};
                setFieldAccessibleAndValueSend(players, 6, CLASS_PACKETPLAYOUTABILITIES, packet, values);
                return true;

            } catch (Exception e1) {
            }

            try {
                // 否则只有 1 个字段的话并且字段类型为 PlayerAbilities 的方式
                Object nmsAbilities = instantiateObject(CLASS_PLAYERABILITIES);
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTABILITIES);
                setFieldAccessibleAndValueSend(players, 1, CLASS_PACKETPLAYOUTABILITIES, packet, nmsAbilities);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    /**
     * <h1>PlayerAbilitiesProperty</h1>
     * 玩家能力封装属性类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class PlayerAbilitiesProperty extends ObjectPropertyBase<PlayerAbilities> {

        /**
         * 玩家能力封装属性类构造函数
         */
        public PlayerAbilitiesProperty() {
        }

        /**
         * 玩家能力封装属性类构造函数
         *
         * @param playerAbilities 玩家能力对象
         */
        public PlayerAbilitiesProperty(PlayerAbilities playerAbilities) {

            super(playerAbilities);
        }

        /**
         * 获取此玩家能力的是否坚不可摧属性
         *
         * @return 是否坚不可摧属性
         */
        public BooleanProperty isInvulnerableProperty() {

            return get().isInvulnerable;
        }

        /**
         * 获取此玩家能力的是否飞行中属性
         *
         * @return 是否飞行中属性
         */
        public BooleanProperty isFlyingProperty() {

            return get().isFlying;
        }

        /**
         * 获取此玩家能力的是否可以飞行属性
         *
         * @return 是否可以飞行属性
         */
        public BooleanProperty canFlyProperty() {

            return get().canFly;
        }

        /**
         * 获取此玩家能力的是否可以快速建造属性
         *
         * @return 是否可以快速建造属性
         */
        public BooleanProperty canInstantlyBuildProperty() {

            return get().canInstantlyBuild;
        }

        /**
         * 获取此玩家能力的是否可以建造属性
         *
         * @return 是否可以建造属性
         */
        public BooleanProperty mayBuildProperty() {

            return get().mayBuild;
        }

        /**
         * 获取此玩家能力的飞行速度属性
         *
         * @return 飞行速度属性
         */
        public FloatProperty flySpeedProperty() {

            return get().flySpeed;
        }

        /**
         * 获取此玩家能力的移动速度属性
         *
         * @return 移动速度属性
         */
        public FloatProperty walkSpeedProperty() {

            return get().walkSpeed;
        }

        @Override
        public String toString() {

            return "PlayerAbilitiesProperty [value: " + get() + "]";
        }
    }

    /**
     * <h1>PlayerAbilities</h1>
     * 玩家能力封装类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public static class PlayerAbilities {

        private BooleanProperty isInvulnerable;
        private BooleanProperty isFlying;
        private BooleanProperty canFly;
        private BooleanProperty canInstantlyBuild;
        private BooleanProperty mayBuild;
        private FloatProperty flySpeed;
        private FloatProperty walkSpeed;

        /**
         * 玩家能力封装类构造函数
         */
        public PlayerAbilities() {

            this(false, false, false, false, true, 0.05f, 0.1f);
        }

        /**
         * 玩家能力封装类构造函数
         *
         * @param isInvulnerable 是否坚不可摧
         * @param isFlying 是否飞行中
         * @param canFly 是否可以飞行
         * @param canInstantlyBuild 是否可以瞬间建造
         * @param mayBuild 是否可以建造
         * @param flySpeed 飞行速度
         * @param walkSpeed 移动速度
         */
        public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

            this.isInvulnerable = new SimpleBooleanProperty(isInvulnerable);
            this.isFlying = new SimpleBooleanProperty(isFlying);
            this.canFly = new SimpleBooleanProperty(canFly);
            this.canInstantlyBuild = new SimpleBooleanProperty(canInstantlyBuild);
            this.mayBuild = new SimpleBooleanProperty(mayBuild);
            this.flySpeed = new SimpleFloatProperty(flySpeed);
            this.walkSpeed = new SimpleFloatProperty(walkSpeed);
        }

        /**
         * 玩家能力封装类构造函数
         *
         * @param player 玩家
         * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
         * @throws MoonLakeException 如果获取玩家的能力属性时错误则抛出异常
         */
        public PlayerAbilities(Player player) {

            Validate.notNull(player, "The player object is null.");

            try {

                Object nmsPlayer = getNMSPlayer(player);
                Object abilities = FIELD_ABILITIES.get(nmsPlayer);

                this.isInvulnerable = new SimpleBooleanProperty((Boolean) FIELD_ISINVULNERABLE.get(abilities));
                this.isFlying = new SimpleBooleanProperty((Boolean) FIELD_ISFLYING.get(abilities));
                this.canFly = new SimpleBooleanProperty((Boolean) FIELD_CANFLY.get(abilities));
                this.canInstantlyBuild = new SimpleBooleanProperty((Boolean) FIELD_CANINSTANTLYBUILD.get(abilities));
                this.mayBuild = new SimpleBooleanProperty((Boolean) FIELD_MAYBUILD.get(abilities));
                this.flySpeed = new SimpleFloatProperty((Float) FIELD_FLYSPEED.get(abilities));
                this.walkSpeed = new SimpleFloatProperty((Float) FIELD_WALKSPEED.get(abilities));
            }
            catch (Exception e) {

                throw new MoonLakeException("The get player playerAbilities object value exception.", e);
            }
        }

        /**
         * 获取此玩家能力的是否坚不可摧属性
         *
         * @return 是否坚不可摧属性
         */
        public BooleanProperty isInvulnerableProperty() {

            return isInvulnerable;
        }

        /**
         * 获取此玩家能力的是否飞行中属性
         *
         * @return 是否飞行中属性
         */
        public BooleanProperty isFlyingProperty() {

            return isFlying;
        }

        /**
         * 获取此玩家能力的是否可以飞行属性
         *
         * @return 是否可以飞行属性
         */
        public BooleanProperty canFlyProperty() {

            return canFly;
        }

        /**
         * 获取此玩家能力的是否可以快速建造属性
         *
         * @return 是否可以快速建造属性
         */
        public BooleanProperty canInstantlyBuildProperty() {

            return canInstantlyBuild;
        }

        /**
         * 获取此玩家能力的是否可以建造属性
         *
         * @return 是否可以建造属性
         */
        public BooleanProperty mayBuildProperty() {

            return mayBuild;
        }

        /**
         * 获取此玩家能力的飞行速度属性
         *
         * @return 飞行速度属性
         */
        public FloatProperty flySpeedProperty() {

            return flySpeed;
        }

        /**
         * 获取此玩家能力的移动速度属性
         *
         * @return 移动速度属性
         */
        public FloatProperty walkSpeedProperty() {

            return walkSpeed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PlayerAbilities that = (PlayerAbilities) o;

            if (isInvulnerable != null ? !isInvulnerable.equals(that.isInvulnerable) : that.isInvulnerable != null)
                return false;
            if (isFlying != null ? !isFlying.equals(that.isFlying) : that.isFlying != null) return false;
            if (canFly != null ? !canFly.equals(that.canFly) : that.canFly != null) return false;
            if (canInstantlyBuild != null ? !canInstantlyBuild.equals(that.canInstantlyBuild) : that.canInstantlyBuild != null)
                return false;
            if (mayBuild != null ? !mayBuild.equals(that.mayBuild) : that.mayBuild != null) return false;
            if (flySpeed != null ? !flySpeed.equals(that.flySpeed) : that.flySpeed != null) return false;
            return walkSpeed != null ? walkSpeed.equals(that.walkSpeed) : that.walkSpeed == null;
        }

        @Override
        public int hashCode() {
            int result = isInvulnerable != null ? isInvulnerable.hashCode() : 0;
            result = 31 * result + (isFlying != null ? isFlying.hashCode() : 0);
            result = 31 * result + (canFly != null ? canFly.hashCode() : 0);
            result = 31 * result + (canInstantlyBuild != null ? canInstantlyBuild.hashCode() : 0);
            result = 31 * result + (mayBuild != null ? mayBuild.hashCode() : 0);
            result = 31 * result + (flySpeed != null ? flySpeed.hashCode() : 0);
            result = 31 * result + (walkSpeed != null ? walkSpeed.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "PlayerAbilities{" +
                    "isInvulnerable=" + isInvulnerable.get() +
                    ", isFlying=" + isFlying.get() +
                    ", canFly=" + canFly.get() +
                    ", canInstantlyBuild=" + canInstantlyBuild.get() +
                    ", mayBuild=" + mayBuild.get() +
                    ", flySpeed=" + flySpeed.get() +
                    ", walkSpeed=" + walkSpeed.get() +
                    '}';
        }
    }
}
