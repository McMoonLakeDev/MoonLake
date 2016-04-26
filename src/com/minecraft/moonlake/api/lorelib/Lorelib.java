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
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    ItemStack addLore(ItemStack item, String lore);
}
