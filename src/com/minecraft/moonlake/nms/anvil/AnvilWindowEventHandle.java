package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.execute.Execute;

/**
 * Created by MoonLake on 2016/10/3.
 */
public interface AnvilWindowEventHandle<T extends AnvilWindowEvent> extends Execute<T> {

    void onExecute(T event);
}
