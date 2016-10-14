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

    public ParticleException() {

        super("The MoonLake Particle Effect Exception.");
    }

    public ParticleException(String message) {

        super(message);
    }

    public ParticleException(String message, Throwable cause) {

        super(message, cause);
    }
}
