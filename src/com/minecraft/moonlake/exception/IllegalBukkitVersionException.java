package com.minecraft.moonlake.exception;

/**
 * <h1>IllegalBukkitVersionException</h1>
 * 非法 Bukkit 版本异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
