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
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutRespawn</h1>
 * 数据包输出重生（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutRespawn extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTRESPAWN;
    private final static Class<?> CLASS_ENUMDIFFICULTY;
    private static volatile ConstructorAccessor<?> packetPlayOutRespawnVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutRespawnConstructor;
    private static volatile MethodAccessor enumDifficultyGetByIdMethod;

    static {

        CLASS_PACKETPLAYOUTRESPAWN = MinecraftReflection.getMinecraftClass("PacketPlayOutRespawn");
        CLASS_ENUMDIFFICULTY = MinecraftReflection.getMinecraftClass("EnumDifficulty");
        Class<?> worldTypeClass = MinecraftReflection.getMinecraftWorldType();
        Class<?> enumGamemodeClass = MinecraftReflection.getEnumGamemodeClass();
        packetPlayOutRespawnVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTRESPAWN);
        packetPlayOutRespawnConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTRESPAWN, int.class, CLASS_ENUMDIFFICULTY, worldTypeClass, enumGamemodeClass);
        enumDifficultyGetByIdMethod = Accessors.getMethodAccessor(CLASS_ENUMDIFFICULTY, "getById", int.class);
    }

    private IntegerProperty worldDimensionId;
    private ObjectProperty<WorldDifficulty> worldDifficulty;
    private ObjectProperty<GameMode> worldGameMode;
    private ObjectProperty<WorldType> worldType;

    /**
     * 数据包输出重生构造函数
     */
    public PacketPlayOutRespawn() {

        this(WorldDimension.OVERWORLD, null, null, null);
    }

    /**
     * 数据包输出重生构造函数
     *
     * @param world 世界
     * @param gameMode 游戏模式
     */
    public PacketPlayOutRespawn(World world, GameMode gameMode) {

        this(WorldDimension.fromWorld(world), WorldDifficulty.fromWorld(world), gameMode, WorldType.fromWorld(world));
    }

    /**
     * 数据包输出重生构造函数
     *
     * @param worldDimension 世界维度
     * @param worldDifficulty 世界难度
     * @param worldGameMode 世界游戏模式
     * @param worldType 世界类型
     */
    public PacketPlayOutRespawn(WorldDimension worldDimension, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this.worldDimensionId = new SimpleIntegerProperty(worldDimension.getId());
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    /**
     * 获取此数据包输出重生的世界维度 Id 属性
     *
     * @return 世界维度 Id 属性
     */
    public IntegerProperty worldDimensionIdProperty() {

        return worldDimensionId;
    }

    /**
     * 获取此数据包输出重生的世界难度属性
     *
     * @return 世界难度属性
     */
    public ObjectProperty<WorldDifficulty> worldDifficultyProperty() {

        return worldDifficulty;
    }

    /**
     * 获取此数据包输出重生的世界游戏模式属性
     *
     * @return 世界游戏模式属性
     */
    public ObjectProperty<GameMode> worldGameModeProperty() {

        return worldGameMode;
    }

    /**
     * 获取此数据包输出重生的世界类型属性
     *
     * @return 世界类型属性
     */
    public ObjectProperty<WorldType> worldTypeProperty() {

        return worldType;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTRESPAWN;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        WorldType worldType = worldTypeProperty().get();
        WorldDifficulty worldDifficulty = worldDifficultyProperty().get();
        GameMode worldGameMode = worldGameModeProperty().get();
        Validate.notNull(worldType, "The world type object is null.");
        Validate.notNull(worldDifficulty, "The world difficulty object is null");
        Validate.notNull(worldGameMode, "The world game mode object is null.");

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

        WorldType worldType = worldTypeProperty().get();
        WorldDifficulty worldDifficulty = worldDifficultyProperty().get();
        GameMode worldGameMode = worldGameModeProperty().get();
        Validate.notNull(worldType, "The world type object is null.");
        Validate.notNull(worldDifficulty, "The world difficulty object is null");
        Validate.notNull(worldGameMode, "The world game mode object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutRespawn 构造函数, 参数 int, EnumDifficulty, WorldType, EnumGamemode
            // 进行反射实例发送
            Object nmsType = MinecraftReflection.worldTypeGetByType(worldType.getName());
            Object nmsDifficulty = enumDifficultyGetByIdMethod.invoke(null, worldDifficulty.getId());
            Object nmsGameMode = MinecraftReflection.enumGamemodeGetById(worldGameMode.getValue());
            return packetPlayOutRespawnConstructor.invoke(worldDimensionId.get(), nmsDifficulty, nmsType, nmsGameMode);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutRespawn 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 4 个的话就是有此方式
                // 这个字段对应 int, EnumDifficulty, WorldType, EnumGamemode 属性
                Object packet = packetPlayOutRespawnVoidConstructor.invoke();
                Object nmsType = MinecraftReflection.worldTypeGetByType(worldType.getName());
                Object nmsDifficulty = enumDifficultyGetByIdMethod.invoke(null, worldDifficulty.getId());
                Object nmsGameMode = MinecraftReflection.enumGamemodeGetById(worldGameMode.getValue());
                Object[] values = { worldDimensionId.get(), nmsDifficulty, nmsType, nmsGameMode };
                return setFieldAccessibleAndValueGet(4, CLASS_PACKETPLAYOUTRESPAWN, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }

    /**
     * <h1>WorldDimension</h1>
     * 世界维度类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum WorldDimension {

        /**
         * 世界维度类型: 主世界
         */
        OVERWORLD(0),
        /**
         * 世界维度类型: 地狱
         */
        NETHER(-1),
        /**
         * 世界维度类型: 末地
         */
        THE_END(1),;

        private final int id;

        /**
         * 世界维度类型类构造函数
         *
         * @param id Id
         */
        WorldDimension(int id) {

            this.id = id;
        }

        /**
         * 获取世界维度类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }

        /**
         * 获取指定世界对象对应的世界维度
         *
         * @param world 世界
         * @return WorldDimension
         */
        public static WorldDimension fromWorld(World world) {

            if(world == null)
                return OVERWORLD;

            switch (world.getEnvironment()) {
                case NORMAL:
                    return OVERWORLD;
                case NETHER:
                    return NETHER;
                case THE_END:
                    return THE_END;
                default:
                    return OVERWORLD;
            }
        }
    }

    /**
     * <h1>WorldDifficulty</h1>
     * 世界难度类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum WorldDifficulty {

        /**
         * 世界难度类型: 和平
         */
        PEACEFUL(0),
        /**
         * 世界难度类型: 简单
         */
        EASY(1),
        /**
         * 世界难度类型: 普通
         */
        NORMAL(2),
        /**
         * 世界难度类型: 困难
         */
        HARD(3),;

        private final int id;

        /**
         * 世界难度类型类构造函数
         *
         * @param id Id
         */
        WorldDifficulty(int id) {

            this.id = id;
        }

        /**
         * 获取世界难度类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }

        /**
         * 获取指定世界对象对应的世界难度
         *
         * @param world 世界
         * @return WorldDifficulty
         */
        public static WorldDifficulty fromWorld(World world) {

            if(world == null)
                return EASY;

            switch (world.getDifficulty()) {
                case PEACEFUL:
                    return PEACEFUL;
                case EASY:
                    return EASY;
                case NORMAL:
                    return NORMAL;
                case HARD:
                    return HARD;
                default:
                    return EASY;
            }
        }
    }

    /**
     * <h1>WorldType</h1>
     * 世界类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum WorldType {

        NORMAL("default"),
        FLAT("flat"),
        LARGE_BIOMES("largeBiomes"),
        AMPLIFIED("amplified"),
        DEBUG_ALL_BLOCK_STATES("debug_all_block_states"),
        CUSTOMIZED("customized"),
        NORMAL_1_1("default_1_1"),;

        private final String name;

        /**
         * 世界类型构造函数
         *
         * @param name 名字
         */
        WorldType(String name) {

            this.name = name;
        }

        /**
         * 获取世界类型的名字
         *
         * @return 名字
         */
        public String getName() {

            return name;
        }

        /**
         * 获取指定世界对象对应的世界类型
         *
         * @param world 世界
         * @return WorldType
         */
        public static WorldType fromWorld(World world) {

            if(world == null)
                return NORMAL;

            switch (world.getWorldType()) {
                case NORMAL:
                    return NORMAL;
                case AMPLIFIED:
                    return AMPLIFIED;
                case CUSTOMIZED:
                    return CUSTOMIZED;
                case FLAT:
                    return FLAT;
                case LARGE_BIOMES:
                    return LARGE_BIOMES;
                case VERSION_1_1:
                    return NORMAL_1_1;
                default:
                    return NORMAL;
            }
        }
    }
}
