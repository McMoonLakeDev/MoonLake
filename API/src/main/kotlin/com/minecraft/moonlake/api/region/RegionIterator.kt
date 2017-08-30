/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.region

class RegionIterator(private val region: Region) : MutableIterator<RegionVectorBlock> {

    private val maxX: Int
    private val maxY: Int
    private val maxZ: Int
    private val min: RegionVector
    private var nextX: Int
    private var nextY: Int
    private var nextZ: Int

    init {
        val max = region.getMaximumPoint()
        this.maxX = max.getBlockX()
        this.maxY = max.getBlockY()
        this.maxZ = max.getBlockZ()
        this.min = region.getMinimumPoint()
        this.nextX = min.getBlockX()
        this.nextY = min.getBlockY()
        this.nextZ = min.getBlockZ()
        this.forward()
    }

    private fun forward() {
        while(hasNext() && !region.contains(RegionVector(nextX, nextY, nextZ)))
            forwardOne()
    }

    private fun forwardOne() {
        if(++nextX <= maxX)
            return
        nextX = min.getBlockX()

        if(++nextY <= maxY)
            return
        nextY = min.getBlockY()

        if(++nextZ <= maxZ)
            return
        nextX = 0x7fffffff
    }

    override fun hasNext(): Boolean
            = nextX != 0x7fffffff

    override fun next(): RegionVectorBlock {
        if(!hasNext())
            throw NoSuchElementException()
        val answer = RegionVectorBlock(nextX, nextY, nextZ)
        forwardOne()
        forward()
        return answer
    }

    override fun remove()
            = throw UnsupportedOperationException()
}
