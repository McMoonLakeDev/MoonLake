package com.minecraft.moonlake.api.nms.packet;

/**
 * Created by MoonLake on 2016/6/23.
 */
public interface Packet<T extends Packet> {

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    void send(String... names);
}
