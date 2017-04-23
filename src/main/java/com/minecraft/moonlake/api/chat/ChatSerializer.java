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


package com.minecraft.moonlake.api.chat;

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.reflect.ExactReflect;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Method;

/**
 * <h1>ChatSerializer</h1>
 * 聊天序列化 (临时)
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatSerializer {

    private static volatile MethodAccessor iChatBaseComponentFormJsonMethod;

    static {

        Class<?> chatSerializerClass = MinecraftReflection.getChatSerializerClass();
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();

        try {

            Method temp = null;

            try {
                // 先直接获取 ChatSerializer 类的 a 函数, 这个函数也就是将 json 转换为 ICBC 对象
                temp = ExactReflect.fromClass(chatSerializerClass, true).getMethod("a", String.class);
            } catch (Exception e) {
            }
            if(temp == null) {
                // 判断一下如果上面的获取异常, 那么在使用这个方式
                // 这个方式就是遍历 ChatSerializer 类的所有公开函数
                // 然后对比返回值和函数的需要参数, 符合则获取对应函数对象
                temp = FuzzyReflect.fromClass(chatSerializerClass, true).getMethodByParameters("a", iChatBaseComponentClass, new Class[] { String.class });
            }
            iChatBaseComponentFormJsonMethod = Accessors.getMethodAccessor(temp);
        }
        catch (Exception e) {

            throw new NMSException("The chat serializer reflect raw initialize exception.", e);
        }
    }

    private ChatSerializer() {
    }

    /**
     * 获取 IChatBaseComponent 的 NMS 类对象
     *
     * @return IChatBaseComponent Class
     * @see MinecraftReflection#getIChatBaseComponentClass()
     */
    public static Class<?> getIChatBaseComponent() {

        return MinecraftReflection.getIChatBaseComponentClass();
    }

    /**
     * 将指定 Json 文本内容转换到 IChatBaseComponent 对象实例
     *
     * @param json Json 文本
     * @return IChatBaseComponent | null
     */
    public static Object fromJson(String json) {

        Validate.notNull(json, "The json object is null.");

        try {
            return iChatBaseComponentFormJsonMethod.invoke(null, json);
        }
        catch (Exception e) {
        }
        return null;
    }
}
