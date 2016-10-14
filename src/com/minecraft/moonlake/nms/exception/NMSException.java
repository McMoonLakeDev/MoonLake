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

    public NMSException() {

        this("The nms exception");
    }

    public NMSException(String message) {

        super(message);
    }

    public NMSException(String message, Throwable cause) {

        super(message, cause);
    }
}
