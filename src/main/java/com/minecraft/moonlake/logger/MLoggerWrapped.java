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

import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>MLoggerWrapped</h1>
 * 日志接口包装类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
@SuppressWarnings("deprecation")
public final class MLoggerWrapped implements MLogger { // TODO 2.0

    private final ReadOnlyStringProperty prefixProperty;
    private final Logger logger;

    /**
     * 日志接口包装类构造函数
     */
    public MLoggerWrapped() {

        this("MoonLake");
    }

    /**
     * 日志接口包装类构造函数
     *
     * @param prefix 消息前缀
     */
    public MLoggerWrapped(String prefix) {

        this.prefixProperty = new SimpleStringProperty(String.format("[%1$s]", prefix));
        this.logger = Logger.getLogger("Minecraft");
    }

    public String getPrefix() {

        return prefixProperty.get();
    }

    @Override
    public void log(String message) {

        log(Level.FINE, message);
    }

    @Override
    public void warn(String message) {

        log(Level.WARNING, message);
    }

    @Override
    public void info(String message) {

        log(Level.INFO, message);
    }

    @Override
    public void error(String message) {

        log(Level.SEVERE, message);
    }

    protected void log(Level level, String message) {

        logger.log(level, String.format("%1$s %2$s", getPrefix(), message));
    }
}
