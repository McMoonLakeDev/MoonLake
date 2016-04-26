package com.minecraft.moonlake.api.lorelib;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public interface Lorelib {

    /**
     * 获取物品栈的标签
     *
     * @param item 物品栈
     * @return String List
     */
    List<String> getLore(ItemStack item);

    /**
     * 给物品栈设置标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    ItemStack setLore(ItemStack item, String... lore);

    /**
     * 给物品栈设置标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    ItemStack setLore(ItemStack item, List<String> lore);

    /**
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    ItemStack addLore(ItemStack item, String... lore);

    /**
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    ItemStack addLore(ItemStack item, List<String> lore);

    /**
     * 获取物品栈是否拥有标签
     *
     * @param item 物品栈
     * @return 是否拥有标签
     */
    boolean hasLore(ItemStack item);

    /**
     * 获取物品栈指定索引的标签
     *
     * @param item 物品栈
     * @param index 索引
     * @return 索引标签 如果物品栈没有标签或索引越界则返回空
     */
    String getLoreFromIndex(ItemStack item, int index);

    /**
     * 给物品栈在指定索引插入标签
     *
     * @param item 物品栈
     * @param index 索引
     * @param insertLore 插入的标签
     * @return 插入标签后的 ItemStack 如果索引越界则追加标签
     */
    ItemStack insertLoreFromIndex(ItemStack item, int index, String... insertLore);
}
