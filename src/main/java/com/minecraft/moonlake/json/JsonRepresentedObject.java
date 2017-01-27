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
 
 
package com.minecraft.moonlake.json;

import java.io.IOException;

/**
 * <h1>JsonRepresentedObject</h1>
 * Json 写实现接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface JsonRepresentedObject {

    /**
     * 将指定内容写入 JsonWrite
     *
     * @param jsonWrite Json 写
     * @throws IOException 如果写出 IO 错误则抛出异常
     */
    void writeJson(JsonWrite jsonWrite) throws IOException;
}
