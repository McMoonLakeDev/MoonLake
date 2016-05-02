package com.minecraft.moonlake.exception.player;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/4/28.
 * @version 1.0
 * @author Month_Light
 */
public class PlayerNotOnlineException extends MoonLakeException {

    private static final long serialVersionUID = 1L;

    public PlayerNotOnlineException() {
        super("Player is not online");
    }

    public PlayerNotOnlineException(String message) {
        super(message);
    }
}
