package com.minecraft.moonlake.nbt.exception;

/**
 * <h1>NBTConvertException</h1>
 * NBT 转换异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
