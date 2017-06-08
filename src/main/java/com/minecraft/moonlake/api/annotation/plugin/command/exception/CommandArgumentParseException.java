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
 * <h1>CommandArgumentParseException</h1>
 * 命令参数解析异常类
 *
 * @version 1.1
 * @author Month_Light
 */
public class CommandArgumentParseException extends CommandException {

    private static final long serialVersionUID = 3339840141491833573L;
    private String argument;
    private Class<?> type;

    /**
     * 命令参数解析异常类构造函数
     */
    public CommandArgumentParseException() {

        this("The command argument parse exception.");
    }

    /**
     * 命令参数解析异常类构造函数
     *
     * @param message 异常消息
     */
    public CommandArgumentParseException(String message) {

        super(message);
    }

    /**
     * 命令参数解析异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public CommandArgumentParseException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * 命令参数解析异常类构造函数
     *
     * @param message 异常消息
     * @param argument 参数
     * @param type 参数类型
     */
    public CommandArgumentParseException(String message, String argument, Class<?> type) {

        super(message);

        this.argument = argument;
        this.type = type;
    }

    /**
     * 命令参数解析异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     * @param argument 参数
     * @param type 参数类型
     */
    public CommandArgumentParseException(String message, Throwable cause, String argument, Class<?> type) {

        super(message, cause);

        this.argument = argument;
        this.type = type;
    }

    /**
     * 获取此命令解析错误的参数
     *
     * @return 参数
     */
    public String getArgument() {

        return argument;
    }

    /**
     * 获取此命令解析错误的参数类型
     *
     * @return 参数类型
     */
    public Class<?> getType() {

        return type;
    }
}
