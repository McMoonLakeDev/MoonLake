package com.minecraft.moonlake.builder;

/**
 * <h1>SimpleParamBuilder</h1>
 * 单参数建造接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface SimpleParamBuilder<R, P> {

    R build(P param);
}
