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
 
 
package com.minecraft.moonlake.api.anvil;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>AnvilWindowClickEvent</h1>
 * 铁砧窗口点击事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class AnvilWindowClickEvent extends AnvilWindowAbstractEvent implements Cancellable {

    private AnvilWindowSlot clickSlot;
    private ItemStack clickItemStack;
    private boolean cancel;

    /**
     * 铁砧窗口点击事件类构造函数
     *
     * @param clicked 点击者玩家
     * @param anvilWindow 铁砧窗口对象
     * @param clickSlot 点击的槽位
     * @param clickItemStack 点击的物品栈
     */
    public AnvilWindowClickEvent(Player clicked, AnvilWindow anvilWindow, AnvilWindowSlot clickSlot, ItemStack clickItemStack) {

        super(clicked, anvilWindow);

        this.clickSlot = clickSlot;
        this.clickItemStack = clickItemStack;
    }

    /**
     * 获取此铁砧窗口点击的槽位
     *
     * @return 槽位
     */
    public AnvilWindowSlot getSlot() {

        return clickSlot;
    }

    /**
     * 获取此铁砧窗口点击的物品栈
     *
     * @return 物品栈
     */
    public ItemStack getItemStack() {

        return clickItemStack;
    }

    @Override
    public boolean isCancelled() {

        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;
    }
}
