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

import org.bukkit.Color

/**
 * @author [DarkBlade12](https://github.com/DarkBlade12) by origin, [lgou2w](https://github.com/lgou2w) by modified.
 */
class ParticleColorOrdinary : ParticleColor {

    val red: Int
    val green: Int
    val blue: Int

    constructor(color: Color) : this(color.red, color.green, color.blue)

    @Throws(IllegalArgumentException::class)
    constructor(red: Int, green: Int, blue: Int) : super() {
        Color.fromBGR(red, green, blue)
        this.red = red
        this.green = green
        this.blue = blue
    }

    override val valueX: Float
        get() = red.div(255f)

    override val valueY: Float
        get() = green.div(255f)

    override val valueZ: Float
        get() = blue.div(255f)
}
