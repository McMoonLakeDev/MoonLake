package com.minecraft.moonlake.api.fancy;

import com.google.gson.stream.JsonWriter;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;

import java.io.IOException;

/**
 * <h1>JsonRepresentedObject Implement Class</h1>
 * Json 实现对象接口实现类
 *
 * @version 1.0
 * @author Month_Light
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
