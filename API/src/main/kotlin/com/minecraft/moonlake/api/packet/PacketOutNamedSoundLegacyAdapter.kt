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

import com.minecraft.moonlake.api.isCombatOrLaterVer

class PacketOutNamedSoundLegacyAdapter : PacketLegacyAdapterCustom<PacketOutNamedSound, PacketOutNamedSoundLegacy>() {

    override val packet: Class<PacketOutNamedSound>
        get() = PacketOutNamedSound::class.java

    override val packetName: String
        get() = "PacketPlayOutCustomSoundEffect" // 1.9+

    override val packetLegacy: Class<PacketOutNamedSoundLegacy>
        get() = PacketOutNamedSoundLegacy::class.java

    override val packetLegacyName: String
        get() = "PacketPlayOutNamedSoundEffect" // 1.8.x

    override val isLegacy: Boolean
        get() = !isCombatOrLaterVer
}
