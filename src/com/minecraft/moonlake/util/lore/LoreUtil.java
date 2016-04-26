package com.minecraft.moonlake.util.lore;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.util.Util;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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

    /**
     * 获取物品栈是否拥有标签
     *
     * @param item 物品栈
     * @return 是否拥有标签
     */
    @Override
    public boolean hasLore(ItemStack item) {
        return getLore(item) != null;
    }

    /**
     * 获取物品栈指定索引的标签
     *
     * @param item 物品栈
     * @param index 索引
     * @return 索引标签 如果物品栈没有标签或索引越界则返回空
     */
    @Override
    public String getLoreFromIndex(ItemStack item, int index) {
        List<String> temp = getLore(item);
        return temp != null && temp.size() - 1 <= index ? temp.get(index) : "";
    }

    /**
     * 给物品栈在指定索引插入标签
     *
     * @param item 物品栈
     * @param index 索引
     * @param insertLore 插入的标签
     * @return 插入标签后的 ItemStack 如果索引越界则追加标签
     */
    @Override
    public ItemStack insertLoreFromIndex(ItemStack item, int index, String... insertLore) {
        List<String> temp = getLore(item);
        if(temp == null) {
            return setLore(item, insertLore);
        }
        if(index >= temp.size() - 1) {
            return addLore(item, insertLore);
        }
        List<String> lore = new ArrayList<String>();
        for(int i = 0; i < index + 1; i++) {
            lore.add(temp.get(i));
        }
        lore.addAll(Arrays.asList(Util.color(insertLore)));
        for(int i = 0;i < temp.size() - index + 1; i++) {
            lore.add(temp.get(i));
        }
        return setLore(item, lore);
    }
}
