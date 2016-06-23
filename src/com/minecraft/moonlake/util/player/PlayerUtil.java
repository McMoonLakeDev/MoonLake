package com.minecraft.moonlake.util.player;

import com.minecraft.moonlake.api.nms.packet.PacketPlayOutChat;
import com.minecraft.moonlake.api.nms.packet.PacketPlayOutPlayerListHeaderFooter;
import com.minecraft.moonlake.api.nms.packet.PacketPlayOutTitle;
import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.exception.player.PlayerNotOnlineException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.util.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
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

        int ping = 0;

        try {

            ping = (Integer) Reflect.getField("EntityPlayer", Reflect.PackageType.MINECRAFT_SERVER, true, "ping").get(Reflect.getMethod("CraftPlayer", Reflect.PackageType.CRAFTBUKKIT_ENTITY, "getHandle").invoke(instance));
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return ping;
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

        new PacketPlayOutTitle(title).send(player);
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

        new PacketPlayOutTitle(title, subTitle).send(player);
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

        new PacketPlayOutTitle(title, drTime, plTime, dcTime).send(player);
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

        new PacketPlayOutTitle(title, subTitle, drTime, plTime, dcTime).send(player);
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
    public void sendChatPacket(String player, String message, PacketPlayOutChat.Mode mode) {
        Util.notNull(message, "待发送的聊天数据包消息是 null 值");

        new PacketPlayOutChat(message, mode).send(player);
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
        sendChatPacket(player, message, PacketPlayOutChat.Mode.DEFAULT);
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
        sendChatPacket(player, message, PacketPlayOutChat.Mode.MAIN);
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

        new PacketPlayOutPlayerListHeaderFooter(header, footer).send(player);
    }

    /**
     * 给玩家发送物品冷却时间数据包
     *
     * @param player 玩家名
     * @param type   物品类型
     * @param tick   冷却的时间
     */
    @Override
    public void sendItemCooldownPacket(String player, Material type, int tick) {
        Util.notNull(type, "待发送的物品冷却时间数据包物品类型是 null 值");

        Player instance = notOnline(player);

        try {

            Class<?> Item = Reflect.PackageType.MINECRAFT_SERVER.getClass("Item");
            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> ItemCooldown = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
            Class<?> CraftMagicNumbers = Reflect.PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");

            Object NMSPlayer = Reflect.getMethod(CraftPlayer, "getHandle").invoke(instance);
            Object ItemCooldownInstance = Reflect.getMethod(EntityHuman, "df").invoke(NMSPlayer);

            Method a = Reflect.getMethod(ItemCooldown, "a", Item, Integer.class);
            a.invoke(ItemCooldownInstance, Reflect.getMethod(CraftMagicNumbers, "getItem", Material.class).invoke(null, type), tick);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 获取玩家某个物品是否还有冷却时间
     *
     * @param player 玩家名
     * @param type   物品类型
     * @return true 还有冷却时间 else 无冷却时间
     */
    @Override
    public boolean hasItemCooldown(String player, Material type) {
        Util.notNull(type, "待获取的物品冷却时间物品类型是 null 值");

        Player instance = notOnline(player);

        try {

            Class<?> Item = Reflect.PackageType.MINECRAFT_SERVER.getClass("Item");
            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> ItemCooldown = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
            Class<?> CraftMagicNumbers = Reflect.PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");

            Object NMSPlayer = Reflect.getMethod(CraftPlayer, "getHandle").invoke(instance);
            Object ItemCooldownInstance = Reflect.getMethod(EntityHuman, "df").invoke(NMSPlayer);

            Method a = Reflect.getMethod(ItemCooldown, "a", Item);
            return (boolean) a.invoke(ItemCooldownInstance, Reflect.getMethod(CraftMagicNumbers, "getItem", Material.class).invoke(null, type));
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 给玩家发送基础聊天消息
     *
     * @param player   玩家名
     * @param baseComponent 基础聊天
     */
    @Override
    public void sendBaseChat(String player, BaseComponent baseComponent) {
        Util.notNull(baseComponent, "待发送的基础聊天消息是 null 值");

        Player instance = notOnline(player);

        instance.spigot().sendMessage(baseComponent);
    }

    /**
     * 给玩家发送基础聊天消息
     *
     * @param player        玩家名
     * @param baseComponent 基础聊天
     */
    @Override
    public void sendBaseChat(String player, BaseComponent... baseComponent) {
        Util.notNull(baseComponent, "待发送的基础聊天消息是 null 值");

        Player instance = notOnline(player);

        instance.spigot().sendMessage(baseComponent);
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
