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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.event.core.MoonLakePacketOutBungeeEvent;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBungee;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * <h1>PacketPlayOutBungeeAbstract</h1>
 * 数据包输出蹦极抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see PacketPlayOutBungee
 */
public abstract class PacketPlayOutBungeeAbstract extends PacketPlayOutAbstract implements PacketPlayOutBungee {

    private final ByteArrayOutputStream data;

    final DataOutputStream dataOut;

    /**
     * 数据包输出蹦极抽象类构造函数
     */
    PacketPlayOutBungeeAbstract() {

        this.data = new ByteArrayOutputStream();
        this.dataOut = new DataOutputStream(data);
    }

    @Override
    protected boolean fireEvent(PacketPlayOut packet, Player... players) {

        if(super.fireEvent(packet, players))
            return true;

        MoonLakePacketOutBungeeEvent mlpobe = new MoonLakePacketOutBungeeEvent(this, players);
        MoonLakeAPI.callEvent(mlpobe);
        return mlpobe.isCancelled();
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        throw new UnsupportedOperationException();
    }

    /**
     * 将需要发送的数据写入到 {@link #dataOut} 对象内
     *
     * @throws IOException 如果 IO 异常则抛出
     */
    protected abstract void write() throws IOException;

    private void writePacket() {

        try {

            write();
        }
        catch (Exception e) {

            throw new PacketException("The write packet data out stream exception.", e);
        }
    }

    @Override
    public byte[] getDataByteArray() {

        if(data.size() == 0) // 如果数据大小为 0 则说明没有 write 则调用函数写入数据
            writePacket();

        return data.toByteArray();
    }

    @Override
    public void sendPacket(Plugin plugin, Player... senders) throws PacketException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(senders, "The sender player object is null.");

        if(fireEvent(this, senders))
            return;

        for(Player sender : senders)
            sender.sendPluginMessage(plugin, CHANNEL, getDataByteArray());
    }

    @Override
    public void sendPacketAsync(Plugin plugin, Player... senders) throws PacketException {

        MoonLakeAPI.runTaskAsync(plugin, new Runnable() {

            @Override
            public void run() {

                sendPacket(plugin, senders);
            }
        });
    }

    @Override
    public void sendPacketUnsafe(Plugin plugin, Player... senders) throws PacketException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(senders, "The sender player object is null.");

        if(fireEvent(this, senders))
            return;

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, getDataByteArray());
        packet.send(senders);
    }

    @Override
    public void sendPacketUnsafeAsync(Plugin plugin, Player... senders) throws PacketException {

        MoonLakeAPI.runTaskAsync(plugin, new Runnable() {

            @Override
            public void run() {

                sendPacketUnsafe(plugin, senders);
            }
        });
    }

    @Override
    public void sendPacket(Plugin plugin, MoonLakePlayer... senders) throws PacketException {

        sendPacket(plugin, PlayerManager.adapter(senders));
    }

    @Override
    public void sendPacketAsync(Plugin plugin, MoonLakePlayer... senders) throws PacketException {

        sendPacketAsync(plugin, PlayerManager.adapter(senders));
    }

    @Override
    public void sendPacketUnsafe(Plugin plugin, MoonLakePlayer... senders) throws PacketException {

        sendPacketUnsafe(plugin, PlayerManager.adapter(senders));
    }

    @Override
    public void sendPacketUnsafeAsync(Plugin plugin, MoonLakePlayer... senders) throws PacketException {

        sendPacketUnsafeAsync(plugin, PlayerManager.adapter(senders));
    }

    @Override
    public void close() throws Exception {

        dataOut.close();
    }
}
