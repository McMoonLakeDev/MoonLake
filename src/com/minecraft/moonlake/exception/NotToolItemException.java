package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotToolItemException extends RuntimeException {

    public NotToolItemException() {
        super("The ItemStack not tool");
    }

    public NotToolItemException(String message) {
        super(message);
    }
}
