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


package com.minecraft.moonlake.api.annotation.plugin;

import com.minecraft.moonlake.api.annotation.plugin.config.ConfigAnnotation;
import com.minecraft.moonlake.api.annotation.plugin.config.ConfigValue;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;

/**
 * <h1>ConfigAnnotationExpression</h1>
 * 配置文件注解接口实现类
 *
 * @version 1.0
 * @author Month_Light
 * @see ConfigAnnotation
 */
class ConfigAnnotationExpression extends PluginAnnotationAbstract implements ConfigAnnotation {

    /**
     * 配置文件注解接口实现类构造函数
     */
    public ConfigAnnotationExpression() {
    }

    @Override
    public void load(Plugin plugin, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");

        load(plugin, plugin.getConfig(), obj);
    }

    @Override
    public void load(Plugin plugin, String file, Object obj) throws MoonLakeException {

        Validate.notNull(file, "The file object is null.");

        File file0 = new File(file);

        if(!file0.isAbsolute()) {

            file0 = new File(plugin.getDataFolder(), file);
        }
        load(plugin, file0, obj);
    }

    @Override
    public void load(Plugin plugin, File file, Object obj) throws MoonLakeException {

        Validate.notNull(file, "The file object is null.");

        load(plugin, YamlConfiguration.loadConfiguration(file), obj);
    }

    @Override
    public void load(Plugin plugin, FileConfiguration config, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(config, "The configuration object is null.");
        Validate.notNull(obj, "The obj object is null.");

        Class<?> clazz = obj.getClass();

        for(final Field field : clazz.getDeclaredFields()) {

            try {

                ConfigValue configValue = field.getAnnotation(ConfigValue.class);

                if(configValue != null) {

                    field.setAccessible(true);

                    if(config.contains(configValue.path())) {

                        Object value = config.get(configValue.path());

                        if(configValue.colorChar() != ' ') {

                            if(value instanceof String) {

                                value = StringUtil.toColor(configValue.colorChar(), (String) value);
                            }
                            else if(value instanceof List) {

                                for(final ListIterator listIterator = ((List) value).listIterator(); listIterator.hasNext();) {

                                    Object listObj = listIterator.next();

                                    if(listObj instanceof String) {

                                        listIterator.set(StringUtil.toColor(configValue.colorChar(), (String) listObj));
                                    }
                                }
                            }
                        }
                        field.set(obj, value);
                    }
                }
            }
            catch (Exception e) {

                throw new MoonLakeException("The failed to set config value for field '" + field.getName() + "' in " + clazz, e);
            }
        }
    }

    @Override
    public void save(Plugin plugin, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");

        save(plugin, "config.yml", obj);
    }

    @Override
    public void save(Plugin plugin, String file, Object obj) throws MoonLakeException {

        Validate.notNull(file, "The file object is null.");

        File file0 = new File(file);

        if(!file0.isAbsolute()) {

            file0 = new File(plugin.getDataFolder(), file);
        }
        save(plugin, file0, obj);
    }

    @Override
    public void save(Plugin plugin, File file, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(file, "The file object is null.");
        Validate.notNull(obj, "The obj object is null.");

        Class<?> clazz = obj.getClass();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(final Field field : clazz.getDeclaredFields()) {

            try {

                ConfigValue configValue = field.getAnnotation(ConfigValue.class);

                if(configValue != null) {

                    field.setAccessible(true);

                    Object value = field.get(obj);

                    if(configValue.colorChar() != ' ') {

                        if(value instanceof String) {

                            value = StringUtil.toColor(configValue.colorChar(), (String) value);
                        }
                        else if(value instanceof List) {

                            for(final ListIterator listIterator = ((List) value).listIterator(); listIterator.hasNext();) {

                                Object listObj = listIterator.next();

                                if(listObj instanceof String) {

                                    listIterator.set(StringUtil.toColor(configValue.colorChar(), (String) listObj));
                                }
                            }
                        }
                    }
                    config.set(configValue.path(), value);

                    try {

                        config.save(file);
                    }
                    catch (Exception e) {

                        throw new MoonLakeException("The save config file '" + file.getName() + "' data exception.", e);
                    }
                }
            }
            catch (Exception e) {

                throw new MoonLakeException("The failed to set config value for field '" + field.getName() + "' in " + clazz, e);
            }
        }
    }
}
