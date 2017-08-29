/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.chat

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.minecraft.moonlake.api.fromNameOrNull
import com.minecraft.moonlake.api.notNull
import java.io.IOException
import java.io.StringReader
import java.lang.StringBuilder
import java.lang.reflect.Type

object ChatSerializer {

    private val GSON: Gson

    init {
        GSON = GsonBuilder()
                .registerTypeHierarchyAdapter(ChatStyle::class.java, ChatStyleSerializer())
                .registerTypeHierarchyAdapter(ChatComponent::class.java, ChatComponentSerializer())
                .create()
    }

    @JvmStatic
    @JvmName("fromJson")
    @Throws(JsonParseException::class)
    fun fromJson(json: String): ChatComponent = try {
        val jsonReader = JsonReader(StringReader(json))
        jsonReader.isLenient = false
        GSON.getAdapter(ChatComponent::class.java).read(jsonReader)
    } catch (e: IOException) {
        throw JsonParseException(e)
    }

    @JvmStatic
    @JvmName("fromJsonLenient")
    @Throws(JsonParseException::class)
    fun fromJsonLenient(json: String): ChatComponent = try {
        val jsonReader = JsonReader(StringReader(json))
        jsonReader.isLenient = true
        GSON.getAdapter(ChatComponent::class.java).read(jsonReader)
    } catch (e: IOException) {
        throw JsonParseException(e)
    }

    @JvmStatic
    @JvmName("toJson")
    fun toJson(chatComponent: ChatComponent): String
            = GSON.toJson(chatComponent)

    @JvmStatic
    @JvmName("toRaw")
    fun toRaw(chatComponent: ChatComponent, color: Boolean = true): String {
        val builder = StringBuilder()
        toRaw0(chatComponent, color, builder)
        return builder.toString()
    }

    @JvmStatic
    @JvmName("toRaw0")
    private fun toRaw0(chatComponent: ChatComponent, color: Boolean = true, builder: StringBuilder) {
        if(color) {
            val chatStyle = chatComponent.getStyle()
            if(chatStyle.color != null)
                appendColor(builder, chatStyle.color.notNull())
            if(chatStyle.bold != null)
                appendColor(builder, ChatColor.BOLD)
            if(chatStyle.italic != null)
                appendColor(builder, ChatColor.ITALIC)
            if(chatStyle.strikethrough != null)
                appendColor(builder, ChatColor.STRIKETHROUGH)
            if(chatStyle.underlined != null)
                appendColor(builder, ChatColor.UNDERLINE)
            if(chatStyle.obfuscated != null)
                appendColor(builder, ChatColor.OBFUSCATED)
        }
        if(chatComponent is ChatComponentText)
            builder.append(chatComponent.getText())
        chatComponent.getExtras().forEach { toRaw0(it, color, builder) }
    }

    @JvmStatic
    @JvmName("appendColor")
    private fun appendColor(builder: StringBuilder, color: ChatColor) {
        builder.append('\u00A7').append(color.code)
    }

