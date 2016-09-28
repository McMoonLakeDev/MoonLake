package com.minecraft.moonlake.mysql.exception;

/**
 * Created by MoonLake on 2016/9/28.
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
