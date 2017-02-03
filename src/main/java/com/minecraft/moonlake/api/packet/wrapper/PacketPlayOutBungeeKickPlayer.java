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
 * <h1>PacketPlayOutBungeeKickPlayer</h1>
 * 数据包输出蹦极目标 KICK
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutBungeeKickPlayer extends PacketPlayOutBungeeAbstractTarget {

    private StringProperty reason;

    /**
     * 数据包输出蹦极目标 KICK 构造函数
     *
     * @param target 目标玩家名
     * @param reason 原因
     */
    public PacketPlayOutBungeeKickPlayer(String target, String reason) {

        super(target);

        this.reason = new SimpleStringProperty(reason);
    }

    /**
     * 获取此数据包输出蹦极目标 KICK 的原因
     *
     * @return 原因
     */
    public StringProperty getReason() {

        return reason;
    }

    @Override
    protected void write() throws IOException {

        super.dataOut.writeUTF("KickPlayer");
        super.dataOut.writeUTF(super.target.get());
        super.dataOut.writeUTF(reason.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketPlayOutBungeeKickPlayer that = (PacketPlayOutBungeeKickPlayer) o;

        return reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return reason.hashCode();
    }

    @Override
    public String toString() {
        return "PacketPlayOutBungeeKickPlayer{" +
                "reason=" + reason +
                ", target=" + target +
                ", dataOut=" + dataOut +
                '}';
    }
}
