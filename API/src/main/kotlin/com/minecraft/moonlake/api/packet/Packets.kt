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

package com.minecraft.moonlake.api.packet

import com.minecraft.moonlake.api.converter.ConverterEquivalentIgnoreNull
import com.minecraft.moonlake.api.reflect.accessor.AccessorConstructor
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftReflection
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

object Packets {

    @JvmStatic
    private val packetDataSerializerConstructor: AccessorConstructor<out Any> by lazy {
        Accessors.getAccessorConstructor(MinecraftReflection.getPacketDataSerializerClass(), false, ByteBuf::class.java) }
    @JvmStatic
    private val packetDataSerializerBuffer: AccessorField by lazy {
        Accessors.getAccessorField(MinecraftReflection.getPacketDataSerializerClass(), ByteBuf::class.java, true) }
    @JvmStatic
    private val packetRead: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getPacketClass(), "a", false, MinecraftReflection.getPacketDataSerializerClass()) }
    @JvmStatic
    private val packetWrite: AccessorMethod by lazy {
        Accessors.getAccessorMethod(MinecraftReflection.getPacketClass(), "b", false, MinecraftReflection.getPacketDataSerializerClass()) }
    @JvmStatic
    private val converter: ConverterEquivalentIgnoreNull<PacketBukkit> by lazy {
        getPacketConverter() }
    @JvmStatic
    private val lookupBukkit: MutableMap<Class<*>, Class<out PacketBukkit>> = HashMap()

    init {
        registerPacketBukkit("PacketPlayOutChat", PacketOutChat::class.java)
        registerPacketBukkit("PacketPlayOutWorldParticles", PacketOutParticles::class.java)
    }

    @JvmStatic
    @JvmName("createPacket")
    fun createPacket(clazz: Class<*>): Any
            = clazz.newInstance()

    @JvmStatic
    @JvmName("createPacket")
    fun createPacket(wrapped: PacketBukkit): Any
            = converter.getGenericValue(wrapped)

    @JvmStatic
    @JvmName("createPacketDataSerializer")
    fun createPacketDataSerializer(buffer: ByteBuf = Unpooled.buffer()): Any
            = packetDataSerializerConstructor.newInstance(buffer)

    @JvmStatic
    @JvmName("createPacketBukkit")
    @Throws(UnsupportedOperationException::class)
    @Synchronized
    fun createPacketBukkit(clazz: Class<*>): PacketBukkit
            = lookupBukkit[clazz]?.newInstance() ?: throw UnsupportedOperationException("未对 NMS 数据包 $clazz 类添加包装类支持.")

    @JvmStatic
    @JvmName("registerPacketBukkit")
    @Throws(IllegalArgumentException::class)
    @Synchronized
    fun registerPacketBukkit(clazzName: String, value: Class<out PacketBukkit>): Boolean {
        val clazz = MinecraftReflection.getMinecraftClassOrNull(clazzName)
        if(clazz == null || lookupBukkit.containsKey(clazz))
            throw IllegalArgumentException("未知的 NMS 数据包 $clazzName 类或已经被注册.")
        return lookupBukkit.put(clazz, value) == null
    }

    @JvmStatic
    @JvmName("getPacketConverter")
    private fun getPacketConverter(): ConverterEquivalentIgnoreNull<PacketBukkit> {
        return object: ConverterEquivalentIgnoreNull<PacketBukkit> {
            override fun getGenericValue(specific: PacketBukkit): Any {
                val handle = createPacket(specific.getType())
                val packetBuffer = PacketBuffer()
                specific.write(packetBuffer)
                packetRead.invoke(handle, createPacketDataSerializer(packetBuffer.getByteBuf()))
                packetBuffer.release()
                return handle
            }
            override fun getSpecificValue(generic: Any): PacketBukkit {
                val packetDataSerializer = createPacketDataSerializer()
                val packetBuffer = PacketBuffer(packetDataSerializerBuffer.get(packetDataSerializer) as ByteBuf)
                val wrapped = createPacketBukkit(generic::class.java)
                packetWrite.invoke(generic, packetDataSerializer)
                wrapped.read(packetBuffer)
                packetBuffer.release()
                return wrapped
            }
            override fun getSpecificType(): Class<PacketBukkit>
                    = PacketBukkit::class.java
        }
    }
}
