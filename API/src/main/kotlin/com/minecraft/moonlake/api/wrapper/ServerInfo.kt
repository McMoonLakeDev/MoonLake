/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.wrapper

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatComponentText
import com.minecraft.moonlake.api.chat.ChatSerializer
import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import io.netty.handler.codec.base64.Base64
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

data class ServerInfo(val version: Version, val players: Players, val description: ChatComponent, val modInfo: ModInfo?, val favicon: BufferedImage?) {

    fun toJson(): String
            = Companion.toJson(this)

    data class Version(val name: String, val protocol: Int)
    data class PlayerSample(val name: String, val id: UUID)
    data class Players(val max: Int, val online: Int, val sample: Array<PlayerSample>) {
        override fun hashCode(): Int {
            var result = max.hashCode()
            result = 31 * result + online.hashCode()
            result = 31 * result + Arrays.hashCode(sample)
            return result
        }
        override fun equals(other: Any?): Boolean {
            if(other === this)
                return true
            if(other is Players)
                return max == other.max && online == other.online && Arrays.equals(sample, other.sample)
            return false
        }
    }
    data class ModSample(val modid: String, val version: String)
    data class ModInfo(val type: String, val modList: Array<ModSample>) {
        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + Arrays.hashCode(modList)
            return result
        }
        override fun equals(other: Any?): Boolean {
            if(other === this)
                return true
            if(other is ModInfo)
                return type == other.type && Arrays.equals(modList, other.modList)
            return false
        }

        companion object {

            @JvmField
            val SAMPLE = ModInfo("FML", arrayOf(
                    // Forge Mod List
                    ModSample("mcp", "9.19"),
                    ModSample("FML", "8.0.99.99"),
                    ModSample("Forge", "11.15.1.1722"),
                    // Other Mod List
                    ModSample("rpcraft", "2.0")
            ))
        }
    }

    companion object {

        @JvmField
        val SAMPLE = ServerInfo(Version("1.8.9", 47), Players(20, 0, arrayOf()), ChatComponentText("A Minecraft Server"), null, null)

        @JvmStatic
        @JvmName("toJson")
        fun toJson(serverInfo: ServerInfo): String {
            val jsonObject = JsonObject()
            val jsonVersion = JsonObject()
            val jsonPlayers = JsonObject()
            jsonVersion.addProperty("name", serverInfo.version.name)
            jsonVersion.addProperty("protocol", serverInfo.version.protocol)
            jsonPlayers.addProperty("max", serverInfo.players.max)
            jsonPlayers.addProperty("online", serverInfo.players.online)
            if(serverInfo.players.sample.isNotEmpty()) {
                val jsonSamples = JsonArray()
                serverInfo.players.sample.forEach {
                    val jsonSample = JsonObject()
                    jsonSample.addProperty("id", it.id.toString())
                    jsonSample.addProperty("name", it.name)
                    jsonSamples.add(jsonSample)
                }
                jsonPlayers.add("sample", jsonSamples)
            }
            jsonObject.add("version", jsonVersion)
            jsonObject.add("players", jsonPlayers)
            jsonObject.add("description", JsonParser().parse(serverInfo.description.toJson()))
            if(serverInfo.modInfo != null) {
                val jsonModInfo = JsonObject()
                val jsonModList = JsonArray()
                serverInfo.modInfo.modList.forEach {
                    val jsonMod = JsonObject()
                    jsonMod.addProperty("modid", it.modid)
                    jsonMod.addProperty("version", it.version)
                    jsonModList.add(jsonMod)
                }
                jsonModInfo.addProperty("type", serverInfo.modInfo.type)
                jsonModInfo.add("modList", jsonModList)
                jsonObject.add("modinfo", jsonModInfo)
            }
            if(serverInfo.favicon != null)
                jsonObject.addProperty("favicon", faviconToString(serverInfo.favicon))
            return jsonObject.toString()
        }

        @JvmStatic
        @JvmName("fromJson")
        fun fromJson(serverInfo: String): ServerInfo {
            val jsonObject = Gson().fromJson<JsonObject>(serverInfo, JsonObject::class.java)
            val jsonVersion = jsonObject["version"].asJsonObject
            val jsonPlayers = jsonObject["players"].asJsonObject
            val version = Version(jsonVersion["name"].asString, jsonVersion["protocol"].asInt)
            val sample = ArrayList<PlayerSample>()
            if(jsonPlayers.has("sample")) {
                val jsonSamples = jsonPlayers["sample"].asJsonArray
                jsonSamples.forEach {
                    val jsonSample = it.asJsonObject
                    sample.add(PlayerSample(jsonSample["name"].asString, UUID.fromString(jsonSample["id"].asString)))
                }
            }
            val players = Players(jsonPlayers["max"].asInt, jsonPlayers["online"].asInt, sample.toTypedArray())
            val description = ChatSerializer.fromJsonLenient(jsonObject["description"].toString())
            var modInfo: ModInfo? = null
            var favicon: BufferedImage? = null
            if(jsonObject.has("modinfo")) {
                val jsonModInfo = jsonObject["modinfo"].asJsonObject
                val modList = ArrayList<ModSample>()
                if(jsonModInfo.has("modList")) {
                    val jsonModList = jsonModInfo["modList"].asJsonArray
                    jsonModList.forEach {
                        val jsonMod = it.asJsonObject
                        modList.add(ModSample(jsonMod["modid"].asString, jsonMod["version"].asString))
                    }
                }
                modInfo = ModInfo(jsonModInfo["type"]?.asString ?: "FML", modList.toTypedArray())
            }
            if(jsonObject.has("favicon"))
                favicon = faviconFromString(jsonObject["favicon"].asString)
            return ServerInfo(version, players, description, modInfo, favicon)
        }

        @JvmStatic
        @JvmName("faviconToString")
        @Throws(IllegalArgumentException::class, IOException::class)
        fun faviconToString(favicon: BufferedImage): String {
            if(favicon.width != 64 || favicon.height != 64)
                throw IllegalArgumentException("图标图片不是 64x64 像素大小.")
            val buffer = Unpooled.buffer()
            try {
                ImageIO.write(favicon, "PNG", ByteBufOutputStream(buffer))
                val encoded = Base64.encode(buffer)
                return "data:image/png;base64,${encoded.toString(Charsets.UTF_8)}"
            } finally {
                buffer.release()
            }
        }

        @JvmStatic
        @JvmName("faviconFromString")
        @Throws(IllegalArgumentException::class, IOException::class)
        fun faviconFromString(faviconData: String): BufferedImage {
            val data = if(faviconData.startsWith("data:image/png;base64,", true)) faviconData.substring("data:image/png;base64,".length) else faviconData
            val buffer = Unpooled.wrappedBuffer(data.toByteArray(Charsets.UTF_8))
            try {
                val favicon = ImageIO.read(ByteBufInputStream(Base64.decode(buffer))) ?: null
                if(favicon == null || (favicon.width != 64 || favicon.height != 64))
                    throw IllegalArgumentException("图标图片不是 64x64 像素大小.")
                return favicon
            } finally {
                buffer.release()
            }
        }
    }
}
