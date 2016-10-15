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

    /**
     * 铁砧窗口关闭事件类构造函数
     *
     * @param player 玩家
     * @param anvilWindow 铁砧窗口对象
     */
    public AnvilWindowCloseEvent(Player player, AnvilWindow anvilWindow) {

        super(player, anvilWindow);
    }
}
