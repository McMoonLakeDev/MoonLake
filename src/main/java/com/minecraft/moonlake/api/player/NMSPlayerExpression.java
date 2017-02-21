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

import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutChat;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutPlayerListHeaderFooter;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutTitle;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>NMSPlayerExpression</h1>
 * 玩家 NMS 接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NMSPlayerExpression implements NMSPlayerLibrary {

    private Class<?> CLASS_CRAFTPLAYER;
    private Class<?> CLASS_ENTITYPLAYER;
    private Method METHOD_GETHANDLE;
    private Field FIELD_PING;

    /**
     * 玩家 NMS 接口实现类构造函数
     *
     * @throws IllegalBukkitVersionException 如果服务端版本不支持则抛出异常
     */
    public NMSPlayerExpression() throws IllegalBukkitVersionException {

        try {

            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            FIELD_PING = getField(CLASS_ENTITYPLAYER, true, "ping");
        }
        catch (Exception e) {

            throw new IllegalBukkitVersionException("The nms player library reflect raw expcetion.", e);
        }
    }

    @Override
    public int getPing(String player) {

        Validate.notNull(player, "The player string object is null.");

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        try {

            return (Integer) FIELD_PING.get(METHOD_GETHANDLE.invoke(target));
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void sendTitlePacket(String player, String title) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");

        new PacketPlayOutTitle(title).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");
        Validate.notNull(subTitle, "The sub title string object is null.");

        new PacketPlayOutTitle(title, subTitle).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, int fadeIn, int stay, int fadeOut) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");

        new PacketPlayOutTitle(title, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");
        Validate.notNull(subTitle, "The sub title string object is null.");

        new PacketPlayOutTitle(title, subTitle, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendMainChatPacket(String player, String message) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(message, "The message string object is null.");

        new PacketPlayOutChat(message, PacketPlayOutChat.Mode.HOTBAR).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(header, "The header string object is null.");

        new PacketPlayOutPlayerListHeaderFooter(header).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header, String footer) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(header, "The header string object is null.");
        Validate.notNull(footer, "The footer string object is null.");

        new PacketPlayOutPlayerListHeaderFooter(header, footer).send(player);
    }
}
