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

package com.mcmoonlake.api.entity

import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.utility.MinecraftConverters
import com.mcmoonlake.api.utility.MinecraftReflection
import org.bukkit.World
import org.bukkit.entity.Entity

object Entities {

    @JvmStatic
    private val worldGetEntityById: AccessorMethod by lazy {
        Accessors.getAccessorMethod(FuzzyReflect.fromClass(MinecraftReflection.worldClass, true)
                .getMethodByParameters("getEntity", MinecraftReflection.entityClass, arrayOf(Int::class.java))) }

    @JvmStatic
    @JvmName("getEntity")
    fun getEntity(world: World, entityId: Int): Entity?
            = asBukkitEntity(worldGetEntityById.invoke(MinecraftConverters.world.getGeneric(world), entityId))

    @JvmStatic
    @JvmName("asNMSEntity")
    fun asNMSEntity(entity: Entity?): Any?
            = MinecraftConverters.getEntity(Entity::class.java).getGeneric(entity)

    @JvmStatic
    @JvmName("asBukkitEntity")
    fun asBukkitEntity(nmsEntity: Any?): Entity?
            = MinecraftConverters.getEntity(Entity::class.java).getSpecific(nmsEntity)
}
