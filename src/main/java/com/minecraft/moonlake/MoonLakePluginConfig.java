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
import com.minecraft.moonlake.manager.IoManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;

/**
 * <h1>MoonLakePluginConfig</h1>
 * 月色之湖插件配置类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakePluginConfig {

    @ConfigValue(path = "version")
    private String version;

    @ConfigValue(path = "Debug")
    private boolean debug;

    @ConfigValue(path = "PacketChannelListener")
    private boolean packetChannelListener;

    private MoonLakePlugin main;

    /**
     * 月色之湖插件配置类构造函数
     *
     * @param main 月色之湖插件
     */
    MoonLakePluginConfig(MoonLakePlugin main) {

        this.main = main;
    }

    /**
     * 获取月色之湖插件对象
     *
     * @return 插件对象
     */
    public MoonLakePlugin getMain() {

        return main;
    }

    /**
     * 重新加载配置文件并载入到此对象
     *
     * @return 是否成功
     */
    public boolean reload() {

        if(!getMain().getDataFolder().exists()) {

            getMain().getDataFolder().mkdirs();
        }
        File config = new File(getMain().getDataFolder(), "config.yml");

        if(config.exists()) {

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(config);

            if(!configuration.contains("version") || !configuration.getString("version").equals(getMain().getPluginVersion())) {

                try {

                    FileInputStream fis = new FileInputStream(config);
                    IoManager.outInputStream(new File(getMain().getDataFolder(), "config.old.yml"), fis);
                }
                catch (Exception e) {

                    throw new MoonLakeException("The copy config old file exception.", e);
                }
                getMain().saveResource(config.getName(), true);
            }
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

    /**
     * 获取月色之湖插件的版本号
     *
     * @return 版本号
     */
    public String getVersion() {

        return version;
    }

    /**
     * 获取是否开启插件的 Debug 功能
     *
     * @return 是否开启
     */
    public boolean isDebug() {

        return debug;
    }

    /**
     * 获取是否开启数据包通道监听器功能
     *
     * @return 是否开启
     */
    public boolean isPacketChannelListener() {

        return packetChannelListener;
    }
}
