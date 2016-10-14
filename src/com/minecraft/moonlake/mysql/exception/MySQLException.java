package com.minecraft.moonlake.mysql.exception;

/**
 * <h1>MySQLException</h1>
 * 数据库异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MySQLException extends RuntimeException {

    public MySQLException() {

        this("The mysql exception");
    }

    public MySQLException(String message) {

        super(message);
    }

    public MySQLException(String message, Throwable cause) {

        super(message, cause);
    }
}