    private class ChatStyleSerializer : JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ChatStyle? {
            if(json.isJsonObject) {
                val jsonObject: JsonObject = json.asJsonObject ?: return null
                val chatStyle = ChatStyle()
                if(jsonObject.has("color"))
                    chatStyle.color = fromNameOrNull<ChatColor>(jsonObject.get("color").asString.toUpperCase())
                if(jsonObject.has("bold"))
                    chatStyle.bold = jsonObject.get("bold").asBoolean
                if(jsonObject.has("italic"))
                    chatStyle.italic = jsonObject.get("italic").asBoolean
                if(jsonObject.has("underlined"))
                    chatStyle.underlined = jsonObject.get("underlined").asBoolean
                if(jsonObject.has("strikethrough"))
                    chatStyle.strikethrough = jsonObject.get("strikethrough").asBoolean
                if(jsonObject.has("obfuscated"))
                    chatStyle.obfuscated = jsonObject.get("obfuscated").asBoolean
                if(jsonObject.has("insertion"))
                    chatStyle.insertion = jsonObject.get("insertion").asString
                if(jsonObject.has("clickEvent")) {
                    val jsonObjectClickEvent = jsonObject.getAsJsonObject("clickEvent") ?: null
                    if(jsonObjectClickEvent != null) {
                        val action = fromNameOrNull<ChatClickEvent.Action>(jsonObjectClickEvent.get("action").asString.toUpperCase())
                        val value = jsonObjectClickEvent.get("value").asString ?: null
                        if(action != null && value != null)
                            chatStyle.clickEvent = ChatClickEvent(action, value)
                    }
                }
                if(jsonObject.has("hoverEvent")) {
                    val jsonObjectHoverEvent = jsonObject.getAsJsonObject("hoverEvent") ?: null
                    if(jsonObjectHoverEvent != null) {
                        val action = fromNameOrNull<ChatHoverEvent.Action>(jsonObjectHoverEvent.get("action").asString.toUpperCase())
                        val value = jsonObjectHoverEvent.get("value") ?: null
                        if(action != null && value != null) {
                            if(value.isJsonPrimitive)
                                chatStyle.hoverEvent = ChatHoverEvent(action, ChatComponentText(value.asString))
                            else
                                chatStyle.hoverEvent = ChatHoverEvent(action, context.deserialize(value, ChatComponent::class.java))
                        }
                    }
                }
                return chatStyle
            }
            return null
        }

