package com.minecraft.moonlake.api.playerlib;

import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/4/27.
 * @version 1.0
 * @author Month_Light
 */
public interface Playerlib {

    /**
     * 获取玩家 Player 的实例对象
     *
     * @param player 玩家名
     * @return Player
     */
    Player getPlayer(String player);

    /**
     * 获取玩家 Player 的实例对象
     *
     * @param uuid 玩家UUID
     * @return Player
     */
    Player getPlayer(UUID uuid);

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param player 玩家名
     * @return OfflinePlayer
     */
    @Deprecated
    OfflinePlayer getOfflinePlayer(String player);

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param uuid 玩家UUID
     * @return OfflinePlayer
     */
    OfflinePlayer getOfflinePlayer(UUID uuid);

    /**
     * 获取玩家的网络延迟 (ms)
     *
     * @param player 玩家名
     * @return 网络延迟 (ms)
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    int getPing(String player);

    /**
     * 给玩家发送网络数据包
     *
     * @param player 玩家名
     * @param packets 数据包
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendPacket(String player, net.minecraft.server.v1_9_R1.Packet<?>... packets);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @param subTitle 子标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, String subTitle);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @param drTime 淡入时间
     * @param plTime 停留时间
     * @param dcTime 淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, int drTime, int plTime, int dcTime);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @param subTitle 子标题
     * @param drTime 淡入时间
     * @param plTime 停留时间
     * @param dcTime 淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, String subTitle, int drTime, int plTime, int dcTime);

    /**
     * 给玩家发送聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @param mode 执行方式
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendChatPacket(String player, String message, ChatPacketMode mode);

    /**
     * 给玩家发送默认聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendDefaultChatPacket(String player, String message);

    /**
     * 给玩家发送中心聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendMainChatPacket(String player, String message);

    /**
     * 给玩家发送Tab列表数据包
     *
     * @param player 玩家名
     * @param header 头文本
     * @param footer 脚文本
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTabListPacket(String player, String header, String footer);

    /**
     * 给玩家发送崩溃客户端数据包 (谨慎使用)
     *
     * @param player 玩家名
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendCrashClientPacket(String player);

    /**
     * 聊天数据包的执行方式
     */
    enum ChatPacketMode {

        /**
         * 聊天数据包默认位置
         */
        DEFAULT((byte)1),
        /**
         * 聊天数据包中心位置
         */
        MAIN((byte)2),;

        private byte mode;

        ChatPacketMode(byte mode) {
            this.mode = mode;
        }

        /**
         * 获取聊天数据包方式
         *
         * @return 方式
         */
        public byte getMode() {
            return mode;
        }
    }
}
