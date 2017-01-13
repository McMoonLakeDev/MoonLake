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


package com.minecraft.moonlake.api;

import com.minecraft.moonlake.api.player.depend.WorldEditVector;
import com.minecraft.moonlake.api.region.RegionBlockVector;
import com.minecraft.moonlake.api.region.RegionVector;
import org.bukkit.util.Vector;

public interface VectorConvertible {

    Vector asBukkitVector();

    WorldEditVector asWorldEditVector();

    RegionVector asRegionVector();

    RegionBlockVector asRegionBlockVector();
}
