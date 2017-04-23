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


package com.minecraft.moonlake.api.player.depend;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>DependPluginPlayerAbstract</h1>
 * 依赖插件玩家抽象类
 *
 * @param <T> 依赖插件
 * @version 1.0
 * @author Month_Light
 * @see Depend
 */
public abstract class DependPluginPlayerAbstract<T extends JavaPlugin & Plugin> implements Depend {

    private T own;
    private PluginDescriptionFile ownDF;

    protected DependPluginPlayerAbstract(T own) {

        this.own = own;
        this.ownDF = own != null ? own.getDescription() : null;

        if(ownDF == null)
            ownDF = new PluginDescriptionFile("Error: Unknown", "Error: Unknown", "Error: Unknown");
    }

    protected DependPluginPlayerAbstract(T own, PluginDescriptionFile ownDF) {

        this.own = own;
        this.ownDF = ownDF;
    }

    protected T getOwn() {

        return own;
    }

    @Override
    public String getPluginPrefix() {

        return ownDF.getPrefix();
    }

    @Override
    public String getPluginName() {

        return ownDF.getName();
    }

    @Override
    public String getPluginMain() {

        return ownDF.getMain();
    }

    @Override
    public String getPluginVersion() {

        return ownDF.getVersion();
    }

    @Override
    public String getPluginWebsite() {

        return ownDF.getWebsite();
    }

    @Override
    public String getPluginDescription() {

        return ownDF.getDescription();
    }

    @Override
    public Set<String> getPluginAuthors() {

        return new HashSet<>(ownDF.getAuthors());
    }

    @Override
    public Set<String> getPluginDepends() {

        return new HashSet<>(ownDF.getDepend());
    }

    @Override
    public Set<String> getPluginSoftDepends() {

        return new HashSet<>(ownDF.getSoftDepend());
    }
}
