package com.minecraft.moonlake.exception.item;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotPotionItemException extends MoonLakeException {

    private static final long serialVersionUID = 1L;

    public NotPotionItemException() {
        super("The ItemStack not potion");
    }

    public NotPotionItemException(String message) {
        super(message);
    }
}
