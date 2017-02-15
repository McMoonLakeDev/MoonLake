/*
 * Copyright (C) 2017 The MoonLake Authors
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

/**
 * <h1>AnvilWindowInputEvent</h1>
 * 铁砧窗口输入事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class AnvilWindowInputEvent extends AnvilWindowAbstractEvent implements Cancellable {

    private String input;
    private boolean cancel = false;

    /**
     * 铁砧窗口输入事件类构造函数
     *
     * @param player 玩家
     * @param anvilWindow 铁砧窗口对象
     * @param input 输入的文本
     */
    public AnvilWindowInputEvent(Player player, AnvilWindow anvilWindow, String input) {

        super(player, anvilWindow);

        this.input = input;
    }

    /**
     * 获取此铁砧窗口输入的文本
     *
     * @return 输入的文本
     */
    public String getInput() {

        return input;
    }

    /**
     * 设置此铁砧窗口输入的文本
     */
    public void setInput(String input) {

        this.input = input;
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
