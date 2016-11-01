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


package com.minecraft.moonlake.api.event.player;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>MoonLakePlayerJoinEvent</h1>
 * 月色之湖玩家加入事件类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakePlayerEvent
 */
public class MoonLakePlayerJoinEvent extends MoonLakePlayerEvent {

    private final static HandlerList handlerList = new HandlerList();
    private final ProtocolVersion protocolVersion;
    private String joinMessage;

    /**
     * 月色之湖玩家加入事件类构造函数
     *
     * @param player Bukkit 玩家
     * @param joinMessage 加入消息
     * @param protocolVersion 客户端版本
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #MoonLakePlayerJoinEvent(MoonLakePlayer, String, ProtocolVersion)}
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public MoonLakePlayerJoinEvent(Player player, String joinMessage, ProtocolVersion protocolVersion) throws IllegalArgumentException {

        super(player);

        this.joinMessage = joinMessage;
        this.protocolVersion = protocolVersion;
    }

    /**
     * 月色之湖玩家加入事件类构造函数
     *
     * @param moonLakePlayer MoonLake 玩家
     * @param joinMessage 加入消息
     * @param protocolVersion 客户端版本
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public MoonLakePlayerJoinEvent(MoonLakePlayer moonLakePlayer, String joinMessage, ProtocolVersion protocolVersion) throws IllegalArgumentException {

        super(moonLakePlayer);

        this.joinMessage = joinMessage;
        this.protocolVersion = protocolVersion;
    }

    @Override
    public HandlerList getHandlers() {

        return handlerList;
    }

    public static HandlerList getHandlerList() {

        return handlerList;
    }

    /**
     * 获取此玩家的客户端版本号
     *
     * @return 客户端版本
     */
    public ProtocolVersion getProtocolVersion() {

        return protocolVersion;
    }

    /**
     * 设置此玩家的加入消息
     *
     * @param joinMessage 加入消息
     */
    public void setJoinMessage(String joinMessage) {

        this.joinMessage = joinMessage;
    }

    /**
     * 获取此玩家的加入消息
     *
     * @return 加入消息
     */
    public String getJoinMessage() {

        return joinMessage;
    }

    /**
     * <h1>ProtocolVersion</h1>
     * 客户端协议版本号（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum ProtocolVersion {

        //
        // 均来自 wiki.vg 链接: http://wiki.vg/Protocol_version_numbers#Versions_after_the_Netty_rewrite
        // 注意: 不包括快照版, 并且是新版协议号, 最低只有 1.7
        ///

        UNKNOWN(-1),
        v1_7_2(4),
        v1_7_4(4),
        v1_7_5(4),
        v1_7_6(5),
        v1_7_7(5),
        v1_7_8(5),
        v1_7_9(5),
        v1_7_10(5),
        v1_8(47),
        v1_8_1(47),
        v1_8_2(47),
        v1_8_3(47),
        v1_8_4(47),
        v1_8_5(47),
        v1_8_6(47),
        v1_8_7(47),
        v1_8_8(47),
        v1_8_9(47),
        v1_9(107),
        v1_9_1(108),
        v1_9_2(109),
        v1_9_3(110),
        v1_9_4(110),
        v1_10(210),
        v1_10_1(210),
        v1_10_2(210),
        ;

        private final int protocol;
        private final static Map<Integer, ProtocolVersion> ID_MAP;

        static {

            ID_MAP = new HashMap<>();

            for(final ProtocolVersion protocolVersion : values()) {

                ID_MAP.put(protocolVersion.protocol, protocolVersion);
            }
        }

        /**
         * 客户端协议版本号类构造函数
         *
         * @param protocol 协议号
         */
        ProtocolVersion(int protocol) {

            this.protocol = protocol;
        }

        /**
         * 获取此客户端协议版本的协议号
         *
         * @return 协议号
         */
        public int getProtocol() {

            return protocol;
        }

        /**
         * 获取此客户端协议版本是否在指定协议版本之前
         *
         * @param protocolVersion 协议版本号
         * @return 是否参数协议版本之前
         */
        public boolean olderThan(ProtocolVersion protocolVersion) {

            return protocol < protocolVersion.getProtocol();
        }

        /**
         * 获取此客户端协议版本是否在指定协议版本之后
         *
         * @param protocolVersion 协议版本号
         * @return 是否参数协议版本之后
         */
        public boolean newerThan(ProtocolVersion protocolVersion) {

            return protocol >= protocolVersion.getProtocol();
        }

        /**
         * 获取此客户端协议版本是否在指定范围
         *
         * @param oldProtocolVersion 旧协议版本号
         * @param newProtocolVersion 新协议版本号
         * @return 是否在指定范围
         */
        public boolean isRange(ProtocolVersion oldProtocolVersion, ProtocolVersion newProtocolVersion) {

            return newerThan(oldProtocolVersion) && olderThan(newProtocolVersion);
        }

        @Override
        public String toString() {

            return name();
        }

        /**
         * 将指定协议号转换到协议版本号类型
         *
         * @param protocol 协议号
         * @return ProtocolVersion
         */
        public static ProtocolVersion fromProtocol(int protocol) {

            ProtocolVersion protocolVersion = ID_MAP.get(protocol);
            return protocolVersion == null ? UNKNOWN : protocolVersion;
        }
    }
}
