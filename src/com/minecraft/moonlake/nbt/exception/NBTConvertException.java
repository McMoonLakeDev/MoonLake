package com.minecraft.moonlake.nbt.exception;

/**
 * <h1>NBTConvertException</h1>
 * NBT 转换异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class NBTConvertException extends NBTException {

    /**
     * NBT 转换异常类构造函数
     */
    public NBTConvertException() {

        this("The nbt object convert exception");
    }

    /**
     * NBT 转换异常类构造函数
     *
     * @param message 异常消息
     */
    public NBTConvertException(String message) {

        super(message);
    }

    /**
     * NBT 转换异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public NBTConvertException(String message, Throwable cause) {

        super(message, cause);
    }
}
