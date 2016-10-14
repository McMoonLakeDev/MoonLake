package com.minecraft.moonlake.nbt.exception;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * <h1>NBTException</h1>
 * NBT 异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
