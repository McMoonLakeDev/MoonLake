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
import com.minecraft.moonlake.api.util.Enums

data class PacketOutAnimation(var entityId: Int, var animation: Type) : PacketOutBukkitAbstract("PacketPlayOutAnimation") {

    @Deprecated("")
    constructor() : this(-1, Type.HURT_EFFECT)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        animation = Enums.ofValuable(Type::class.java, data.readUnsignedByte().toInt()) ?: Type.HURT_EFFECT
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeByte(animation.id)
    }

    enum class Type(val id: Int) : Valuable<Int> {

        /**
         * 动画类型: 主手臂摇摆
         */
        SWING_MAIN_ARM(0),
        /**
         * 动画类型: 副手臂摇摆 (注: 此项只兼容 1.9+ 服务器版本)
         */
        SWING_OFFHAND_ARM(3),
        /**
         * 动画类型: 受伤效果
         */
        HURT_EFFECT(1),
        /**
         * 动画类型: 离开床
         */
        LEAVE_BED(2),
        /**
         * 动画类型: 暴击效果
         */
        CRITICAL_EFFECT(4),
        /**
         * 动画类型: 魔法暴击效果
         */
        MAGIC_CRITICAL_EFFECT(5),
        ;

        override fun value(): Int
                = id
    }
}
