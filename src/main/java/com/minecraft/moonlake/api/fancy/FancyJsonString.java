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
 
 
package com.minecraft.moonlake.api.fancy;

import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.json.JsonWrite;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;

import java.io.IOException;

/**
 * <h1>JsonRepresentedObject Implement Class</h1>
 * Json 实现对象接口实现类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class FancyJsonString implements JsonRepresentedObject {

    private StringProperty value;

    /**
     * 花式消息 Json 字符串构造函数
     *
     * @param value 字符串值
     */
    public FancyJsonString(String value) {

        this.value = new SimpleStringProperty(value);
    }

    /**
     * 获取花式消息的字符串值
     *
     * @return 值
     */
    public StringProperty getValue() {

        return value;
    }

    @Override
    public void writeJson(JsonWrite jsonWrite) throws IOException {

        jsonWrite.value(getValue().get());
    }

    @Override
    public String toString() {

        return getValue().get();
    }
}
