package com.minecraft.moonlake.api.playerlib;

import com.minecraft.moonlake.api.nms.packet.PacketPlayOutChat;
import com.minecraft.moonlake.exception.player.PlayerNotOnlineException;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * <h1>提供玩家的API函数 (数据包、网络延迟等等)</h1>
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
    void sendChatPacket(String player, String message, PacketPlayOutChat.Mode mode);

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
     * 给玩家发送物品冷却时间数据包
     *
     * @param player 玩家名
     * @param type 物品类型
     * @param tick 冷却的时间
     * @throws com.minecraft.moonlake.exception.IllegalBukkitVersionException 如果您的服务器 Bukkit 版本低于 1.9 则抛出异常
     */
    void sendItemCooldownPacket(String player, Material type, int tick);

    /**
     * 获取玩家某个物品是否还有冷却时间
     *
     * @param player 玩家名
     * @param type 物品类型
     * @return true 还有冷却时间 else 无冷却时间
     * @throws com.minecraft.moonlake.exception.IllegalBukkitVersionException 如果您的服务器 Bukkit 版本低于 1.9 则抛出异常
     */
    boolean hasItemCooldown(String player, Material type);

    /**
     * 给玩家发送基础聊天消息
     *
     * @param player 玩家名
     * @param baseComponent 基础聊天
     */
    void sendBaseChat(String player, BaseComponent baseComponent);

    /**
     * 给玩家发送基础聊天消息
     *
     * @param player 玩家名
     * @param baseComponent 基础聊天
     */
    void sendBaseChat(String player, BaseComponent... baseComponent);

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

    /**
     * 判断玩家是否不在线则抛出异常否则返回 Player 实例对象
     *
     * @param player 玩家名
     * @return Player
     */
    Player notOnline(String player);
}
