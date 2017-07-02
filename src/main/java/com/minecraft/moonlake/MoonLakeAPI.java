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


package com.minecraft.moonlake;

import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotationAPI;
import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotationFactory;
import com.minecraft.moonlake.api.annotation.plugin.command.CommandAnnotation;
import com.minecraft.moonlake.api.annotation.plugin.config.ConfigAnnotation;
import com.minecraft.moonlake.api.anvil.AnvilWindow;
import com.minecraft.moonlake.api.anvil.AnvilWindowFactory;
import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.chat.ChatComponentFancy;
import com.minecraft.moonlake.api.event.MoonLakeEvent;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.api.fancy.FancyMessageFactory;
import com.minecraft.moonlake.api.fancy.TextualComponent;
import com.minecraft.moonlake.api.i18n.I18n;
import com.minecraft.moonlake.api.i18n.I18nFactory;
import com.minecraft.moonlake.api.item.ItemBuilder;
import com.minecraft.moonlake.api.item.ItemLibrary;
import com.minecraft.moonlake.api.item.ItemLibraryFactorys;
import com.minecraft.moonlake.api.item.firework.FireworkBuilder;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.api.nbt.NBTLibrary;
import com.minecraft.moonlake.api.nbt.NBTList;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.PacketPlayOutBungee;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.listener.PacketMessageListener;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutChat;
import com.minecraft.moonlake.api.player.DependPlayerFactory;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.player.PlayerLibrary;
import com.minecraft.moonlake.api.player.PlayerLibraryFactorys;
import com.minecraft.moonlake.api.player.depend.DependEconomy;
import com.minecraft.moonlake.api.player.depend.DependEconomyVault;
import com.minecraft.moonlake.api.player.depend.DependPermissionsEx;
import com.minecraft.moonlake.api.player.depend.DependWorldEdit;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.exception.CannotDependException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.mysql.MySQLConnection;
import com.minecraft.moonlake.mysql.MySQLFactory;
import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.particle.ParticleEffect;
import com.minecraft.moonlake.particle.ParticleException;
import com.minecraft.moonlake.task.MoonLakeRunnable;
import com.minecraft.moonlake.task.TaskHelper;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>MoonLakeAPI</h1>
 * 月色之湖插件 API 类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class MoonLakeAPI {

    private static MoonLakePlugin moonlake;

    private MoonLakeAPI() {
    }

    public static void setMoonlake(MoonLakePlugin moonLakePlugin) {

        if(moonlake != null) {

            throw new UnsupportedOperationException("Cannot redefine singleton MoonLake.");
        }
        moonlake = moonLakePlugin;
    }

    /**
     * 获取月色之湖插件对象
     *
     * @return 插件对象
     */
    public static MoonLakePlugin getMoonLake() {

        return moonlake;
    }

    /**
     * 获取控制台日志对象
     *
     * @return 日志对象
     */
    public static Logger getLogger() {

        return moonlake.getLogger();
    }

    /**
     * 获取月色之湖插件配置对象
     *
     * @return 插件配置对象
     */
    public static MoonLakePluginConfig getConfiguration() {

        return moonlake.getConfiguration();
    }

    /**
     * 获取是否开启插件的 Debug 功能
     *
     * @return 是否开启
     * @see MoonLakePluginConfig#isDebug()
     */
    public static boolean isDebug() {

        return MoonLakePluginDebug.isDebug();
    }

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    public static String getPluginPrefix() {

        return moonlake.getPluginPrefix();
    }

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    public static String getPluginName() {

        return moonlake.getPluginName();
    }

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    public static String getPluginMain() {

        return moonlake.getPluginMain();
    }

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    public static String getPluginVersion() {

        return moonlake.getPluginVersion();
    }

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    public static String getPluginWebsite() {

        return moonlake.getPluginWebsite();
    }

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    public static String getPluginDescription() {

        return moonlake.getPluginDescription();
    }

    /**
     * 获取插件的作者
     *
     * @return Author
     */
    public static Set<String> getPluginAuthors() {

        return moonlake.getPluginAuthors();
    }

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    public static Set<String> getPluginDepends() {

        return moonlake.getPluginDepends();
    }

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    public static Set<String> getPluginSoftDepends() {

        return moonlake.getPluginSoftDepends();
    }

    /**
     * 获取 Bukkit 服务器的版本
     *
     * @return 版本
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #currentBukkitVersionString()}
     */
    @Deprecated
    public static String getBukkitVersion() { // TODO 2.0

        //return moonlake.getBukkitVersion();
        //return Reflect.getServerVersion();
        return currentBukkitVersionString();
    }

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static int getReleaseNumber() { // TODO 2.0

        //return moonlake.getReleaseNumber();
        //return Reflect.getServerVersionNumber();
        return currentBukkitVersion().getRelease();
    }

    /**
     * 获取 Minecraft Bukkit 服务器的版本字符串
     *
     * @return 版本字符串
     */
    public static String currentBukkitVersionString() {

        return currentBukkitVersion().getVersion();
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本
     *
     * @return Bukkit 版本 | {@code null}
     */
    public static MinecraftBukkitVersion currentBukkitVersion() {

        return MinecraftBukkitVersion.getCurrentVersion();
    }

    /**
     * 获取此 Minecraft 服务器的版本
     *
     * @return MC 版本 | {@code null}
     */
    public static MinecraftVersion currentMCVersion() {

        return MinecraftVersion.getCurrentVersion();
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本是否符合目标版本
     *
     * @param target 目标版本
     * @return 是否符合目标版本
     */
    public static boolean currentBukkitVersionIs(MinecraftBukkitVersion target) {

        return currentBukkitVersion().equals(target);
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本是否在目标版本之后
     *
     * @param target 目标版本
     * @return 是否在目标版本之后
     * @see MinecraftBukkitVersion#isLater(MinecraftBukkitVersion)
     */
    public static boolean currentBukkitVersionIsLater(MinecraftBukkitVersion target) {

        return currentBukkitVersion().isLater(target);
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本是否在目标版本或之后
     *
     * @param target 目标版本
     * @return 是否在目标版本或之后
     * @see MinecraftBukkitVersion#isOrLater(MinecraftBukkitVersion)
     */
    public static boolean currentBukkitVersionIsOrLater(MinecraftBukkitVersion target) {

        return currentBukkitVersion().isOrLater(target);
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本是否在参数 {@code min} 版本和参数 {@code max} 范围
     *
     * @param min 最小版本
     * @param max 最大版本
     * @return 是否在参数 {@code min} 版本和参数 {@code max} 范围
     * @see MinecraftBukkitVersion#isRange(MinecraftBukkitVersion, MinecraftBukkitVersion)
     */
    public static boolean currentBukkitVersionIsRange(MinecraftBukkitVersion min, MinecraftBukkitVersion max) {

        return currentBukkitVersion().isRange(min, max);
    }

    /**
     * 获取此 Minecraft Bukkit 服务器的版本是否在参数 {@code min} 版本和参数 {@code max} 范围或
     *
     * @param min 最小版本
     * @param max 最大版本
     * @return 是否在参数 {@code min} 版本和参数 {@code max} 范围或
     * @see MinecraftBukkitVersion#isOrRange(MinecraftBukkitVersion, MinecraftBukkitVersion)
     */
    public static boolean currentBukkitVersionIsOrRange(MinecraftBukkitVersion min, MinecraftBukkitVersion max) {

        return currentBukkitVersion().isOrRange(min, max);
    }

    /**
     * 获取 ItemLibrary 对象
     * 
     * @return ItemLibrary
     */
    public static ItemLibrary getItemLibrary() {
        
        return ItemLibraryFactorys.item();
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param itemStack 物品栈对象
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(ItemStack itemStack) {

        return ItemLibraryFactorys.itemBuilder(itemStack);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(Material material) {

        return ItemLibraryFactorys.itemBuilder(material);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(Material material, int data) {

        return ItemLibraryFactorys.itemBuilder(material, data);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(Material material, int data, int amount) {

        return ItemLibraryFactorys.itemBuilder(material, data, amount);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(Material material, int data, int amount, String displayName) {

        return ItemLibraryFactorys.itemBuilder(material, data, amount, displayName);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签信息
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(Material material, int data, int amount, String displayName, String... lore) {

        return ItemLibraryFactorys.itemBuilder(material, data, amount, displayName, lore);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(PotionType potion) {

        return ItemLibraryFactorys.itemBuilder(potion);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(PotionType potion, int data) {

        return ItemLibraryFactorys.itemBuilder(potion, data);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(PotionType potion, int data, int amount) {

        return ItemLibraryFactorys.itemBuilder(potion, data, amount);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(PotionType potion, int data, int amount, String displayName) {

        return ItemLibraryFactorys.itemBuilder(potion, data, amount, displayName);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签信息
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder newItemBuilder(PotionType potion, int data, int amount, String displayName, String... lore) {

        return ItemLibraryFactorys.itemBuilder(potion, data, amount, displayName, lore);
    }

    /**
     * 获取 FireworkBuilder 实例对象
     *
     * @return FireworkBuilder
     */
    public static FireworkBuilder newFireworkBuilder() {

        return ItemLibraryFactorys.fireworkBuilder();
    }

    /**
     * 获取 PlayerLibrary 对象
     *
     * @return PlayerLibrary
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static PlayerLibrary getPlayerLibrary() {

        return PlayerLibraryFactorys.player();
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatComponentFancy}
     */
    @Deprecated
    public static FancyMessage newFancyMessage(String text) {

        return FancyMessageFactory.get().message(text);
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatComponentFancy}
     */
    @Deprecated
    public static FancyMessage newFancyMessage(TextualComponent text) {

        return FancyMessageFactory.get().message(text);
    }

    /**
     * 将指定聊天花式组件对象发送给命令执行者
     *
     * @param componentFancy 聊天花式组件
     * @param sender 命令执行者
     * @throws IllegalArgumentException 如果聊天花式组件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果命令执行者对象为 {@code null} 则抛出异常
     */
    public static void chatComponentSent(ChatComponentFancy componentFancy, CommandSender sender) {

        chatComponentSent(componentFancy, PacketPlayOutChat.Mode.CHAT, sender);
    }

    /**
     * 将指定聊天花式组件对象发送给命令执行者
     *
     * @param componentFancy 聊天花式组件
     * @param mode 聊天模式
     * @param sender 命令执行者
     * @throws IllegalArgumentException 如果聊天花式组件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果聊天模式对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果命令执行者对象为 {@code null} 则抛出异常
     */
    public static void chatComponentSent(ChatComponentFancy componentFancy, PacketPlayOutChat.Mode mode, CommandSender sender) {

        Validate.notNull(componentFancy, "The chat component fancy object is null.");

        chatComponentSent(componentFancy.build(), mode, sender);
    }

    /**
     * 将指定聊天组件对象发送给命令执行者
     *
     * @param chatComponent 聊天组件
     * @param sender 命令执行者
     * @throws IllegalArgumentException 如果聊天组件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果命令执行者对象为 {@code null} 则抛出异常
     */
    public static void chatComponentSent(ChatComponent chatComponent, CommandSender sender) {

        chatComponentSent(chatComponent, PacketPlayOutChat.Mode.CHAT, sender);
    }

    /**
     * 将指定聊天组件对象发送给命令执行者
     *
     * @param chatComponent 聊天组件
     * @param mode 聊天模式
     * @param sender 命令执行者
     * @throws IllegalArgumentException 如果聊天组件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果聊天模式对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果命令执行者对象为 {@code null} 则抛出异常
     */
    public static void chatComponentSent(ChatComponent chatComponent, PacketPlayOutChat.Mode mode, CommandSender sender) {

        Validate.notNull(chatComponent, "The chat component object is null.");
        Validate.notNull(mode, "The mode object is null.");
        Validate.notNull(sender, "The sender object is null.");

        if(sender instanceof Player) {
            PacketPlayOutChat ppoc = new PacketPlayOutChat(chatComponent);
            ppoc.modeProperty().set(mode);
            ppoc.send((Player) sender);
        } else {
            sender.sendMessage(chatComponent.toRaw());
        }
    }

    /**
     * 获取铁砧窗口 AnvilWindow 实例对象
     *
     * @param plugin 插件
     * @return AnvilWindow
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static AnvilWindow newAnvilWindow(Plugin plugin) {

        return AnvilWindowFactory.newAnvilWindow(plugin);
    }

    /**
     * 插件注解类接口 API PluginAnnotationAPI 实例对象
     *
     * @return PluginAnnotationAPI
     */
    public static PluginAnnotationAPI getPluginAnnotation() {

        return new PluginAnnotationAPI() {

            @Override
            public ConfigAnnotation getConfig() {

                return PluginAnnotationFactory.get().config();
            }

            @Override
            public CommandAnnotation getCommand() {

                return PluginAnnotationFactory.get().command();
            }
        };
    }

    /**
     * 获取插件注解配置文件类接口 ConfigAnnotation 实例对象
     *
     * @return ConfigAnnotation
     */
    public static ConfigAnnotation getConfigAnnotation() {

        return getPluginAnnotation().getConfig();
    }

    /**
     * 获取插件注解m了类接口 CommandAnnotation 实例对象
     *
     * @return CommandAnnotation
     */
    public static CommandAnnotation getCommandAnnotation() {

        return getPluginAnnotation().getCommand();
    }

    /**
     * 获取 NBTLibrary 对象
     *
     * @return NBTLibrary
     */
    public static NBTLibrary getNBTLibrary() {

        return NBTFactory.get();
    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @return NBTCompound
     */
    public static NBTCompound newNBTCompound() {

        return NBTFactory.newCompound();
    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @param tag NBT 对象
     * @return NBTCompound
     */
    public static NBTCompound newNBTCompound(Object tag) {

        return NBTFactory.newCompound(tag);
    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @param map Map 对象
     * @return NBTCompound
     */
    public static NBTCompound newNBTCompound(Map map) {

        return NBTFactory.newCompound(map);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @return NBTList
     */
    public static NBTList newNBTList() {

        return NBTFactory.newList();
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param tag NBT 对象
     * @return NBTList
     */
    public static NBTList newNBTList(Object tag) {

        return NBTFactory.newList(tag);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param collection 集合对象
     * @return NBTList
     */
    public static NBTList newNBTList(Collection collection) {

        return NBTFactory.newList(collection);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param array 数组对象
     * @return NBTList
     */
    public static NBTList newNBTList(Object[] array) {

        return NBTFactory.newList(array);
    }

    /**
     * 获取国际化语言 I18n 实例对象
     *
     * @param file 语言文件
     * @return I18n
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语言文件不存在则抛出异常
     */
    public static I18n newInternationalization(File file) {

        return I18nFactory.newInstance(file);
    }

    /**
     * 获取国际化语言 I18n 实例对象
     *
     * @param file 语言文件
     * @return I18n
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语言文件不存在则抛出异常
     */
    public static I18n newInternationalization(String file) {

        return I18nFactory.newInstance(file);
    }

    /**
     * 获取国际化语言 I18n 实例对象
     *
     * @param file 语言文件
     * @param prefix 前缀
     * @return I18n
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语言文件不存在则抛出异常
     * @throws IllegalArgumentException 如果前缀对象为 {@code null} 则抛出异常
     */
    public static I18n newInternationalization(File file, String prefix) {

        return I18nFactory.newInstance(file, prefix);
    }

    /**
     * 获取国际化语言 I18n 实例对象
     *
     * @param file 语言文件
     * @param prefix 前缀
     * @return I18n
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语言文件不存在则抛出异常
     * @throws IllegalArgumentException 如果前缀对象为 {@code null} 则抛出异常
     */
    public static I18n newInternationalization(String file, String prefix) {

        return I18nFactory.newInstance(file, prefix);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection() throws MySQLInitializeException {

        return MySQLFactory.connection();
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(@Nullable String username, @Nullable String password) throws MySQLInitializeException {

        return MySQLFactory.connection(username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(@Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        return MySQLFactory.connection(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(@Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        return MySQLFactory.connection(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(@Nullable String host, int port, @Nullable String username, String password) throws MySQLInitializeException {

        return MySQLFactory.connection(host, port, username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String host, int port, @Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        return MySQLFactory.connection(host, port, username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String host, int port, @Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        return MySQLFactory.connection(host, port, username, password, charset);
    }

    /**
     * 获取指定 PacketPlayOutBukkit 的实例对象
     *
     * @param packet 数据包
     * @param <T> 数据包类
     * @return 数据包实例对象
     * @throws PacketException 如果获取错误则抛出异常
     */
    public static <T extends PacketPlayOutBukkit> T newPacketPlayOutBukkit(Class<T> packet) throws PacketException {

        try {

            return packet.newInstance();

        } catch (Exception e) {

            throw new PacketException(e.getMessage(), e);
        }
    }

    /**
     * 调用任务帮助器运行任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTask(Plugin plugin, Runnable task) {

        return TaskHelper.runTask(plugin, task);
    }

    /**
     * 调用任务帮助器运行延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLater(Plugin plugin, Runnable task, long delay) {

        return TaskHelper.runTaskLater(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {

        return TaskHelper.runTaskTimer(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器运行异步任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTaskAsync(Plugin plugin, Runnable task) {

        return TaskHelper.runTaskAsync(plugin, task);
    }

    /**
     * 调用任务帮助器运行异步延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLaterAsync(Plugin plugin, Runnable task, long delay) {

        return TaskHelper.runTaskLaterAsync(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行异步定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimerAsync(Plugin plugin, Runnable task, long delay, long period) {

        return TaskHelper.runTaskTimerAsync(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器运行任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTask(Plugin plugin, MoonLakeRunnable task) {

        return TaskHelper.runTask(plugin, task);
    }

    /**
     * 调用任务帮助器运行延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLater(Plugin plugin, MoonLakeRunnable task, long delay) {

        return TaskHelper.runTaskLater(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimer(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return TaskHelper.runTaskTimer(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器运行异步任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTaskAsync(Plugin plugin, MoonLakeRunnable task) {

        return TaskHelper.runTaskAsync(plugin, task);
    }

    /**
     * 调用任务帮助器运行异步延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLaterAsync(Plugin plugin, MoonLakeRunnable task, long delay) {

        return TaskHelper.runTaskLaterAsync(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行异步定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimerAsync(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return TaskHelper.runTaskTimerAsync(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器触发异步函数
     *
     * @param plugin 插件
     * @param task 任务
     * @param <T> 类型
     * @return Future
     */
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {

        return TaskHelper.callSyncMethod(plugin, task);
    }

    /**
     * 调用任务帮助器触发 Future
     *
     * @param plugin 插件
     * @param task 任务
     * @param <T> 类型
     * @return 类型值
     * @throws MoonLakeException 如果触发错误则抛出异常
     */
    public static <T> T callFuture(Plugin plugin, Callable<T> task) throws MoonLakeException {

        return TaskHelper.callFuture(plugin, task);
    }

    /**
     * 调用任务帮助器触发 Future
     *
     * @param future Future
     * @param <T> 类型
     * @return 类型值
     * @throws MoonLakeException 如果触发错误则抛出异常
     */
    public static <T> T callFuture(Future<T> future) throws MoonLakeException {

        return TaskHelper.callFuture(future);
    }

    /**
     * 调用任务帮助器关闭指定任务
     *
     * @param task 任务
     */
    public static void cancelTask(BukkitTask task) {

        TaskHelper.cancelTask(task);
    }

    /**
     * 调用任务帮助器关闭指定任务
     *
     * @param taskId 任务 Id
     */
    public static void cancelTask(int taskId) {

        TaskHelper.cancelTask(taskId);
    }

    /**
     * 调用任务帮助器关闭指定插件的所有任务
     *
     * @param plugin 插件
     */
    public static void cancelTasks(Plugin plugin) {

        TaskHelper.cancelTasks(plugin);
    }

    /**
     * 调用任务帮助器关闭所有任务
     */
    public static void cancelAllTasks() {

        TaskHelper.cancelAllTasks();
    }

    /**
     * <h1>将字符串按 {@link StringUtil#COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @see com.minecraft.moonlake.util.ColorTableDoc
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String toColor(String source) {

        return StringUtil.toColor(source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String toColor(char altColorChar, String source) {

        return StringUtil.toColor(altColorChar, source);
    }

    /**
     * <h1>将字符串按 {@link StringUtil#COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @see com.minecraft.moonlake.util.ColorTableDoc
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String[] toColor(String... source) {

        return StringUtil.toColor(source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String[] toColor(char altColorChar, String... source) {

        return StringUtil.toColor(altColorChar, source);
    }

    /**
     * <h1>将字符串按 {@link StringUtil#COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @see com.minecraft.moonlake.util.ColorTableDoc
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static List<String> toColor(Collection<? extends String> source) {

        return StringUtil.toColor(source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static List<String> toColor(char altColorChar, Collection<? extends String> source) {

        return StringUtil.toColor(altColorChar, source);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String stripColor(String source) {

        return StringUtil.stripColor(source);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String[] stripColor(String... source) {

        return StringUtil.stripColor(source);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static List<String> stripColor(Collection<? extends String> source) {

        return StringUtil.stripColor(source);
    }

    /**
     * 将字符串进行格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static String stringFormat(String format, Object... args) {

        return StringUtil.format(format, args);
    }

    /**
     * 将字符串进行消息格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static String messageFormat(String format, Object... args) {

        return StringUtil.messageFormat(format, args);
    }

    /**
     * 获取系统当前的时间 (yyyy-MM-dd HH:mm:ss)
     *
     * @see StringUtil#getSystemTime(String)
     * @return 系统时间
     */
    public static String getSystemTime() {

        return StringUtil.getSystemTime();
    }

    /**
     * 获取指定格式的系统当前的时间
     *
     * @param format 时间格式
     * @return 系统时间
     * @throws IllegalArgumentException 如果格式字符串对象为 {@code null} 则抛出异常
     */
    public static String getSystemTime(String format) {

        return StringUtil.getSystemTime(format);
    }

    /**
     * 将指定双精度浮点数按保留 {@code 2} 位数四舍五入
     *
     * @param value 值
     * @return 最终值
     */
    public static double rounding(double value) {

        return StringUtil.rounding(value);
    }

    /**
     * 将指定双精度浮点数按指定位数四舍五入
     *
     * @param value 值
     * @param bit 保留位数
     * @return 最终值
     */
    public static double rounding(double value, int bit) {

        return StringUtil.rounding(value, bit);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(offsetX, offsetY, offsetZ, speed, amount, center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");
        
        effect.display(offsetX, offsetY, offsetZ, speed, amount, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");
        
        effect.display(offsetX, offsetY, offsetZ, speed, amount, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public static void display(ParticleEffect effect, org.bukkit.util.Vector direction, float speed, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");
        
        effect.display(direction, speed, center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public static void display(ParticleEffect effect, org.bukkit.util.Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(direction, speed, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public static void display(ParticleEffect effect, org.bukkit.util.Vector direction, float speed, Location center, Player... players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(direction, speed, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param color 效果颜色
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleColor color, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(color, center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param color 效果颜色
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleColor color, Location center, List<Player> players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(color, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param color 效果颜色
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleColor color, Location center, Player... players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(color, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(data, offsetX, offsetY, offsetZ, speed, amount, center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(data, offsetX, offsetY, offsetZ, speed, amount, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(data, offsetX, offsetY, offsetZ, speed, amount, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, org.bukkit.util.Vector direction, float speed, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(data, direction, speed, center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, org.bukkit.util.Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(direction, speed, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, org.bukkit.util.Vector direction, float speed, Location center, Player... players) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(direction, speed, center, players);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param center 位置
     * @param range 范围
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(center, range, offsetX, offsetY, offsetZ, speed, amount);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param center 位置
     * @param range 范围
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, Location center, double range) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(center, range);
    }

    /**
     * 将指定粒子效果在指定位置播放
     *
     * @param effect 粒子效果
     * @param data 效果数据
     * @param center 位置
     * @param range 范围
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @throws IllegalArgumentException 如果粒子效果对象为 {@code null} 则抛出异常
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public static void display(ParticleEffect effect, ParticleEffect.ParticleData data, Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        Validate.notNull(effect, "The particle effect object is null.");

        effect.display(data, center, range, offsetX, offsetY, offsetZ, speed, amount);
    }

    /**
     * 调用事件帮助器触发指定事件
     *
     * @param event 事件
     */
    public static void callEvent(Event event) {

        EventHelper.callEvent(event);
    }

    /**
     * 调用事件帮助器触发指定事件
     *
     * @param event 事件
     */
    public static void callEvent(MoonLakeEvent event) {

        EventHelper.callEvent(event);
    }

    /**
     * 调用事件帮助器异步触发指定事件
     *
     * @param plugin 插件
     * @param event 事件
     */
    public static void callEventAsync(Plugin plugin, Event event) {

        EventHelper.callEventAsync(plugin, event);
    }

    /**
     * 调用事件帮助器异步触发指定事件
     *
     * @param plugin 插件
     * @param event 事件
     */
    public static void callEventAsync(Plugin plugin, MoonLakeEvent event) {

        EventHelper.callEventAsync(plugin, event);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param listener 监听器
     * @param plugin 插件
     */
    public static void registerEvent(Listener listener, Plugin plugin) {

        EventHelper.registerEvent(listener, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param listener 监听器
     * @param plugin 插件
     */
    public static void registerEvent(MoonLakeListener listener, Plugin plugin) {

        EventHelper.registerEvent(listener, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     */
    public static void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {

        EventHelper.registerEvent(event, listener, priority, executor, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     * @param ignoreCancelled 是否忽略阻止
     */
    public static void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

        EventHelper.registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     */
    public static void registerEvent(Class<? extends MoonLakeEvent> event, MoonLakeListener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {

        EventHelper.registerEvent(event, listener, priority, executor, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     * @param ignoreCancelled 是否忽略阻止
     */
    public static void registerEvent(Class<? extends MoonLakeEvent> event, MoonLakeListener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

        EventHelper.registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    /**
     * 调用事件帮助器卸载全部事件监听器
     */
    public static void unregisterAll() {

        EventHelper.unregisterAll();
    }

    /**
     * 调用事件帮助器卸载指定插件的全部事件监听器
     *
     * @param plugin 插件
     */
    public static void unregisterAll(Plugin plugin) {

        EventHelper.unregisterAll(plugin);
    }

    /**
     * 调用事件帮助器卸载指定事件监听器
     *
     * @param listener 监听器
     */
    public static void unregisterAll(Listener listener) {

        EventHelper.unregisterAll(listener);
    }

    /**
     * 调用事件帮助器卸载指定事件监听器
     *
     * @param listener 监听器
     */
    public static void unregisterAll(MoonLakeListener listener) {

        EventHelper.unregisterAll(listener);
    }

    /**
     * 调用同步任务来获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackSyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer) {

        TaskHelper.callBackSyncConsumer(plugin, callback, consumer);
    }

    /**
     * 调用同步任务来延迟获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @param delay 延迟
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackSyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer, long delay) {

        TaskHelper.callBackSyncConsumer(plugin, callback, consumer, delay);
    }

    /**
     * 调用异步任务来获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackAsyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer) {

        TaskHelper.callBackAsyncConsumer(plugin, callback, consumer);
    }

    /**
     * 调用异步任务来延迟获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @param delay 延迟
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackAsyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer, long delay) {

        TaskHelper.callBackAsyncConsumer(plugin, callback, consumer, delay);
    }

    private static void packetBungeeClose(PacketPlayOutBungee packet) {

        if(packet == null) return;

        try {

            packet.close();
        }
        catch (Exception e) {

            MoonLakeAPI.getLogger().log(Level.WARNING, "The close bungee packet play out '" + packet + "' exception.", e);
        }
    }

    /**
     * 将指定蹦极数据包以指定玩家发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacket(Plugin plugin, PacketPlayOutBungee packet, Player... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacket(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketAsync(Plugin plugin, PacketPlayOutBungee packet, Player... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketAsync(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家不安全的发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketUnsafe(Plugin plugin, PacketPlayOutBungee packet, Player... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketUnsafe(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家不安全的异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketUnsafeAsync(Plugin plugin, PacketPlayOutBungee packet, Player... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketUnsafeAsync(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacket(Plugin plugin, PacketPlayOutBungee packet, MoonLakePlayer... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacket(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketAsync(Plugin plugin, PacketPlayOutBungee packet, MoonLakePlayer... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketAsync(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家不安全的发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketUnsafe(Plugin plugin, PacketPlayOutBungee packet, MoonLakePlayer... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketUnsafe(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以指定玩家不安全的异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @param sender 发送者
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketUnsafeAsync(Plugin plugin, PacketPlayOutBungee packet, MoonLakePlayer... sender) {

        Validate.notNull(packet, "The bungee packet play out object is null.");

        packet.sendPacketUnsafeAsync(plugin, sender);
        packetBungeeClose(packet);
    }

    /**
     * 将指定蹦极数据包以在线玩家发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketOnline(Plugin plugin, PacketPlayOutBungee packet) {

        sendBungeePacket(plugin, packet, PlayerManager.getOnlines());
    }

    /**
     * 将指定蹦极数据包以在线玩家异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果插件对象没有注册 {@link PacketPlayOutBungee#CHANNEL} 通道则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketOnlineAsync(Plugin plugin, PacketPlayOutBungee packet) {

        sendBungeePacketAsync(plugin, packet, PlayerManager.getOnlines());
    }

    /**
     * 将指定蹦极数据包以在线玩家不安全的发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketOnlineUnsafe(Plugin plugin, PacketPlayOutBungee packet) {

        sendBungeePacketUnsafe(plugin, packet, PlayerManager.getOnlines());
    }

    /**
     * 将指定蹦极数据包以在线玩家不安全的异步发送到蹦极代理
     *
     * @param plugin 插件
     * @param packet 蹦极数据包
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果蹦极数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果发送者对象为 {@code null} 则抛出异常
     * @throws PacketException 如果发送时错误则抛出异常
     */
    public static void sendBungeePacketOnlineUnsafeAsync(Plugin plugin, PacketPlayOutBungee packet) {

        sendBungeePacketUnsafeAsync(plugin, packet, PlayerManager.getOnlines());
    }

    /**
     * 读取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return Null | NBTCompound
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     */
    public static NBTCompound readNBT(ItemStack itemStack) {

        return getNBTLibrary().read(itemStack);
    }

    /**
     * 读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @return Null | NBTCompound
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取方块时错误则抛出异常
     */
    public static NBTCompound readNBT(Block block) {

        return getNBTLibrary().read(block);
    }

    /**
     * 读取指定实体的 NBT 数据
     *
     * @param entity 实体
     * @return Null | NBTCompound
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取实体时错误则抛出异常
     */
    public static NBTCompound readNBT(Entity entity) {

        return getNBTLibrary().read(entity);
    }

    /**
     * 读取指定区块的 NBT 数据
     *
     * @param chunk 区块
     * @return Null | NBTCompound
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取区块时错误则抛出异常
     */
    public static NBTCompound readNBT(Chunk chunk) {

        return getNBTLibrary().read(chunk);
    }

    /**
     * 安全读取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return NBTCompound
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     */
    public static NBTCompound readSafeNBT(ItemStack itemStack) {

        return getNBTLibrary().readSafe(itemStack);
    }

    /**
     * 安全读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @return NBTCompound
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取方块时错误则抛出异常
     */
    public static NBTCompound readSafeNBT(Block block) {

        return getNBTLibrary().readSafe(block);
    }

    /**
     * 安全读取指定实体的 NBT 数据
     *
     * @param entity 实体
     * @return NBTCompound
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取实体时错误则抛出异常
     */
    public static NBTCompound readSafeNBT(Entity entity) {

        return getNBTLibrary().readSafe(entity);
    }

    /**
     * 安全读取指定区块的 NBT 数据
     *
     * @param chunk 区块
     * @return NBTCompound
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取区块时错误则抛出异常
     */
    public static NBTCompound readSafeNBT(Chunk chunk) {

        return getNBTLibrary().readSafe(chunk);
    }

    /**
     * 安全读取指定物品栈的 NBT 数据并给予消费者
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取物品栈时错误则抛出异常
     */
    public static void readSafeConsumerNBT(ItemStack itemStack, Consumer<NBTCompound> consumer) {

        getNBTLibrary().readSafeConsumer(itemStack, consumer);
    }

    /**
     * 安全读取指定方块的 NBT 数据并给予消费者
     *
     * @param block 方块
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取方块时错误则抛出异常
     */
    public static void readSafeConsumerNBT(Block block, Consumer<NBTCompound> consumer) {

        getNBTLibrary().readSafeConsumer(block, consumer);
    }

    /**
     * 安全读取指定实体的 NBT 数据并给予消费者
     *
     * @param entity 实体
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取实体时错误则抛出异常
     */
    public static void readSafeConsumerNBT(Entity entity, Consumer<NBTCompound> consumer) {

        getNBTLibrary().readSafeConsumer(entity, consumer);
    }

    /**
     * 安全读取指定区块的 NBT 数据并给予消费者
     *
     * @param chunk 区块
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws NBTException 如果读取区块时错误则抛出异常
     */
    public static void readSafeConsumerNBT(Chunk chunk, Consumer<NBTCompound> consumer) {

        getNBTLibrary().readSafeConsumer(chunk, consumer);
    }

    /**
     * 获取依赖 MoonLakeEconomy 经济接口 API 对象
     *
     * @return DependEconomy
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomy getEconomyDepend() throws CannotDependException {

        return DependPlayerFactory.get().dependEconomy();
    }

    /**
     * 获取依赖 Vault 经济接口 API 对象
     *
     * @return DependEconomyVault
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomyVault getVaultDepend() throws CannotDependException {

        return DependPlayerFactory.get().dependVault();
    }

    /**
     * 获取依赖 WorldEdit 创世神接口 API 对象
     *
     * @return DependEconomyVault
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependWorldEdit getWorldEditDepend() throws CannotDependException {

        return DependPlayerFactory.get().dependWorldEdit();
    }

    /**
     * 获取依赖 PermissionsEx 权限接口 API 对象
     *
     * @return DependPermissionsEx
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependPermissionsEx getPermissionsExDepend() throws CannotDependException {

        return DependPlayerFactory.get().dependPermissionsEx();
    }

    /**
     * 创建拥有指定持有者和类型的物品栏
     *
     * @param holder 持有者
     * @param type 类型
     * @return 物品栏
     * @see Inventory
     */
    public static Inventory createInventory(InventoryHolder holder, InventoryType type) {

        return Bukkit.getServer().createInventory(holder, type);
    }

    /**
     * 创建拥有指定持有者和类型的物品栏
     *
     * @param holder 持有者
     * @param type 类型
     * @param title 标题
     * @return 物品栏
     * @see Inventory
     */
    public static Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {

        return Bukkit.getServer().createInventory(holder, type, title);
    }

    /**
     * 创建拥有指定持有者和大小的物品栏
     *
     * @param holder 持有者
     * @param size 大小 (是 9 的倍数, 且最大为 54)
     * @return 物品栏
     * @throws IllegalArgumentException 如果物品栏大小是非法值则抛出异常
     * @see Inventory
     */
    public static Inventory createInventory(InventoryHolder holder, int size) {

        return Bukkit.getServer().createInventory(holder, size);
    }

    /**
     * 创建拥有指定持有者和大小的物品栏
     *
     * @param holder 持有者
     * @param size 大小 (是 9 的倍数, 且最大为 54)
     * @param title 标题
     * @return 物品栏
     * @throws IllegalArgumentException 如果物品栏大小是非法值则抛出异常
     * @see Inventory
     */
    public static Inventory createInventory(InventoryHolder holder, int size, String title) {

        return Bukkit.getServer().createInventory(holder, size, title);
    }

    /**
     * 获取由服务器控制的主计分板
     *
     * @return 计分板
     * @see Scoreboard
     */
    public static Scoreboard getScoreboardMain() {

        return Bukkit.getScoreboardManager().getMainScoreboard();
    }

    /**
     * 获取由服务器跟踪的新记分板
     *
     * @return 计分板
     * @see Scoreboard
     */
    public static Scoreboard getScoreboardNew() {

        return Bukkit.getScoreboardManager().getNewScoreboard();
    }

    /**
     * 将控制台强制执行指定命令行
     *
     * @param cmd 命令
     * @return 是否成功
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     * @see ConsoleCommandSender
     */
    public static boolean dispatchConsoleCmd(String cmd) {

        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), handleCmd(cmd));
    }

    /**
     * 将指定命令执行者强制执行指定命令行
     *
     * @param cmd 命令
     * @return 是否成功
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     * @see CommandSender
     */
    public static boolean dispatchCmd(CommandSender sender, String cmd) {

        return Bukkit.dispatchCommand(sender, handleCmd(cmd));
    }

    private static String handleCmd(String cmd) {

        Validate.notNull(cmd, "The cmd object is null.");
        return cmd.charAt(0) == '/' ? cmd.substring(1) : cmd;
    }

    /**
     * 获取此服务器的插件通道消息管理者
     *
     * @return 消息管理者
     * @see Messenger
     */
    public static Messenger getMessenger() {

        return Bukkit.getMessenger();
    }

    /**
     * 将指定插件注册到所请求的传出插件通道, 允许其通过该通道将消息发送到任何客户端
     *
     * @param plugin 插件
     * @param channel 通道
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     */
    public static void registerOutgoingPluginChannel(Plugin plugin, String channel) {

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }

    /**
     * 将指定插件注册到用于监听所请求的传入插件通道, 使其能够对任何插件消息执行操作
     *
     * @param plugin 插件
     * @param channel 通道
     * @param listener 监听器
     * @return PluginMessageListenerRegistration
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     * @throws IllegalArgumentException 如果监听器对象为 {@code null} 则抛出异常
     */
    public static PluginMessageListenerRegistration registerIncomingPluginChannel(Plugin plugin, String channel, PacketMessageListener listener) {

        return Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, listener);
    }

    /**
     * 将指定插件从所有传出的插件通道中注销, 不再允许它发送任何插件消息到客户端
     *
     * @param plugin 插件
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static void unregisterOutgoingPluginChannel(Plugin plugin) {

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin);
    }

    /**
     * 将指定插件从指定传出的插件通道中注销, 不再允许它通过此插件通道发送任何插件消息到客户端
     *
     * @param plugin 插件
     * @param channel 通道
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     */
    public static void unregisterOutgoingPluginChannel(Plugin plugin, String channel) {

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, channel);
    }

    /**
     * 将指定插件的所有插件通道监听器注销
     *
     * @param plugin 插件
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static void unregisterIncomingPluginChannel(Plugin plugin) {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin);
    }

    /**
     * 将指定插件的指定插件通道监听器注销, 不再允许它对任何插件消息执行操作
     *
     * @param plugin 插件
     * @param channel 通道
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     */
    public static void unregisterIncomingPluginChannel(Plugin plugin, String channel) {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, channel);
    }

    /**
     * 将指定插件的指定插件通道的指定监听器注销, 不再允许它对任何插件消息执行操作
     *
     * @param plugin 插件
     * @param channel 通道
     * @param listener 监听器
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     * @throws IllegalArgumentException 如果监听器对象为 {@code null} 则抛出异常
     */
    public static void unregisterIncomingPluginChannel(Plugin plugin, String channel, PacketMessageListener listener) {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, channel, listener);
    }

    /**
     * 将指定插件注册到 BungeeCord 通道, 允许其通过该通道将消息发送到 BungeeCord 代理
     *
     * @param plugin 插件
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static void registerOutgoingBungeeCordChannel(Plugin plugin) {

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, PacketPlayOutBungee.CHANNEL);
    }

    /**
     * 将指定插件注册到 BungeeCord 通道, 使其能够对 BungeeCord 代理消息执行操作
     *
     * @param plugin 插件
     * @param listener 监听器
     * @return PluginMessageListenerRegistration
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果监听器对象为 {@code null} 则抛出异常
     */
    public static PluginMessageListenerRegistration registerIncomingBungeeCordChannel(Plugin plugin, PacketMessageListener listener) {

        return Bukkit.getMessenger().registerIncomingPluginChannel(plugin, PacketPlayOutBungee.CHANNEL, listener);
    }

    /**
     * 将指定插件从 BungeeCord 通道中注销, 不再允许它发送任何消息到 BungeeCord 代理
     *
     * @param plugin 插件
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static void unregisterOutgoingBungeeCordChannel(Plugin plugin) {

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, PacketPlayOutBungee.CHANNEL);
    }

    /**
     * 将指定插件的 BungeeCord 通道监听器注销, 不再允许它对 BungeeCord 代理消息执行操作
     *
     * @param plugin 插件
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static void unregisterIncomingBungeeCordChannel(Plugin plugin) {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, PacketPlayOutBungee.CHANNEL);
    }

    /**
     * 将指定插件的 BungeeCord 通道的指定监听器注销, 不再允许它对 BungeeCord 代理消息执行操作
     *
     * @param plugin 插件
     * @param listener 监听器
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果监听器对象为 {@code null} 则抛出异常
     */
    public static void unregisterIncomingBungeeCordChannel(Plugin plugin, PacketMessageListener listener) {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, PacketPlayOutBungee.CHANNEL, listener);
    }

    /**
     * 检查指定的插件是否已注册通过请求的通道接收传入的消息
     *
     * @param plugin 插件
     * @param channel 通道
     * @return 是否已经注册
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     */
    public static boolean isIncomingChannelRegistered(Plugin plugin, String channel) {

        return Bukkit.getMessenger().isIncomingChannelRegistered(plugin, channel);
    }

    /**
     * 检查指定的插件是否已注册通过请求的通道发送传出的消息
     *
     * @param plugin 插件
     * @param channel 通道
     * @return 是否已经注册
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果通道对象为 {@code null} 或通道长度大于 {@code 20} 则抛出异常
     */
    public static boolean isOutgoingChannelRegistered(Plugin plugin, String channel) {

        return Bukkit.getMessenger().isOutgoingChannelRegistered(plugin, channel);
    }

    /**
     * 获取指定的插件是否已注册 BungeeCord 通道来接收传入的 BungeeCord 代理消息
     *
     * @param plugin 插件
     * @return 是否已经注册
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static boolean isIncomingBungeeCordChannelRegistered(Plugin plugin) {

        return Bukkit.getMessenger().isIncomingChannelRegistered(plugin, PacketPlayOutBungee.CHANNEL);
    }

    /**
     * 获取指定的插件是否已注册 BungeeCord 通道来发送传出的 BungeeCord 代理消息
     *
     * @param plugin 插件
     * @return 是否已经注册
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static boolean isOutgoingBungeeCordChannelRegistered(Plugin plugin) {

        return Bukkit.getMessenger().isOutgoingChannelRegistered(plugin, PacketPlayOutBungee.CHANNEL);
    }
}
