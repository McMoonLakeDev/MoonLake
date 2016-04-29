package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotArmorItemException extends RuntimeException {

    public NotArmorItemException() {
        super("The ItemStack not armor");
    }

    public NotArmorItemException(String message) {
        super(message);
    }
}
