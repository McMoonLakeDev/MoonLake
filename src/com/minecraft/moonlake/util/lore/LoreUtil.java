package com.minecraft.moonlake.util.lore;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.util.Util;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 */
public class LoreUtil implements Lorelib {

    static {}

    public LoreUtil() {

    }

    /**
     * 获取物品栈的标签
     *
     * @param item 物品栈
     * @return String List
     */
    @Override
    public List<String> getLore(ItemStack item) {
        Util.notNull(item, "待获取标签的物品栈是 null 值");

        return null;
    }

    /**
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    @Override
    public ItemStack addLore(ItemStack item, String lore) {
        Util.notNull(item, "待追加标签的物品栈是 null 值");

        return null;
    }
}
