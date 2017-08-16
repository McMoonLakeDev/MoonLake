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

package com.minecraft.moonlake.core.player

import com.minecraft.moonlake.api.attribute.Attribute
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.player.IllegalOfflinePlayerException
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

class MoonLakePlayerBase : MoonLakePlayerAbstract {

    /** constructor */

    @Throws(IllegalOfflinePlayerException::class)
    constructor(uuid: UUID) : super(uuid)

    @Throws(IllegalOfflinePlayerException::class)
    constructor(player: Player) : super(player)

    /** function */

    override fun isInvulnerable(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun setInvulnerable(invulnerable: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun isGlowing(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun setGlowing(glowing: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun isGliding(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun setGliding(gliding: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun isSilent(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun setSilent(silent: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun hasGravity(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun setGravity(gravity: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun stopSound(sound: Sound) {
        throw UnsupportedOperationException()
    }

    override fun stopSound(sound: String) {
        throw UnsupportedOperationException()
    }

    override fun getAttribute(type: AttributeType): Attribute {
        throw UnsupportedOperationException()
    }
}
