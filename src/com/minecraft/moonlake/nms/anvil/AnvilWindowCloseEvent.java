package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * <h1>AnvilWindowCloseEvent</h1>
 * 铁砧窗口关闭事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public class AnvilWindowCloseEvent extends AnvilWindowAbstractEvent {

    public AnvilWindowCloseEvent(Player player, AnvilWindow anvilWindow) {

        super(player, anvilWindow);
    }
}
