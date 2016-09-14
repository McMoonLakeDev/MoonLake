package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class IllegalMySQLException extends MoonLakeException {

    public IllegalMySQLException(String message) {

        super("The MySQL error info exception: " + message);
    }
}
