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
 
 
package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;

import javax.annotation.Nullable;
import java.nio.charset.Charset;

/**
 * <h1>MySQLFactory</h1>
 * 数据库工厂类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MySQLFactory {

    /**
     * 数据库工厂类构造函数
     */
    private MySQLFactory() {

    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection() throws MySQLInitializeException {

        return new MySQLConnectionExpression();
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(@Nullable String username, @Nullable String password) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(@Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(@Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, @Nullable String username, @Nullable String password) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, @Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, @Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password, charset);
    }
}
