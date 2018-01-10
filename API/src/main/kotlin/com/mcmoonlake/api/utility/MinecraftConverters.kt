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

package com.mcmoonlake.api.utility

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatSerializer
import com.mcmoonlake.api.converter.ConverterEquivalent
import com.mcmoonlake.api.converter.ConverterEquivalentIgnoreNull
import com.mcmoonlake.api.nbt.NBTBase
import com.mcmoonlake.api.nbt.NBTFactory
import com.mcmoonlake.api.reflect.accessor.AccessorField
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import java.io.StringReader

object MinecraftConverters {

    /** chat component */

    @JvmStatic
    private val chatSerializerGson: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.chatSerializerClass, Gson::class.java, true) }

    @JvmStatic
    val chatComponent: ConverterEquivalent<ChatComponent>
        get() = object: ConverterEquivalentIgnoreNull<ChatComponent> {
            override fun getGenericValue(specific: ChatComponent): Any {
                val gson = chatSerializerGson.get(null) as Gson
                val adapter = gson.getAdapter(MinecraftReflection.iChatBaseComponentClass)
                return adapter.read(JsonReader(StringReader(specific.toJson())))
            }
            override fun getSpecificValue(generic: Any): ChatComponent {
                val gson = chatSerializerGson.get(null) as Gson
                val json = gson.toJson(generic)
                return ChatSerializer.fromJson(json)
            }
            override fun getSpecificType(): Class<ChatComponent>
                    = ChatComponent::class.java
        }

    /** world */

    @JvmStatic
    private val craftWorldGetHandle: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.craftWorldClass, "getHandle", true) }
    @JvmStatic
    private val worldGetWorld: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.worldClass, "getWorld", true) }

    @JvmStatic
    val world: ConverterEquivalent<World>
        get() = object: ConverterEquivalentIgnoreNull<World> {
            override fun getGenericValue(specific: World): Any
                    = craftWorldGetHandle.invoke(specific) as Any
            override fun getSpecificValue(generic: Any): World
                    = worldGetWorld.invoke(generic) as World
            override fun getSpecificType(): Class<World>
                    = World::class.java
        }

    /** entity */

    @JvmStatic
    private val craftEntityGetHandle: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.craftEntityClass, "getHandle") }
    @JvmStatic
    private val entityGetBukkitEntity: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.entityClass, "getBukkitEntity") }

    @JvmStatic
    @JvmName("getEntity")
    fun <T: Entity> getEntity(clazz: Class<T>): ConverterEquivalent<T> {
        return object: ConverterEquivalentIgnoreNull<T> {
            override fun getGenericValue(specific: T): Any
                    = craftEntityGetHandle.invoke(specific) as Any
            @Suppress("UNCHECKED_CAST")
            override fun getSpecificValue(generic: Any): T
                    = entityGetBukkitEntity.invoke(generic) as T
            override fun getSpecificType(): Class<T>
                    = clazz
        }
    }

    /** item stack */

    @JvmStatic
    private val craftItemStackAsNMSCopy: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.craftItemStackClass, "asNMSCopy", false, ItemStack::class.java) }

    @JvmStatic
    private val craftItemStackAsCraftMirror: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.craftItemStackClass, "asCraftMirror", false, MinecraftReflection.itemStackClass) }

    @JvmStatic
    val itemStack: ConverterEquivalent<ItemStack>
        get() = object: ConverterEquivalentIgnoreNull<ItemStack> {
            override fun getGenericValue(specific: ItemStack): Any
                    = craftItemStackAsNMSCopy.invoke(null, specific) as Any
            override fun getSpecificValue(generic: Any): ItemStack
                    = craftItemStackAsCraftMirror.invoke(null, generic) as ItemStack
            override fun getSpecificType(): Class<ItemStack>
                    = ItemStack::class.java
        }

    /** nbt */

    @JvmStatic
    val nbt: ConverterEquivalent<NBTBase<*>>
        get() = object: ConverterEquivalentIgnoreNull<NBTBase<*>> {
            override fun getGenericValue(specific: NBTBase<*>): Any
                    = NBTFactory.fromBase(specific).handle
            override fun getSpecificValue(generic: Any): NBTBase<*>
                    = NBTFactory.fromNMS<Any>(generic)
            override fun getSpecificType(): Class<NBTBase<*>>
                    = NBTBase::class.java
        }

    /** server */

    @JvmStatic
    private val craftServerMinecraft: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.craftServerClass, MinecraftReflection.minecraftServerClass, true) }
    @JvmStatic
    private val minecraftServerCraft: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.minecraftServerClass, MinecraftReflection.craftServerClass, true) }

    @JvmStatic
    val server: ConverterEquivalent<Server>
        get() = object: ConverterEquivalentIgnoreNull<Server> {
            override fun getSpecificValue(generic: Any): Server
                    = minecraftServerCraft.get(generic) as Server
            override fun getGenericValue(specific: Server): Any
                    = craftServerMinecraft.get(specific) as Any
            override fun getSpecificType(): Class<Server>
                    = Server::class.java
        }
}
