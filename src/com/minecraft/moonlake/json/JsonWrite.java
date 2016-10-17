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

import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.Writer;

/**
 * <h1>JsonWrite</h1>
 * Json Write Class
 *
 * @version 1.0
 * @author Month_Light
 */
public class JsonWrite extends JsonWriter {

    /**
     * Creates a new instance that writes a JSON-encoded stream to {@code out}.
     * For best performance, ensure {@link Writer} is buffered; wrapping in
     * {@link BufferedWriter BufferedWriter} if necessary.
     *
     * @param out 输出
     */
    public JsonWrite(Writer out) {

        super(out);
    }
}
