package com.minecraft.moonlake.nms.packet.exception;

import com.minecraft.moonlake.nms.exception.NMSException;

/**
 * <h1>PacketException</h1>
 * 数据包异常类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketException extends NMSException {

    /**
     * 数据包异常类构造函数
     */
    public PacketException() {

        this("The nms packet exception");
    }

    /**
     * 数据包异常类构造函数
     *
     * @param message 异常消息
     */
    public PacketException(String message) {

        super(message);
    }

    /**
     * 数据包异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public PacketException(String message, Throwable cause) {

        super(message, cause);
    }
}
