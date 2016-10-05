package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by MoonLake on 2016/7/17.
 */
@SuppressWarnings("deprecation")
public class BlockManager extends MoonLakeManager {

    /**
     * 坠落方块无视的方块类型集合
     */
    private final static Set<Material> FALLING_BLOCK_IGNORE_SET;

    static {

        FALLING_BLOCK_IGNORE_SET = new HashSet<>();
        FALLING_BLOCK_IGNORE_SET.add(Material.AIR);
        FALLING_BLOCK_IGNORE_SET.add(Material.SIGN_POST);
        FALLING_BLOCK_IGNORE_SET.add(Material.CHEST);
        FALLING_BLOCK_IGNORE_SET.add(Material.STONE_PLATE);
        FALLING_BLOCK_IGNORE_SET.add(Material.WOOD_PLATE);
        FALLING_BLOCK_IGNORE_SET.add(Material.WALL_SIGN);
        FALLING_BLOCK_IGNORE_SET.add(Material.WALL_BANNER);
        FALLING_BLOCK_IGNORE_SET.add(Material.STANDING_BANNER);
        FALLING_BLOCK_IGNORE_SET.add(Material.CROPS);
        FALLING_BLOCK_IGNORE_SET.add(Material.LONG_GRASS);
        FALLING_BLOCK_IGNORE_SET.add(Material.SAPLING);
        FALLING_BLOCK_IGNORE_SET.add(Material.DEAD_BUSH);
        FALLING_BLOCK_IGNORE_SET.add(Material.RED_ROSE);
        FALLING_BLOCK_IGNORE_SET.add(Material.RED_MUSHROOM);
        FALLING_BLOCK_IGNORE_SET.add(Material.BROWN_MUSHROOM);
        FALLING_BLOCK_IGNORE_SET.add(Material.TORCH);
        FALLING_BLOCK_IGNORE_SET.add(Material.LADDER);
        FALLING_BLOCK_IGNORE_SET.add(Material.VINE);
        FALLING_BLOCK_IGNORE_SET.add(Material.DOUBLE_PLANT);
        FALLING_BLOCK_IGNORE_SET.add(Material.PORTAL);
        FALLING_BLOCK_IGNORE_SET.add(Material.CACTUS);
        FALLING_BLOCK_IGNORE_SET.add(Material.WATER);
        FALLING_BLOCK_IGNORE_SET.add(Material.STATIONARY_WATER);
        FALLING_BLOCK_IGNORE_SET.add(Material.LAVA);
        FALLING_BLOCK_IGNORE_SET.add(Material.STATIONARY_LAVA);
        FALLING_BLOCK_IGNORE_SET.add(Material.DOUBLE_STEP);
        FALLING_BLOCK_IGNORE_SET.add(Material.STEP);
    }

    private BlockManager() {

    }

    /**
     * 获取指定位置的半径内的方块
     *
     * @param location 位置
     * @param radius 半径
     * @return 方块集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<Block> getBlocksInRadius(Location location, int radius) {

        return getBlocksInRadius(location, radius, true);
    }

    /**
     * 获取指定位置的半径内的方块
     *
     * @param location 位置
     * @param radius 半径
     * @param hollow 是否空洞
     * @return 方块集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow) {

        return getBlocksInRadius(location, radius, hollow, new HashSet<>(Arrays.asList(Material.AIR)));
    }

    /**
     * 获取指定位置的半径内的方块
     *
     * @param location 位置
     * @param radius 半径
     * @param hollow 是否空洞
     * @param ignoreBlock 无视的方块
     * @return 方块集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow, Set<Material> ignoreBlock) {

        Validate.notNull(location, "The localtion object is null.");

        List<Block> blockList = new ArrayList<>();
        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();

        for(int x = bX - radius; x <= bX + radius; x++) {

            for(int y = bY - radius; y < bY + radius; y++) {

                for(int z = bZ - radius; z < bZ + radius; z++) {

                    double distance = (bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z);

                    if((distance < radius * radius) && ((!hollow) || (distance >= (radius - 1) * (radius - 1)))) {

                        Block block = new Location(location.getWorld(), x, y, z).getBlock();

                        if(block != null && block.getType() != Material.AIR && block.getType() != Material.BARRIER && block.getType().isSolid() && !ignoreBlock.contains(block.getType())) {

                            blockList.add(block);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    /**
     * 获取指定位置的半径内的坠落方块类型方块
     *
     * @param location 位置
     * @param radius 半径
     * @return 坠落方块类型方块集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<Block> getFallingBlocksInRadius(Location location, int radius) {

        return getFallingBlocksInRadius(location, radius, true);
    }

    /**
     * 获取指定位置的半径内的坠落方块类型方块
     *
     * @param location 位置
     * @param radius 半径
     * @param hollow 是否空洞
     * @return 坠落方块类型方块集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<Block> getFallingBlocksInRadius(Location location, int radius, boolean hollow) {

        Validate.notNull(location, "The localtion object is null.");

        List<Block> blockList = new ArrayList<>();
        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();

        for(int x = bX - radius; x <= bX + radius; x++) {

            for(int y = bY - radius; y < bY + radius; y++) {

                for(int z = bZ - radius; z < bZ + radius; z++) {

                    double distance = (bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z);

                    if((distance < radius * radius) && ((!hollow) || (distance >= (radius - 1) * (radius - 1)))) {

                        Block block = new Location(location.getWorld(), x, y, z).getBlock();

                        if(block != null && block.getType() != Material.AIR && block.getType() != Material.BARRIER && block.getType().isSolid() && !FALLING_BLOCK_IGNORE_SET.contains(block.getType()) && block.getRelative(BlockFace.UP).getType() == Material.AIR) {

                            blockList.add(block);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    /**
     * 交互指定位置的箱子方块
     *
     * @param location 箱子方块位置
     * @param action 交互 true 则打开 else 关闭
     * @throws IllegalArgumentException 如果箱子方块位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果箱子方块类型不为 {@code Material.CHEST} 或 {@code Material.ENDER_CHEST} 则抛出异常
     */
    public static void actionChest(Location location, boolean action) {

        Validate.notNull(location, "The block location object is null.");

        actionChest(location.getBlock(), action);
    }

