package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/9/15.
 */
public class NotImplementedException extends MoonLakeException {

    public NotImplementedException() {

        super("The method not implementsed exception.");
    }

    public NotImplementedException(String message) {

        super(message);
    }
}
