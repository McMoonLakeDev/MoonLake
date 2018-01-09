/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

package com.mcmoonlake.api.region

class RegionIteratorFlat(
        private val region: Region
) : MutableIterator<RegionVector2D> {

    private val y: Int
    private val minX: Int
    private val maxX: Int
    private val maxZ: Int
    private var nextX: Int
    private var nextZ: Int

    init {
        val min = region.minimumPoint
        val max = region.maximumPoint
        this.y = min.blockY
        this.minX = min.blockX
        this.maxX = max.blockX
        this.maxZ = max.blockZ
        this.nextX = minX
        this.nextZ = min.blockZ
        this.forward()
    }

    private fun forward() {
        while(hasNext() && !region.contains(RegionVector(nextX, y, nextZ)))
            forwardOne()
    }

    private fun forwardOne() {
        if(++nextX <= maxX)
            return
        nextX = minX

        if(++nextZ <= maxZ)
            return
        nextX = 0x7fffffff
    }

    override fun hasNext(): Boolean
            = nextX != 0x7fffffff

    override fun next(): RegionVector2D {
        if(!hasNext())
            throw NoSuchElementException()
        val answer = RegionVector2D(nextX, nextZ)
        forwardOne()
        forward()
        return answer
    }

    override fun remove()
            = throw UnsupportedOperationException()
}
