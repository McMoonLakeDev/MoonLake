/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
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
