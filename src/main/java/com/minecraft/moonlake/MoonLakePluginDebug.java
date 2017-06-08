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


package com.minecraft.moonlake;

import java.util.logging.Level;

/**
 * <h1>MoonLakePluginDebug</h1>
 * 月色之湖插件 Debug 功能类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakePluginDebug {

    private final static boolean debug = MoonLakeAPI.getMoonLake().getConfiguration().isDebug();

    MoonLakePluginDebug() {
    }

    /**
     * 获取月色之湖核心插件是否开启 Debug 功能
     *
     * @return 是否开启
     */
    public static boolean isDebug() {
        return debug;
    }

    private static String formatMsg(String msg) {
        return "[Debug] Exception: " + msg;
    }

    /**
     * 将指定消息和参数 Debug 打印到控制台
     *
     * @param msg 消息
     * @param params 参数
     * @see MoonLakePluginConfig#isDebug()
     */
    public static void debug(String msg, Object... params) {
        if(debug)
            MoonLakeAPI.getLogger().log(Level.WARNING, formatMsg(msg), params);
    }

    /**
     * 将指定消息和异常 Debug 打印到控制台
     *
     * @param msg 消息
     * @param e 异常
     * @see MoonLakePluginConfig#isDebug()
     */
    public static void debug(String msg, Throwable e) {
        if(debug)
            MoonLakeAPI.getLogger().log(Level.WARNING, formatMsg(msg), e);
    }

    /**
     * 将指定异常 Debug 打印到控制台
     *
     * @param e 异常
     * @see MoonLakePluginConfig#isDebug()
     */
    public static void debug(Throwable... e) {
        if(debug) for (Throwable t : e) if(t != null)
            MoonLakeAPI.getLogger().log(Level.WARNING, formatMsg(t.getMessage()), t);
    }
}
