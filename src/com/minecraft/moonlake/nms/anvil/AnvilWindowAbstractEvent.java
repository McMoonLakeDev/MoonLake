package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * <h1>AnvilWindowAbstractEvent</h1>
 * 铁砧窗口抽象事件类
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class AnvilWindowAbstractEvent implements AnvilWindowEvent {

    private final Player player;
    private final AnvilWindow anvilWindow;

    /**
     * 铁砧窗口抽象事件类构造函数
     *
     * @param player 玩家
     * @param anvilWindow 铁砧窗口对象
     */
    public AnvilWindowAbstractEvent(Player player, AnvilWindow anvilWindow) {

        this.player = player;
        this.anvilWindow = anvilWindow;
    }

    public Player getPlayer() {

        return player;
    }

    public AnvilWindow getAnvilWindow() {

        return anvilWindow;
    }
}
