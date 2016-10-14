package com.minecraft.moonlake.api;

/**
 * <h1>BukkitInfo</h1>
 * Bukkit 服务端信息接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface BukkitInfo {

    /**
     * 获取 Bukkit 服务器的版本
     *
     * @return 版本
     */
    String getBukkitVersion();

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     */
    int getReleaseNumber();
}
