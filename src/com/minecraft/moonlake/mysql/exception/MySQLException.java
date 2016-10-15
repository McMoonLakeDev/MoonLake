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
