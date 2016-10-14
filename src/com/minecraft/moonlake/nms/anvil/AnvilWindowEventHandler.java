package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.execute.Execute;

/**
 * <h1>AnvilWindowEventHandler</h1>
 * 铁砧窗口事件处理接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface AnvilWindowEventHandler<T extends AnvilWindowEvent> extends Execute<T> {

    void onExecute(T event);
}
