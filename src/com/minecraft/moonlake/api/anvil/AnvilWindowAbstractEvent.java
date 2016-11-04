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

/**
 * <h1>AnvilWindowAbstractEvent</h1>
 * 铁砧窗口抽象事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class AnvilWindowAbstractEvent implements AnvilWindowEvent {

    private final Player player;
    private final AnvilWindow anvilWindow;

    /**
     * 铁砧窗口抽象事件类构造函数
     *
     * @param player 玩家
     * @param anvilWindow 铁砧窗口对象
     */
    public AnvilWindowAbstractEvent(Player player, AnvilWindow anvilWindow) {

        this.player = player;
        this.anvilWindow = anvilWindow;
    }

    public Player getPlayer() {

        return player;
    }

    public AnvilWindow getAnvilWindow() {

        return anvilWindow;
    }
}
