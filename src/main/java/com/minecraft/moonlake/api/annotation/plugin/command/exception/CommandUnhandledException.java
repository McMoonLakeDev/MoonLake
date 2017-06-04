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


package com.minecraft.moonlake.api.annotation.plugin.command.exception;

/**
 * <h1>CommandUnhandledException</h1>
 * 命令未处理异常类
 *
 * @version 1.1
 * @author Month_Light
 */
public class CommandUnhandledException extends CommandException {

    private static final long serialVersionUID = 367166525844370485L;

    /**
     * 命令未处理异常类构造函数
     */
    public CommandUnhandledException() {

        this("The moonlake command unhandled exception");
    }

    /**
     * 命令未处理异常类构造函数
     *
     * @param message 异常消息
     */
    public CommandUnhandledException(String message) {

        super(message);
    }

    /**
     * 命令未处理异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public CommandUnhandledException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * 命令未处理异常类构造函数
     *
     * @param cause 异常原因
     */
    public CommandUnhandledException(Throwable cause) {

        super(cause);
    }
}
