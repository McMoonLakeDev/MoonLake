package com.minecraft.moonlake.nms.packet.exception;

/**
 * <h1>PacketInitializeException</h1>
 * 数据包初始化异常类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketInitializeException extends PacketException {

    public PacketInitializeException() {

        this("The nms packet initialize exception");
    }

    public PacketInitializeException(String message) {

        super(message);
    }

    public PacketInitializeException(String message, Throwable cause) {

        super(message, cause);
    }
}
