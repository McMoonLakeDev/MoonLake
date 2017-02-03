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

import java.io.IOException;

/**
 * <h1>PacketPlayOutBungeeGetUUIDOther</h1>
 * 数据包输出蹦极获取目标 UUID
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutBungeeGetUUIDOther extends PacketPlayOutBungeeAbstractTarget {

    /**
     * 数据包输出蹦极获取目标 UUID 构造函数
     *
     * @param target 目标玩家名
     */
    public PacketPlayOutBungeeGetUUIDOther(String target) {

        super(target);
    }

    @Override
    protected void write() throws IOException {

        super.dataOut.writeUTF("UUIDOther");
        super.dataOut.writeUTF(super.target.get());
    }

    @Override
    public String toString() {
        return "PacketPlayOutBungeeGetUUIDOther{" +
                "target=" + target +
                ", dataOut=" + dataOut +
                '}';
    }
}
