package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import org.bukkit.inventory.ItemStack;

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
    void setUnbreakable(ItemStack itemStack, boolean unbreakable);

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
    void setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount);

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
    void setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount);

    /**
     * 设置物品栈的特殊属性项
     *
     * @param itemStack 物品栈
     * @param attribute 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果特殊属性对象为 {@code null} 则抛出异常
     */
    void setAttribute(ItemStack itemStack, AttributeModify attribute);

    /**
     * 获取物品栈的特殊属性
     *
     * @param itemStack 物品栈
     * @return 特殊属性
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    List<AttributeModify> getAttributes(ItemStack itemStack);
}
