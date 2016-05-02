package com.minecraft.moonlake.exception.item;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public class NotBookItemException extends MoonLakeException {

    private static final long serialVersionUID = 1L;

    public NotBookItemException() {
        super("The ItemStack not book");
    }

    public NotBookItemException(String message) {
        super(message);
    }
}
