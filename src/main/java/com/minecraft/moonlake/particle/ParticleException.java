/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.particle;

import com.minecraft.moonlake.exception.MoonLakeException;

/**
 * <h1>ParticleException</h1>
 * 粒子效果异常类（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public class ParticleException extends MoonLakeException {

    private static final long serialVersionUID = 885601937060886278L;

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

    /**
     * 粒子效果异常类构造函数
     *
     * @param cause 异常原因
     */
    public ParticleException(Throwable cause) {

        super(cause);
    }
}
