package com.minecraft.moonlake.api;

import com.minecraft.moonlake.logger.MLogger;

/**
 * <hr />
 * <div>
 *     <h1>主实例对象API接口提供 (子API、特殊函数等等)</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLake extends MoonLakeInfo, BukkitInfo {

    /**
     * 获取控制台日志对象
     *
     * @return 日志对象
     */
    MLogger getMLogger();
}
