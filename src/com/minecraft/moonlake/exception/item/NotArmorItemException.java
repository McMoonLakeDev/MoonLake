package com.minecraft.moonlake.exception.item;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotArmorItemException extends MoonLakeException {

    private static final long serialVersionUID = 1L;

    public NotArmorItemException() {
        super("The ItemStack not armor");
    }

    public NotArmorItemException(String message) {
        super(message);
    }
}
