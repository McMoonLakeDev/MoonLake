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

import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;

import java.io.IOException;

/**
 * <h1>PacketPlayOutBungeeConnectOther</h1>
 * 数据包输出蹦极目标连接服务器
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutBungeeConnectOther extends PacketPlayOutBungeeAbstractTarget {

    private StringProperty targetServer;

    /**
     * 数据包输出蹦极目标连接服务器构造函数
     *
     * @param target 目标玩家名
     * @param targetServer 目标服务器
     */
    public PacketPlayOutBungeeConnectOther(String target, String targetServer) {

        super(target);

        this.targetServer = new SimpleStringProperty(targetServer);
    }

    /**
     * 获取此数据包输出蹦极目标连接服务器的目标服务器
     *
     * @return 目标服务器
     */
    public StringProperty getTargetServer() {

        return targetServer;
    }

    @Override
    protected void write() throws IOException {

        super.dataOut.writeUTF("ConnectOther");
        super.dataOut.writeUTF(super.target.get());
        super.dataOut.writeUTF(targetServer.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketPlayOutBungeeConnectOther that = (PacketPlayOutBungeeConnectOther) o;

        return targetServer.equals(that.targetServer);
    }

    @Override
    public int hashCode() {
        return targetServer.hashCode();
    }

    @Override
    public String toString() {
        return "PacketPlayOutBungeeConnectOther{" +
                "targetServer=" + targetServer +
                ", target=" + target +
                ", dataOut=" + dataOut +
                '}';
    }
}
