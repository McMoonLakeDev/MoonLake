package com.minecraft.moonlake.api;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.api.playerlib.Playerlib;

/**
 * <h1>主实例对象API接口提供 (子API、特殊函数等等)</h1>
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLake extends MoonLakeInfo, BukkitInfo {

    /**
     * 给控制台输出日志
     *
     * @see MLogger#log(String)
     * @param log 日志
     */
    @Deprecated
    void log(String log);

    /**
     * 获取控制台日志对象
     *
     * @return 日志对象
     */
    MLogger getMLogger();

    /**
     * 获取物品支持库实例对象
     *
     * @return Itemlib
     */
    Itemlib getItemlib();

    /**
     * 获取标签支持库实例对象
     *
     * @return Lorelib
     */
    Lorelib getLorelib();

    /**
     * 获取玩家支持库实例对象
     *
     * @return Playerlib
     */
    Playerlib getPlayerlib();
}
