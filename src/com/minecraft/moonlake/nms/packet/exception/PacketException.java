package com.minecraft.moonlake.nms.packet.exception;

import com.minecraft.moonlake.nms.exception.NMSException;

/**
 * Created by MoonLake on 2016/9/29.
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
