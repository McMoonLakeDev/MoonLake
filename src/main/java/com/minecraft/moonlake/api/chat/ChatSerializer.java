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


package com.minecraft.moonlake.api.chat;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.minecraft.moonlake.api.chat.adapter.ChatAdapter;
import com.minecraft.moonlake.api.chat.adapter.ChatJsonAdapter;
import com.minecraft.moonlake.api.chat.adapter.ChatObjectAdapter;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.reflect.ExactReflect;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <h1>ChatSerializer</h1>
 * 聊天组件序列化类
 *
 * @version 1.0.1
 * @author Month_Light
 */
public final class ChatSerializer {

    private final static Gson GSON;
    private static volatile MethodAccessor iChatBaseComponentToJsonMethod;
    private static volatile MethodAccessor iChatBaseComponentFormJsonMethod;

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
     * 从指定 Json 格式的聊天内容获取聊天适配器
     *
     * @param json Json
     * @return ChatAdapter
     */
    public static ChatAdapter jsonAdapter(String json) {
        Validate.notNull(json, "The json object is null.");
        return new ChatJsonAdapter(json);
    }

    /**
     * 从指定 {@code ChatComponent | IChatBaseComponent} 对象获取聊天适配器
     *
     * @param obj {@code ChatComponent | IChatBaseComponent}
     * @return ChatAdapter
     * @throws IllegalArgumentException 如果对象不为 {@code ChatComponent | IChatBaseComponent} 的实例则抛出异常
     */
    public static ChatAdapter objectAdapter(Object obj) {
        if(obj instanceof ChatComponent)
            return new ChatObjectAdapter((ChatComponent) obj);
        else if(MinecraftReflection.isICBC(obj))
            return new ChatObjectAdapter(obj);
        throw new IllegalArgumentException("The object adapter only allow ChatComponent or IChatBaseComponent instance.");
    }

    /**
     * 将指定 Json 格式的聊天内容转换为 IChatBaseComponent 对象
     *
     * @param json Json
     * @return IChatBaseComponent
     */
    public static Object iCBCFromJson(String json) {
        Validate.notNull(json, "The json object is null.");
        if(iChatBaseComponentFormJsonMethod == null) {
            Class<?> chatSerializerClass = MinecraftReflection.getChatSerializerClass();
            Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
            try {
                Method temp = null;
                try {
                    // 先直接获取 ChatSerializer 类的 a 函数, 这个函数也就是将 json 转换为 ICBC 对象
                    temp = ExactReflect.fromClass(chatSerializerClass, true).getMethod("a", String.class);
                } catch (Exception e) {
                }
                if(temp == null) {
                    // 判断一下如果上面的获取异常, 那么在使用这个方式
                    // 这个方式就是遍历 ChatSerializer 类的所有公开函数
                    // 然后对比返回值和函数的需要参数, 符合则获取对应函数对象
                    temp = FuzzyReflect.fromClass(chatSerializerClass, true).getMethodByParameters("a", iChatBaseComponentClass, new Class[] { String.class });
                }
                iChatBaseComponentFormJsonMethod = Accessors.getMethodAccessor(temp);
            }
            catch (Exception e) {
                throw new NMSException("The chat serializer icbc from json method initialize exception.", e);
            }
        }
        return iChatBaseComponentFormJsonMethod.invoke(null, json);
    }

    /**
     * 将鬼知道 IChatBaseComponent 对象转换为 Json 格式的聊天内容
     *
     * @param icbc IChatBaseComponent
     * @return Json
     */
    public static String iCBCToJson(Object icbc) {
        Validate.notNull(icbc, "The icbc object is null.");
        if(iChatBaseComponentToJsonMethod == null) {
            Class<?> chatSerializerClass = MinecraftReflection.getChatSerializerClass();
            Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
            try {
                Method temp = null;
                try {
                    // 先直接获取 ChatSerializer 类的 a 函数, 这个函数也就是将 ICBC 转换为 json 对象
                    temp = ExactReflect.fromClass(chatSerializerClass, true).getMethod("a", iChatBaseComponentClass);
                } catch (Exception e) {
                }
                if(temp == null) {
                    // 判断一下如果上面的获取异常, 那么在使用这个方式
                    // 这个方式就是遍历 ChatSerializer 类的所有公开函数
                    // 然后对比返回值和函数的需要参数, 符合则获取对应函数对象
                    temp = FuzzyReflect.fromClass(chatSerializerClass, true).getMethodByParameters("a", String.class, new Class[] { iChatBaseComponentClass });
                }
                iChatBaseComponentToJsonMethod = Accessors.getMethodAccessor(temp);
            }
            catch (Exception e) {
                throw new NMSException("The chat serializer icbc to json method initialize exception.", e);
            }
        }
        return (String) iChatBaseComponentToJsonMethod.invoke(null, icbc);
    }

    /**
     * 将指定聊天内容组件转换为 IChatBaseComponent 对象
     *
     * @param chatComponent 聊天内容组件
     * @return IChatBaseComponent
     */
    public static Object iCBCFromChatComponent(ChatComponent chatComponent) {
        Validate.notNull(chatComponent, "The chat component object is null.");
        return iCBCFromJson(chatComponent.toJson());
    }

    /**
     * 将指定 IChatBaseComponent 对象转换为聊天内容组件
     *
     * @param icbc IChatBaseComponent
     * @return ChatComponent
     */
    public static ChatComponent chatComponentFromICBC(Object icbc) {
        String json = iCBCToJson(icbc);
        return fromJson(json);
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
        if(color) {
            ChatStyle style = chatComponent.getStyle();
            if(style.color != null)
                appendColor(builder, style.color);
            if(style.bold != null && style.bold)
                appendColor(builder, ChatColor.BOLD);
            if(style.italic != null && style.italic)
                appendColor(builder, ChatColor.ITALIC);
            if(style.strikethrough != null && style.strikethrough)
                appendColor(builder, ChatColor.STRIKETHROUGH);
            if(style.underlined != null && style.underlined)
                appendColor(builder, ChatColor.UNDERLINE);
            if(style.obfuscated != null && style.obfuscated)
                appendColor(builder, ChatColor.OBFUSCATED);
        }
        if(chatComponent instanceof ChatComponentText)
            builder.append(((ChatComponentText) chatComponent).getText());
        for(ChatComponent extra : chatComponent.getExtras())
            toRaw0(extra, color, builder);
    }

    private static void appendColor(StringBuilder builder, ChatColor color) {
        builder.append('\u00A7').append(color.getCode());
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
                        JsonElement value = jsonObjectHoverEvent.get("value");
                        if(value.isJsonPrimitive())
                            style.hoverEvent = new ChatHoverEvent(action, new ChatComponentText(value.getAsString()));
                        else
                            style.hoverEvent = new ChatHoverEvent(action, context.deserialize(value, ChatComponent.class));
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
                if(src.hoverEvent.getValue() instanceof ChatComponentText)
                    jsonObjectHoverEvent.addProperty("value", ((ChatComponentText) src.hoverEvent.getValue()).getText());
                else
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
                if(componentScore.getValue() != null) jsonObjectScore.addProperty("value", componentScore.getValue());
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
