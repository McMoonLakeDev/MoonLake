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
 
 
package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPlayerInfo</h1>
 * 数据包输出玩家信息抽象类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutPlayerInfo}
 */
@Deprecated
public class PacketPlayOutPlayerInfo extends PacketAbstract<PacketPlayOutPlayerInfo> {

    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERINFO;
    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION;
    private final static Class<?> CLASS_ENTITYPLAYER;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTPLAYERINFO = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo");
            CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "EnumPlayerInfoAction" : "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            METHOD_VALUEOF = getMethod(CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION, "valueOf", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out player info reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<Action> action;
    private ObjectProperty<Player> player;

    /**
     * 数据包输出玩家信息抽象类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutPlayerInfo(Action, Player)}
     * @see #PacketPlayOutPlayerInfo(Action, Player)
     */
    @Deprecated
    public PacketPlayOutPlayerInfo() {

        this(null, null);
    }

    /**
     * 数据包输出玩家信息抽象类构造函数
     *
     * @param action 交互类型
     * @param player 玩家
     */
    public PacketPlayOutPlayerInfo(Action action, Player player) {

        this.action = new SimpleObjectProperty<>(action);
        this.player = new SimpleObjectProperty<>(player);
    }

    /**
     * 获取此数据包输出玩家信息的交互类型
     *
     * @return 交互类型
     */
    public ObjectProperty<Action> getAction() {

        return action;
    }

    /**
     * 获取此数据包输出玩家信息的玩家
     *
     * @return 玩家
     */
    public ObjectProperty<Player> getPlayer() {

        return player;
    }

    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object enumPlayerInfoAction = METHOD_VALUEOF.invoke(null, getAction().get().name());
            Object nmsPlayer = PacketReflect.get().getNMSPlayer(getPlayer().get());
            Object arrayEntityPlayerObj = Array.newInstance(CLASS_ENTITYPLAYER, 1);

            Array.set(arrayEntityPlayerObj, 0, nmsPlayer);

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERINFO, enumPlayerInfoAction, arrayEntityPlayerObj);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out player info send exception.", e);
        }
    }

    /**
     * <h1>Action</h1>
     * 数据包输出玩家信息交互类型
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum Action {

        /**
         * 玩家信息交互类型: 添加玩家
         */
        ADD_PLAYER,
        /**
         * 玩家信息交互类型: 更新游戏模式
         */
        UPDATE_GAME_MODE,
        /**
         * 玩家信息交互类型: 更新延迟
         */
        UPDATE_LATENCY,
        /**
         * 玩家信息交互类型: 更新显示名称
         */
        UPDATE_DISPLAY_NAME,
        /**
         * 玩家信息交互类型: 删除玩家
         */
        REMOVE_PLAYER,;
    }

    /**
     * 获取对应版本的 PacketPlayOutPlayerInfo 实例对象
     *
     * @param action 交互
     * @param player 玩家
     * @return PacketPlayOutPlayerInfo
     * @throws IllegalArgumentException 如果交互对象或玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果不存在或获取错误则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 已修复版本兼容问题.
     */
    @Deprecated
    public static PacketPlayOutPlayerInfo get(Action action, Player player) throws PacketException {

        Validate.notNull(action, "The player info action object is null.");
        Validate.notNull(player, "The target player object is null.");

        return new PacketPlayOutPlayerInfo(action, player);
    }

    /**
     * 获取对应版本的 PacketPlayOutPlayerInfo 实例对象
     *
     * @param action 交互
     * @param player 玩家
     * @return PacketPlayOutPlayerInfo
     * @throws IllegalArgumentException 如果交互对象或玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果不存在或获取错误则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 已修复版本兼容问题.
     */
    @Deprecated
    public static PacketPlayOutPlayerInfo get(Action action, MoonLakePlayer player) throws PacketException {

        Validate.notNull(action, "The player info action object is null.");
        Validate.notNull(player, "The player object is null.");

        return new PacketPlayOutPlayerInfo(action, player.getBukkitPlayer());
    }
}
