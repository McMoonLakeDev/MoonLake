package com.minecraft.moonlake.nms.packet.exception;

/**
 * Created by MoonLake on 2016/9/29.
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
