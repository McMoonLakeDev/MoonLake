package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/21.
 */
public class PacketPlayOutRespawn extends PacketAbstract<PacketPlayOutRespawn> {

    private IntegerProperty worldDimensionId;
    private ObjectProperty<WorldDifficulty> worldDifficulty;
    private ObjectProperty<GameMode> worldGameMode;
    private ObjectProperty<WorldType> worldType;

    public PacketPlayOutRespawn(WorldDimension worldDimension, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this(worldDimension.getId(), worldDifficulty, worldGameMode, worldType);
    }

    public PacketPlayOutRespawn(int worldDimensionId, WorldDifficulty worldDifficulty, GameMode worldGameMode, WorldType worldType) {

        this.worldDimensionId = new SimpleIntegerProperty(worldDimensionId);
        this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
        this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
        this.worldType = new SimpleObjectProperty<>(worldType);
    }

    public PacketPlayOutRespawn(Entity entity) {

        try {

            Class<?> World = Reflect.PackageType.MINECRAFT_SERVER.getClass("World");
            Class<?> Entity = Reflect.PackageType.MINECRAFT_SERVER.getClass("Entity");
            Class<?> WorldType = Reflect.PackageType.MINECRAFT_SERVER.getClass("WorldType");
            Class<?> WorldProvider = Reflect.PackageType.MINECRAFT_SERVER.getClass("WorldProvider");
            Class<?> EnumDifficulty = Reflect.PackageType.MINECRAFT_SERVER.getClass("EnumDifficulty");
            Class<?> DimensionManager = Reflect.PackageType.MINECRAFT_SERVER.getClass("DimensionManager");
            Class<?> CraftEntity = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftEntity");

            Object NMSEntity = Reflect.getMethod(CraftEntity, "getHandle").invoke(entity);
            Object NMSWorld = Reflect.getMethod(Entity, "getWorld").invoke(NMSEntity);

            Field worldProvider = Reflect.getField(World, true, "worldProvider");
            Object instanceWorldProvider = worldProvider.get(NMSWorld);
            Object instanceWorldDifficulty = Reflect.getMethod(World, "getDifficulty").invoke(NMSWorld);
            Object NMSDimensionManager = Reflect.getMethod(WorldProvider, "getDimensionManager").invoke(instanceWorldProvider);

            int dimensionId = (int)Reflect.getMethod(DimensionManager, "getDimensionID").invoke(NMSDimensionManager);

            Object name = Reflect.getMethod(EnumDifficulty, "name").invoke(instanceWorldDifficulty);
            WorldDifficulty worldDifficulty = PacketPlayOutRespawn.WorldDifficulty.valueOf((String) name);

            Object instanceWorldType = Reflect.getMethod(World, "L").invoke(NMSWorld);
            String name1 = ((String)Reflect.getMethod(WorldType, "name").invoke(instanceWorldType)).toUpperCase();
            PacketPlayOutRespawn.WorldType worldType = PacketPlayOutRespawn.WorldType.valueOf(name1);
            GameMode worldGameMode = entity instanceof Player ? ((Player)entity).getGameMode() : GameMode.SURVIVAL;

            this.worldDimensionId = new SimpleIntegerProperty(dimensionId);
            this.worldDifficulty = new SimpleObjectProperty<>(worldDifficulty);
            this.worldGameMode = new SimpleObjectProperty<>(worldGameMode);
            this.worldType = new SimpleObjectProperty<>(worldType);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
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

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> WorldType = Reflect.PackageType.MINECRAFT_SERVER.getClass("WorldType");
            Class<?> EnumDifficulty = Reflect.PackageType.MINECRAFT_SERVER.getClass("EnumDifficulty");
            Class<?> EnumGamemode = Reflect.PackageType.MINECRAFT_SERVER.getClass("EnumGamemode");
            Class<?> PacketPlayOutRespawn = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutRespawn");

            Method getById = Reflect.getMethod(EnumDifficulty, "getById", Integer.class);
            Object instanceWorldDifficulty = getById.invoke(null, worldDifficulty.get().getId());

            String worldTypeFieldName = worldType.get().name().toUpperCase();
            Object instanceWorldType = Reflect.getField(WorldType, true, worldTypeFieldName).get(null);

            Method getById1 = Reflect.getMethod(EnumGamemode, "getById", Integer.class);
            Object instanceWorldGameMode = getById1.invoke(null, worldGameMode.get().getValue());

            Object ppor = Reflect.instantiateObject(PacketPlayOutRespawn, worldDimensionId.get(), instanceWorldDifficulty, instanceWorldType, instanceWorldGameMode);

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Player[] players = PacketManager.getPlayersfromNames(names);

            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppor);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
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
