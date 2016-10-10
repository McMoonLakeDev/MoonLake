package com.minecraft.moonlake.json;

import com.google.gson.stream.JsonReader;

import java.io.Reader;

/**
 * Created by MoonLake on 2016/10/10.
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
