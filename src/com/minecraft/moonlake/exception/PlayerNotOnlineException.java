package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/9/14.
 */
public class PlayerNotOnlineException extends MoonLakeException {

    public PlayerNotOnlineException() {

        super("The player not online exception.");
    }

    public PlayerNotOnlineException(String player) {

        super("The player not online exception: " + player);
    }
}
