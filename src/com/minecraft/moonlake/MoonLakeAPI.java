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
import com.minecraft.moonlake.api.player.PlayerLibrary;
import com.minecraft.moonlake.api.player.PlayerLibraryFactorys;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.logger.MLogger;
import com.minecraft.moonlake.mysql.MySQLConnection;
import com.minecraft.moonlake.mysql.MySQLFactory;
import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;
import com.minecraft.moonlake.nms.packet.Packet;
import com.minecraft.moonlake.nms.packet.PacketFactory;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.particle.ParticleEffect;
import com.minecraft.moonlake.particle.ParticleException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.task.MoonLakeRunnable;
import com.minecraft.moonlake.task.TaskHelper;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
    public static MLogger getMLogger() {

        return moonlake.getMLogger();
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
     */
    public static String getBukkitVersion() {

        //return moonlake.getBukkitVersion();
        return Reflect.getServerVersion();
    }

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     */
    public static int getReleaseNumber() {

        //return moonlake.getReleaseNumber();
        return Reflect.getServerVersionNumber();
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
     */
    public static PlayerLibrary getPlayerLibrary() {

        return PlayerLibraryFactorys.player();
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public static FancyMessage newFancyMessage(String text) {

        return FancyMessageFactory.get().message(text);
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public static FancyMessage newFancyMessage(TextualComponent text) {

        return FancyMessageFactory.get().message(text);
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
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String username, String password) throws MySQLInitializeException {

        return MySQLFactory.connection(username, password);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String username, String password, String charset) throws MySQLInitializeException {

        return MySQLFactory.connection(username, password, charset);
    }

    /**
     * 获取 MySQL 连接实例对象
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @return MySQLConnection
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String username, String password, Charset charset) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String host, int port, String username, String password) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String host, int port, String username, String password, String charset) throws MySQLInitializeException {

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
     * @throws IllegalArgumentException 如果数据库用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库密码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public static MySQLConnection newMySQLConnection(String host, int port, String username, String password, Charset charset) throws MySQLInitializeException {

        return MySQLFactory.connection(host, port, username, password, charset);
    }

    /**
     * 获取指定 Packet 的实例对象
     *
     * @param packet Packet
     * @param <T> Packet
     * @return Packet 实例对象
     * @throws PacketException 如果获取错误则抛出异常
     * @deprecated 已过时, 建议使用参数获取实例对象
     * @see #newPacket(Class, Object...)
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static <T extends Packet> T newPacket(Class<T> packet) throws PacketException {

        return PacketFactory.get().instance(packet);
    }

    /**
     * 获取指定 Packet 的实例对象
     *
     * @param packet Packet
     * @param args Packet 构造参数
     * @param <T> Packet
     * @return Packet 实例对象
     * @throws PacketException 如果获取错误则抛出异常
     */
    public static <T extends Packet> T newPacket(Class<T> packet, Object... args) throws PacketException {

        return PacketFactory.get().instance(packet, args);
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
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
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
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
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
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
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
}
