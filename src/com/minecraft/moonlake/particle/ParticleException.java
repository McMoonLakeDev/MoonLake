package com.minecraft.moonlake.particle;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * <h1>ParticleException</h1>
 * 粒子效果异常类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class ParticleException extends MoonLakeException {

    /**
     * 粒子效果异常类构造函数
     */
    public ParticleException() {

        super("The MoonLake Particle Effect Exception.");
    }

    /**
     * 粒子效果异常类构造函数
     *
     * @param message 异常消息
     */
    public ParticleException(String message) {

        super(message);
    }

    /**
     * 粒子效果异常类构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public ParticleException(String message, Throwable cause) {

        super(message, cause);
    }
}
