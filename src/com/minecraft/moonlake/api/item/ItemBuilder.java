package com.minecraft.moonlake.api.item;

import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

/**
 * Created by MoonLake on 2016/9/13.
 */
public interface ItemBuilder {

    /**
     * 构建并获取物品栈对象
     *
     * @return ItemStack
     */
    ItemStack build();

    /**
     * 构建并获取物品栈对象
     *
     * @param safeObj 安全对象
     * @return ItemStack
     */
    ItemStack build(boolean safeObj);

    /**
     * 设置物品栈的显示名称
     *
     * @param displayName 显示名称
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemBuilder setDisplayName(String displayName);

    /**
     * 设置物品栈的堆叠数量
     *
     * @param amount 数量
     */
    ItemBuilder setAmount(int amount);

    /**
     * 设置物品栈的耐久度
     *
     * @param durability 耐久度
     */
    ItemBuilder setDurability(int durability);

    /**
     * 重置物品栈的耐久度
     */
    ItemBuilder resetDurability();

    /**
     * 将物品栈的耐久度添加
     *
     * @param durability 耐久度
     */
    ItemBuilder addDurability(int durability);

    /**
     * 设置物品栈的耐久度减少
     *
     * @param durability 耐久度
     */
    ItemBuilder takeDurability(int durability);

    /**
     * 将物品栈设置标签信息
     *
     * @param lore 标签信息
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemBuilder setLore(String... lore);

    /**
     * 将物品栈设置标签信息
     *
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemBuilder setLore(Collection<? extends String> lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemBuilder addLore(String... lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemBuilder addLore(Collection<? extends String> lore);

    /**
     * 将物品栈清除标签信息
     */
    ItemBuilder clearLore();

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemBuilder addEnchantment(Enchantment enchantment, int level);

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     */
    ItemBuilder addEnchantment(Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     */
    ItemBuilder addSafeEnchantment(Enchantment enchantment, int level);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     */
    ItemBuilder addSafeEnchantment(Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param enchantment 附魔类型
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemBuilder removeEnchantment(Enchantment enchantment);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param enchantments 附魔类型
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemBuilder removeEnchantment(Collection<? extends Enchantment> enchantments);

    /**
     * 将物品栈的附魔效果清除
     */
    ItemBuilder clearEnchantment();

    /**
     * 将物品栈添加指定标示
     *
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemBuilder addFlags(ItemFlag... flags);

    /**
     * 将物品栈添加指定标示
     *
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemBuilder addFlags(Collection<? extends ItemFlag> flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemBuilder removeFlags(ItemFlag... flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemBuilder removeFlags(Collection<? extends ItemFlag> flags);

    /**
     * 将物品栈的标示清除
     */
    ItemBuilder clearFlags();

    /**
     * 设置皮革物品栈的颜色值
     *
     * @param color 颜色
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     */
    ItemBuilder setLeatherColor(Color color);

    /**
     * 设置皮革物品栈的颜色值
     *
     * @param red 红色值
     * @param green 绿色值
     * @param blue 蓝色值
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     * @throws IllegalArgumentException 如果颜色 RGB 不符合值范围则抛出异常 (0 - 255)
     */
    ItemBuilder setLeatherColor(int red, int green, int blue);
}
