package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * <h1>AnvilWindowEvent</h1>
 * 铁砧窗口事件接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface AnvilWindowEvent {

    Player getPlayer();

    AnvilWindow getAnvilWindow();
}
