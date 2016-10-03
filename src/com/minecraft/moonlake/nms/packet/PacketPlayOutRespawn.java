package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutRespawn extends PacketAbstract<PacketPlayOutRespawn> {

    private final static Class<?> CLASS_PACKETPLAYOUTRESPAWN;
    private final static Class<?> CLASS_WORLDTYPE;
    private final static Class<?> CLASS_ENUMDIFFICULTY;
    private final static Class<?> CLASS_ENUMGAMEMODE;
    private final static Class<?> CLASS_WORLD;
    private final static Class<?> CLASS_ENTITY;
    private final static Class<?> CLASS_WORLDPROVIDER;
    private final static Class<?> CLASS_DIMENSIONMANAGER;
    private final static Class<?> CLASS_CRAFTENTITY;
    private final static Method METHOD_GETBYID0;
    private final static Method METHOD_GETBYID1;

    static {

        try {

            CLASS_PACKETPLAYOUTRESPAWN = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutRespawn");
            CLASS_WORLDTYPE = PackageType.MINECRAFT_SERVER.getClass("WorldType");
            CLASS_ENUMDIFFICULTY = PackageType.MINECRAFT_SERVER.getClass("EnumDifficulty");
            CLASS_ENUMGAMEMODE = PackageType.MINECRAFT_SERVER.getClass("EnumGamemode");
            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            CLASS_WORLDPROVIDER = PackageType.MINECRAFT_SERVER.getClass("WorldProvider");
            CLASS_ENTITY = PackageType.MINECRAFT_SERVER.getClass("Entity");
            CLASS_DIMENSIONMANAGER = PackageType.MINECRAFT_SERVER.getClass("DimensionManager");
            CLASS_CRAFTENTITY = PackageType.MINECRAFT_SERVER.getClass("CraftEntity");
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

    @Deprecated
    public PacketPlayOutRespawn() {

        this(WorldDimension.OVERWORLD, WorldDifficulty.PEACEFUL, GameMode.SURVIVAL, WorldType.NORMAL);
    }

    public PacketPlayOutRespawn(WorldDimension worldDimension, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this(worldDimension.getId(), worldDifficulty, worldGameMode, worldType);
    }

    public PacketPlayOutRespawn(int worldDimensionId, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this.worldDimensionId = new SimpleIntegerProperty(worldDimensionId);
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    public IntegerProperty getWorldDimensionId() {

        return worldDimensionId;
    }

    public ObjectProperty<WorldDifficulty> getWorldDifficulty() {

        return worldDifficulty;
    }

    public ObjectProperty<GameMode> getWorldGameMode() {

        return worldGameMode;
    }

    public ObjectProperty<WorldType> getWorldType() {

        return worldType;
    }

    @Override
    public void send(Player... players) throws PacketException {

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
     * 世界维度类型
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

        WorldDimension(int id) {

            this.id = id;
        }

        public int getId() {

            return id;
        }
    }

    /**
     * 世界难度类型
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

        WorldDifficulty(int id) {

            this.id = id;
        }

        public int getId() {

            return id;
        }
    }

    /**
     * 世界类型
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
