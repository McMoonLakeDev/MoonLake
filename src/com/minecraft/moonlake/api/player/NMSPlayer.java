package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.nms.packet.Packet;
import com.minecraft.moonlake.exception.player.PlayerNotOnlineException;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/7/17.
 */
public interface NMSPlayer {

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     */
    int getPing();

    /**
     * 给玩家发送数据包
     *
     * @param packet 数据包
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendPacket(Packet<?> packet);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String title);

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @param subTitle 子标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTitlePacket(String title, String subTitle);

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
    void sendTitlePacket(String title, int drTime, int plTime, int dcTime);

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
    void sendTitlePacket(String title, String subTitle, int drTime, int plTime, int dcTime);

    /**
     * 给玩家发送中心聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendMainChatPacket(String message);

    /**
     * 给玩家发送Tab列表数据包
     *
     * @param player 玩家名
     * @param header 头文本
     * @param footer 脚文本
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    void sendTabListPacket(String header, String footer);

    /**
     * 给玩家发送物品冷却时间数据包
     *
     * @param player 玩家名
     * @param type 物品类型
     * @param tick 冷却的时间
     */
    void sendItemCooldownPacket(Material type, int tick);

    /**
     * 获取玩家某个物品是否还有冷却时间
     *
     * @param player 玩家名
     * @param type 物品类型
     * @return true 还有冷却时间 else 无冷却时间
     */
    boolean hasItemCooldown(Material type);
}
