/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.region.iterator;

import com.minecraft.moonlake.api.region.Region;
import com.minecraft.moonlake.api.region.RegionVector;
import com.minecraft.moonlake.api.region.RegionVector2D;
import com.minecraft.moonlake.validate.Validate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FlatRegionIterator implements Iterator<RegionVector2D> {

    private Region region;
    private int y;
    private int minX;
    private int nextX;
    private int nextZ;
    private int maxX;
    private int maxZ;

    public FlatRegionIterator(Region region) {

        this.region = Validate.checkNotNull(region);

        RegionVector min = region.getMinimumPoint();
        RegionVector max = region.getMaximumPoint();

        this.y = min.getBlockY();
        this.minX = min.getBlockX();
        this.nextX = minX;
        this.nextZ = min.getBlockZ();
        this.maxX = max.getBlockX();
        this.maxZ = max.getBlockZ();

        this.forward();
    }

    private void forward() {

        while (hasNext() && !region.contains(new RegionVector(nextX, y, nextZ)))
            forwardOne();
    }

    private void forwardOne() {

        if(++nextX <= maxX)
            return;

        nextX = minX;

        if(++nextZ <= maxZ)
            return;

        nextX = 0x7fffffff;
    }

    @Override
    public boolean hasNext() {

        return nextX != 0x7fffffff;
    }

    @Override
    public RegionVector2D next() {

        if(!hasNext())
            throw new NoSuchElementException();

        RegionVector2D answer = new RegionVector2D(nextX, nextZ);

        forwardOne();
        forward();

        return answer;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();
    }
}
