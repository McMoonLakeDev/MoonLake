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

package com.mcmoonlake.api.particle

import org.bukkit.Material

/**
 * @author [DarkBlade12](https://github.com/DarkBlade12) by origin, [lgou2w](https://github.com/lgou2w) by modified.
 */
abstract class ParticleData {

    /** member */

    val material: Material
    val data: Byte
    val packetData: Array<out Int>

    /** constructor */

    constructor(material: Material, data: Byte) {
        this.material = material
        this.data = data
        this.packetData = arrayOf(material.id, data.toInt())
    }

    /** api */
    open val packetDataString: String
        get() = "_${packetData[0]}_${packetData[1]}"
}
