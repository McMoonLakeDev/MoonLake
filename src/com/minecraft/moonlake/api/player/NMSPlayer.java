package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.nms.packet.Packet;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/7/17.
 */
public interface NMSPlayer {

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    ReadOnlyIntegerProperty getPing();

    /**
     * 给此玩家发送数据包
     * 
     * @param packet 数据包
     * @throws IllegalArgumentException 如果数据包对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendPacket(Packet<?> packet);
    
    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String title);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param subTitle 子标题
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String title, String subTitle);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String title, int fadeIn, int stay, int fadeOut);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTitlePacket(String title, String subTitle, int fadeIn, int stay, int fadeOut);

    /**
     * 给此玩家发送主聊天消息 (经验条上面)
     *
     * @param message 消息
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendMainChatPacket(String message);

    /**
     * 给此玩家发送 TAB 列表数据包
     *
     * @param header 头内容
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTabListPacket(String header);

    /**
     * 给此玩家发送 TAB 列表数据包
     *
     * @param header 头内容
     * @param footer 脚内容
     * @throws IllegalArgumentException 如果头内容对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果脚内容对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     */
    void sendTabListPacket(String header, String footer);

    /**
     * 给此玩家设置物品栈冷却时间
     *
     * @param material 物品栈类型
     * @param tick 时间 Tick (1s = 20tick)
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    void setItemCooldown(Material material, int tick);

    /**
     * 获取此玩家物品栈类型是否拥有冷却时间
     *
     * @param material 物品栈类型
     * @return true 则物品栈类型拥有冷却时间
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    ReadOnlyBooleanProperty hasItemCooldown(Material material);
}
