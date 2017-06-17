/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.chat;

import com.minecraft.moonlake.validate.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <h1>ChatComponentFancy</h1>
 * 聊天花式组件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatComponentFancy {

    private List<ChatComponent> extras;

    /**
     * 聊天花式组件类构造函数
     *
     * @param text 文本内容
     */
    public ChatComponentFancy(String text) {
        this.extras = new ArrayList<>();
        this.then(text);
    }

    /**
     * 初始化设置下一个聊天文本组件
     *
     * @param text 文本内容
     * @throws IllegalArgumentException 如果文本内容对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy then(String text) {
        Validate.notNull(text, "The text object is null.");
        return then(new ChatComponentText(text));
    }

    /**
     * 初始化设置下一个聊天组件
     *
     * @param component 聊天组件
     * @throws IllegalArgumentException 如果聊天组件对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy then(ChatComponent component) {
        Validate.notNull(component, "The component object is null.");
        extras.add(component);
        return this;
    }

    /**
     * 设置当前聊天组件的聊天颜色属性
     *
     * @param color 聊天颜色
     * @throws IllegalArgumentException 如果聊天颜色对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy color(ChatColor color) {
        Validate.notNull(color, "The chat color object is null.");
        getLast().getStyle().setColor(color);
        return this;
    }

    /**
     * 设置当前聊天组件的聊天点击事件打开文件属性
     *
     * @param path 文件路径
     * @throws IllegalArgumentException 如果文件路径对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy file(String path) {
        Validate.notNull(path, "The path object is null.");
        getLast().getStyle().setClickEvent(new ChatClickEvent(ChatClickEvent.Action.OPEN_FILE, path));
        return this;
    }

    /**
     * 设置当前聊天组件的聊天点击事件打开链接属性
     *
     * @param url 链接
     * @throws IllegalArgumentException 如果链接对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy link(String url) {
        Validate.notNull(url, "The url object is null.");
        getLast().getStyle().setClickEvent(new ChatClickEvent(ChatClickEvent.Action.OPEN_URL, url));
        return this;
    }

    /**
     * 设置当前聊天组件的聊天点击事件替换命令属性
     *
     * @param command 命令
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy suggest(String command) {
        Validate.notNull(command, "The command object is null.");
        getLast().getStyle().setClickEvent(new ChatClickEvent(ChatClickEvent.Action.SUGGEST_COMMAND, command));
        return this;
    }

    /**
     * 设置当前聊天组件的聊天点击事件执行命令属性
     *
     * @param command 命令
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy command(String command) {
        Validate.notNull(command, "The command object is null.");
        getLast().getStyle().setClickEvent(new ChatClickEvent(ChatClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    /**
     * 设置当前聊天组件的 Shift 点击文字插入内容
     *
     * @param insertion 插入内容
     * @throws IllegalArgumentException 如果插入内容对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy insert(String insertion) {
        Validate.notNull(insertion, "The insertion object is null.");
        getLast().getStyle().setInsertion(insertion);
        return this;
    }

    /**
     * 设置当前聊天组件的聊天移动上事件显示文本
     *
     * @param text 显示文本
     * @throws IllegalArgumentException 如果显示文本对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy tooltipText(String text) {
        Validate.notNull(text, "The text object is null.");
        getLast().getStyle().setHoverEvent(new ChatHoverEvent(ChatHoverEvent.Action.SHOW_TEXT, new ChatComponentText(text)));
        return this;
    }

    /**
     * 设置当前聊天组件的聊天移动上事件显示多行文本
     *
     * @param texts 多行文本
     * @throws IllegalArgumentException 如果多行文本对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy tooltipTexts(String... texts) {
        Validate.notNull(texts, "The texts object is null.");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < texts.length; i++) {
            builder.append(texts[i]);
            if(i != texts.length - 1)
                builder.append('\n');
        }
        return tooltipText(builder.toString());
    }

    /**
     * 设置当前聊天组件的聊天移动上事件显示多行文本
     *
     * @param collection 文本集合
     * @throws IllegalArgumentException 如果文本集合对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy tooltipTexts(Collection<? extends String> collection) {
        Validate.notNull(collection, "The collection object is null.");
        return tooltipTexts(collection.toArray(new String[collection.size()]));
    }

    /**
     * 设置当前聊天组件的聊天移动上事件显示物品
     *
     * @param item 物品 NBT 数据
     * @throws IllegalArgumentException 如果物品 {@code NBT} 数据对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy tooltipItem(String item) {
        Validate.notNull(item, "The item object is null.");
        getLast().getStyle().setHoverEvent(new ChatHoverEvent(ChatHoverEvent.Action.SHOW_ITEM, new ChatComponentText(item)));
        return this;
    }

    /**
     * 将指定聊天花式组件和此聊天花式组件连接
     *
     * @param componentFancy 聊天花式组件
     * @throws IllegalArgumentException 如果聊天花式组件对象为 {@code null} 则抛出异常
     */
    public ChatComponentFancy join(ChatComponentFancy componentFancy) {
        Validate.notNull(componentFancy, "The component fancy object is null.");
        extras.addAll(componentFancy.extras);
        return this;
    }

    /**
     * 将此聊天花式组件构建为 {@link ChatComponent} 对象
     *
     * @return ChatComponent
     * @see ChatComponent
     */
    public ChatComponent build() {
        ChatComponent component = new ChatComponentText("");
        component.getExtras().addAll(extras);
        return component;
    }

    /**
     * 获取当前聊天花式组件列表内最后一个聊天组件
     *
     * @return 最后一个聊天组件
     */
    protected ChatComponent getLast() {
        return extras.get(extras.size() - 1);
    }
}
