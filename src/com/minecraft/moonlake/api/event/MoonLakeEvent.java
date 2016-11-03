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


package com.minecraft.moonlake.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * <h1>MoonLakeEvent</h1>
 * 月色之湖事件类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Event
 */
public abstract class MoonLakeEvent extends Event {

    /**
     * 月色之湖事件类构造函数
     */
    public MoonLakeEvent() {

        super();
    }

    /**
     * 月色之湖事件类构造函数
     *
     * @param async 是否异步
     */
    public MoonLakeEvent(boolean async) {

        super(async);
    }

    @Override
    public String getEventName() {

        return super.getEventName();
    }

    @Override
    public abstract HandlerList getHandlers();
}
