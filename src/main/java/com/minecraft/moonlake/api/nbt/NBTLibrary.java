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
 
 
package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft NBT Options Library</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>目标支持的 NBT 操作功能:</h1>
 *     <ul>
 *         <li>读写实体 {@link NBTEntity}</li>
 *         <li>读写方块 {@link NBTBlock}</li>
 *         <li>读写区块 {@link NBTChunk}</li>
 *         <li>读写物品栈 {@link NBTItemStack}</li>
 *     </ul>
 *     <p>还有更多正在开发中...</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简单的调用使用方法:</h1>
 *     <h2>写物品栈 NBT 数据:</h2>
 *     <p>ItemStack itemStack = new ItemStack(Material.IRON_SWORD);</p>
 *     <p>NBTCompound nbt = NBTFactory.newCompound();</p>
 *     <p>nbt.put("Damage", 100);</p>
 *     <p>nbt.put("Amount", 10);</p>
 *     <p>NBTFactory.get().write(itemStack, nbt);</p>
 *     <h2>读实体 NBT 数据:</h2>
 *     <p>Player player = Bukkit.getServer().getPlayer("Notch");</p>
 *     <p>NBTCompound nbt = NBTFactory.get().read(player);</p>
 *     <p>nbt.put("Fire", 10 * 20);</p>
 *     <p>nbt.put("OnGround", 0);</p>
 *     <p>NBTFactory.get().write(player, nbt);</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 * @see NBTEntity
 * @see NBTBlock
 * @see NBTChunk
 * @see NBTItemStack
 * @see NBTCompound
 * @see NBTList
 * @see NBTFactory
 */
public interface NBTLibrary {

    /**
     * 读取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return NBTCompound
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    NBTCompound read(ItemStack itemStack) throws NBTException;

    /**
     * 安全读取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return NBTCompound
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    NBTCompound readSafe(ItemStack itemStack) throws NBTException;

    /**
     * 安全读取指定物品栈的 NBT 数据并给予消费者
     *
     * @param itemStack 物品栈
     * @param consumer 消费者
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    void readSafeConsumer(ItemStack itemStack, Consumer<NBTCompound> consumer) throws NBTException;

    /**
     * 将指定 NBT 数据写入到物品栈
     *
     * @param itemStack 物品栈
     * @param nbt NBT 数据
     * @throws NBTException 如果写入物品栈时错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void write(ItemStack itemStack, NBTCompound nbt) throws NBTException;

    /**
     * 读取指定实体的 NBT 数据
     *
     * @param entity 实体
     * @return NBTCompound
     * @throws NBTException 如果读取实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    NBTCompound read(Entity entity) throws NBTException;

    /**
     * 安全读取指定实体的 NBT 数据
     *
     * @param entity 实体
     * @return NBTCompound
     * @throws NBTException 如果读取实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    NBTCompound readSafe(Entity entity) throws NBTException;

    /**
     * 安全读取指定实体的 NBT 数据并给予消费者
     *
     * @param entity 实体
     * @param consumer 消费者
     * @throws NBTException 如果读取实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    void readSafeConsumer(Entity entity, Consumer<NBTCompound> consumer) throws NBTException;

    /**
     * 将指定 NBT 数据写入到实体
     *
     * @param entity 实体
     * @param nbt NBT 数据
     * @throws NBTException 如果写入实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void write(Entity entity, NBTCompound nbt) throws NBTException;

    /**
     * 读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @return NBTCompound
     * @throws NBTException 如果读取方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    NBTCompound read(Block block) throws NBTException;

    /**
     * 安全读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @return NBTCompound
     * @throws NBTException 如果读取方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    NBTCompound readSafe(Block block) throws NBTException;

    /**
     * 安全读取指定方块的 NBT 数据并给予消费者
     *
     * @param block 方块
     * @param consumer 消费者
     * @throws NBTException 如果读取方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    void readSafeConsumer(Block block, Consumer<NBTCompound> consumer) throws NBTException;

    /**
     * 将指定 NBT 数据写入到方块
     *
     * @param block 方块
     * @param nbt NBT 数据
     * @throws NBTException 如果写入方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void write(Block block, NBTCompound nbt) throws NBTException;

    /**
     * 读取指定区块的 NBT 数据
     *
     * @param chunk 区块
     * @return NBTCompound
     * @throws NBTException 如果读取区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     */
    NBTCompound read(Chunk chunk) throws NBTException;

    /**
     * 安全读取指定区块的 NBT 数据
     *
     * @param chunk 区块
     * @return NBTCompound
     * @throws NBTException 如果读取区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     */
    NBTCompound readSafe(Chunk chunk) throws NBTException;

    /**
     * 安全读取指定区块的 NBT 数据并给予消费者
     *
     * @param chunk 区块
     * @param consumer 消费者
     * @throws NBTException 如果读取区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    void readSafeConsumer(Chunk chunk, Consumer<NBTCompound> consumer) throws NBTException;

    /**
     * 将指定 NBT 数据写入到区块
     *
     * @param chunk 区块
     * @param nbt NBT 数据
     * @throws NBTException 如果写入区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void write(Chunk chunk, NBTCompound nbt) throws NBTException;

    /**
     * 将指定 NBT 数据生成为实体
     *
     * @param nbt NBT 数据
     * @param world 目标世界
     * @return Entity
     * @throws NBTException 如果生成实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象或目标世界对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v1.9-a6 删除.
     */
    @Deprecated
    Entity spawnEntity(NBTCompound nbt, World world) throws NBTException;
}
