package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.block.Block;

/**
 * Created by MoonLake on 2016/10/2.
 */
public interface NBTBlock {

    /**
     * 读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果读取方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    void readBlock(Block block, Object nbtTagCompound) throws NBTException;

    /**
     * 将指定 NBT 数据写入到方块
     *
     * @param block 方块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果写入方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void writeBlock(Block block, Object nbtTagCompound) throws NBTException;

    /**
     * 更新指定方块对象
     *
     * @param block 方块
     * @throws NBTException 如果更新方块时错误则抛出异常
     */
    void update(Block block) throws NBTException;

    /**
     * 获取指定 TileEntity 对象
     *
     * @param block 方块
     * @return TileEntity
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果获取时错误则抛出异常
     * @throws NBTException 如果方块不是 TileEntity 对象则抛出异常
     */
    Object getTileEntity(Block block) throws NBTException;
}
