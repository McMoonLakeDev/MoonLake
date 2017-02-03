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


package com.minecraft.moonlake.api.packet.listener;

/**
 * <h1>Cancellable</h1>
 * 阻止器类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class Cancellable implements org.bukkit.event.Cancellable {

    private boolean cancel;

    /**
     * 阻止器类构造函数
     */
    public Cancellable() {
    }

    /**
     * 设置此阻止器是否阻止
     *
     * @param cancel 是否阻止
     */
    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;
    }

    /**
     * 获取此阻止器是否阻止
     *
     * @return 是否阻止
     */
    @Override
    public boolean isCancelled() {

        return cancel;
    }
}
