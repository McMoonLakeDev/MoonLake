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
import com.minecraft.moonlake.api.nbt.NBTCompound;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Set;

/**
 * <h1>ItemStack AttributeLibrary</h1>
 * 物品栈属性支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface AttributeLibrary {

    /**
     * 设置物品栈是否无法破坏
     *
     * @param itemStack 物品栈
     * @param unbreakable 是否无法破坏
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable);

    /**
     * 设置指定 NBT 复合对象是否无法破坏
     *
     * @param nbtCompound NBT 复合对象
     * @param unbreakable 是否无法破坏
     * @throws IllegalArgumentException 如果 NBT 复合对象对象为 {@code null} 则抛出异常
     */
    void setUnbreakable(NBTCompound nbtCompound, boolean unbreakable);

    /**
     * 获取物品栈是否无法破坏
     *
     * @param itemStack 物品栈
     * @return true 则物品栈无法破坏
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isUnbreakable(ItemStack itemStack);

    /**
     * 获取指定 NBT 复合对象是否无法破坏
     *
     * @param nbtCompound NBT 复合对象
     * @return true 则物品栈无法破坏
     * @throws IllegalArgumentException 如果 NBT 复合对象对象为 {@code null} 则抛出异常
     */
    boolean isUnbreakable(NBTCompound nbtCompound);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param itemStack 物品栈
     * @param type 属性类型
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param itemStack 物品栈
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性生效槽位对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount);

    /**
     * 设置指定 NBT 复合对象的特殊属性项
     *
     * @param nbtCompound 物品栈
     * @param type 属性类型
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Operation operation, double amount);

    /**
     * 设置指定 NBT 复合对象的特殊属性项
     *
     * @param nbtCompound 物品栈
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算模式
     * @param amount 属性值
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性生效槽位对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性运算模式对象为 {@code null} 则抛出异常
     */
    void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param itemStack 物品栈
     * @param attribute 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性对象为 {@code null} 则抛出异常
     */
    ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute);

    /**
     * 设置指定 NBT 复合对象的特殊属性项
     *
     * @param nbtCompound NBT 复合对象
     * @param attribute 特殊属性
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性对象为 {@code null} 则抛出异常
     */
    void setAttribute(NBTCompound nbtCompound, AttributeModify attribute);

    /**
     * 获取物品栈的特殊属性
     *
     * @param itemStack 物品栈
     * @return 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Set<AttributeModify> getAttributes(ItemStack itemStack);

    /**
     * 获取指定 NBT 复合对象的特殊属性
     *
     * @param nbtCompound NBT 复合对象
     * @return 特殊属性
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     */
    Set<AttributeModify> getAttributes(NBTCompound nbtCompound);

    /**
     * 获取物品栈是否拥有特殊属性
     *
     * @param itemStack 物品栈
     * @param type 特殊属性类型
     * @return 是否拥有特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性类型对象为 {@code null} 则抛出异常
     */
    boolean hasAttribute(ItemStack itemStack, AttributeModify.Type type);

    /**
     * 获取指定 NBT 复合对象是否拥有特殊属性
     *
     * @param nbtCompound NBT 复合对象
     * @param type 特殊属性类型
     * @return 是否拥有特殊属性
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性类型对象为 {@code null} 则抛出异常
     */
    boolean hasAttribute(NBTCompound nbtCompound, AttributeModify.Type type);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 获取药水物品栈的自定义药水效果
     *
     * @param itemStack 药水物品栈
     * @return 自定义药水效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    Set<PotionEffectCustom> getCustomPotion(ItemStack itemStack);

    /**
     * 获取药水物品栈是否拥有指定药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @return 是否拥有药水效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    boolean hasCustomPotion(ItemStack itemStack, PotionEffectType effectType);

    /**
     * 获取药水物品栈是否拥有指定药水效果
     *
     * @param itemStack 药水物品栈
     * @param effectType 药水效果类型
     * @return 是否拥有药水效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    boolean hasCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType);


    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, PotionEffectCustom... effects);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, Collection<? extends PotionEffectCustom> effects);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient);

    /**
     * 设置指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @param amplifier 药水效果等级
     * @param duration 药水效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 获取指定 NBT 复合对象的自定义药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @return 自定义药水效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     */
    Set<PotionEffectCustom> getCustomPotion(NBTCompound nbtCompound);

    /**
     * 获取指定 NBT 复合对象是否拥有指定药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @return 是否拥有药水效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    boolean hasCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType);

    /**
     * 获取指定 NBT 复合对象是否拥有指定药水效果
     *
     * @param nbtCompound NBT 复合对象
     * @param effectType 药水效果类型
     * @return 是否拥有药水效果
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    boolean hasCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType);
}
