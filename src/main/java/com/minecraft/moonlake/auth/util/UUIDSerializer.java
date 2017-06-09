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


package com.minecraft.moonlake.auth.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.UUID;

/**
 * <h1>UUIDSerializer</h1>
 * UUID 序列化类
 *
 * @version 1.0
 * @author Month_Light
 * @see TypeAdapter
 */
public class UUIDSerializer extends TypeAdapter<UUID> {

    public UUIDSerializer() {
    }

    @Override
    public void write(JsonWriter jsonWriter, UUID uuid) throws IOException {
        jsonWriter.value(fromUUID(uuid));
    }

    @Override
    public UUID read(JsonReader jsonReader) throws IOException {
        return fromString(jsonReader.nextString());
    }

    /**
     * 将指定 UUID 对象转换为 32 位字符串 UUID
     *
     * @param value UUID
     * @return 32 位字符串 UUID
     */
    public static String fromUUID(UUID value) {
        if(value == null)
            return "";
        return value.toString().replace("-", "");
    }

    /**
     * 将指定字符串 UUID 对象转换为 UUID 对象
     *
     * @param value 字符串 UUID
     * @return UUID 对象
     */
    public static UUID fromString(String value) {
        if(value == null || value.equals(""))
            return null;
        return UUID.fromString(value.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }
}
