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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>ChatSerializer</h1>
 * 聊天序列化 (临时)
 *
 * @version 1.0
 * @author Month_Light
 */
public class ChatSerializer {

    private final static Class<?> CLASS_ICHATBASECOMPONENT;
    private final static Class<?> CLASS_CHARSERIALIZER;
    private final static Method METHOD_JSONTOICBC;

    static {

        try {
            CLASS_ICHATBASECOMPONENT = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");
            CLASS_CHARSERIALIZER = PackageType.MINECRAFT_SERVER.getClass(MoonLakeAPI.currentBukkitVersionIs(MinecraftBukkitVersion.V1_8_R1) ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");

            Method JSONTOICBC_TEMP = null;

            try {
                // 先直接获取 ChatSerializer 类的 a 函数, 这个函数也就是将 json 转换为 ICBC 对象
                JSONTOICBC_TEMP = getMethod(CLASS_CHARSERIALIZER, "a", String.class);
            } catch (Exception e) {
            }
            if(JSONTOICBC_TEMP == null) {
                // 判断一下如果上面的获取异常, 那么在使用这个方式
                // 这个方式就是遍历 ChatSerializer 类的所有公开函数
                // 然后对比返回值和函数的需要参数, 符合则获取对应函数对象
                try {
                    Class<?>[] parameArray = { String.class };
                    Method[] methods = CLASS_CHARSERIALIZER.getMethods();
                    for(Method method : methods)
                        if(CLASS_ICHATBASECOMPONENT.isAssignableFrom(method.getReturnType()) && DataType.compare(method.getParameterTypes(), parameArray)) {
                            JSONTOICBC_TEMP = method;
                            break;
                        }
                } catch (Exception e) {
                }
            }
            METHOD_JSONTOICBC = JSONTOICBC_TEMP;
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
     */
    public static Class<?> getIChatBaseComponent() {

        return CLASS_ICHATBASECOMPONENT;
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
            return METHOD_JSONTOICBC.invoke(null, json);
        }
        catch (Exception e) {
        }
        return null;
    }
}
