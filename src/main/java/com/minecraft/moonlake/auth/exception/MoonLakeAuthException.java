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
 * <h1>MoonLakeAuthException</h1>
 * 月色之湖认证异常
 *
 * @version 1.0
 * @author Month_Light
 * @see Exception
 */
public class MoonLakeAuthException extends Exception {

    private static final long serialVersionUID = -2842152632597783319L;

    /**
     * 月色之湖认证异常构造函数
     */
    public MoonLakeAuthException() {
    }

    /**
     * 月色之湖认证异常构造函数
     *
     * @param message 异常信息
     */
    public MoonLakeAuthException(String message) {
        super(message);
    }

    /**
     * 月色之湖认证异常构造函数
     *
     * @param message 异常信息
     * @param cause 原因
     */
    public MoonLakeAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 月色之湖认证异常构造函数
     *
     * @param cause 原因
     */
    public MoonLakeAuthException(Throwable cause) {
        super(cause);
    }
}
