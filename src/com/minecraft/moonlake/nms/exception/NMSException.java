package com.minecraft.moonlake.nms.exception;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * <h1>NMSException</h1>
 * NMS 异常类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class NMSException extends MoonLakeException {

    /**
     * NMS 异常类构造函数
     */
    public NMSException() {

        this("The nms exception");
    }

    /**
     * NMS 异常类构造函数
     *
     * @param message 异常消息
     */
    public NMSException(String message) {

        super(message);
    }

    /**
     * NMS 异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public NMSException(String message, Throwable cause) {

        super(message, cause);
    }
}
