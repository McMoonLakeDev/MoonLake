package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>NBTItemStack</h1>
 * NBT 物品栈支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTItemStack {

    /**
     * 将指定物品栈创建为 NMS 物品栈对象
     *
     * @param itemStack 物品栈
     * @return NMS ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Object createNMSItemStack(ItemStack itemStack) throws NBTException;

    /**
     * 将指定 NMS 物品栈对象创建为物品栈对象
     *
     * @param nmsItemStack NMS 物品栈
     * @return ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果 NMS 物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack createCraftItemStack(Object nmsItemStack) throws NBTException;

    /**
     * 将指定物品栈对象创建为物品栈对象
     *
     * @param itemStack 物品栈
     * @return ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack createCraftItemStack(ItemStack itemStack) throws NBTException;

    /**
     * 将指定物品栈设置 NBT 数据
     *
     * @param itemStack 物品栈
     * @param nbtTagCompound NBT 数据
     * @throws NBTException 如果设置错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void setTag(ItemStack itemStack, Object nbtTagCompound) throws NBTException;

    /**
     * 获取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return NBT 对象
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Object getTag(ItemStack itemStack) throws NBTException;
}
