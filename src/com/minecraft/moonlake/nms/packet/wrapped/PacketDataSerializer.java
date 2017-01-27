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


package com.minecraft.moonlake.nms.packet.wrapped;

import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketDataSerializer {

    private final static Class<?> CLASS_PACKETDATASERIALIZER;

    static {

        try {

            CLASS_PACKETDATASERIALIZER = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketDataSerializer");
        }
        catch (Exception e) {

            throw new NMSException("The packet data serializer initialize exception.", e);
        }
    }

    private ObjectProperty<ByteBuf> byteBuf;

    public PacketDataSerializer() {

        this(Unpooled.buffer());
    }

    public PacketDataSerializer(ByteBuf byteBuf) {

        this.byteBuf = new SimpleObjectProperty<>(byteBuf);
    }

    public ByteBuf getByteBuf() {

        return byteBuf.get();
    }

    public void setByteBuf(ByteBuf byteBuf) {

        this.byteBuf.set(byteBuf);
    }

    @Override
    public String toString() {
        return "PacketDataSerializer{" +
                "byteBuf=" + byteBuf.get() +
                '}';
    }

    public Object asNMS() throws NMSException {

        try {

            return Reflect.instantiateObject(CLASS_PACKETDATASERIALIZER, getByteBuf());
        }
        catch (Exception e) {

            throw new NMSException();
        }
    }
}
