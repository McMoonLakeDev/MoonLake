package com.minecraft.moonlake.nbt.exception;

/**
 * Created by MoonLake on 2016/9/22.
 */
public class NBTConvertException extends NBTException {

    public NBTConvertException() {

        this("The nbt object convert exception");
    }

    public NBTConvertException(String message) {

        super(message);
    }

    public NBTConvertException(String message, Throwable cause) {

        super(message, cause);
    }
}
