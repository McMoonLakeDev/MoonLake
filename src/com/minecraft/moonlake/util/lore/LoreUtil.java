package com.minecraft.moonlake.util.lore;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.util.Util;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public class LoreUtil implements Lorelib {

    static {}

    public LoreUtil() {

    }

    /**
     * 获取物品栈的标签
     *
     * @param item 物品栈
     * @return String List 如果物品栈没有标签则返回 null
     */
    @Override
    public List<String> getLore(ItemStack item) {
        Util.notNull(item, "待获取标签的物品栈是 null 值");

        return item.hasItemMeta() ? item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : null : null;
    }

    /**
     * 给物品栈设置标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    @Override
    public ItemStack setLore(ItemStack item, String... lore) {
        Util.notNull(item, "待设置标签的物品栈是 null 值");

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(Util.color(lore)));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 给物品栈设置标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    @Override
    public ItemStack setLore(ItemStack item, List<String> lore) {
        Util.notNull(item, "待设置标签的物品栈是 null 值");

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Util.color(lore));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    @Override
    public ItemStack addLore(ItemStack item, String... lore) {
        Util.notNull(item, "待追加标签的物品栈是 null 值");

        List<String> temp = getLore(item);
        if(temp == null) {
            return setLore(item, lore);
        }
        for(int i = 0; i < lore.length; i++) {
            temp.add(Util.color(lore[i]));
        }
        return setLore(item, temp);
    }

    /**
     * 给物品栈追加标签
     *
     * @param item 物品栈
     * @param lore 标签
     * @return ItemStack
     */
    @Override
    public ItemStack addLore(ItemStack item, List<String> lore) {
        Util.notNull(item, "待追加标签的物品栈是 null 值");

        List<String> temp = getLore(item);
        if(temp == null) {
            return setLore(item, lore);
        }
        for(int i = 0; i < lore.size(); i++) {
            temp.add(Util.color(lore.get(i)));
        }
        return setLore(item, temp);
    }
}
