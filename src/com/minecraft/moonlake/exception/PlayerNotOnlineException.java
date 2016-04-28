package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/4/28.
 * @version 1.0
 * @author Month_Light
 */
public class PlayerNotOnlineException extends Exception {

    public PlayerNotOnlineException() {
        super("Player is not online.");
    }

    public PlayerNotOnlineException(String message) {
        super(message);
    }
}
