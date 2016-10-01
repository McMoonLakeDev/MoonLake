package com.minecraft.moonlake.api.fancy;

import com.google.gson.stream.JsonWriter;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.property.*;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/9/16.
 */
class FancyMessagePart implements JsonRepresentedObject, Cloneable {

    ObjectProperty<ChatColor> color;
    ObjectProperty<TextualComponent> text;
    ObjectProperty<List<FancyMessageStyle>> styles;
    ObjectProperty<JsonRepresentedObject> hoverActionValue;
    ObjectProperty<List<JsonRepresentedObject>> translationReplacements;
    StringProperty clickAction, clickActionValue, hoverAction, insertion;

    public FancyMessagePart() {

        this(null);
    }

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
            if (translationReplacements.get().size() > 0 && text.get() != null && TextualComponent.isTranslatableText(text.get()).get()) {

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

    boolean hasText() {

        return text.get() != null;
    }
}
