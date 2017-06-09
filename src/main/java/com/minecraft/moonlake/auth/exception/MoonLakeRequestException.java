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
 * <h1>MoonLakeRequestException</h1>
 * 请求时异常
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeAuthException
 */
public class MoonLakeRequestException extends MoonLakeAuthException {

    private static final long serialVersionUID = 2670560596110569586L;

    /**
     * 请求时异常构造函数
     */
    public MoonLakeRequestException() {
    }

    /**
     * 请求时异常构造函数
     *
     * @param message 异常消息
     */
    public MoonLakeRequestException(String message) {
        super(message);
    }

    /**
     * 请求时异常构造函数
     *
     * @param message 异常消息
     * @param cause 原因
     */
    public MoonLakeRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 请求时异常构造函数
     *
     * @param cause 原因
     */
    public MoonLakeRequestException(Throwable cause) {
        super(cause);
    }
}
