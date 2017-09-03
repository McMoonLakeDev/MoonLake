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

package com.minecraft.moonlake.api.utility

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatSerializer
import com.minecraft.moonlake.api.converter.ConverterEquivalent
import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.toMoonLakePlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.StringReader

object MinecraftConverters {

    /** chat component */

    @JvmStatic
    private val chatSerializerGson: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getChatSerializerClass(), Gson::class.java, true) }

    @JvmStatic
    @JvmName("getChatComponent")
    fun getChatComponent(): ConverterEquivalent<ChatComponent> {
        return object: ConverterEquivalent<ChatComponent> {
            override fun getGeneric(specific: ChatComponent): Any {
                val gson = chatSerializerGson.get(null) as Gson
                val adapter = gson.getAdapter(MinecraftReflection.getIChatBaseComponentClass())
                return adapter.read(JsonReader(StringReader(specific.toJson())))
            }
            override fun getSpecific(generic: Any): ChatComponent {
                val gson = chatSerializerGson.get(null) as Gson
                val json = gson.toJson(generic)
                return ChatSerializer.fromJson(json)
            }
            override fun getSpecificType(): Class<ChatComponent>
                    = ChatComponent::class.java
        }
    }

    /** entity */

    @JvmStatic
    private val craftEntityGetHandle: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getCraftEntityClass(), "getHandle") }
    @JvmStatic
    private val entityGetBukkitEntity: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getEntityClass(), "getBukkitEntity") }

    @JvmStatic
    @JvmName("getEntity")
    @Suppress("UNCHECKED_CAST")
    fun <T: Entity> getEntity(clazz: Class<T>): ConverterEquivalent<T> {
        return object: ConverterEquivalent<T> {
            override fun getGeneric(specific: T): Any
                    = craftEntityGetHandle.invoke(specific)
            override fun getSpecific(generic: Any): T
                    = entityGetBukkitEntity.invoke(generic) as T
            override fun getSpecificType(): Class<T>
                    = clazz
        }
    }

    @JvmStatic
    @JvmName("getEntityPlayer")
    fun getEntityPlayer(): ConverterEquivalent<MoonLakePlayer> {
        return object: ConverterEquivalent<MoonLakePlayer> {
            private val playerConverter: ConverterEquivalent<Player> by lazy { getEntity(Player::class.java) }

            override fun getGeneric(specific: MoonLakePlayer): Any
                    = playerConverter.getGeneric(specific.getBukkitPlayer())
            override fun getSpecific(generic: Any): MoonLakePlayer
                    = playerConverter.getSpecific(generic).toMoonLakePlayer()
            override fun getSpecificType(): Class<MoonLakePlayer>
                    = MoonLakePlayer::class.java
        }
    }

    /** item stack */

    @JvmStatic
    private val craftItemStackAsNMSCopy: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getCraftBukkitClass("inventory.CraftItemStack"), "asNMSCopy", false, ItemStack::class.java) }

    @JvmStatic
    private val craftItemStackAsBukkitCopy: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getCraftBukkitClass("inventory.CraftItemStack"), "asBukkitCopy", false, MinecraftReflection.getItemStackClass()) }

    @JvmStatic
    @JvmName("getItemStack")
    fun getItemStack(): ConverterEquivalent<ItemStack> {
        return object: ConverterEquivalent<ItemStack> {
            override fun getGeneric(specific: ItemStack): Any
                    = craftItemStackAsNMSCopy.invoke(null, specific)
            override fun getSpecific(generic: Any): ItemStack
                    = craftItemStackAsBukkitCopy.invoke(null, generic) as ItemStack
            override fun getSpecificType(): Class<ItemStack>
                    = ItemStack::class.java
        }
    }
}
