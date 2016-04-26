package com.minecraft.moonlake.util.item;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.util.lore.LoreUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/4/26.
 */
public class ItemUtil extends LoreUtil implements Itemlib {

    static {}

    public ItemUtil() {

    }

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id) {
        return new ItemStack(id);
    }
}
