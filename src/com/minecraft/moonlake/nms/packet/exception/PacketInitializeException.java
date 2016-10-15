package com.minecraft.moonlake.nms.packet.exception;

/**
 * <h1>PacketInitializeException</h1>
 * 数据包初始化异常类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketInitializeException extends PacketException {

    /**
     * 数据包初始化异常类构造函数
     */
    public PacketInitializeException() {

        this("The nms packet initialize exception");
    }

    /**
     * 数据包初始化异常类构造函数
     *
     * @param message 异常消息
     */
    public PacketInitializeException(String message) {

        super(message);
    }

    /**
     * 数据包初始化异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public PacketInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
