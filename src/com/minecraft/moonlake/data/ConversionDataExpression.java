package com.minecraft.moonlake.data;

/**
 * Created by MoonLake on 2016/9/19.
 */
public abstract class ConversionDataExpression implements ConversionData {

    protected final Object obj;

    public ConversionDataExpression(Object obj) {

        this.obj = obj;
    }
}