        override fun serialize(src: ChatStyle, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
            if(src.isEmpty())
                return null
            val jsonObject = JsonObject()
            if(src.color != null)
                jsonObject.addProperty("color", src.color.toString().toLowerCase())
            if(src.bold != null)
                jsonObject.addProperty("bold", src.bold)
            if(src.italic != null)
                jsonObject.addProperty("italic", src.italic)
            if(src.underlined != null)
                jsonObject.addProperty("underlined", src.underlined)
            if(src.strikethrough != null)
                jsonObject.addProperty("strikethrough", src.strikethrough)
            if(src.obfuscated != null)
                jsonObject.addProperty("obfuscated", src.obfuscated)
            if(src.insertion != null)
                jsonObject.add("insertion", context.serialize(src.insertion))
            if(src.clickEvent != null) {
                val jsonObjectClickEvent = JsonObject()
                jsonObjectClickEvent.addProperty("action", src.clickEvent?.action.toString().toLowerCase())
                jsonObjectClickEvent.addProperty("value", src.clickEvent?.value)
                jsonObject.add("clickEvent", jsonObjectClickEvent)
            }
            if(src.hoverEvent != null) {
                val jsonObjectHoverEvent = JsonObject()
                jsonObjectHoverEvent.addProperty("action", src.hoverEvent?.action.toString().toLowerCase())
                if(src.hoverEvent?.value is ChatComponentText)
                    jsonObjectHoverEvent.addProperty("value", (src.hoverEvent?.value as ChatComponentText).getText())
                else
                    jsonObjectHoverEvent.add("value", context.serialize(src.hoverEvent?.value))
                jsonObject.add("hoverEvent", jsonObjectHoverEvent)
            }
            return jsonObject
        }
    }

    private class ChatComponentSerializer : JsonDeserializer<ChatComponent>, JsonSerializer<ChatComponent> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ChatComponent? {
            if(json.isJsonPrimitive)
                return ChatComponentText(json.asString)
            if(!json.isJsonObject && json.isJsonArray) {
                var component: ChatComponent? = null
                val jsonArray = json.asJsonArray
                jsonArray.forEach {
                    val component1 = deserialize(it, it.javaClass, context)
                    if(component == null)
                        component = component1
                    if(component1 != null)
                        component?.append(component1)
                }
                return component
            }
            val component: ChatComponent?
            val jsonObject = json.asJsonObject
            if(jsonObject.has("text")) {
                component = ChatComponentText(jsonObject.get("text").asString)
            } else if(jsonObject.has("translate")) {
                val translate = jsonObject.get("translate").asString
                if(jsonObject.has("with")) {
                    val jsonArray = jsonObject.getAsJsonArray("with")
                    val withs = arrayOfNulls<Any>(jsonArray.size())
                    (0 until withs.size).forEach {
                        withs[it] = deserialize(jsonArray[it], typeOfT, context)
                        if(withs[it] is ChatComponentText) {
                            val componentText = withs[it] as ChatComponentText
                            if(componentText.getStyle().isEmpty() && componentText.getExtras().isEmpty())
                                withs[it] = componentText.getText()
                        }
                    }
                    component = ChatComponentTranslation(translate).addWiths(withs)
                } else {
                    component = ChatComponentTranslation(translate)
                }
            } else if(jsonObject.has("score")) {
                val jsonObjectScore = jsonObject.getAsJsonObject("score")
                if(!jsonObjectScore.has("name") || !jsonObjectScore.has("objective"))
                    throw JsonParseException("分数聊天组件至少需要一个 name 或 objective 属性.")
                component = ChatComponentScore(jsonObjectScore.get("name").asString, jsonObjectScore.get("objective").asString)
                if(jsonObjectScore.has("value"))
                    component.setValue(jsonObjectScore.get("value").asString)
            } else if(jsonObject.has("selector")) {
                component = ChatComponentSelector(jsonObject.get("selector").asString)
            } else if(jsonObject.has("keybind")) {
                component = ChatComponentKeybind(jsonObject.get("keybind").asString)
            } else {
                throw JsonParseException("不知道如何把 $json 解析为聊天组件.")
            }
            if(jsonObject.has("extra")) {
                val jsonArray = jsonObject.getAsJsonArray("extra")
                if(jsonArray.size() <= 0)
                    throw JsonParseException("意外的空数组组件.")
                (0 until jsonArray.size()).forEach {
                    val component1 = deserialize(jsonArray[it], typeOfT, context)
                    if(component1 != null)
                        component.append(component1)
                }
            }
            component.setStyle(context.deserialize(json, ChatStyle::class.java))
            return component
        }

        override fun serialize(src: ChatComponent, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
            val jsonObject = JsonObject()
            if(!src.getStyle().isEmpty()) {
                val jsonElement = context.serialize(src.getStyle())
                if(jsonElement.isJsonObject) {
                    val jsonObjectStyle = jsonElement.asJsonObject
                    jsonObjectStyle.entrySet().forEach { jsonObject.add(it.key, it.value) }
                }
            }
            if(!src.getExtras().isEmpty()) {
                val jsonArray = JsonArray()
                src.getExtras().forEach { jsonArray.add(serialize(it, it.javaClass, context)) }
                jsonObject.add("extra", jsonArray)
            }
            if(src is ChatComponentText) {
                jsonObject.addProperty("text", src.getText())
            } else if(src is ChatComponentTranslation) {
                jsonObject.addProperty("translate", src.getKey())
                if(!src.getWiths().isEmpty()) {
                    val jsonArray = JsonArray()
                    src.getWiths().forEach {
                        if(it is ChatComponent)
                            jsonArray.add(serialize(it, it.javaClass, context))
                        else
                            jsonArray.add(JsonPrimitive(it.toString()))
                    }
                    jsonObject.add("with", jsonArray)
                }
            } else if(src is ChatComponentScore) {
                val jsonObjectScore = JsonObject()
                jsonObjectScore.addProperty("name", src.getName())
                jsonObjectScore.addProperty("objective", src.getObjective())
                if(src.getValue() != null) jsonObjectScore.addProperty("value", src.getValue())
                jsonObject.add("score", jsonObjectScore)
            } else if(src is ChatComponentSelector) {
                jsonObject.addProperty("selector", src.getSelector())
            } else if(src is ChatComponentKeybind) {
                jsonObject.addProperty("keybind", src.getKeybind())
            } else {
                throw JsonParseException("不知道如何序列化 $src 聊天组件.")
            }
            return jsonObject
        }
    }
}
