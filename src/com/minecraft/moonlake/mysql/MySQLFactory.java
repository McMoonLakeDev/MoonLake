package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;

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
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password, String charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password, Charset charset) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password, String charset) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password, Charset charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password, charset);
    }
}
