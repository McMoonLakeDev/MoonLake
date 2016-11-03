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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>Command</h1>
 * 命令注解（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * 获取此命令的名称
     *
     * @return 名称
     */
    String name() default "";

    /**
     * 获取此命令的别称
     *
     * @return 别称
     */
    String[] aliases() default {};

    /**
     * 获取此命令的用法
     *
     * @return 用法
     */
    String usage() default "";

    /**
     * 获取此命令的注释
     *
     * @return 注释
     */
    String description() default "";

    /**
     * 获取此命令的最小参数长度
     *
     * @return 最小参数长度
     */
    int min() default 0;

    /**
     * 获取此命令的最大参数长度
     *
     * @return 最大参数长度
     */
    int max() default -1;

    /**
     * 获取此命令的回退前缀
     *
     * @return 回退前缀
     */
    String fallBackPrefix() default "";

    /**
     * 获取此命令的错误处理器类
     *
     * @return 错误处理器类
     * @see CommandErrorHandler
     */
    Class<? extends CommandErrorHandler> errorHandler() default CommandErrorMoonLakeHandler.class;
}
