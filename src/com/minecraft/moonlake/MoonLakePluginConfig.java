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


package com.minecraft.moonlake;

import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotationFactory;
import com.minecraft.moonlake.api.annotation.plugin.config.ConfigValue;
import com.minecraft.moonlake.exception.MoonLakeException;

import java.io.File;

public class MoonLakePluginConfig {

    @ConfigValue(path = "version")
    private String version;

    @ConfigValue(path = "Language")
    private String language;

    private MoonLakePlugin main;

    MoonLakePluginConfig(MoonLakePlugin main) {

        this.main = main;
    }

    public MoonLakePlugin getMain() {

        return main;
    }

    public boolean reload() {

        if(!getMain().getDataFolder().exists()) {

            getMain().getDataFolder().mkdirs();
        }
        File config = new File(getMain().getDataFolder(), "config.yml");

        if(config.exists()) {

            // 配置文件已经存在则验证版本号
        }
        else {

            getMain().saveResource(config.getName(), false);
        }
        try {

            PluginAnnotationFactory.get().config().load(getMain(), config, this);

            return true;
        }
        catch (Exception e) {

            throw new MoonLakeException("The load plugin config.yml exception.", e);
        }
    }

    public String getVersion() {

        return version;
    }

    public String getLanguage() {

        return language;
    }
}
