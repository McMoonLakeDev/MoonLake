package com.minecraft.moonlake.exception;

/**
 * <h1>MoonLakeException</h1>
 * 月色之湖异常类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakeException extends RuntimeException {

    /**
     * 月色之湖异常类构造函数
     */
    public MoonLakeException() {

        this("The moonlake plugin exception");
    }

    /**
     * 月色之湖异常类构造函数
     *
     * @param message 异常消息
     */
    public MoonLakeException(String message) {

        super(message);
    }

    /**
     * 月色之湖异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public MoonLakeException(String message, Throwable cause) {

        super(message, cause);
    }
}
