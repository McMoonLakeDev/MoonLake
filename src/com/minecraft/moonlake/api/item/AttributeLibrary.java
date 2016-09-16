package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

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
    ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable);

    /**
     * 获取物品栈是否无法破坏
     *
     * @param itemStack 物品栈
     * @return true 则物品栈无法破坏
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack);

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
     * 设置物品栈的特殊属性项
     *
     * @param itemStack 物品栈
     * @param attribute 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性对象为 {@code null} 则抛出异常
     */
    ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute);

    /**
     * 获取物品栈的特殊属性
     *
     * @param itemStack 物品栈
     * @return 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    List<AttributeModify> getAttributes(ItemStack itemStack);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects);

    /**
     * 创建自定义效果药水物品栈 ItemStack 对象
     *
     * @param effects 药水自定义效果
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果药水自定义效果对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.*Potion} 则抛出异常
     */
    ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects);

    /**
     * 设置药水物品栈的自定义药水效果
     *
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
}
