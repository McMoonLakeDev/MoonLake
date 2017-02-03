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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.PacketPlayOutBungee;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;

import java.io.IOException;

/**
 * <h1>PacketPlayOutBungeeAbstractTarget</h1>
 * 数据包输出蹦极目标抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see PacketPlayOutBungee
 * @see PacketPlayOutBungeeAbstract
 */
public abstract class PacketPlayOutBungeeAbstractTarget extends PacketPlayOutBungeeAbstract {

    final StringProperty target;

    /**
     * 数据包输出蹦极目标抽象类构造函数
     *
     * @param target 目标玩家名
     */
    PacketPlayOutBungeeAbstractTarget(String target) {

        this.target = new SimpleStringProperty(target);
    }

    /**
     * 获取此数据包输出蹦极目标的目标玩家名
     *
     * @return 目标玩家名
     */
    public StringProperty getTarget() {

        return target;
    }

    protected abstract void write() throws IOException;
}
