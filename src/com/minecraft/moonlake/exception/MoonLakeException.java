package com.minecraft.moonlake.exception;

/**
 * Created by MoonLake on 2016/5/2.
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakeException extends RuntimeException {

    public MoonLakeException() {

        this("The moonlake plugin exception");
    }

    public MoonLakeException(String message) {

        super(message);
    }
}
