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

package com.minecraft.moonlake.api.packet

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.isFrostburnOrLaterVer
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.util.Enums

data class PacketInResourcePackStatus(var status: Status,
        /**
         * * Resource package hash value, valid only in version 1.9 or earlier.
         * * 资源包哈希值, 仅在 1.9 或更早的版本有效.
         */
        var hash: String?) : PacketInBukkitAbstract("PacketPlayInResourcePackStatus") {

    @Deprecated("")
    constructor() : this(Status.DECLINED, "")

    override fun read(data: PacketBuffer) {
        hash = if(!isFrostburnOrLaterVer) data.readString() else null
        status = Enums.ofValuable(Status::class.java, data.readVarInt()) ?: Status.DECLINED
    }

    override fun write(data: PacketBuffer) {
        if(!isFrostburnOrLaterVer)
            data.writeString(hash.notNull("当前服务端兼容材质包 Hash 值, 但是为 null 值."))
        data.writeVarInt(status.value)
    }

    enum class Status(val value: Int) : Valuable<Int> {

        /**
         * Resource Pack Status: Successfully Loaded (资源包状态: 成功加载)
         */
        SUCCESSFULLY_LOADED(0),
        /**
         * Resource Pack Status: Declined (资源包状态: 拒绝)
         */
        DECLINED(1),
        /**
         * Resource Pack Status: Failed Download (资源包状态: 下载失败)
         */
        FAILED_DOWNLOAD(2),
        /**
         * Resource Pack Status: Accepted (资源包状态: 接受)
         */
        ACCEPTED(3),
        ;

        override fun value(): Int
                = value
    }
}
