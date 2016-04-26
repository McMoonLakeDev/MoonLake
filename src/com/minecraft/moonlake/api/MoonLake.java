package com.minecraft.moonlake.api;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.api.lorelib.Lorelib;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLake extends MoonLakeInfo {

    /**
     * 获取物品支持库实例对象
     *
     * @return Itemlib
     */
    Itemlib getItemlib();

    /**
     * 获取标签支持库实例对象
     *
     * @return Lorelib
     */
    Lorelib getLorelib();
}
