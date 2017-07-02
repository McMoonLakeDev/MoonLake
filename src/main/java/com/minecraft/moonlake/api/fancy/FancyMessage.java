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
 
 
package com.minecraft.moonlake.api.fancy;

import com.minecraft.moonlake.api.chat.ChatComponentFancy;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.builder.Builder;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;

/**
 * <hr />
 * <div>
 *     <h1>花式消息 FancyMessage <span style="background: black; color: black"><s>然而原形还是 tellraw</s></span></h1>
 *     <p>其实就是把 Tellraw 给简单化了，可以实现出非常厉害的 Json 消息。</p>
 *     <p>By Month_Light Ver: 1.0</p>
 *     <hr />
 *     <h2>获取实例对象:</h2>
 *     <p>{@link FancyMessageFactory#message(String)}</p>
 *     <p>{@link FancyMessageFactory#message(TextualComponent)}</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简单的例子:</h1>
 *     <p>FancyMessage test1 = FancyMessageFactory.get()</p>
 *     <p style="text-indent: 30px">.message("你可以用鼠标点击或移动到后面的文字:")</p>
 *     <p style="text-indent: 50px">.color(ChatColor.YELLOW)</p>
 *     <p style="text-indent: 50px">.style(FancyMessageStyle.BOLD)</p>
 *     <p style="text-indent: 30px">.then(" <点我打开网页> ")</p>
 *     <p style="text-indent: 50px">.color(ChatColor.AQUA)</p>
 *     <p style="text-indent: 50px">.link("http://minecraft.net")</p>
 *     <p style="text-indent: 30px">.then(" <点我执行命令> ")</p>
 *     <p style="text-indent: 50px">.color(ChatColor.AQUA)</p>
 *     <p style="text-indent: 50px">.command("/gamemode 1")</p>
 *     <p style="text-indent: 30px">.then(" <放我这里查看文本> ")</p>
 *     <p style="text-indent: 50px">.color(ChatColor.RED)</p>
 *     <p style="text-indent: 50px">.tooltip("竟然发现了这个文本 (๑• . •๑)")</p>
 *     <p style="text-indent: 30px">.build();</p>
 *     <h2>最后发送给玩家可以使用:</h2>
 *     <p>test1.sendAll(); // 发送给在线所有玩家</p>
 *     <p>test1.send("Notch"); // 发送给单个玩家</p>
 * </div>
 * <hr />
 *
 * @version 1.1
 * @author Month_Light
 * @see TextualComponent
 * @see FancyMessageFactory
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatComponentFancy}
 */
@Deprecated
public interface FancyMessage extends JsonRepresentedObject, Cloneable, Iterable<FancyMessagePart>, Builder<FancyMessage> {

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
     * 设置此花式消息移动上后显示的文本内容
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果文本集合对象为 {@code null} 则抛出异常
     */
    FancyMessage tooltip(Collection<? extends String> text);

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
     * 设置此花式消息的替换为玩家选择的语言的翻译器
     *
     * @param replacements 翻译器
     */
    FancyMessage translationReplacements(String... replacements);

    /**
     * 设置此花式消息的替换为玩家选择的语言的翻译器
     *
     * @param replacements 翻译器
     */
    FancyMessage translationReplacements(FancyMessage... replacements);

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
     * 将指定花式消息和此花式消息连接
     *
     * @param fancyMessage 花式消息
     * @throws IllegalArgumentException 如果花式消息对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果最后一个花式消息对象没有文本内容则抛出异常
     */
    FancyMessage join(FancyMessage fancyMessage);

    /**
     * 创建花式消息对象
     */
    @Override
    FancyMessage build();

    /**
     * 将花式消息对象转换到 Json 字符串
     *
     * @return Json 字符串
     */
    String toJsonString();

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
     * @param moonLakePlayer 月色之湖玩家对象
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    void send(MoonLakePlayer moonLakePlayer);

    /**
     * 将此花式消息发送给指定玩家
     *
     * @param player 玩家名
     * @throws IllegalArgumentException 如果玩家名字对象为 {@code null} 则抛出异常
     */
    void send(String player);

    /**
     * 将此花式消息发送给在线所有玩家
     */
    void sendAll();
}
