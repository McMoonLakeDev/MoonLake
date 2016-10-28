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


package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutCloseWindow</h1>
 * 数据包输出关闭窗口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutCloseWindow extends PacketAbstract<PacketPlayOutCloseWindow> {

    private final static Class<?> CLASS_PACKETPLAYOUTCLOSEWINDOW;

    static {

        try {

            CLASS_PACKETPLAYOUTCLOSEWINDOW = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutCloseWindow");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out close window reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty windowId;

    /**
     * 数据包输出关闭窗口类构造函数
     *
     * @param windowId 窗口 Id
     */
    public PacketPlayOutCloseWindow(int windowId) {

        this.windowId = new SimpleIntegerProperty(windowId);
    }

    public IntegerProperty getWindowId() {

        return windowId;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object packet= instantiateObject(CLASS_PACKETPLAYOUTCLOSEWINDOW, windowId.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out close window send exception.", e);
        }
    }
}
