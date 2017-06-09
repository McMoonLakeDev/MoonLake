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
 
 
package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.builder.Builder;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

/**
 * <h1>ItemStack Builder</h1>
 * 物品栈建造（详细doc待补充...）
 *
 * @version 1.0.1
 * @author Month_Light
 */
public interface ItemBuilder extends Builder<ItemStack> {

    /**
     * 构建并获取物品栈对象
     *
     * @return ItemStack
     */
    @Override
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
     * 设置物品栈的本地化名称
     *
     * @param localizedName 本地化名称
     * @throws IllegalArgumentException 如果本地化名称对象为 {@code null} 则抛出异常
     */
    ItemBuilder setLocalizedName(String localizedName);

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
     * 将物品栈添加指定附魔效果
     *
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemBuilder addEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment, int level);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     */
    ItemBuilder addSafeEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment, int level);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param enchantment 附魔类型
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ItemBuilder removeEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment);

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

    /**
     * 设置皮革物品栈的颜色值从随机 (0 - 255)
     *
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     */
    ItemBuilder setLeatherColorFromRandom();

    /**
     * 设置物品栈是否无法破坏
     *
     * @param unbreakable 是否无法破坏
     */
    ItemBuilder setUnbreakable(boolean unbreakable);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param type 属性类型
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    ItemBuilder setAttribute(AttributeModify.Type type, AttributeModify.Operation operation, double amount);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性生效槽位对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    ItemBuilder setAttribute(AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param attribute 特殊属性
     * @throws IllegalArgumentException 如果特殊属性对象为 {@code null} 则抛出异常
     */
    ItemBuilder setAttribute(AttributeModify attribute);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effect 药水自定义效果
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(PotionEffectCustom... effect);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effect 药水自定义效果
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(Collection<? extends PotionEffectCustom> effect);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 设置头颅物品栈的头颅材质皮肤数据
     *
     * @param data 皮肤数据
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemBuilder setSkullWithSkin(String data);

    /**
     * 设置头颅物品栈的头颅材质皮肤数据
     *
     * @param value 头颅材质值
     * @param signature 头颅材质签名
     * @throws IllegalArgumentException 如果头颅材质值对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemBuilder setSkullWithSkin(String value, String signature);
}
