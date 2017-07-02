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

/**
 * <h1>FancyMessageFactory</h1>
 * 花式消息工厂类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatComponentFancy}
 */
@Deprecated
public class FancyMessageFactory {

    /**
     * 花式消息工厂静态对象
     */
    private static FancyMessageFactory fancyMessageInstance;

    /**
     * 花式消息工厂类构造函数
     */
    private FancyMessageFactory() {

    }

    /**
     * 获取 FancyMessageFactory 对象
     *
     * @return FancyMessageFactory
     */
    public static FancyMessageFactory get() {

        if(fancyMessageInstance == null) {

            fancyMessageInstance = new FancyMessageFactory();
        }
        return fancyMessageInstance;
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public FancyMessage message(String text) {

        return new FancyMessageExpression(text);
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public FancyMessage message(TextualComponent text) {

        return new FancyMessageExpression(text);
    }
}
