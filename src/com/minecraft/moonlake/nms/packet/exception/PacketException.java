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

    public PacketException() {

        this("The nms packet exception");
    }

    public PacketException(String message) {

        super(message);
    }

    public PacketException(String message, Throwable cause) {

        super(message, cause);
    }
}
