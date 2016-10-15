package com.minecraft.moonlake.api;

/**
 * <h1>MoonLakeCore</h1>
 * 月色之湖核心接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
