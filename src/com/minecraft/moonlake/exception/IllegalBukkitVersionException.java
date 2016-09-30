package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/7/26.
 */
public class IllegalBukkitVersionException extends MoonLakeException {

    public IllegalBukkitVersionException() {

        this("The method not support this bukkit version.");
    }

    public IllegalBukkitVersionException(String message) {

        super(message);
    }

    public IllegalBukkitVersionException(String message, Throwable cause) {

        super(message, cause);
    }
}
