/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.chat;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.minecraft.moonlake.validate.Validate;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <h1>ChatSerializer</h1>
 * 聊天组件序列化类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class ChatSerializer {

    private final static Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeHierarchyAdapter(ChatComponent.class, new ChatComponentSerializer())
                .registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyleSerializer())
                .create();
    }

    /**
     * 聊天组件序列化类构造函数
     */
    private ChatSerializer() {
    }

    /**
     * 将指定聊天组件转换为源文本内容
     *
     * @param chatComponent 聊天组件
     * @return 源文本内容
     */
    public static String toRaw(ChatComponent chatComponent) {
        return toRaw(chatComponent, true);
    }

    /**
     * 将指定聊天组件转换为源文本内容
     *
     * @param chatComponent 聊天组件
     * @param color 是否附加颜色
     * @return 源文本内容
     */
    public static String toRaw(ChatComponent chatComponent, boolean color) {
        StringBuilder builder = new StringBuilder();
        toRaw0(chatComponent, color, builder);
        return builder.toString();
    }

    private static void toRaw0(ChatComponent chatComponent, boolean color, StringBuilder builder) {
        if(chatComponent instanceof ChatComponentText) {
            if(color && chatComponent.getStyle().getColor() != null)
                builder.append('\u00A7').append(chatComponent.getStyle().getColor().getCode());
            builder.append(((ChatComponentText) chatComponent).getText());
        }
        for(ChatComponent extra : chatComponent.getExtras())
            toRaw0(extra, color, builder);
    }

    /**
     * 将指定聊天组件转换为 Json 格式内容
     *
     * @param chatComponent 聊天组件
     * @return Json
     */
    public static String toJson(ChatComponent chatComponent) {
        Validate.notNull(chatComponent, "The chat component object is null.");
        return GSON.toJson(chatComponent);
    }

    /**
     * 将指定 Json 格式的聊天内容转换为聊天组件
     *
     * @param json Json
     * @return ChatComponent
     * @throws JsonParseException 如果解析 Json 时错误则抛出异常
     */
    public static ChatComponent fromJson(String json) {
        Validate.notNull(json, "The json object is null.");
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(json));
            jsonReader.setLenient(false);
            return GSON.getAdapter(ChatComponent.class).read(jsonReader);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * 将指定 Json 格式 (宽容模式) 的聊天内容转换为聊天组件
     *
     * @param json Json
     * @return ChatComponent
     * @throws JsonParseException 如果解析 Json 时错误则抛出异常
     */
    public static ChatComponent fromJsonLenient(String json) {
        Validate.notNull(json, "The json object is null.");
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(json));
            jsonReader.setLenient(true);
            return GSON.getAdapter(ChatComponent.class).read(jsonReader);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    private final static class ChatStyleSerializer implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle> {
        @Override
        public ChatStyle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if(json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                if(jsonObject == null)
                    return null;
                ChatStyle style = new ChatStyle();
                if(jsonObject.has("color"))
                    style.color = ChatColor.fromName(jsonObject.get("color").getAsString());
                if(jsonObject.has("bold"))
                    style.bold = jsonObject.get("bold").getAsBoolean();
                if(jsonObject.has("italic"))
                    style.italic = jsonObject.get("italic").getAsBoolean();
                if(jsonObject.has("underlined"))
                    style.underlined = jsonObject.get("underlined").getAsBoolean();
                if(jsonObject.has("strikethrough"))
                    style.strikethrough = jsonObject.get("strikethrough").getAsBoolean();
                if(jsonObject.has("obfuscated"))
                    style.obfuscated = jsonObject.get("obfuscated").getAsBoolean();
                if(jsonObject.has("insertion"))
                    style.insertion = jsonObject.get("insertion").getAsString();
                if(jsonObject.has("clickEvent")) {
                    JsonObject jsonObjectClickEvent = jsonObject.getAsJsonObject("clickEvent");
                    if(jsonObjectClickEvent != null) {
                        ChatClickEvent.Action action = ChatClickEvent.Action.fromName(jsonObjectClickEvent.get("action").getAsString());
                        String value = jsonObjectClickEvent.get("value").getAsString();
                        style.clickEvent = new ChatClickEvent(action, value);
                    }
                }
                if(jsonObject.has("hoverEvent")) {
                    JsonObject jsonObjectHoverEvent = jsonObject.getAsJsonObject("hoverEvent");
                    if(jsonObjectHoverEvent != null) {
                        ChatHoverEvent.Action action = ChatHoverEvent.Action.fromName(jsonObjectHoverEvent.get("action").getAsString());
                        ChatComponent value = context.deserialize(jsonObjectHoverEvent.get("value"), ChatComponent.class);
                        style.hoverEvent = new ChatHoverEvent(action, value);
                    }
                }
                return style;
            }
            return null;
        }

        @Override
        public JsonElement serialize(ChatStyle src, Type typeOfSrc, JsonSerializationContext context) {
            if(src.isEmpty())
                return null;
            JsonObject jsonObject = new JsonObject();
            if(src.color != null)
                jsonObject.addProperty("color", src.color.toString().toLowerCase());
            if(src.bold != null)
                jsonObject.addProperty("bold", src.bold);
            if(src.italic != null)
                jsonObject.addProperty("italic", src.italic);
            if(src.underlined != null)
                jsonObject.addProperty("underlined", src.underlined);
            if(src.strikethrough != null)
                jsonObject.addProperty("strikethrough", src.strikethrough);
            if(src.obfuscated != null)
                jsonObject.addProperty("bold", src.obfuscated);
            if(src.insertion != null)
                jsonObject.add("insertion", context.serialize(src.insertion));
            if(src.clickEvent != null) {
                JsonObject jsonObjectClickEvent = new JsonObject();
                jsonObjectClickEvent.addProperty("action", src.clickEvent.getAction().toString().toLowerCase());
                jsonObjectClickEvent.addProperty("value", src.clickEvent.getValue());
                jsonObject.add("clickEvent", jsonObjectClickEvent);
            }
            if(src.hoverEvent != null) {
                JsonObject jsonObjectHoverEvent = new JsonObject();
                jsonObjectHoverEvent.addProperty("action", src.hoverEvent.getAction().toString().toLowerCase());
                jsonObjectHoverEvent.add("value", context.serialize(src.hoverEvent.getValue()));
                jsonObject.add("hoverEvent", jsonObjectHoverEvent);
            }
            return jsonObject;
        }
    }

    private final static class ChatComponentSerializer implements JsonDeserializer<ChatComponent>, JsonSerializer<ChatComponent> {
        @Override
        public ChatComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if(json.isJsonPrimitive())
                return new ChatComponentText(json.getAsString());
            if(!json.isJsonObject() && json.isJsonArray()) {
                ChatComponent component = null;
                JsonArray array = json.getAsJsonArray();
                for(JsonElement element : array) {
                    ChatComponent component1 = deserialize(element, element.getClass(), context);
                    if(component == null) component = component1;
                    else component.append(component1);
                }
                return component;
            }
            ChatComponent component = null;
            JsonObject jsonObject = json.getAsJsonObject();
            if(jsonObject.has("text")) {
                component = new ChatComponentText(jsonObject.get("text").getAsString());
            } else if(jsonObject.has("translate")) {
                String translate = jsonObject.get("translate").getAsString();
                if(jsonObject.has("with")) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("with");
                    Object[] withs = new Object[jsonArray.size()];
                    for(int i = 0; i < withs.length; i++) {
                        withs[i] = deserialize(jsonArray.get(i), typeOfT, context);
                        if(withs[i] instanceof ChatComponentText) {
                            ChatComponentText componentText = (ChatComponentText) withs[i];
                            if(componentText.getStyle().isEmpty() && componentText.getExtras().isEmpty())
                                withs[i] = componentText.getText();
                        }
                    }
                    component = new ChatComponentTranslation(translate).addWiths(withs);
                } else {
                    component = new ChatComponentTranslation(translate);
                }
            } else if(jsonObject.has("score")) {
                JsonObject jsonObjectScore = jsonObject.getAsJsonObject("score");
                if(!jsonObjectScore.has("name") || !jsonObjectScore.has("objective"))
                    throw new JsonParseException("A score component needs a least a name and an objective");
                component = new ChatComponentScore(jsonObjectScore.get("name").getAsString(), jsonObjectScore.get("objective").getAsString());
                if(jsonObjectScore.has("value"))
                    ((ChatComponentScore) component).setValue(jsonObjectScore.get("value").getAsString());
            } else if(jsonObject.has("selector")) {
                component = new ChatComponentSelector(jsonObject.get("selector").getAsString());
            } else if(jsonObject.has("keybind")) {
                component = new ChatComponentKeybind(jsonObject.get("keybind").getAsString());
            } else {
                throw new JsonParseException("Don't know how to turn " + json + " into a Component.");
            }

            if(jsonObject.has("extra")) {
                JsonArray jsonArray = jsonObject.getAsJsonArray("extra");
                if(jsonArray.size() <= 0)
                    throw new JsonParseException("Unexpected empty array of components");
                for(int i = 0; i < jsonArray.size(); ++i)
                    component.append(deserialize(jsonArray.get(i), typeOfT, context));
            }
            component.setStyle(context.deserialize(json, ChatStyle.class));
            return component;
        }

        @Override
        public JsonElement serialize(ChatComponent src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            if(!src.getStyle().isEmpty()) {
                JsonElement jsonElement = context.serialize(src.getStyle());
                if(jsonElement.isJsonObject()) {
                    JsonObject jsonObjectStyle = jsonElement.getAsJsonObject();
                    for(Map.Entry<String, JsonElement> entry : jsonObjectStyle.entrySet())
                        jsonObject.add(entry.getKey(), entry.getValue());
                }
            }
            if(!src.getExtras().isEmpty()) {
                JsonArray jsonArray = new JsonArray();
                for(ChatComponent component : src.getExtras())
                    jsonArray.add(serialize(component, component.getClass(), context));
                jsonObject.add("extra", jsonArray);
            }
            if(src instanceof ChatComponentText) {
                ChatComponentText componentText = (ChatComponentText) src;
                jsonObject.addProperty("text", componentText.getText());
            } else if(src instanceof ChatComponentTranslation) {
                ChatComponentTranslation componentTranslation = (ChatComponentTranslation) src;
                jsonObject.addProperty("translate", componentTranslation.getKey());
                if(!componentTranslation.getWiths().isEmpty()) {
                    JsonArray jsonArray = new JsonArray();
                    for(Object object : componentTranslation.getWiths()) {
                        if(object instanceof ChatComponent) jsonArray.add(serialize((ChatComponent) object, object.getClass(), context));
                        else jsonArray.add(new JsonPrimitive(String.valueOf(object)));
                    }
                    jsonObject.add("with", jsonArray);
                }
            } else if(src instanceof ChatComponentScore) {
                JsonObject jsonObjectScore = new JsonObject();
                ChatComponentScore componentScore = (ChatComponentScore) src;
                jsonObjectScore.addProperty("name", componentScore.getName());
                jsonObjectScore.addProperty("objective", componentScore.getObjective());
                jsonObjectScore.addProperty("value", componentScore.getValue());
                jsonObject.add("score", jsonObjectScore);
            } else if(src instanceof ChatComponentSelector) {
                ChatComponentSelector componentSelector = (ChatComponentSelector) src;
                jsonObject.addProperty("selector", componentSelector.getSelector());
            } else if(src instanceof ChatComponentKeybind) {
                ChatComponentKeybind componentKeybind = (ChatComponentKeybind) src;
                jsonObject.addProperty("keybind", componentKeybind.getKeybind());
            } else {
                throw new JsonParseException("Don't know how to serialize " + src + " as a Component");
            }
            return jsonObject;
        }
    }
}
