package com.minecraft.moonlake.particle;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * Created by MoonLake on 2016/6/8.
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
