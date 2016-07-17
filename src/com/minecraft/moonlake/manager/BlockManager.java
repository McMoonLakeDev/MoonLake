package com.minecraft.moonlake.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.*;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class BlockManager extends MoonLakeManager {

    /**
     * Falling Block Type Ignore List.
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

    /**
     * 获取指定位置的半径内的方块
     *
     * @param location 位置
     * @param radius 半径
     * @return 方块集合
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
     */
    public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow, Set<Material> ignoreBlock) {

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
     */
    public static List<Block> getFallingBlocksInRadius(Location location, int radius, boolean hollow) {

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
}
