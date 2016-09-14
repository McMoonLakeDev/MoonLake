package com.minecraft.moonlake.api;

import com.minecraft.moonlake.property.ReadOnlyStringProperty;

import java.util.Set;

/**
 * <h1>MoonLake Core API Plugin Info Interface</h1>
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLakeInfo {

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    ReadOnlyStringProperty getPluginPrefix();

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    ReadOnlyStringProperty getPluginName();

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    ReadOnlyStringProperty getPluginMain();

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    ReadOnlyStringProperty getPluginVersion();

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    ReadOnlyStringProperty getPluginWebsite();

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    ReadOnlyStringProperty getPluginDescription();

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
