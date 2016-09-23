package com.minecraft.moonlake.nbt.exception;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/9/22.
 */
public class NBTException extends MoonLakeException {

    public NBTException() {

        this("The nbt object exception");
    }

    public NBTException(String message) {

        super(message);
    }

    public NBTException(String message, Throwable cause) {

        super(message, cause);
    }
}
