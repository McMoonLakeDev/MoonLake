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
