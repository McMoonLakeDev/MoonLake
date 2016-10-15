package com.minecraft.moonlake.json;

import com.google.gson.stream.JsonReader;

import java.io.Reader;

/**
 * <h1>JsonRead</h1>
 * Json Read Class
 *
 * @version 1.0
 * @author Month_Light
 */
public class JsonRead extends JsonReader {

    /**
     * Creates a new instance that reads a JSON-encoded stream from {@code in}.
     *
     * @param in 输入
     */
    public JsonRead(Reader in) {

        super(in);
    }
}
