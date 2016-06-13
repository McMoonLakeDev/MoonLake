package com.minecraft.moonlake.exception.item;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/6/13.
 */
public class NotArmorItemException extends MoonLakeException {

    public NotArmorItemException() {

        super("The ItemStack not Armor.");
    }

    public NotArmorItemException(String message) {

        super(message);
    }
}
