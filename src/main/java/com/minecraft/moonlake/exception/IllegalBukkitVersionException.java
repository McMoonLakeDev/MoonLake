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
 * <h1>IllegalBukkitVersionException</h1>
 * 非法 Bukkit 版本异常（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public class IllegalBukkitVersionException extends MoonLakeException {

    private static final long serialVersionUID = -8942367208773219669L;

    /**
     * 非法 Bukkit 版本异常类构造函数
     */
    public IllegalBukkitVersionException() {

        this("The method not support this bukkit version.");
    }

    /**
     * 非法 Bukkit 版本异常类构造函数
     *
     * @param message 异常消息
     */
    public IllegalBukkitVersionException(String message) {

        super(message);
    }

    /**
     * 非法 Bukkit 版本异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public IllegalBukkitVersionException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * 非法 Bukkit 版本异常类构造函数
     *
     * @param cause 异常原因
     */
    public IllegalBukkitVersionException(Throwable cause) {

        super(cause);
    }
}
