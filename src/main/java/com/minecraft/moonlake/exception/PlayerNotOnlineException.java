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
 
 
package com.minecraft.moonlake.exception;

/**
 * <h1>PlayerNotOnlineException</h1>
 * 玩家未在线异常（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public class PlayerNotOnlineException extends MoonLakeException {

    private static final long serialVersionUID = -8860411433147386980L;

    /**
     * 玩家未在线异常类构造函数
     */
    public PlayerNotOnlineException() {

        super("The player not online exception.");
    }

    /**
     * 玩家未在线异常类构造函数
     *
     * @param player 玩家名
     */
    public PlayerNotOnlineException(String player) {

        super("The player not online exception: " + player);
    }
}
