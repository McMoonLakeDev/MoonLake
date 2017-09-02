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

import com.minecraft.moonlake.api.cached.CachedPackage
import com.minecraft.moonlake.api.currentBukkitVersion
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.reflect.ClassSource
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion

object MinecraftReflection {

    /** member */

    @JvmStatic
    val craftBukkitFullPackage: String by lazy {
        "org.bukkit.craftbukkit.${MinecraftBukkitVersion.currentVersion().getVersion()}" }
    @JvmStatic
    val minecraftFullPackage: String by lazy {
        "net.minecraft.server.${MinecraftBukkitVersion.currentVersion().getVersion()}" }
    @JvmStatic
    val craftBukkitPackage: CachedPackage by lazy {
        CachedPackage(craftBukkitFullPackage, source) }
    @JvmStatic
    val minecraftPackage: CachedPackage by lazy {
        CachedPackage(minecraftFullPackage, source) }
    @JvmStatic
    val source: ClassSource by lazy {
        ClassSource.fromClassLoader() }

    /** api */

    @JvmStatic
    @JvmName("getMinecraftClass")
    @Throws(MoonLakeException::class)
    fun getMinecraftClass(className: String): Class<*>
            = minecraftPackage.getPackageClass(className)

    @JvmStatic
    @JvmName("setMinecraftClass")
    fun setMinecraftClass(className: String, clazz: Class<*>?): Class<*>?
            { minecraftPackage.setPackageClass(className, clazz); return clazz; }

    @JvmStatic
    @JvmName("getCraftBukkitClass")
    @Throws(MoonLakeException::class)
    fun getCraftBukkitClass(className: String): Class<*>
            = craftBukkitPackage.getPackageClass(className)

    @JvmStatic
    @JvmName("setCraftBukkitClass")
    fun setCraftBukkitClass(className: String, clazz: Class<*>?): Class<*>?
            { craftBukkitPackage.setPackageClass(className, clazz); return clazz; }

    @JvmStatic
    @JvmName("getChatSerializerClass")
    @Throws(MoonLakeException::class)
    fun getChatSerializerClass(): Class<*> {
        if(currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_8_R2))
            return getMinecraftClass("IChatBaseComponent\$ChatSerializer") // 1.8.3+
        return getMinecraftClass("ChatSerializer")
    }

    @JvmStatic
    @JvmName("getIChatBaseComponentClass")
    @Throws(MoonLakeException::class)
    fun getIChatBaseComponentClass(): Class<*>
            = getMinecraftClass("IChatBaseComponent")

    @JvmStatic
    @JvmName("getEntityClass")
    @Throws(MoonLakeException::class)
    fun getEntityClass(): Class<*>
            = getMinecraftClass("Entity")

    @JvmStatic
    @JvmName("getCraftEntityClass")
    fun getCraftEntityClass(): Class<*>
            = getCraftBukkitClass("entity.CraftEntity")

    @JvmStatic
    @JvmName("getEntityPlayerClass")
    @Throws(MoonLakeException::class)
    fun getEntityPlayerClass(): Class<*>
            = getMinecraftClass("EntityPlayer")

    @JvmStatic
    @JvmName("getCraftPlayerClass")
    fun getCraftPlayerClass(): Class<*>
            = getCraftBukkitClass("entity.CraftPlayer")

    @JvmStatic
    @JvmName("getEntityHumanClass")
    @Throws(MoonLakeException::class)
    fun getEntityHumanClass(): Class<*>
            = getMinecraftClass("EntityHuman")
}
