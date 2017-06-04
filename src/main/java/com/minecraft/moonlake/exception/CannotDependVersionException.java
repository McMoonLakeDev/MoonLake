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
 * <h1>CannotDependVersionException</h1>
 * 无法依赖版本异常类
 *
 * @version 1.1
 * @author Month_Light
 */
public class CannotDependVersionException extends CannotDependException {

    private static final long serialVersionUID = 1970119983604709622L;

    /**
     * 无法依赖版本异常类构造函数
     */
    public CannotDependVersionException() {

        this("The cannot depend plugin exception");
    }

    /**
     * 无法依赖版本异常类构造函数
     *
     * @param message 异常消息
     */
    public CannotDependVersionException(String message) {

        super(message);
    }

    /**
     * 无法依赖版本异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public CannotDependVersionException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * 无法依赖版本异常类构造函数
     *
     * @param cause 异常原因
     */
    public CannotDependVersionException(Throwable cause) {

        super(cause);
    }
}
