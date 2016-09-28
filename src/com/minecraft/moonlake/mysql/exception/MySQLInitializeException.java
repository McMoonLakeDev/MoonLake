package com.minecraft.moonlake.mysql.exception;

/**
 * Created by MoonLake on 2016/9/28.
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
