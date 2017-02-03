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
 
 
package com.minecraft.moonlake.mysql.exception;

/**
 * <h1>MySQLException</h1>
 * 数据库异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MySQLException extends RuntimeException {

    /**
     * 数据库异常类构造函数
     */
    public MySQLException() {

        this("The mysql exception");
    }

    /**
     * 数据库异常类构造函数
     *
     * @param message 异常消息
     */
    public MySQLException(String message) {

        super(message);
    }

    /**
     * 数据库异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public MySQLException(String message, Throwable cause) {

        super(message, cause);
    }
}
