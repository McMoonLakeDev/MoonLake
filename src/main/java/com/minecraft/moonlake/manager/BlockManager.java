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
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.api.packet.wrapper.BlockPosition;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import java.util.*;

/**
 * <h1>BlockManager</h1>
 * 方块管理实现类
 *
 * @version 1.0.3
 * @author Month_Light
 */
public class BlockManager extends MoonLakeManager {

    /**
     * 坠落方块无视的方块类型集合
     *
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    private final static Set<Material> FALLING_BLOCK_IGNORE_SET;
    private static volatile MethodAccessor worldPlayBlockActionMethod;
    private static volatile MethodAccessor tileEntityGetBlockMethod;

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
     * 方块管理实现类构造函数
     */
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

        return getBlocksInRadius(location, radius, hollow, new HashSet<>(Collections.singletonList(Material.AIR)));
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
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
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
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
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

    private static MethodAccessor getWorldPlayBlockActionMethod() {
        if(worldPlayBlockActionMethod == null) {
            Class<?> worldClass = MinecraftReflection.getMinecraftWorldClass();
            Class<?> blockClass = MinecraftReflection.getMinecraftBlockClass();
            Class<?> blockPositionClass = MinecraftReflection.getMinecraftBlockPositionClass();
            worldPlayBlockActionMethod = Accessors.getMethodAccessorOrNull(worldClass, "playBlockAction", blockPositionClass, blockClass, int.class, int.class);
        }
        return worldPlayBlockActionMethod;
    }

    private static MethodAccessor getTileEntityGetBlockMethod() {
        if(tileEntityGetBlockMethod == null) {
            Class<?> blockClass = MinecraftReflection.getMinecraftBlockClass();
            Class<?> tileEntityClass = MinecraftReflection.getMinecraftTileEntityClass();
            tileEntityGetBlockMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(tileEntityClass, true).getMethodByParameters("getBlock", blockClass, new Class[] {}));
        }
        return tileEntityGetBlockMethod;
    }

    /**
     * 交互指定位置的箱子方块
     *
     * @param location 箱子方块位置
     * @param action 交互 true 则打开 else 关闭
     * @throws IllegalArgumentException 如果箱子方块位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果箱子方块类型不为 {@code Material.CHEST | TRAPPED_CHEST | ENDER_CHEST} 则抛出异常
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
     * @throws IllegalArgumentException 如果箱子方块类型不为 {@code Material.CHEST | TRAPPED_CHEST | ENDER_CHEST} 则抛出异常
     */
    public static void actionChest(Block block, boolean action) {

        Validate.notNull(block, "The block object is null.");
        Validate.isTrue(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.ENDER_CHEST, "The block type not is chest or ender chest.");

        Location location = block.getLocation();

        try {

            Object nmsWorld = MinecraftReflection.getWorldServer(block.getWorld());
            Object nmsBlockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()).asNMS();
            Object nmsTileEntityChest = MinecraftReflection.getTileEntity(nmsWorld, nmsBlockPosition);
            Object nmsBlock = getTileEntityGetBlockMethod().invoke(nmsTileEntityChest);

            getWorldPlayBlockActionMethod().invoke(nmsWorld, nmsBlockPosition, nmsBlock, 1, action ? 1 : 0);
        }
        catch (Exception e) {

            throw new MoonLakeException("The action block chest exception.", e);
        }
    }

    /**
     * 获取指定方块是否为方块实体 (详情: <a href='http://minecraft-zh.gamepedia.com/%E6%96%B9%E5%9D%97%E5%AE%9E%E4%BD%93%E5%80%BC' target='_blank'>方块实体</a>)
     *
     * @param block 方块
     * @return 是否为方块实体
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    public static boolean isTileEntity(Block block) {
        Validate.notNull(block, "The block object is null.");
        return block.getType() != Material.AIR && isTileEntity(block.getLocation());
    }

    /**
     * 获取指定位置的方块是否为方块实体 (详情: <a href='http://minecraft-zh.gamepedia.com/%E6%96%B9%E5%9D%97%E5%AE%9E%E4%BD%93%E5%80%BC' target='_blank'>方块实体</a>)
     *
     * @param location 位置
     * @return 位置的方块是否为方块实体
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static boolean isTileEntity(Location location) {
        return MinecraftReflection.getTileEntity(location) != null;
    }

    /**
     * 获取指定位置指定类型的方块实体对象 (详情: <a href='http://minecraft-zh.gamepedia.com/%E6%96%B9%E5%9D%97%E5%AE%9E%E4%BD%93%E5%80%BC' target='_blank'>方块实体</a>)
     *
     * @param location 位置
     * @param clazz 方块实体类型
     * @param <T> 方块实体类型
     * @return 对应类型的方块实体对象 | null
     */
    public static <T extends BlockState> T getTileEntity(Location location, Class<T> clazz) {
        Validate.notNull(location, "The location object is null.");
        Validate.notNull(clazz, "The block state class object is null.");
        BlockState blockState = location.getBlock().getState();
        return blockState != null && clazz.isInstance(blockState) ? clazz.cast(blockState) : null;
    }
}
