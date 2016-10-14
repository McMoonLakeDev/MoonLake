package com.minecraft.moonlake.exception;

/**
 * <h1>PlayerNotOnlineException</h1>
 * 玩家未在线异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PlayerNotOnlineException extends MoonLakeException {

    public PlayerNotOnlineException() {

        super("The player not online exception.");
    }

    public PlayerNotOnlineException(String player) {

        super("The player not online exception: " + player);
    }
}
