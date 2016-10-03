package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/10/3.
 */
public class AnvilWindowClickEvent extends AnvilWindowAbstractEvent {

    private AnvilWindowSlot clickSlot;
    private ItemStack clickItemStack;

    public AnvilWindowClickEvent(Player clicked, AnvilWindow anvilWindow, AnvilWindowSlot clickSolt, ItemStack clickItemStack) {

        super(clicked, anvilWindow);

        this.clickSlot = clickSolt;
        this.clickItemStack = clickItemStack;
    }

    public AnvilWindowSlot getSlot() {

        return clickSlot;
    }

    public ItemStack getItemStack() {

        return clickItemStack;
    }
}
