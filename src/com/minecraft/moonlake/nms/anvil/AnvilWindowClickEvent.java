package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>AnvilWindowClickEvent</h1>
 * 铁砧窗口点击事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class AnvilWindowClickEvent extends AnvilWindowAbstractEvent {

    private AnvilWindowSlot clickSlot;
    private ItemStack clickItemStack;

    /**
     * 铁砧窗口点击事件类构造函数
     *
     * @param clicked 点击者玩家
     * @param anvilWindow 铁砧窗口对象
     * @param clickSolt 点击的槽位
     * @param clickItemStack 点击的物品栈
     */
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
