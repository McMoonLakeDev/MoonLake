package com.minecraft.moonlake.api;

import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;

/**
 * Created by MoonLake on 2016/5/2.
 * @version 1.0
 * @author Month_Light
 */
public interface BukkitInfo {

    /**
     * 获取 Bukkit 服务器的版本
     *
     * @return 版本
     */
    ReadOnlyStringProperty getBukkitVersion();

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     */
    ReadOnlyIntegerProperty getReleaseNumber();
}
