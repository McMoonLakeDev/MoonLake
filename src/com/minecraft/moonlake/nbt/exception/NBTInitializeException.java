package com.minecraft.moonlake.nbt.exception;

/**
 * <h1>NBTInitializeException</h1>
 * NBT 初始化异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
