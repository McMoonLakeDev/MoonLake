/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet;

import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.executor.Executor;
import org.bukkit.entity.Player;

/**
 * <h1>PacketPlayInExecutor</h1>
 * Minecraft 数据包输入执行者接口
 *
 * @version 1.0
 * @author Month_Light
 * @see Executor
 */
public interface PacketPlayInExecutor extends Executor<Player[]> {

    /**
     * 将此 Minecraft 输入数据包执行给指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果执行错误则抛出异常
     */
    @Override
    void execute(Player... players) throws PacketException;

    /**
     * 将此 Minecraft 输入数据包执行给指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果执行错误则抛出异常
     */
    void execute(MoonLakePlayer... players) throws PacketException;

    /**
     * 将此 Minecraft 输入数据包执行给指定玩家
     *
     * @param players 玩家
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PacketException 如果执行错误则抛出异常
     */
    void execute(String... players) throws PacketException;

    /**
     * 将此 Minecraft 输入数据包执行给在线所有玩家
     *
     * @throws PacketException 如果执行错误则抛出异常
     */
    void executeAll() throws PacketException;
}
