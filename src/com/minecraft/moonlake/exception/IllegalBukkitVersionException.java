package com.minecraft.moonlake.exception;

/**
 * <h1>IllegalBukkitVersionException</h1>
 * 非法 Bukkit 版本异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class IllegalBukkitVersionException extends MoonLakeException {

    /**
     * 非法 Bukkit 版本异常类构造函数
     */
    public IllegalBukkitVersionException() {

        this("The method not support this bukkit version.");
    }

    /**
     * 非法 Bukkit 版本异常类构造函数
     *
     * @param message 异常消息
     */
    public IllegalBukkitVersionException(String message) {

        super(message);
    }

    /**
     * 非法 Bukkit 版本异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public IllegalBukkitVersionException(String message, Throwable cause) {

        super(message, cause);
    }
}
