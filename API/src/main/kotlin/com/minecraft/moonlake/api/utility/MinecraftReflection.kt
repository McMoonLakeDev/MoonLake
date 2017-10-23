/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */

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
    private val craftBukkitFullPackage: String by lazy {
        "org.bukkit.craftbukkit.${MinecraftBukkitVersion.currentVersion().version}" }
    @JvmStatic
    private val minecraftFullPackage: String by lazy {
        "net.minecraft.server.${MinecraftBukkitVersion.currentVersion().version}" }
    @JvmStatic
    private val craftBukkitPackage: CachedPackage by lazy {
        CachedPackage(craftBukkitFullPackage, source) }
    @JvmStatic
    private val minecraftPackage: CachedPackage by lazy {
        CachedPackage(minecraftFullPackage, source) }
    @JvmStatic
    private val source: ClassSource by lazy {
        ClassSource.fromClassLoader() }

    /** api */

    @JvmStatic
    @JvmName("getMinecraftClass")
    @Throws(MoonLakeException::class)
    fun getMinecraftClass(className: String): Class<*>
            = minecraftPackage.getPackageClass(className)

    @JvmStatic
    @JvmName("getMinecraftClass")
    @Throws(MoonLakeException::class)
    fun getMinecraftClass(className: String, vararg aliases: String): Class<*> = try {
        getMinecraftClass(className)
    } catch(e: MoonLakeException) {
        var result: Class<*>? = null
        for(alias in aliases) try {
            result = getMinecraftClass(alias)
        } catch(e: MoonLakeException) {
        }
        if(result != null) result.also { setMinecraftClass(className, it) }
        else throw MoonLakeException("未查找到 $className 以及别名 ${aliases.joinToString()} 的类.", e)
    }

    @JvmStatic
    @JvmName("getMinecraftClassOrNull")
    fun getMinecraftClassOrNull(className: String): Class<*>? = try {
        getMinecraftClass(className)
    } catch(e: MoonLakeException) {
        null
    }

    @JvmStatic
    @JvmName("getMinecraftClassOrNull")
    fun getMinecraftClassOrNull(className: String, vararg aliases: String): Class<*>? = try {
        getMinecraftClass(className, *aliases)
    } catch(e: MoonLakeException) {
        null
    }

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
    @JvmName("getWorldClass")
    @Throws(MoonLakeException::class)
    fun getWorldClass(): Class<*>
            = getMinecraftClass("World")

    @JvmStatic
    @JvmName("getWorldServerClass")
    @Throws(MoonLakeException::class)
    fun getWorldServerClass(): Class<*>
            = getMinecraftClass("WorldServer")

    @JvmStatic
    @JvmName("getCraftWorldClass")
    fun getCraftWorldClass(): Class<*>
            = getCraftBukkitClass("CraftWorld")

    @JvmStatic
    @JvmName("getEntityClass")
    @Throws(MoonLakeException::class)
    fun getEntityClass(): Class<*>
            = getMinecraftClass("Entity")

    @JvmStatic
    @JvmName("getEntityLivingClass")
    @Throws(MoonLakeException::class)
    fun getEntityLivingClass(): Class<*>
            = getMinecraftClass("EntityLiving")

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

    @JvmStatic
    @JvmName("getItemClass")
    @Throws(MoonLakeException::class)
    fun getItemClass(): Class<*>
            = getMinecraftClass("Item")

    @JvmStatic
    @JvmName("getItemStackClass")
    @Throws(MoonLakeException::class)
    fun getItemStackClass(): Class<*>
            = getMinecraftClass("ItemStack")

    @JvmStatic
    @JvmName("getCraftItemStack")
    fun getCraftItemStackClass(): Class<*>
            = getCraftBukkitClass("inventory.CraftItemStack")

    @JvmStatic
    @JvmName("getNBTBaseClass")
    @Throws(MoonLakeException::class)
    fun getNBTBaseClass(): Class<*>
            = getMinecraftClass("NBTBase")

    @JvmStatic
    @JvmName("getNBTTagCompoundClass")
    @Throws(MoonLakeException::class)
    fun getNBTTagCompoundClass(): Class<*>
            = getMinecraftClass("NBTTagCompound")

    @JvmStatic
    @JvmName("getPlayerConnectionClass")
    @Throws(MoonLakeException::class)
    fun getPlayerConnectionClass(): Class<*>
            = getMinecraftClass("PlayerConnection")

    @JvmStatic
    @JvmName("getPacketClass")
    fun getPacketClass(): Class<*>
            = getMinecraftClass("Packet")

    @JvmStatic
    @JvmName("getPacketListenerClass")
    fun getPacketListenerClass(): Class<*>
            = getMinecraftClass("PacketListener")

    @JvmStatic
    @JvmName("getPacketDataSerializerClass")
    fun getPacketDataSerializerClass(): Class<*>
            = getMinecraftClass("PacketDataSerializer")

    @JvmStatic
    @JvmName("getNetworkManagerClass")
    fun getNetworkManagerClass(): Class<*>
            = getMinecraftClass("NetworkManager")

    @JvmStatic
    @JvmName("getMinecraftServerClass")
    fun getMinecraftServerClass(): Class<*>
            = getMinecraftClass("MinecraftServer")

    @JvmStatic
    @JvmName("getServerConnectionClass")
    fun getServerConnectionClass(): Class<*>
            = getMinecraftClass("ServerConnection")

    @JvmStatic
    @JvmName("getCraftServerClass")
    fun getCraftServerClass(): Class<*>
            = getCraftBukkitClass("CraftServer")
}
