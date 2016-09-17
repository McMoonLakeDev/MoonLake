package com.minecraft.moonlake.api.fancy;

import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

/**
 * Created by MoonLake on 2016/9/16.
 *
 * @see FancyMessageFactory
 */
public interface FancyMessage extends JsonRepresentedObject, Cloneable, Iterable<FancyMessagePart> {

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    Iterator<FancyMessagePart> iterator();

    /**
     * 设置此花式消息的文本内容
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果文本字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage text(String text);

    /**
     * 设置此花式消息的文本内容
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果文本对象为 {@code null} 则抛出异常
     */
    FancyMessage text(TextualComponent text);

    /**
     * 设置此花式消息的文本颜色
     *
     * @see org.bukkit.ChatColor
     * @see org.bukkit.ChatColor#isColor()
     * @param color 文本颜色
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果颜色对象并不是颜色类型则抛出异常
     */
    FancyMessage color(ChatColor color);

    /**
     * 设置此花式消息的文本样式
     *
     * @param style 文本样式
     * @throws IllegalArgumentException 如果样式对象为 {@code null} 则抛出异常
     */
    FancyMessage style(FancyMessageStyle... style);

    /**
     * 设置此花式消息点击后打开文件
     *
     * @param path 文件路径
     * @throws IllegalArgumentException 如果路径字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage file(String path);

    /**
     * 设置此花式消息点击后打开超链接
     *
     * @param url 网址
     * @throws IllegalArgumentException 如果网址字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage link(String url);

    /**
     * 设置此花式消息点击后在聊天栏添加命令
     *
     * @param command 命令
     * @throws IllegalArgumentException 如果命令字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage suggest(String command);

    /**
     * 设置此花式消息点击后在聊天栏插入命令
     *
     * @param command 命令
     * @throws IllegalArgumentException 如果命令字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage insert(String command);

    /**
     * 设置此花式消息点击后执行命令
     *
     * @param command 命令
     * @throws IllegalArgumentException 如果命令字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage command(String command);

    /**
     * 设置此花式消息移动上后显示文本内容
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果文本字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage tooltip(String text);

    /**
     * 设置此花式消息移动上后显示文本内容
     *
     * @param texts 文本
     * @throws IllegalArgumentException 如果文本字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage tooltip(String... texts);

    /**
     * 设置此花式消息移动上后显示物品栈
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    FancyMessage tooltip(ItemStack itemStack);

    /**
     * 设置此花式消息移动上后显示成就
     *
     * @see org.bukkit.Achievement
     * @param name 成就名字
     * @throws IllegalArgumentException 如果文本字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage tooltipAchievement(String name);

    /**
     *
     * @param replacements
     */
    FancyMessage translationReplacements(String... replacements);

    /**
     *
     * @param replacements
     */
    FancyMessage translationReplacements(FancyMessage... replacements);

    /**
     * 初始化设置下一个花式消息对象
     *
     * @see FancyMessage#text(String)
     * @see FancyMessage#text(TextualComponent)
     * @throws IllegalArgumentException 如果最后一个花式消息对象没有文本内容则抛出异常
     * @deprecated 如果忘记设置这个花式消息对象的消息接下来的操作可能会抛出异常
     */
    @Deprecated
    FancyMessage then();

    /**
     * 初始化设置下一个花式消息对象
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果最后一个花式消息对象没有文本内容则抛出异常
     * @throws IllegalArgumentException 如果文本字符串对象为 {@code null} 则抛出异常
     */
    FancyMessage then(String text);

    /**
     * 初始化设置下一个花式消息对象
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果最后一个花式消息对象没有文本内容则抛出异常
     * @throws IllegalArgumentException 如果文本对象为 {@code null} 则抛出异常
     */
    FancyMessage then(TextualComponent text);

    /**
     * 构造花式消息对象
     */
    FancyMessage build();

    /**
     * 将花式消息对象转换到 Json 字符串
     *
     * @return Json 字符串
     */
    ReadOnlyStringProperty toJsonString();

    /**
     * 将此花式消息发送给指定玩家
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    void send(Player player);

    /**
     * 将此花式消息发送给指定玩家
     *
     * @param player 玩家名
     * @throws IllegalArgumentException 如果玩家名字对象为 {@code null} 则抛出异常
     */
    void send(String player);
}
