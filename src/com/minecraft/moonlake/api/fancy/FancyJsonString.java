package com.minecraft.moonlake.api.fancy;

import com.google.gson.stream.JsonWriter;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;

import java.io.IOException;

/**
 * Created by MoonLake on 2016/9/16.
 */
public class FancyJsonString implements JsonRepresentedObject {

    private StringProperty value;

    public FancyJsonString(String value) {

        this.value = new SimpleStringProperty(value);
    }

    public StringProperty getValue() {

        return value;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {

        jsonWriter.value(getValue().get());
    }

    @Override
    public String toString() {

        return getValue().get();
    }
}
