package com.minecraft.moonlake.api;

import java.util.Set;

/**
 * <hr />
 * <div>
 *     <h1>MoonLake Core API Plugin Info Interface</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLakeInfo {

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    String getPluginPrefix();

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    String getPluginName();

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    String getPluginMain();

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    String getPluginVersion();

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    String getPluginWebsite();

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    String getPluginDescription();

    /**
     * 获取插件的作者
     *
     * @return Auther
     */
    Set<String> getPluginAuthers();

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    Set<String> getPluginDepends();

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    Set<String> getPluginSoftDepends();
}
