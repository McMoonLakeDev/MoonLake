package com.minecraft.moonlake.api;

/**
 * Created by MoonLake on 2016/7/17.
 */
public interface MoonLakeCore {

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #getMoonLake()}
     */
    @Deprecated
    MoonLake getInstance();

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     */
    MoonLake getMoonLake();
}
