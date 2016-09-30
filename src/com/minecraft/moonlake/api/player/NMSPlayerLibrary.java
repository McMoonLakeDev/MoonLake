package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;

/**
 * Created by MoonLake on 2016/9/14.
 */
public interface NMSPlayerLibrary {

    /**
     * 获取指定玩家的网络 Ping 值
     *
     * @param player 玩家名
     * @return Ping 值
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    ReadOnlyIntegerProperty getPing(String player);

    /**
     * 给指定玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 主标题
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title);

    /**
     * 给指定玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 主标题
     * @param subTitle 子标题
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, String subTitle);

    /**
     * 给指定玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 主标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, int fadeIn, int stay, int fadeOut);

    /**
     * 给指定玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String player, String title, String subTitle, int fadeIn, int stay, int fadeOut);

    /**
     * 给指定玩家发送主聊天消息 (经验条上面)
     *
     * @param player 玩家名
     * @param message 消息
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendMainChatPacket(String player, String message);

    /**
     * 给指定玩家发送 TAB 列表数据包
     *
     * @param player 玩家名
     * @param header 头内容
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头内容对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTabListPacket(String player, String header);

    /**
     * 给指定玩家发送 TAB 列表数据包
     *
     * @param player 玩家名
     * @param header 头内容
     * @param footer 脚内容
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头内容对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果脚内容对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTabListPacket(String player, String header, String footer);
}
