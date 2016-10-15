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

    /**
     * NBT 异常类构造函数
     */
    public NBTException() {

        this("The nbt object exception");
    }

    /**
     * NBT 异常类构造函数
     *
     * @param message 异常消息
     */
    public NBTException(String message) {

        super(message);
    }

    /**
     * NBT 异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public NBTException(String message, Throwable cause) {

        super(message, cause);
    }
}
