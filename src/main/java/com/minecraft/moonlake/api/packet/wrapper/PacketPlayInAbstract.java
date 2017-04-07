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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayIn;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * <h1>PacketPlayInAbstract</h1>
 * Minecraft 数据包输入抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayIn
 */
public abstract class PacketPlayInAbstract implements PacketPlayIn {

    /**
     * 触发数据包输入事件
     *
     * @param packet 数据包对象
     * @return 是否阻止
     */
    protected boolean fireEvent(PacketPlayIn packet) {

        return false;
    }

    @Override
    public String getPacketName() {

        return null;
    }

    @Override
    public Class<?> getPacketClass() {

        return null;
    }

    @Override
    public abstract void execute(Player... players) throws PacketException;

    @Override
    public void execute(MoonLakePlayer... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        execute(PlayerManager.adapter(players));
    }

    @Override
    public void execute(String... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        execute(PlayerManager.adapter(players));
    }

    @Override
    public void executeAll() throws PacketException {

        execute(PlayerManager.getOnlines());
    }
}
