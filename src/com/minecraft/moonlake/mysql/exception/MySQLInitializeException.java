package com.minecraft.moonlake.mysql.exception;

/**
 * <h1>MySQLInitializeException</h1>
 * 数据库初始化异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MySQLInitializeException extends MySQLException {

    public MySQLInitializeException() {

        this("The mysql initialize exception");
    }

    public MySQLInitializeException(String message) {

        super(message);
    }

    public MySQLInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
