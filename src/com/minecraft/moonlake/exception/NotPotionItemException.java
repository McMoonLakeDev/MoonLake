package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotPotionItemException extends RuntimeException {

    public NotPotionItemException() {
        super("The ItemStack not potion");
    }

    public NotPotionItemException(String message) {
        super(message);
    }
}
