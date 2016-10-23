/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
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