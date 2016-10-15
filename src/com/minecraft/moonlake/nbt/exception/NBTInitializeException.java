package com.minecraft.moonlake.nbt.exception;

/**
 * <h1>NBTInitializeException</h1>
 * NBT 初始化异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class NBTInitializeException extends NBTException {

    /**
     * NBT 初始化异常类构造函数
     */
    public NBTInitializeException() {

        this("The nbt initialize exception");
    }

    /**
     * NBT 初始化异常类构造函数
     *
     * @param message 异常消息
     */
    public NBTInitializeException(String message) {

        super(message);
    }

    /**
     * NBT 初始化异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public NBTInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
