package com.minecraft.moonlake.util.player;

import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.exception.player.PlayerNotOnlineException;
import com.minecraft.moonlake.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        Util.notNull(player, "待获取玩家实例对象的是 null 值");

        return Bukkit.getServer().getPlayer(player);
    }

    /**
     * 获取玩家 Player 的实例对象
     *
     * @param uuid 玩家UUID
     * @return Player
     */
    @Override
    public Player getPlayer(UUID uuid) {
        Util.notNull(uuid, "待获取玩家实例对象的是 null 值");

        return Bukkit.getServer().getPlayer(uuid);
    }

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param player 玩家名
     * @return OfflinePlayer
     */
    @Override
    @Deprecated
    public OfflinePlayer getOfflinePlayer(String player) {
        Util.notNull(player, "待获取离线玩家实例对象的是 null 值");

        return Bukkit.getServer().getOfflinePlayer(player);
    }

    /**
     * 获取离线玩家 OfflinePlayer 的实例对象
     *
     * @param uuid 玩家UUID
     * @return OfflinePlayer
     */
    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        Util.notNull(uuid, "待获取离线玩家实例对象的是 null 值");

        return Bukkit.getServer().getOfflinePlayer(uuid);
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
        Player instance = notOnline(player);

        return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer) instance).getHandle().ping;
    }

    /**
     * 给玩家发送网络数据包
     *
     * @param player 玩家名
     * @param packets 数据包
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    public void sendPacket(String player, net.minecraft.server.v1_9_R2.Packet<?>... packets) {
        Util.notNull(packets, "待发送的网络数据包是 null 值");

        Player instance = notOnline(player);
        for(int i = 0; i < packets.length; i++) {
            ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer)instance).getHandle().playerConnection.sendPacket(packets[i]);
        }
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title) {
        Util.notNull(title, "待发送的标题数据包标题是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(title) + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TITLE, icbc);

        sendPacket(player, ppot);
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param player 玩家名
     * @param title 标题
     * @param subTitle 子标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {
        Util.notNull(title, "待发送的标题数据包标题是 null 值");
        Util.notNull(subTitle, "待发送的标题数据包子标题是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(title) + "\"}");
        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc2 = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(subTitle) + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TITLE, icbc);
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot2 = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc2);

        sendPacket(player, ppot2, ppot);
    }

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
    @Override
    public void sendTitlePacket(String player, String title, int drTime, int plTime, int dcTime) {
        Util.notNull(title, "待发送的标题数据包标题是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(title) + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TITLE, icbc);
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot2 = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TIMES, null, drTime, plTime, dcTime);

        sendPacket(player, ppot2, ppot);
    }

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
    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int drTime, int plTime, int dcTime) {
        Util.notNull(title, "待发送的标题数据包标题是 null 值");
        Util.notNull(subTitle, "待发送的标题数据包子标题是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(title) + "\"}");
        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc2 = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(subTitle) + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TITLE, icbc);
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot2 = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc2);
        net.minecraft.server.v1_9_R2.PacketPlayOutTitle ppot3 = new net.minecraft.server.v1_9_R2.PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TIMES, null, drTime, plTime, dcTime);

        sendPacket(player, ppot3, ppot2, ppot);
    }

    /**
     * 给玩家发送聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @param mode 执行方式
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendChatPacket(String player, String message, ChatPacketMode mode) {
        Util.notNull(message, "待发送的聊天数据包消息是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent icbc = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.color(message) + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutChat ppoc = new net.minecraft.server.v1_9_R2.PacketPlayOutChat(icbc, mode == null ? (byte)1 : mode.getMode());

        sendPacket(player, ppoc);
    }

    /**
     * 给玩家发送默认聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendDefaultChatPacket(String player, String message) {
        sendChatPacket(player, message, ChatPacketMode.DEFAULT);
    }

    /**
     * 给玩家发送中心聊天数据包
     *
     * @param player 玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendMainChatPacket(String player, String message) {
        sendChatPacket(player, message, ChatPacketMode.MAIN);
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
        Util.notNull(header, "待发送的Tab列表数据包头文本是 null 值");
        Util.notNull(footer, "待发送的Tab列表数据包脚文本是 null 值");

        net.minecraft.server.v1_9_R2.IChatBaseComponent tabheader = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        net.minecraft.server.v1_9_R2.IChatBaseComponent tabfooter = net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter ppoplhf = new net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter(tabfooter);
        Field field = null;

        try {
            field = ppoplhf.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(ppoplhf, tabheader);
        }
        catch (Exception e) { }
        sendPacket(player, ppoplhf);
    }

    /**
     * 给玩家发送崩溃客户端数据包 (谨慎使用)
     *
     * @param player 玩家名
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendCrashClientPacket(String player) {

        Player instance = notOnline(player);
        net.minecraft.server.v1_9_R2.PacketPlayOutExplosion ppoe = new net.minecraft.server.v1_9_R2.PacketPlayOutExplosion(
                instance.getLocation().getX(),
                instance.getLocation().getY(),
                instance.getLocation().getZ(),
                Float.MAX_VALUE, new ArrayList<net.minecraft.server.v1_9_R2.BlockPosition>(),
                new net.minecraft.server.v1_9_R2.Vec3D(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE)
        );
        sendPacket(player, ppoe);
    }

    /**
     * 给玩家发送基础聊天消息
     *
     * @param player 玩家名
     * @param bc     基础聊天
     */
    @Override
    public void sendMessage(String player, BaseChat bc) {
        Util.notNull(bc, "待发送基础聊天消息的是 null 值");

        Player instance = notOnline(player);
        instance.spigot().sendMessage(bc);
    }

    /**
     * 判断玩家是否不在线则抛出异常否则返回 Player 实例对象
     *
     * @param player 玩家名
     * @return Player
     */
    @Override
    public Player notOnline(String player) {
        Player instance = getPlayer(player);
        if(instance == null || !instance.isOnline()) {
            throw new PlayerNotOnlineException();
        }
        return instance;
    }
}
