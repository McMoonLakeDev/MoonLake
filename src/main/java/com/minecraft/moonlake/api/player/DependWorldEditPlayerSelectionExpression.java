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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.depend.WorldEditSelection;
import com.minecraft.moonlake.api.player.depend.WorldEditVector;
import com.minecraft.moonlake.validate.Validate;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;

/**
 * <h1>DependWorldEditPlayerSelectionExpression</h1>
 * 创世神选择区域接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class DependWorldEditPlayerSelectionExpression implements WorldEditSelection {

    private final Selection handler;

    /**
     * 创世神选择区域接口实现类构造函数
     *
     * @param handler 创世神选择区域对象
     */
    public DependWorldEditPlayerSelectionExpression(Selection handler) {

        this.handler = handler;
    }

    @Override
    public Location getMinimumPoint() {

        return handler.getMinimumPoint();
    }

    @Override
    public Location getMaximumPoint() {

        return handler.getMaximumPoint();
    }

    @Override
    public WorldEditVector getNativeMinimumPoint() {

        return asVector(handler.getNativeMinimumPoint());
    }

    @Override
    public WorldEditVector getNativeMaximumPoint() {

        return asVector(handler.getNativeMaximumPoint());
    }

    protected final WorldEditVector asVector(Vector vector) {

        return vector != null ? new WorldEditVector(vector.getX(), vector.getY(), vector.getZ()) : null;
    }

    @Nullable
    @Override
    public World getWorld() {

        return handler.getWorld();
    }

    @Override
    public int getArea() {

        return handler.getArea();
    }

    @Override
    public int getWidth() {

        return handler.getWidth();
    }

    @Override
    public int getHeight() {

        return handler.getHeight();
    }

    @Override
    public int getLength() {

        return handler.getLength();
    }

    @Override
    public boolean contains(Location location) {

        Validate.notNull(location, "The location object is null.");

        return handler.contains(location);
    }

    @Override
    public boolean contains(Entity entity) {

        Validate.notNull(entity, "The entity object is null.");

        return handler.contains(entity.getLocation());
    }
}
