/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.particle

/**
 * @author [DarkBlade12](https://github.com/DarkBlade12) by origin, [lgou2w](https://github.com/lgou2w) by modified.
 */
enum class ParticleProperty {

    /**
     * Particle Property: Requires Water (粒子效果属性: 需求水源)
     */
    REQUIRES_WATER,
    /**
     * Particle Property: Requires Data (粒子效果属性: 需求数据)
     */
    REQUIRES_DATA,
    /**
     * Particle Property: Directional (粒子效果属性: 矢量方向)
     */
    DIRECTIONAL,
    /**
     * Particle Property: Color (粒子效果属性: 颜色)
     */
    COLOR,
    ;
}
