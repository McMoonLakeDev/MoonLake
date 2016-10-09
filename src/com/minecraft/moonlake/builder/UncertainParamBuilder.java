package com.minecraft.moonlake.builder;

/**
 * Created by MoonLake on 2016/10/9.
 */
public interface UncertainParamBuilder<R, P> {

    R build(P... params);
}
