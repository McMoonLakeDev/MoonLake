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
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutRespawn</h1>
 * 数据包输出重生（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutRespawn}
 */
@Deprecated
public class PacketPlayOutRespawn extends PacketAbstract<PacketPlayOutRespawn> {

    private final static Class<?> CLASS_PACKETPLAYOUTRESPAWN;
    private final static Class<?> CLASS_WORLDTYPE;
    private final static Class<?> CLASS_ENUMDIFFICULTY;
    private final static Class<?> CLASS_ENUMGAMEMODE;
    private final static Method METHOD_GETBYID0;
    private final static Method METHOD_GETBYID1;

    static {

        try {

            String enumGamemode = getServerVersion().equals("v1_8_R1") || getServerVersionNumber() >= 10 ? "EnumGamemode" : "WorldSettings$EnumGamemode";

            CLASS_PACKETPLAYOUTRESPAWN = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutRespawn");
            CLASS_WORLDTYPE = PackageType.MINECRAFT_SERVER.getClass("WorldType");
            CLASS_ENUMDIFFICULTY = PackageType.MINECRAFT_SERVER.getClass("EnumDifficulty");
            CLASS_ENUMGAMEMODE = PackageType.MINECRAFT_SERVER.getClass(enumGamemode);
            METHOD_GETBYID0 = getMethod(CLASS_ENUMDIFFICULTY, "getById", int.class);
            METHOD_GETBYID1 = getMethod(CLASS_ENUMGAMEMODE, "getById", int.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out respawn reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty worldDimensionId;
    private ObjectProperty<WorldDifficulty> worldDifficulty;
    private ObjectProperty<GameMode> worldGameMode;
    private ObjectProperty<WorldType> worldType;

    /**
     * 数据包输出重生类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutRespawn(WorldDimension, WorldDifficulty, GameMode, WorldType)}
     * @see #PacketPlayOutRespawn(WorldDimension, WorldDifficulty, GameMode, WorldType)
     */
    @Deprecated
    public PacketPlayOutRespawn() {

        this(WorldDimension.OVERWORLD, WorldDifficulty.PEACEFUL, GameMode.SURVIVAL, WorldType.NORMAL);
    }

    /**
     * 数据包输出重生类构造函数
     *
     * @param world 世界
     * @param gameMode 游戏模式
     */
    public PacketPlayOutRespawn(World world, GameMode gameMode) {

        WorldDimension worldDimension;
        WorldDifficulty worldDifficulty;
        WorldType worldType;

        // dimension
        switch (world.getEnvironment()) {
            case NORMAL:
                worldDimension = WorldDimension.OVERWORLD; break;
            case NETHER:
                worldDimension = WorldDimension.NETHER; break;
            case THE_END:
                worldDimension = WorldDimension.THE_END; break;
            default:
                worldDimension = WorldDimension.OVERWORLD; break;
        }

        // difficulty
        switch (world.getDifficulty()) {
            case PEACEFUL:
                worldDifficulty = WorldDifficulty.PEACEFUL; break;
            case EASY:
                worldDifficulty = WorldDifficulty.EASY; break;
            case NORMAL:
                worldDifficulty = WorldDifficulty.NORMAL; break;
            case HARD:
                worldDifficulty = WorldDifficulty.HARD; break;
            default:
                worldDifficulty = WorldDifficulty.EASY; break;
        }

        // worldtype
        switch (world.getWorldType()) {
            case NORMAL:
                worldType = WorldType.NORMAL; break;
            case AMPLIFIED:
                worldType = WorldType.AMPLIFIED; break;
            case CUSTOMIZED:
                worldType = WorldType.CUSTOMIZED; break;
            case FLAT:
                worldType = WorldType.FLAT; break;
            case LARGE_BIOMES:
                worldType = WorldType.LARGE_BIOMES; break;
            case VERSION_1_1:
                worldType = WorldType.NORMAL_1_1; break;
            default:
                worldType = WorldType.NORMAL; break;
        }

        this.worldDimensionId = new SimpleIntegerProperty(worldDimension.getId());
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(gameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    /**
     * 数据包输出重生类构造函数
     *
     * @param worldDimension 世界维度
     * @param worldDifficulty 世界难度
     * @param worldGameMode 世界游戏模式
     * @param worldType 世界类型
     */
    public PacketPlayOutRespawn(WorldDimension worldDimension, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this(worldDimension.getId(), worldDifficulty, worldGameMode, worldType);
    }

    /**
     * 数据包输出重生类构造函数
     *
     * @param worldDimensionId 世界维度 Id
     * @param worldDifficulty 世界难度
     * @param worldGameMode 世界游戏模式
     * @param worldType 世界类型
     */
    @Deprecated
    public PacketPlayOutRespawn(int worldDimensionId, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this.worldDimensionId = new SimpleIntegerProperty(worldDimensionId);
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    /**
     * 获取此数据包输出重生的世界维度 Id
     *
     * @return 世界维度 Id
     */
    public IntegerProperty getWorldDimensionId() {

        return worldDimensionId;
    }

    /**
     * 获取此数据包输出重生的世界难度类型
     *
     * @return 世界难度类型
     */
    public ObjectProperty<WorldDifficulty> getWorldDifficulty() {

        return worldDifficulty;
    }

    /**
     * 获取此数据包输出重生的世界游戏模式类型
     *
     * @return 世界游戏模式类型
     */
    public ObjectProperty<GameMode> getWorldGameMode() {

        return worldGameMode;
    }

    /**
     * 获取此数据包输出重生的世界类型
     *
     * @return 世界类型
     */
    public ObjectProperty<WorldType> getWorldType() {

        return worldType;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Field FIELD_WORLDTYPE_TARGET = getField(CLASS_WORLDTYPE, true, worldType.get().name().toUpperCase());

            Object nmsWorldDifficulty = METHOD_GETBYID0.invoke(null, worldDifficulty.get().getId());
            Object nmsGamemode = METHOD_GETBYID1.invoke(null, worldGameMode.get().getValue());
            Object nmsWorldType = FIELD_WORLDTYPE_TARGET.get(null);

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTRESPAWN, worldDimensionId.get(), nmsWorldDifficulty, nmsWorldType, nmsGamemode);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out respawn send exception.", e);
        }
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
    }

    /**
     * <h1>WorldType</h1>
     * 世界类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum WorldType {

        NORMAL,
        FLAT,
        LARGE_BIOMES,
        AMPLIFIED,
        DEBUG_ALL_BLOCK_STATES,
        CUSTOMIZED,
        NORMAL_1_1,;
    }
}
