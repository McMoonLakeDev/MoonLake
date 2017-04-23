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
 
 
package com.minecraft.moonlake.logger;

/**
 * <h1>MLogger</h1>
 * 日志接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public interface MLogger { // TODO 2.0

    /**
     * 获取此控制台日志对象的前缀属性
     *
     * @return 前缀属性
     */
    String getPrefix();

    /**
     * 在控制台输出日志消息
     *
     * @param message 消息
     */
    void log(String message);

    /**
     * 在控制台输出警告消息
     *
     * @param message 消息
     */
    void warn(String message);

    /**
     * 在控制台输出信息消息
     *
     * @param message 消息
     */
    void info(String message);

    /**
     * 在控制台输出错误消息
     *
     * @param message 消息
     */
    void error(String message);
}