    /**
     * 交互指定位置的箱子方块
     *
     * @param block 箱子方块
     * @param action 交互 true 则打开 else 关闭
     * @throws IllegalArgumentException 如果箱子方块对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果箱子方块类型不为 {@code Material.CHEST} 或 {@code Material.ENDER_CHEST} 则抛出异常
     */
    public static void actionChest(Block block, boolean action) {

        Validate.notNull(block, "The block object is null.");
        Validate.isTrue(block.getType() == Material.CHEST || block.getType() == Material.ENDER_CHEST, "The block type not is chest or ender chest.");

        Location location = block.getLocation();

        try {

            Class<?> WORLD = Reflect.PackageType.MINECRAFT_SERVER.getClass("World");
            Class<?> CRAFT_WORLD = Reflect.PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            Class<?> BLOCK = Reflect.PackageType.MINECRAFT_SERVER.getClass("Block");
            Class<?> BLOCK_POSITION = Reflect.PackageType.MINECRAFT_SERVER.getClass("BlockPosition");

            Object nmsWorld = Reflect.getMethod(CRAFT_WORLD, "getHandle").invoke(block.getWorld());
            Object nmsBlockPosition = Reflect.instantiateObject(BLOCK_POSITION, location.getX(), location.getY(), location.getZ());

            String playBlockActionMethodName = "getBlock";
            int version = getMain().getReleaseNumber();

            if(version <= 8) {

                playBlockActionMethodName = "w";
            }
            if(block.getType() == Material.CHEST) {

                Class<?> TILE_ENTITY_CHEST = Reflect.PackageType.MINECRAFT_SERVER.getClass("TileEntityChest");

                Method playBlockAction = Reflect.getMethod(WORLD, "playBlockAction", BLOCK_POSITION, BLOCK, int.class, int.class);
                Object nmsTileEntityChest = Reflect.getMethod(WORLD, "getTileEntity", BLOCK_POSITION).invoke(nmsWorld, nmsBlockPosition);
                Object nmsBlock = Reflect.getMethod(TILE_ENTITY_CHEST, playBlockActionMethodName).invoke(nmsTileEntityChest);

                playBlockAction.invoke(nmsWorld, nmsBlockPosition, nmsBlock, 1, action ? 1 : 0);
            }
            else if(block.getType() == Material.ENDER_CHEST) {

                Class<?> TILE_ENTITY_ENDER_CHEST = Reflect.PackageType.MINECRAFT_SERVER.getClass("TileEntityEnderChest");

                Method playBlockAction = Reflect.getMethod(WORLD, "playBlockAction", BLOCK_POSITION, BLOCK, int.class, int.class);
                Object nmsTileEntityChest = Reflect.getMethod(WORLD, "getTileEntity", BLOCK_POSITION).invoke(nmsWorld, nmsBlockPosition);
                Object nmsBlock = Reflect.getMethod(TILE_ENTITY_ENDER_CHEST, playBlockActionMethodName).invoke(nmsTileEntityChest);

                playBlockAction.invoke(nmsWorld, nmsBlockPosition, nmsBlock, 1, action ? 1 : 0);
            }
        }
        catch (Exception e) {

            MoonLakePlugin.getInstances().getMLogger().warn("交互位置为 '" + location + "' 的箱子方块时异常: " + e.getMessage());
        }
    }
}
