package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/13.
 */
public interface AttributeLibrary {

    /**
     * 设置物品栈是否无法破坏
     *
     * @param itemStack 物品栈
     * @param unbreakable 是否无法破坏
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void setUnbreakable(ItemStack itemStack, boolean unbreakable);

    /**
     * 获取物品栈是否无法破坏
     *
     * @param itemStack 物品栈
     * @return true 则物品栈无法破坏
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack);

    ////////////////////////////////
    /// item stack attribute util

    // void setAttribute(ItemStack itemStack);
}
