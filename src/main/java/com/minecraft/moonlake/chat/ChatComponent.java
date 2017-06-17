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

import java.util.List;

/**
 * <h1>ChatComponent</h1>
 * 聊天组件接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface ChatComponent {

    /**
     * 获取此聊天组件的聊天样式对象
     *
     * @return 聊天样式对象
     */
    ChatStyle getStyle();

    /**
     * 设置此聊天组件的聊天样式对象
     *
     * @param style 聊天样式对象
     * @throws IllegalArgumentException 如果聊天样式对象为 {@code null} 则抛出异常
     */
    ChatComponent setStyle(ChatStyle style);

    /**
     * 获取此聊天组件附加组件列表
     *
     * @return 附加组件列表
     */
    List<ChatComponent> getExtras();

    /**
     * 获取此聊天组件附加组件列表大小
     *
     * @return 附加组件列表大小
     */
    int getExtraSize();

    /**
     * 将指定文本附加到此聊天组件的附加列表中
     *
     * @param text 文本
     * @throws IllegalArgumentException 如果文本对象为 {@code null} 则抛出异常
     */
    ChatComponent append(String text);

    /**
     * 将指定聊天组件附加到此聊天组件的附加列表中
     *
     * @param extra 附加
     * @throws IllegalArgumentException 如果附加对象为 {@code null} 则抛出异常
     */
    ChatComponent append(ChatComponent extra);

    /**
     * 将此聊天组件转换为 Json 格式字符串
     *
     * @return Json
     */
    String toJson();

    /**
     * 将此聊天组件转换为源文本内容
     *
     * @return 源文本内容
     */
    String toRaw();

    /**
     * 将此聊天组件转换为源文本内容
     *
     * @param color 是否附加颜色
     * @return 源文本内容
     */
    String toRaw(boolean color);
}
