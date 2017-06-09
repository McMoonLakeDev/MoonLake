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


package com.minecraft.moonlake.auth.exception;

/**
 * <h1>MoonLakeProfileNotFoundException</h1>
 * 游戏档案不存在异常
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeProfileException
 */
public class MoonLakeProfileNotFoundException extends MoonLakeProfileException {

    private static final long serialVersionUID = 8398382809558438203L;

    /**
     * 游戏档案不存在异常构造函数
     */
    public MoonLakeProfileNotFoundException() {
    }

    /**
     * 游戏档案不存在异常构造函数
     *
     * @param message 异常消息
     */
    public MoonLakeProfileNotFoundException(String message) {
        super(message);
    }

    /**
     * 游戏档案不存在异常构造函数
     *
     * @param message 异常消息
     * @param cause 原因
     */
    public MoonLakeProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 游戏档案不存在异常构造函数
     *
     * @param cause 原因
     */
    public MoonLakeProfileNotFoundException(Throwable cause) {
        super(cause);
    }
}
