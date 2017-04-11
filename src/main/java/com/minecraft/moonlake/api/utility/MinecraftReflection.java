/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.utility;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class MinecraftReflection {

    private static String MINECRAFT_FULL_PACKAGE = null;
    private static String CRAFTBUKKIT_FULL_PACKAGE = null;
    private static String CRAFTBUKKIT_NMS_VERSION = null;
    private static CachedPackage minecraftPackage;
    private static CachedPackage craftBukkitPackage;
    private static ClassSource source;
    private static MethodAccessor enumConstantDirectory;

    static {
    }

    private MinecraftReflection() {
        super();
    }

    public static String getMinecraftFullPackage() {
        if(MINECRAFT_FULL_PACKAGE == null)
            MINECRAFT_FULL_PACKAGE = "net.minecraft.server." + getCraftbukkitNMSVersion();
        return MINECRAFT_FULL_PACKAGE;
    }

    public static String getCraftBukkitFullPackage() {
        if(CRAFTBUKKIT_FULL_PACKAGE == null)
            CRAFTBUKKIT_FULL_PACKAGE = "org.bukkit.craftbukkit." + getCraftbukkitNMSVersion();
        return CRAFTBUKKIT_FULL_PACKAGE;
    }

    public static String getCraftbukkitNMSVersion() {
        if(CRAFTBUKKIT_NMS_VERSION == null)
            CRAFTBUKKIT_NMS_VERSION = MinecraftBukkitVersion.getCurrentVersion().getVersion();
        return CRAFTBUKKIT_NMS_VERSION;
    }

    public static Class<?> getClass(String className) {
        try {
            return MinecraftReflection.class.getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new MoonLakeException("", e);
        }
    }

    public static Class<?> getMinecraftClass(String className) {
        if(minecraftPackage == null)
            minecraftPackage = new CachedPackage(getMinecraftFullPackage(), getClassSource());
        return minecraftPackage.getPackageClass(className);
    }

    public static Class<?> getMinecraftClass(String className, String... aliases) {
        try {
            return getMinecraftClass(className);

        } catch (MoonLakeException e) {
            Class<?> success = null;
            for(String alias : aliases) try {
                success = getMinecraftClass(alias);
            } catch (MoonLakeException e1) {
            }
            if(success != null) {
                minecraftPackage.setPackageClass(className, success);
                return success;
            }
            throw new MoonLakeException(String.format("Unable to find %s (%s)", className, Arrays.toString(aliases)), e);
        }
    }

    public static Class<?> getCraftBukkitClass(String className) {
        if(craftBukkitPackage == null)
            craftBukkitPackage = new CachedPackage(getCraftBukkitFullPackage(), getClassSource());
        return craftBukkitPackage.getPackageClass(className);
    }

    private static Class<?> setMinecraftClass(String className, Class<?> clazz) {
        if(minecraftPackage == null)
            minecraftPackage = new CachedPackage(getMinecraftFullPackage(), getClassSource());
        minecraftPackage.setPackageClass(className, clazz);
        return clazz;
    }

    public static Class<?> getMinecraftWorldClass() {
        return getMinecraftClass("World");
    }

    public static Class<?> getMinecraftWorldServerClass() {
        return getMinecraftClass("WorldServer");
    }

    public static Class<?> getCraftWorldClass() {
        return getCraftBukkitClass("CraftWorld");
    }

    public static Class<?> getMinecraftEntityClass() {
        return getMinecraftClass("Entity");
    }

    public static Class<?> getCraftEntityClass() {
        return getCraftBukkitClass("entity.CraftEntity");
    }

    public static Class<?> getMinecraftEntityItemClass() {
        return getMinecraftClass("EntityItem");
    }

    public static Class<?> getMinecraftEntityHumanClass() {
        return getMinecraftEntityPlayerClass().getSuperclass();
    }

    public static Class<?> getMinecraftEntityPlayerClass() {
        return getMinecraftClass("EntityPlayer");
    }

    public static Class<?> getCraftPlayerClass() {
        return getCraftBukkitClass("entity.CraftPlayer");
    }

    public static Class<?> getMinecraftTileEntityClass() {
        return getMinecraftClass("TileEntity");
    }

    public static Class<?> getMinecraftBlockClass() {
        return getMinecraftClass("Block");
    }

    public static Class<?> getMinecraftBlockPositionClass() {
        return getMinecraftClass("BlockPosition");
    }

    public static Class<?> getMinecraftItemStackClass() {
        return getMinecraftClass("ItemStack");
    }

    public static Class<?> getCraftItemStackClass() {
        return getCraftBukkitClass("inventory.CraftItemStack");
    }

    public static Class<?> getMinecraftVec3DClass() {
        try {
            return getMinecraftClass("Vec3D");
        } catch (MoonLakeException e) {
        }
        return null;
    }

    public static Class<?> getMinecraftWorldType() { // TODO FuzzyReflect
        return getMinecraftClass("WorldType");
    }

    public static Class<?> getIChatBaseComponentClass() { // TODO FuzzyReflect
        try {
            return getMinecraftClass("IChatBaseComponent");
        } catch (MoonLakeException e) {
        }
        return null;
    }

    public static Class<?> getChatMessageClass() {
        return getMinecraftClass("ChatMessage");
    }

    public static Class<?> getCraftChatMessageClass() {
        return getCraftBukkitClass("util.CraftChatMessage");
    }

    public static Class<?> getChatComponentTextClass() { // TODO FuzzyReflect
        try {
            return getMinecraftClass("ChatComponentText");
        } catch (MoonLakeException e) {
            try {

            } catch (MoonLakeException e1) {
            }
        }
        throw new IllegalStateException("Cannot find ChatComponentText class.");
    }

    public static Class<?> getChatSerializerClass() {
        try {
            return getMinecraftClass("ChatSerializer", "IChatBaseComponent$ChatSerializer");
        } catch (MoonLakeException e) {
            throw new IllegalStateException("Could not find ChatSerializer class.");
        }
    }

    public static Class<?> getNBTTagCompoundClass() {
        return getMinecraftClass("NBTTagCompound");
    }

    public static Class<?> getNBTCompressedStreamToolsClass() { // TODO FuzzyReflect
        return getMinecraftClass("NBTCompressedStreamTools");
    }

    public static Class<?> getNetworkManagerClass() { // TODO FuzzyReflect
        return getMinecraftClass("NetworkManager", "INetworkManager");
    }

    public static Class<?> getPlayerConnectionClass() { // TODO FuzzyReflect
        return getMinecraftClass("PlayerConnection");
    }

    public static Class<?> getPacketDataSerializerClass() { // TODO FuzzyReflect
        return getMinecraftClass("PacketDataSerializer");
    }

    public static Class<?> getPacketClass() {
        return getMinecraftClass("Packet");
    }

    public static boolean is(Class<?> clazz, Object obj) {
        return !(clazz == null || obj == null) && clazz.isAssignableFrom(obj.getClass());
    }

    @SuppressWarnings("unchecked")
    public static Object enumValueOf(Class<? extends Enum<?>> enumClass, String name) {
        if(enumConstantDirectory == null)
            enumConstantDirectory = Accessors.getMethodAccessor(Class.class, "enumConstantDirectory");
        Object obj = ((Map<String, ?>) enumConstantDirectory.invoke(enumClass)).get(name);
        if(obj != null)
            return obj;
        if(name == null)
            throw new NullPointerException("Name is null.");
        throw new IllegalArgumentException("No enum constant " + enumClass.getCanonicalName() + "." + name);
    }

    public static <T extends Enum<T>> T enumValueOfType(Class<T> enumClass, String name) {
        return Enum.valueOf(enumClass, name);
    }

    public static Class<?> getArrayClass(Class<?> type) {
        return Array.newInstance(type, 0).getClass();
    }

    public static ClassSource getClassSource() {
        if(source == null)
            source = ClassSource.fromClassLoader();
        return source;
    }
}
