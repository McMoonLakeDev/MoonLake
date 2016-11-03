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


package com.minecraft.moonlake.api.annotation.plugin.command;

import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotation;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public interface CommandAnnotation extends PluginAnnotation {

    Set<CommandAnnotated> registerCommand(Plugin plugin, Object obj) throws MoonLakeException;
}
