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

import com.minecraft.moonlake.MoonLakePluginDebug;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.json.JsonWrite;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>FancyMessagePart</h1>
 * 花式消息部分实现类
 *
 * @version 1.1
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
class FancyMessagePart implements JsonRepresentedObject, Cloneable {

    ChatColor color;
    TextualComponent text;
    List<FancyMessageStyle> styles;
    JsonRepresentedObject hoverActionValue;
    List<JsonRepresentedObject> translationReplacements;
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

        this.text = text;
        this.color = ChatColor.WHITE;
        this.styles = new ArrayList<>();
        this.hoverActionValue = null;
        this.translationReplacements = new ArrayList<>();
        this.clickAction = new SimpleStringProperty(null);
        this.clickActionValue = new SimpleStringProperty(null);
        this.hoverAction = new SimpleStringProperty(null);
        this.insertion = new SimpleStringProperty(null);
    }

    @Override
    public void writeJson(JsonWrite jsonWrite) {

        try {

            jsonWrite.beginObject();
            text.writeJson(jsonWrite);
            jsonWrite.name("color").value(color.name().toLowerCase());

            for (final FancyMessageStyle style : styles) {

                jsonWrite.name(style.getType().toLowerCase()).value(true);
            }
            if (clickAction.get() != null && clickActionValue.get() != null) {

                jsonWrite.name("clickEvent")
                        .beginObject()
                        .name("action").value(clickAction.get())
                        .name("value").value(clickActionValue.get())
                        .endObject();
            }
            if (hoverAction.get() != null && hoverAction.get() != null) {

                jsonWrite.name("hoverEvent")
                        .beginObject()
                        .name("action").value(hoverAction.get())
                        .name("value");
                hoverActionValue.writeJson(jsonWrite);
                jsonWrite.endObject();
            }
            if (insertion.get() != null) {

                jsonWrite.name("insertion").value(insertion.get());
            }
            if (translationReplacements.size() > 0 && text != null && TextualComponent.isTranslatableText(text)) {

                jsonWrite.name("with").beginArray();

                for (JsonRepresentedObject obj : translationReplacements) {

                    obj.writeJson(jsonWrite);
                }
                jsonWrite.endArray();
            }
            jsonWrite.endObject();
        }
        catch (Exception e) {

            MoonLakePluginDebug.debug(e);
        }
    }

    /**
     * 获取花式消息部分是否拥有文本
     *
     * @return 是否拥有文本
     */
    boolean hasText() {

        return text != null;
    }
}
