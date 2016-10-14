package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.lang.reflect.Method;
import java.util.*;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>BlockManager</h1>
 * 方块管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
@SuppressWarnings("deprecation")
public class BlockManager extends MoonLakeManager {

    /**
     * 坠落方块无视的方块类型集合
     */
    private final static Set<Material> FALLING_BLOCK_IGNORE_SET;

    private final static Class<?> CLASS_WORLD;
    private final static Class<?> CLASS_CRAFTWORLD;
    private final static Class<?> CLASS_BLOCK;
    private final static Class<?> CLASS_BLOCKPOSITION;
    private final static Class<?> CLASS_TILEENTITYCHEST;
    private final static Class<?> CLASS_TILEENTITYENDERCHEST;
    private final static Method METHOD_GETHANDLE;
    private final static Method METHOD_PLAYERBLOCKACTION;
    private final static Method METHOD_GETTILEENTITY;
    private final static Method METHOD_GETBLOCKCHEST;
    private final static Method METHOD_GETBLOCKENDERCHEST;

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

        try {

            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            CLASS_BLOCK = PackageType.MINECRAFT_SERVER.getClass("Block");
            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_TILEENTITYCHEST = PackageType.MINECRAFT_SERVER.getClass("TileEntityChest");
            CLASS_TILEENTITYENDERCHEST = PackageType.MINECRAFT_SERVER.getClass("TileEntityEnderChest");
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_PLAYERBLOCKACTION = getMethod(CLASS_WORLD, "playBlockAction", CLASS_BLOCKPOSITION, CLASS_BLOCK, int.class, int.class);
            METHOD_GETTILEENTITY = getMethod(CLASS_WORLD, "getTileEntity", CLASS_BLOCKPOSITION);
            METHOD_GETBLOCKCHEST = getMethod(CLASS_TILEENTITYCHEST, getServerVersionNumber() <= 8 ? "w" : "getBlock");
            METHOD_GETBLOCKENDERCHEST = getMethod(CLASS_TILEENTITYENDERCHEST, getServerVersionNumber() <= 8 ? "w" : "getBlock");
        }
        catch (Exception e) {

            throw new IllegalBukkitVersionException("The block manager reflect raw exception.", e);
        }
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

            Object nmsWorld = METHOD_GETHANDLE.invoke(block.getWorld());
            Object nmsBlockPosition = instantiateObject(CLASS_BLOCKPOSITION, location.getX(), location.getY(), location.getZ());
            Object nmsTileEntityChest = METHOD_GETTILEENTITY.invoke(nmsWorld, nmsBlockPosition);
            Object nmsBlock = block.getType() == Material.CHEST  ? METHOD_GETBLOCKCHEST.invoke(nmsTileEntityChest) : METHOD_GETBLOCKENDERCHEST.invoke(nmsTileEntityChest);

            METHOD_PLAYERBLOCKACTION.invoke(nmsWorld, nmsBlockPosition, nmsBlock, 1, action ? 1 : 0);
        }
        catch (Exception e) {

            throw new MoonLakeException("The action block chest exception.", e);
        }
    }
}
