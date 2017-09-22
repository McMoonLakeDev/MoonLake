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

import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatSerializer
import com.minecraft.moonlake.api.nbt.NBTCompound
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.util.*

data class PacketBuffer(private var byteBuf: ByteBuf) {

    /** constructor */

    constructor() : this(Unpooled.buffer())
    constructor(buffer: ByteArray) : this(Unpooled.wrappedBuffer(buffer))

    /** api */

    fun getByteBuf(): ByteBuf
            = byteBuf

    fun setByteBuf(buffer: ByteBuf): PacketBuffer
            { byteBuf = buffer; return this; }

    fun setByteBuf(buffer: ByteArray): PacketBuffer
            { byteBuf = Unpooled.wrappedBuffer(buffer); return this; }

    fun writeByte(value: Byte): PacketBuffer
            { byteBuf.writeByte(value.toInt()); return this; }

    fun writeByte(value: Int): PacketBuffer
            { byteBuf.writeByte(value); return this; }

    fun writeBytes(value: ByteArray): PacketBuffer
            { byteBuf.writeBytes(value); return this; }

    fun writeShort(value: Int): PacketBuffer
            { byteBuf.writeShort(value); return this; }

    fun writeInt(value: Int): PacketBuffer
            { byteBuf.writeInt(value); return this; }

    fun writeLong(value: Long): PacketBuffer
            { byteBuf.writeLong(value); return this; }

    fun writeFloat(value: Float): PacketBuffer
            { byteBuf.writeFloat(value); return this; }

    fun writeDouble(value: Double): PacketBuffer
            { byteBuf.writeDouble(value); return this; }

    /**
     * @throws IllegalArgumentException If string [value] bytes length > 32767.
     */
    @Throws(IllegalArgumentException::class)
    fun writeString(value: String): PacketBuffer {
        val buffer = value.toByteArray()
        if(buffer.size > 32767)
            throw IllegalArgumentException("字符串值字节不能大于 32767 长度.")
        writeVarInt(buffer.size)
        writeBytes(buffer)
        return this
    }

    /**
     * @throws IllegalArgumentException If per string for [value] bytes length > 32767.
     */
    @Throws(IllegalArgumentException::class)
    fun writeStrings(value: Array<out String>): PacketBuffer
            { value.forEach { writeString(it) }; return this; }

    fun writeUUID(value: UUID): PacketBuffer
            { writeLong(value.mostSignificantBits); writeLong(value.leastSignificantBits); return this; }

    fun writeChatComponent(value: ChatComponent): PacketBuffer
            { writeString(value.toJson()); return this; }

    fun writeNBTComponent(value: NBTCompound?): PacketBuffer
            { return this; } // TODO

    fun writeVarInt(value: Int): PacketBuffer {
        var value0 = value
        while((value0 and 0x7F.inv()) != 0) {
            writeByte((value0 and 0x7F).or(0x80))
            value0 = value0.ushr(7)
        }
        writeByte(value0)
        return this
    }

    fun writeVarLong(value: Long): PacketBuffer {
        var value0 = value
        while((value0 and 0x7F.inv()) != 0L) {
            writeByte(((value0 and 0x7F).or(0x80)).toInt())
            value0 = value0.ushr(7)
        }
        writeByte(value0.toInt())
        return this
    }

    fun readByte(): Byte
            = byteBuf.readByte()

    /**
     * @throws IllegalArgumentException If [length] < 0.
     */
    @Throws(IllegalArgumentException::class)
    fun readBytes(length: Int): ByteArray {
        val buffer = if(length >= 0) ByteArray(length) else throw IllegalArgumentException("待读取的数组不能小于 0 长度.")
        byteBuf.readBytes(buffer)
        return buffer
    }

    fun readShort(): Short
            = byteBuf.readShort()

    fun readInt(): Int
            = byteBuf.readInt()

    fun readLong(): Long
            = byteBuf.readLong()

    fun readFloat(): Float
            = byteBuf.readFloat()

    fun readDouble(): Double
            = byteBuf.readDouble()

    fun readString(): String {
        val length = readVarInt()
        val buffer = readBytes(length)
        return String(buffer)
    }

    fun readUUID(): UUID
            = UUID(readLong(), readLong())

    fun readChatComponent(): ChatComponent
            = ChatSerializer.fromJson(readString())

    fun readNBTComponent(): NBTCompound?
            = throw UnsupportedOperationException() // TODO

    /**
     * @throws IllegalArgumentException If VarInt length > 5.
     */
    @Throws(IllegalArgumentException::class)
    fun readVarInt(): Int {
        var value = 0
        var size = 0
        var b = 0
        while((readByte().toInt().apply { b = this }.and(0x80)) == 0x80) {
            value = value or ((b and 0x7F) shl (size++ * 7))
            if(size > 5)
                throw IllegalArgumentException("VarInt 值数据太大，必须小于或等于 5 长度.")
        }
        return value or ((b and 0x7F) shl (size * 7))
    }

    /**
     * @throws IllegalArgumentException If VarLong length > 10.
     */
    @Throws(IllegalArgumentException::class)
    fun readVarLong(): Long {
        var value = 0L
        var size = 0
        var b = 0
        while((readByte().toInt().apply { b = this }.and(0x80)) == 0x80) {
            value = value or ((b and 0x7F) shl (size++ * 7)).toLong()
            if(size > 10)
                throw IllegalArgumentException("VarLong 值数据太大，必须小于或等于 10 长度.")
        }
        return value or ((b and 0x7F) shl (size * 7)).toLong()
    }

    fun clear(): PacketBuffer
            { byteBuf.clear(); return this; }

    fun release(): Boolean
            = byteBuf.release()

    fun release(decrement: Int): Boolean
            = byteBuf.release(decrement)
}
