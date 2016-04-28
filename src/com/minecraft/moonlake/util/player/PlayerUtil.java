package com.minecraft.moonlake.util.player;

import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/4/28.
 * @version 1.0
 * @author Month_Light
 */
public class PlayerUtil implements Playerlib {

    static {}

    public PlayerUtil() {

    }

    /**
     * 获取玩家 Player 的实例对象
     *
     * @param player 玩家名
     * @return Player
     */
    @Override
    public Player getPlayer(String player) {
        return null;
    }

    /**
     * 获取玩家 Player 的实例对象
     *
     * @param uuid 玩家UUID
     * @return Player
     */
    @Override
    public Player getPlayer(UUID uuid) {
        return null;
    }

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param player 玩家名
     * @return OfflinePlayer
     */
    @Override
    public OfflinePlayer getOfflinePlayer(String player) {
        return null;
    }

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param uuid 玩家UUID
     * @return OfflinePlayer
     */
    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return null;
    }

    /**
     * 获取玩家的网络延迟 (ms)
     *
     * @param player 玩家名
     * @return 网络延迟 (ms)
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public int getPing(String player) {
        return 0;
    }

    /**
     * 给玩家发送网络数据包
     *
     * @param player 玩家名
     * @param packet 数据包
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendPacket(String player, net.minecraft.server.v1_9_R1.Packet<?> packet) {

    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title  标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title) {

    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player   玩家名
     * @param title    标题
     * @param subTitle 子标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {

    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title  标题
     * @param drTime 淡入时间
     * @param plTime 停留时间
     * @param dcTime 淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title, int drTime, int plTime, int dcTime) {

    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player   玩家名
     * @param title    标题
     * @param subTitle 子标题
     * @param drTime   淡入时间
     * @param plTime   停留时间
     * @param dcTime   淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int drTime, int plTime, int dcTime) {

    }

    /**
     * 给玩家发送聊天数据包
     *
     * @param player  玩家名
     * @param message 消息
     * @param mode    执行方式
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendChatPacket(String player, String message, ChatPacketMode mode) {

    }

    /**
     * 给玩家发送默认聊天数据包
     *
     * @param player  玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendDefaultChatPacket(String player, String message) {

    }

    /**
     * 给玩家发送中心聊天数据包
     *
     * @param player  玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendMainChatPacket(String player, String message) {

    }

    /**
     * 给玩家发送Tab列表数据包
     *
     * @param player 玩家名
     * @param header 头文本
     * @param footer 脚文本
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTabListPacket(String player, String header, String footer) {

    }

    /**
     * 给玩家发送崩溃客户端数据包 (谨慎使用)
     *
     * @param player 玩家名
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendCrashClientPacket(String player) {

    }
}
