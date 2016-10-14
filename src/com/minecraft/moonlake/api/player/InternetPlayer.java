package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.PlayerNotOnlineException;

import java.net.InetSocketAddress;

/**
 * <h1>InternetPlayer</h1>
 * 玩家网络接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface InternetPlayer {

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    int getPing();

    /**
     * 获取此玩家的网络套接字地址
     *
     * @return 网络套接字地址
     */
    InetSocketAddress getAddress();

    /**
     * 获取此玩家的网络套接字地址 IP
     *
     * @return 网络套接字地址 IP 如果未解析则返回 127.0.0.1
     */
    String getIp();

    /**
     * 获取此玩家的网络套接字地址端口号
     *
     * @return 网络套接字地址端口号
     */
    int getPort();
}
