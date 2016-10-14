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

    private MySQLFactory() {

    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @param charset MySQL 编码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password, String charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @param charset MySQL 编码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String username, String password, Charset charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host MySQL 地址
     * @param port MySQL 端口
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host MySQL 地址
     * @param port MySQL 端口
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @param charset MySQL 编码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password, String charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host MySQL 地址
     * @param port MySQL 端口
     * @param username MySQL 用户名
     * @param password MySQL 密码
     * @param charset MySQL 编码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果 MySQL 初始化错误则抛出异常
     */
    public static MySQLConnection connection(String host, int port, String username, String password, Charset charset) throws MySQLInitializeException {

        return new MySQLConnectionExpression(host, port, username, password, charset);
    }
}
