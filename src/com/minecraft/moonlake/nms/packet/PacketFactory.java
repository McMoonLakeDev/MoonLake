package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.reflect.Reflect;

/**
 * Created by MoonLake on 2016/10/1.
 */
public class PacketFactory {

    /**
     * PacketFactory Static Instance;
     */
    private static PacketFactory packetFactoryInstance;

    private PacketFactory() {

    }

    /**
     * 获取 PacketFactory 对象
     *
     * @return PacketFactory
     */
    public static PacketFactory get() {

        if(packetFactoryInstance == null) {

            packetFactoryInstance = new PacketFactory();
        }
        return packetFactoryInstance;
    }

    /**
     * 获取指定 Packet 的实例对象
     *
     * @param clazz Packet
     * @param <T> Packet
     * @return Packet 实例对象
     * @throws PacketException 如果获取错误则抛出异常
     * @deprecated 已过时, 建议使用参数获取实例对象
     * @see #instance(Class, Object...)
     */
    @Deprecated
    public <T extends Packet> T instance(Class<T> clazz) throws PacketException {

        return instance(clazz, new Object[] { });
    }

    /**
     * 获取指定 Packet 的实例对象
     *
     * @param clazz Packet
     * @param args Packet 构造参数
     * @param <T> Packet
     * @return Packet 实例对象
     * @throws PacketException 如果获取错误则抛出异常
     */
    public <T extends Packet> T instance(Class<T> clazz, Object... args) throws PacketException {

        try {

            return (T) Reflect.instantiateObject(clazz, args);
        }
        catch (Exception e) {

            throw new PacketException("The get nms packet instance exception.", e);
        }
    }
}
