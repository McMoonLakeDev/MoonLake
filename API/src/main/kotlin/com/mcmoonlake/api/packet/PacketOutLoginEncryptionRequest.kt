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

package com.mcmoonlake.api.packet

import com.mcmoonlake.api.security.RSAUtils
import java.security.PublicKey
import java.util.*

data class PacketOutLoginEncryptionRequest(
        var serverId: String,
        var publicKey: PublicKey,
        var verifyToken: ByteArray
) : PacketOutBukkitAbstract("PacketLoginOutEncryptionBegin"),
        PacketLogin {

    @Deprecated("")
    constructor() : this("Unknown", EMPTY_KEY, byteArrayOf())

    override fun read(data: PacketBuffer) {
        serverId = data.readString()
        publicKey = RSAUtils.decodePublicKey(data.readBytes(data.readVarInt()))
        verifyToken = data.readBytes(data.readVarInt())
    }

    override fun write(data: PacketBuffer) {
        data.writeString(serverId)
        val encoded = publicKey.encoded
        data.writeVarInt(encoded.size)
        data.writeBytes(encoded)
        data.writeVarInt(verifyToken.size)
        data.writeBytes(verifyToken)
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketOutLoginEncryptionRequest)
            return super.equals(other) && serverId == other.serverId && publicKey == other.publicKey && Arrays.equals(verifyToken, other.verifyToken)
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + serverId.hashCode()
        result = 31 * result + publicKey.hashCode()
        result = 31 * result + Arrays.hashCode(verifyToken)
        return result
    }

    companion object {

        @JvmStatic
        private val EMPTY_KEY = object: PublicKey {
            override fun getAlgorithm(): String
                    = "Empty"
            override fun getEncoded(): ByteArray
                    = byteArrayOf()
            override fun getFormat(): String
                    = "Empty"
        }
    }
}
