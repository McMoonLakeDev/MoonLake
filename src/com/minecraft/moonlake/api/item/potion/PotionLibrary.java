package com.minecraft.moonlake.api.item.potion;

import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/12.
 */
public interface PotionLibrary {

    /**
     * 创建药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createPotion(PotionType type, PotionBase base);

    /**
     * 创建药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createPotion(PotionType type, PotionBase base, int amount);

    /**
     * 创建药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param base 药水基础效果
     * @param amount 物品栈属性
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createPotion(PotionType type, PotionBase base, int amount, String displayName);

    /**
     *
     * 创建普通药水物品栈 ItemStack 对象
      * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base);

    /**
     *
     * 创建普通药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base, int amount);

    /**
     *
     * 创建普通药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base, int amount, String displayName);

    /**
     *
     * 创建投掷药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createSplashPotion(PotionBase base);

    /**
     *
     * 创建投掷药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createSplashPotion(PotionBase base, int amount);

    /**
     *
     * 创建投掷药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createSplashPotion(PotionBase base, int amount, String displayName);

    /**
     *
     * 创建滞留药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base);

    /**
     *
     * 创建滞留药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base, int amount);

    /**
     *
     * 创建滞留药水物品栈 ItemStack 对象
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base, int amount, String displayName);
}
