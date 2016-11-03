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

import com.minecraft.moonlake.api.annotation.plugin.command.CommandAnnotation;
import com.minecraft.moonlake.api.annotation.plugin.config.ConfigAnnotation;

/**
 * <h1>PluginAnnotationFactory</h1>
 * 插件注解工厂类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PluginAnnotationFactory {

    private static PluginAnnotationFactory pluginAnnotationFactoryInstance;

    /**
     * 插件注解工厂类构造函数
     */
    private PluginAnnotationFactory() {
    }

    /**
     * 获取 PluginAnnotationFactory 对象
     *
     * @return PluginAnnotationFactory
     */
    public static PluginAnnotationFactory get() {

        if(pluginAnnotationFactoryInstance == null) {

            pluginAnnotationFactoryInstance = new PluginAnnotationFactory();
        }
        return pluginAnnotationFactoryInstance;
    }

    /**
     * 获取配置文件注解 ConfigAnnotation 实例对象
     *
     * @return ConfigAnnotation
     */
    public ConfigAnnotation config() {

        return new ConfigAnnotationExpression();
    }

    /**
     * 获取命令注解 CommandAnnotation 实例对象
     *
     * @return CommandAnnotation
     */
    public CommandAnnotation command() {

        return new CommandAnnotationExpression();
    }
}
