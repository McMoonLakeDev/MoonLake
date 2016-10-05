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
     */
    public MoonLake getInstance() {

        return MAIN;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     */
    protected static MoonLake getMain() {

        return MAIN;
    }
}
