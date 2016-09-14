package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.firework.FireworkLibrary;
import com.minecraft.moonlake.api.item.meta.MetaLibrary;
import com.minecraft.moonlake.api.item.potion.PotionLibrary;
import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/12.
 */
public interface ItemLibrary extends CraftLibrary, MetaLibrary, AttributeLibrary, PotionLibrary, SkullLibrary, FireworkLibrary {

    /**
     * 获取指定物品栈类型是否为工具
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为工具
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isTool(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为武器
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为武器
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isWeapon(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为护甲
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为护甲
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isArmor(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为皮革护甲
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为皮革护甲
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isLeatherArmor(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为药水
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为药水
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isPotion(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为书
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为书
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty isWrittenBook(ItemStack itemStack);
}
