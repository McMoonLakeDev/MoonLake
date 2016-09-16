package com.minecraft.moonlake.json;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by MoonLake on 2016/9/16.
 */
public interface JsonRepresentedObject {

    void writeJson(JsonWriter jsonWriter) throws IOException;
}
