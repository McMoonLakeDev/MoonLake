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
 
 
package com.minecraft.moonlake.api.item.meta;

import com.minecraft.moonlake.api.item.AttributeLibrary;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>ItemStack MetaLibrary</h1>
 * 物品栈元属性支持库（详细doc待补充...）
 *
 * @version 1.0.1
 * @author Month_Light
 */
public interface MetaLibrary extends AttributeLibrary {

    /**
     * 设置物品栈的显示名称
     *
     * @param itemStack 物品栈
     * @param displayName 显示名称
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack setDisplayName(ItemStack itemStack, String displayName);

    /**
     * 设置物品栈的本地化名称
     *
     * @param itemStack 物品栈
     * @param localizedName 本地化名称
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果本地化名称对象为 {@code null} 则抛出异常
     */
    ItemStack setLocalizedName(ItemStack itemStack, String localizedName);

    /**
     * 获取物品栈的本地化名称
     *
     * @param itemStack 物品栈
     * @return 本地化名称
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    String getLocalizedName(ItemStack itemStack);

    /**
     * 获取物品栈是否拥有本地化名称
     *
     * @param itemStack 物品栈
     * @return 是否拥有本地化名称
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean hasLocalizedName(ItemStack itemStack);

    /**
     * 设置物品栈的堆叠数量
     *
     * @param itemStack 物品栈
     * @param amount 数量
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack setAmount(ItemStack itemStack, int amount);

    /**
     * 设置物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack setDurability(ItemStack itemStack, int durability);

    /**
     * 获取物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    int getDurability(ItemStack itemStack);

    /**
     * 重置物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack resetDurability(ItemStack itemStack);

    /**
     * 将物品栈的耐久度添加
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack addDurability(ItemStack itemStack, int durability);

    /**
     * 设置物品栈的耐久度减少
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack takeDurability(ItemStack itemStack, int durability);

    /**
     * 获取物品栈的标签信息
     *
     * @param itemStack 物品栈
     * @return 标签信息 没有则返回 null
     */
    List<String> getLore(ItemStack itemStack);

    /**
     * 获取物品栈的标签信息
     *
     * @param itemStack 物品栈
     * @param ignoreColor 是否忽略颜色
     * @return 标签信息 没有则返回 null
     */
    List<String> getLore(ItemStack itemStack, boolean ignoreColor);

    /**
     * 将物品栈设置标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemStack setLore(ItemStack itemStack, String... lore);

    /**
     * 将物品栈设置标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemStack setLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemStack addLore(ItemStack itemStack, String... lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ItemStack addLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 将物品栈清除标签信息
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack clearLore(ItemStack itemStack);

    /**
     * 获取物品栈是否拥有标签信息
     *
     * @param itemStack 物品栈
     * @return true 则拥有标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean hasLore(ItemStack itemStack);

    /**
     * 获取物品栈是否包含指定标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    boolean containLore(ItemStack itemStack, String... lore);

    /**
     * 获取物品栈是否包含指定标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    boolean containLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 获取物品栈是否包含指定标签信息 (忽略颜色)
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    boolean containLoreIgnoreColor(ItemStack itemStack, String... lore);

    /**
     * 获取物品栈是否包含指定标签信息 (忽略颜色)
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    boolean containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 获取物品栈的附魔效果
     *
     * @param itemStack 物品栈
     * @return 附魔效果 没有则返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Map<Enchantment, Integer> getEnchantments(ItemStack itemStack);

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level);

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     */
    ItemStack addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     * @throws IllegalArgumentException 如果附魔效果不能附魔到指定物品栈对象则抛出异常
     */
    ItemStack addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     * @throws IllegalArgumentException 如果附魔效果不能附魔到指定物品栈对象则抛出异常
     */
    ItemStack addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemStack removeEnchantment(ItemStack itemStack, Enchantment enchantment);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantments 附魔类型
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemStack removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments);

    /**
     * 将物品栈的附魔效果清除
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack clearEnchantment(ItemStack itemStack);

    /**
     * 获取物品栈是否拥有指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @return true 则拥有指定附魔效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    boolean hasEnchantment(ItemStack itemStack, Enchantment enchantment);

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemStack addEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     * @throws IllegalArgumentException 如果附魔效果不能附魔到指定物品栈对象则抛出异常
     */
    ItemStack addSafeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemStack removeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment);

    /**
     * 获取物品栈是否拥有指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @return true 则拥有指定附魔效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    boolean hasEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment);

    /**
     * 获取物品栈的标示
     *
     * @param itemStack 物品栈
     * @return 标示集合 没有则返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Set<ItemFlag> getFlags(ItemStack itemStack);

    /**
     * 将物品栈添加指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemStack addFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈添加指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemStack addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemStack removeFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ItemStack removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags);

    /**
     * 获取物品栈是否拥有指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @return true 则拥有指定标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    boolean hasFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈的标示清除
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack clearFlags(ItemStack itemStack);

    /**
     * 设置皮革物品栈的颜色值
     *
     * @param itemStack 皮革物品栈
     * @param color 颜色
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     */
    ItemStack setLeatherColor(ItemStack itemStack, Color color);

    /**
     * 设置皮革物品栈的颜色值
     *
     * @param itemStack 皮革物品栈
     * @param red 红色值
     * @param green 绿色值
     * @param blue 蓝色值
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     * @throws IllegalArgumentException 如果颜色 RGB 不符合值范围则抛出异常 (0 - 255)
     */
    ItemStack setLeatherColor(ItemStack itemStack, int red, int green, int blue);

    /**
     * 设置皮革物品栈的颜色值从随机 (0 - 255)
     *
     * @param itemStack 皮革物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     */
    ItemStack setLeatherColorFromRandom(ItemStack itemStack);
}
