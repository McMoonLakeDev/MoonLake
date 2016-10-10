package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;

/**
 * Created by MoonLake on 2016/7/17.
 */
@SuppressWarnings("deprecation")
public abstract class MoonLakeManager implements MoonLakeCore {

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #getMoonLakes()}
     */
    @Override
    @Deprecated
    public MoonLake getInstance() {

        return MAIN;
    }

    @Override
    public MoonLake getMoonLake() {

        return MAIN;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #getMoonLakes()}
     */
    @Deprecated
    protected static MoonLake getMain() {

        return MAIN;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     */
    protected static MoonLake getMoonLakes() {

        return MAIN;
    }
}
