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
 * <h1>CommandInvalidLengthException</h1>
 * 命令错误参数长度异常类
 *
 * @version 1.1
 * @author Month_Light
 */
public class CommandInvalidLengthException extends CommandException {

    private static final long serialVersionUID = -7741012724284893495L;
    private int expected, given;

    /**
     * 命令错误参数长度异常类构造函数
     *
     * @param expected 预期的长度
     * @param given 给予的长度
     */
    public CommandInvalidLengthException(int expected, int given) {

        super("The " + (given < expected ? "missing" : "too many") + " arguments. (" + given + (given < expected ? " < " : " > ") + expected + ")");

        this.expected = expected;
        this.given = given;
    }

    /**
     * 获取此命令预期的参数长度
     *
     * @return 预期的参数长度
     */
    public int getExpected() {

        return expected;
    }

    /**
     * 获取此命令给予的参数长度
     *
     * @return 给予的参数长度
     */
    public int getGiven() {

        return given;
    }
}
