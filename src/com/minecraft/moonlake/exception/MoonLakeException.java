package com.minecraft.moonlake.exception;

/**
 * <h1>MoonLakeException</h1>
 * 月色之湖异常类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakeException extends RuntimeException {

    public MoonLakeException() {

        this("The moonlake plugin exception");
    }

    public MoonLakeException(String message) {

        super(message);
    }

    public MoonLakeException(String message, Throwable cause) {

        super(message, cause);
    }
}
