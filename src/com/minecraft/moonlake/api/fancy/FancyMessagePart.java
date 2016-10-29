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

import com.google.gson.stream.JsonWriter;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.property.*;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>FancyMessagePart</h1>
 * 花式消息部分实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class FancyMessagePart implements JsonRepresentedObject, Cloneable {

    ObjectProperty<ChatColor> color;
    ObjectProperty<TextualComponent> text;
    ObjectProperty<List<FancyMessageStyle>> styles;
    ObjectProperty<JsonRepresentedObject> hoverActionValue;
    ObjectProperty<List<JsonRepresentedObject>> translationReplacements;
    StringProperty clickAction, clickActionValue, hoverAction, insertion;

    /**
     * 花式消息部分实现类构造函数
     */
    public FancyMessagePart() {

        this(null);
    }

    /**
     * 花式消息部分实现类构造函数
     *
     * @param text 文本组件
     */
    public FancyMessagePart(TextualComponent text) {

        this.text = new SimpleObjectProperty<>(text);
        this.color = new SimpleObjectProperty<>(ChatColor.WHITE);
        this.styles = new SimpleObjectProperty<>(new ArrayList<>());
        this.hoverActionValue = new SimpleObjectProperty<>(null);
        this.translationReplacements = new SimpleObjectProperty<>(new ArrayList<>());
        this.clickAction = new SimpleStringProperty(null);
        this.clickActionValue = new SimpleStringProperty(null);
        this.hoverAction = new SimpleStringProperty(null);
        this.insertion = new SimpleStringProperty(null);
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) {

        try {

            jsonWriter.beginObject();
            text.get().writeJson(jsonWriter);
            jsonWriter.name("color").value(color.get().name().toLowerCase());

            for (final FancyMessageStyle style : styles.get()) {

                jsonWriter.name(style.getType().toLowerCase()).value(true);
            }
            if (clickAction.get() != null && clickActionValue.get() != null) {

                jsonWriter.name("clickEvent")
                        .beginObject()
                        .name("action").value(clickAction.get())
                        .name("value").value(clickActionValue.get())
                        .endObject();
            }
            if (hoverAction.get() != null && hoverAction.get() != null) {

                jsonWriter.name("hoverEvent")
                        .beginObject()
                        .name("action").value(hoverAction.get())
                        .name("value");
                hoverActionValue.get().writeJson(jsonWriter);
                jsonWriter.endObject();
            }
            if (insertion.get() != null) {

                jsonWriter.name("insertion").value(insertion.get());
            }
            if (translationReplacements.get().size() > 0 && text.get() != null && TextualComponent.isTranslatableText(text.get())) {

                jsonWriter.name("with").beginArray();

                for (JsonRepresentedObject obj : translationReplacements.get()) {

                    obj.writeJson(jsonWriter);
                }
                jsonWriter.endArray();
            }
            jsonWriter.endObject();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 获取花式消息部分是否拥有文本
     *
     * @return 是否拥有文本
     */
    boolean hasText() {

        return text.get() != null;
    }
}
