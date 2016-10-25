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
 
 
package com.minecraft.moonlake.api.item.potion;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * <h1>ItemStack PotionLibrary</h1>
 * 药水物品栈支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
     * 创建普通药水物品栈 ItemStack 对象
     *
      * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base);

    /**
     * 创建普通药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base, int amount);

    /**
     * 创建普通药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createNormalPotion(PotionBase base, int amount, String displayName);

    /**
     * 创建投掷药水物品栈 ItemStack 对象
     *
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
     * 创建投掷药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createSplashPotion(PotionBase base, int amount, String displayName);

    /**
     * 创建滞留药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base);

    /**
     * 创建滞留药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base, int amount);

    /**
     * 创建滞留药水物品栈 ItemStack 对象
     *
     * @param base 药水基础效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水基础效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createLingeringPotion(PotionBase base, int amount, String displayName);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, PotionEffectCustom... effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, PotionEffectCustom... effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectCustom... effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, Collection<? extends PotionEffectCustom> effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, Collection<? extends PotionEffectCustom> effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effects 药水自定义效果
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, Collection<? extends PotionEffectCustom> effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param type 药水类型
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return ItemStack
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);
}
