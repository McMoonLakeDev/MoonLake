package com.minecraft.moonlake.nms.exception;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/10/3.
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
