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
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutRespawn</h1>
 * 数据包输出重生（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutRespawn extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTRESPAWN;
    private final static Class<?> CLASS_WORLDTYPE;
    private final static Class<?> CLASS_ENUMDIFFICULTY;
    private final static Class<?> CLASS_ENUMGAMEMODE;
    private final static Method METHOD_GETBYID0;
    private final static Method METHOD_GETBYID1;
    private final static Method METHOD_GETTYPE;

    static {

        try {

            CLASS_PACKETPLAYOUTRESPAWN = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutRespawn");
            CLASS_WORLDTYPE = PackageType.MINECRAFT_SERVER.getClass("WorldType");
            CLASS_ENUMDIFFICULTY = PackageType.MINECRAFT_SERVER.getClass("EnumDifficulty");
            CLASS_ENUMGAMEMODE = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") || getServerVersionNumber() >= 10 ? "EnumGamemode" : "WorldSettings$EnumGamemode");
            METHOD_GETBYID0 = getMethod(CLASS_ENUMDIFFICULTY, "getById", int.class);
            METHOD_GETBYID1 = getMethod(CLASS_ENUMGAMEMODE, "getById", int.class);
            METHOD_GETTYPE = getMethod(CLASS_WORLDTYPE, "getType", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out respawn reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty worldDimensionId;
    private ObjectProperty<WorldDifficulty> worldDifficulty;
    private ObjectProperty<GameMode> worldGameMode;
    private ObjectProperty<WorldType> worldType;

    public PacketPlayOutRespawn() {

        this(WorldDimension.OVERWORLD, null, null, null);
    }

    public PacketPlayOutRespawn(World world, GameMode gameMode) {

        this(WorldDimension.fromWorld(world), WorldDifficulty.fromWorld(world), gameMode, WorldType.fromWorld(world));
    }

    public PacketPlayOutRespawn(WorldDimension worldDimension, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this.worldDimensionId = new SimpleIntegerProperty(worldDimension.getId());
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    public IntegerProperty worldDimensionIdProperty() {

        return worldDimensionId;
    }

    public ObjectProperty<WorldDifficulty> worldDifficultyProperty() {

        return worldDifficulty;
    }

    public ObjectProperty<GameMode> worldGameModeProperty() {

        return worldGameMode;
    }

    public ObjectProperty<WorldType> worldTypeProperty() {

        return worldType;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean sendPacket(Player... players) throws Exception {

        WorldType worldType = worldTypeProperty().get();
        WorldDifficulty worldDifficulty = worldDifficultyProperty().get();
        GameMode worldGameMode = worldGameModeProperty().get();
        Validate.notNull(worldType, "The world type object is null.");
        Validate.notNull(worldDifficulty, "The world difficulty object is null");
        Validate.notNull(worldGameMode, "The world game mode object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutRespawn 构造函数, 参数 int, EnumDifficulty, WorldType, EnumGamemode
            // 进行反射实例发送
            Object nmsType = METHOD_GETTYPE.invoke(null, worldType.getName());
            Object nmsDifficulty = METHOD_GETBYID0.invoke(null, worldDifficulty.getId());
            Object nmsGameMode = METHOD_GETBYID1.invoke(null, worldGameMode.getValue());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTRESPAWN, worldDimensionId.get(), nmsDifficulty, nmsType, nmsGameMode);
            sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutRespawn 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 4 个的话就是有此方式
                // 这个字段对应 int, EnumDifficulty, WorldType, EnumGamemode 属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTRESPAWN);
                Object nmsType = METHOD_GETTYPE.invoke(null, worldType.getName());
                Object nmsDifficulty = METHOD_GETBYID0.invoke(null, worldDifficulty.getId());
                Object nmsGameMode = METHOD_GETBYID1.invoke(null, worldGameMode.getValue());
                Object[] values = { worldDimensionId.get(), nmsDifficulty, nmsType, nmsGameMode };
                setFieldAccessibleAndValueSend(players, 4, CLASS_PACKETPLAYOUTRESPAWN, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
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
