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

package com.mcmoonlake.api.utility

import com.mcmoonlake.api.cached.CachedPackage
import com.mcmoonlake.api.currentBukkitVersion
import com.mcmoonlake.api.exception.MoonLakeException
import com.mcmoonlake.api.isOrLater
import com.mcmoonlake.api.reflect.ClassSource
import com.mcmoonlake.api.version.MinecraftBukkitVersion

object MinecraftReflection {

    /** member */

    private const val PACKAGE_CRAFTBUKKIT = "org.bukkit.craftbukkit"
    private const val PACKAGE_MINECRAFT_SERVER = "net.minecraft.server"

    @JvmStatic
    private val PACKAGE_FULL_CRAFTBUKKIT = "$PACKAGE_CRAFTBUKKIT.${MinecraftBukkitVersion.currentVersion().version}"
    @JvmStatic
    private val PACKAGE_FULL_MINECRAFT_SERVER = "$PACKAGE_MINECRAFT_SERVER.${MinecraftBukkitVersion.currentVersion().version}"
    @JvmStatic
    private val PACKAGE_CACHED_CRAFTBUKKIT: CachedPackage by lazy { CachedPackage(PACKAGE_FULL_CRAFTBUKKIT, SOURCE) }
    @JvmStatic
    private val PACKAGE_CACHED_MINECRAFT_SERVER: CachedPackage by lazy { CachedPackage(PACKAGE_FULL_MINECRAFT_SERVER, SOURCE) }
    @JvmStatic
    private val SOURCE: ClassSource by lazy { ClassSource.fromClassLoader() }

    /** api */

    @JvmStatic
    @JvmName("getMinecraftClass")
    @Throws(MoonLakeException::class)
    fun getMinecraftClass(className: String): Class<*>
            = PACKAGE_CACHED_MINECRAFT_SERVER.getPackageClass(className)

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
            { PACKAGE_CACHED_MINECRAFT_SERVER.setPackageClass(className, clazz); return clazz; }

    @JvmStatic
    @JvmName("getCraftBukkitClass")
    @Throws(MoonLakeException::class)
    fun getCraftBukkitClass(className: String): Class<*>
            = PACKAGE_CACHED_CRAFTBUKKIT.getPackageClass(className)

    @JvmStatic
    @JvmName("getCraftBukkitClassOrNull")
    fun getCraftBukkitClassOrNull(className: String): Class<*>? = try {
        getCraftBukkitClass(className)
    } catch(e: MoonLakeException) {
        null
    }

    @JvmStatic
    @JvmName("setCraftBukkitClass")
    fun setCraftBukkitClass(className: String, clazz: Class<*>?): Class<*>?
            { PACKAGE_CACHED_CRAFTBUKKIT.setPackageClass(className, clazz); return clazz; }

    /**
     * * NMS -> ChatSerializer
     */
    @JvmStatic
    val chatSerializerClass: Class<*>
        get() {
            if(currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_8_R2))
                return getMinecraftClass("IChatBaseComponent\$ChatSerializer") // 1.8.3+
            return getMinecraftClass("ChatSerializer")
        }

    /**
     * * NMS -> IChatBaseComponent
     */
    @JvmStatic
    val iChatBaseComponentClass: Class<*>
        get() = getMinecraftClass("IChatBaseComponent")

    /**
     * * NMS -> World
     */
    @JvmStatic
    val worldClass: Class<*>
        get() = getMinecraftClass("World")

    /**
     * * NMS -> WorldServer
     */
    @JvmStatic
    val worldServerClass: Class<*>
         get() = getMinecraftClass("WorldServer")

    /**
     * * OBC -> CraftWorld
     */
    @JvmStatic
    val craftWorldClass: Class<*>
        get() = getCraftBukkitClass("CraftWorld")

    /**
     * * NMS -> Entity
     */
    @JvmStatic
    val entityClass: Class<*>
        get() = getMinecraftClass("Entity")

    /**
     * * NMS -> EntityLiving
     */
    @JvmStatic
    val entityLivingClass: Class<*>
        get() = getMinecraftClass("EntityLiving")

    /**
     * * OBC -> entity.CraftEntity
     */
    @JvmStatic
    val craftEntityClass: Class<*>
        get() = getCraftBukkitClass("entity.CraftEntity")

    /**
     * * NMS -> EntityPlayer
     */
    @JvmStatic
    val entityPlayerClass: Class<*>
        get() = getMinecraftClass("EntityPlayer")

    /**
     * * OBC -> entity.CraftPlayer
     */
    @JvmStatic
    val craftPlayerClass: Class<*>
        get() = getCraftBukkitClass("entity.CraftPlayer")

    /**
     * * NMS -> EntityHuman
     */
    @JvmStatic
    val entityHumanClass: Class<*>
        get() = getMinecraftClass("EntityHuman")

    /**
     * * NMS -> Item
     */
    @JvmStatic
    val itemClass: Class<*>
        get() = getMinecraftClass("Item")

    /**
     * * NMS -> ItemStack
     */
    @JvmStatic
    val itemStackClass: Class<*>
        get() = getMinecraftClass("ItemStack")

    /**
     * * OBC -> inventory.CraftItemStack
     */
    @JvmStatic
    val craftItemStackClass: Class<*>
        get() = getCraftBukkitClass("inventory.CraftItemStack")

    /**
     * * NMS -> NBTBase
     */
    @JvmStatic
    val nbtBaseClass: Class<*>
        get() = getMinecraftClass("NBTBase")

    /**
     * * NMS -> NBTTagCompound
     */
    @JvmStatic
    val nbtTagCompoundClass: Class<*>
        get() = getMinecraftClass("NBTTagCompound")

    /**
     * * NMS -> PlayerConnection
     */
    @JvmStatic
    val playerConnectionClass: Class<*>
        get() = getMinecraftClass("PlayerConnection")

    /**
     * * NMS -> Packet
     */
    @JvmStatic
    val packetClass: Class<*>
        get() = getMinecraftClass("Packet")

    /**
     * * NMS -> PacketListener
     */
    @JvmStatic
    val packetListenerClass: Class<*>
        get() = getMinecraftClass("PacketListener")

    /**
     * * NMS -> PacketDataSerializer
     */
    @JvmStatic
    val packetDataSerializerClass: Class<*>
        get() = getMinecraftClass("PacketDataSerializer")

    /**
     * * NMS -> NetworkManager
     */
    @JvmStatic
    val networkManagerClass: Class<*>
        get() = getMinecraftClass("NetworkManager")

    /**
     * * NMS -> MinecraftServer
     */
    @JvmStatic
    val minecraftServerClass: Class<*>
        get() = getMinecraftClass("MinecraftServer")

    /**
     * * NMS -> ServerConnection
     */
    @JvmStatic
    val serverConnectionClass: Class<*>
        get() = getMinecraftClass("ServerConnection")

    /**
     * * OBC -> CraftServer
     */
    @JvmStatic
    val craftServerClass: Class<*>
        get() = getCraftBukkitClass("CraftServer")
}
