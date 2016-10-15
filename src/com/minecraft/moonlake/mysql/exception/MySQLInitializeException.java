package com.minecraft.moonlake.mysql.exception;

/**
 * <h1>MySQLInitializeException</h1>
 * 数据库初始化异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MySQLInitializeException extends MySQLException {

    /**
     * 数据库初始化异常类构造函数
     */
    public MySQLInitializeException() {

        this("The mysql initialize exception");
    }

    /**
     * 数据库初始化异常类构造函数
     *
     * @param message 异常消息
     */
    public MySQLInitializeException(String message) {

        super(message);
    }

    /**
     * 数据库初始化异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public MySQLInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
