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


package com.minecraft.moonlake.api.region;

import com.minecraft.moonlake.api.region.iterator.RegionIterator;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Iterator;

/**
 * <h1>AbstractRegion</h1>
 * 区域抽象实现类
 *
 * @version 1.0
 * @author Month_Light
 * @see Region
 * @see CuboidRegion
 * @see CylinderRegion
 */
public abstract class AbstractRegion implements Region {

    protected final World world;

    /**
     * 区域抽象实现类构造函数
     *
     * @param world 世界
     */
    public AbstractRegion(World world) {

        this.world = world;
    }

    @Override
    public World getWorld() {

        return world;
    }

    @Override
    public RegionVector getCenter() {

        return getMinimumPoint().add(getMaximumPoint()).divide(2);
    }

    @Override
    public int getArea() {

        RegionVector min = getMinimumPoint();
        RegionVector max = getMaximumPoint();
        return (int) ((max.getX() - min.getX() + 1d) * (max.getY() - min.getY() + 1d) * (max.getZ() - min.getZ() + 1d));
    }

    @Override
    public int getWidth() {

        RegionVector min = getMinimumPoint();
        RegionVector max = getMaximumPoint();
        return (int) (max.getX() - min.getX() + 1d);
    }

    @Override
    public int getHeight() {

        RegionVector min = getMinimumPoint();
        RegionVector max = getMaximumPoint();
        return (int) (max.getY() - min.getY() + 1d);
    }

    @Override
    public int getLength() {

        RegionVector min = getMinimumPoint();
        RegionVector max = getMaximumPoint();
        return (int) (max.getZ() - min.getZ() + 1d);
    }

    @Override
    public boolean contains(Location location) {

        return location != null && location.getWorld().equals(world) && contains(new RegionVector(location.getX(), location.getY(), location.getZ()));
    }

    @Override
    public boolean contains(Entity entity) {

        return entity != null && contains(entity.getLocation());
    }

    @Override
    public boolean contains(Block block) {

        return block != null && contains(block.getLocation());
    }

    @Override
    public Iterator<RegionBlockVector> iterator() {

        return new RegionIterator(this);
    }

    @Override
    public AbstractRegion clone() {

        try {

            return (AbstractRegion) super.clone();
        }
        catch (CloneNotSupportedException e) {

            throw new MoonLakeException(e.getMessage(), e);
        }
    }
}
