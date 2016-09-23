package com.minecraft.moonlake.nbt.exception;

/**
 * Created by MoonLake on 2016/9/22.
 */
public class NBTInitializeException extends NBTException {

    public NBTInitializeException() {

        this("The nbt initialize exception");
    }

    public NBTInitializeException(String message) {

        super(message);
    }

    public NBTInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
