package com.minecraft.moonlake.binding;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WeakListener {

    /**
     * 获取如果连接的监听器需要回收,在这种情况下,监听器可以从观察者清除
     *
     * @return true 如果连接的监听器需要回收
     */
    boolean wasGarbageCollected();
}
