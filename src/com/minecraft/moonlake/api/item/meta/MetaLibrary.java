package com.minecraft.moonlake.api.item.meta;

import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/9/12.
 */
public interface MetaLibrary {

    /**
     * 设置物品栈的显示名称
     *
     * @param itemStack 物品栈
     * @param displayName 显示名称
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    void setDisplayName(ItemStack itemStack, String displayName);

    /**
     * 设置物品栈的堆叠数量
     *
     * @param itemStack 物品栈
     * @param amount 数量
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void setAmount(ItemStack itemStack, int amount);

    /**
     * 设置物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void setDurability(ItemStack itemStack, int durability);

    /**
     * 获取物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyIntegerProperty getDurability(ItemStack itemStack);

    /**
     * 重置物品栈的耐久度
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void resetDurability(ItemStack itemStack);

    /**
     * 将物品栈的耐久度添加
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void addDurability(ItemStack itemStack, int durability);

    /**
     * 设置物品栈的耐久度减少
     *
     * @param itemStack 物品栈
     * @param durability 耐久度
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void takeDurability(ItemStack itemStack, int durability);

    /**
     * 获取物品栈的标签信息
     *
     * @param itemStack 物品栈
     * @return 标签信息 没有则返回 null
     */
    Set<String> getLores(ItemStack itemStack);

    /**
     * 获取物品栈的标签信息
     *
     * @param itemStack 物品栈
     * @param ignoreColor 是否忽略颜色
     * @return 标签信息 没有则返回 null
     */
    Set<String> getLores(ItemStack itemStack, boolean ignoreColor);

    /**
     * 将物品栈设置标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    void setLore(ItemStack itemStack, String... lore);

    /**
     * 将物品栈设置标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    void setLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    void addLore(ItemStack itemStack, String... lore);

    /**
     * 将物品栈添加标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    void addLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 将物品栈清除标签信息
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void clearLore(ItemStack itemStack);

    /**
     * 获取物品栈是否拥有标签信息
     *
     * @param itemStack 物品栈
     * @return true 则拥有标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty hasLore(ItemStack itemStack);

    /**
     * 获取物品栈是否包含指定标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty containLore(ItemStack itemStack, String... lore);

    /**
     * 获取物品栈是否包含指定标签信息
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty containLore(ItemStack itemStack, Collection<? extends String> lore);

    /**
     * 获取物品栈是否包含指定标签信息 (忽略颜色)
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, String... lore);

    /**
     * 获取物品栈是否包含指定标签信息 (忽略颜色)
     *
     * @param itemStack 物品栈
     * @param lore 标签信息
     * @return true 则包含指定标签信息
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore);

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
    void addEnchantment(ItemStack itemStack, Enchantment enchantment, int level);

    /**
     * 将物品栈添加指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     */
    void addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @param level 附魔等级
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     */
    void addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level);

    /**
     * 将物品栈添加安全的指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantmentMap 附魔效果 Map 集合
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔的等级小于附魔类型的开始等级或大于最大等级则抛出异常
     */
    void addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    void removeEnchantment(ItemStack itemStack, Enchantment enchantment);

    /**
     * 将物品栈删除指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantments 附魔类型
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    void removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments);

    /**
     * 将物品栈的附魔效果清除
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void clearEnchantment(ItemStack itemStack);

    /**
     * 获取物品栈是否拥有指定附魔效果
     *
     * @param itemStack 物品栈
     * @param enchantment 附魔类型
     * @return true 则拥有指定附魔效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果附魔类型对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty hasEnchantment(ItemStack itemStack, Enchantment enchantment);

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
    void addFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈添加指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    void addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    void removeFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈删除指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    void removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags);

    /**
     * 获取物品栈是否拥有指定标示
     *
     * @param itemStack 物品栈
     * @param flags 标示
     * @return true 则拥有指定标示
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品标示对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty hasFlags(ItemStack itemStack, ItemFlag... flags);

    /**
     * 将物品栈的标示清除
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void clearFlags(ItemStack itemStack);

    /**
     * 设置皮革物品栈的颜色值
     *
     * @param itemStack 皮革物品栈
     * @param color 颜色
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.Leather*} 则抛出异常
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     */
    void setLeatherColor(ItemStack itemStack, Color color);

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
    void setLeatherColor(ItemStack itemStack, int red, int green, int blue);
}
