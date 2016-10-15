package com.minecraft.moonlake.builder;

/**
 * <h1>UncertainParamBuilder</h1>
 * 不定长度参数建造接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface UncertainParamBuilder<R, P> {

    R build(P... params);
}
