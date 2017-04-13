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

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

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
    private static volatile MethodAccessor enumConstantDirectoryMethod;
    private static volatile MethodAccessor entityPlayerHandleMethod;
    private static volatile MethodAccessor sendPacketMethod;
    private static volatile FieldAccessor playerConnectionField;
    private static volatile FieldAccessor networkManagerField;

    static {
    }

    private MinecraftReflection() {
        super();
    }

    public static String getMinecraftFullPackage() {
        if(MINECRAFT_FULL_PACKAGE == null)
            MINECRAFT_FULL_PACKAGE = "net.minecraft.server." + getCraftBukkitNMSVersion();
        return MINECRAFT_FULL_PACKAGE;
    }

    public static String getCraftBukkitFullPackage() {
        if(CRAFTBUKKIT_FULL_PACKAGE == null)
            CRAFTBUKKIT_FULL_PACKAGE = "org.bukkit.craftbukkit." + getCraftBukkitNMSVersion();
        return CRAFTBUKKIT_FULL_PACKAGE;
    }

    public static String getCraftBukkitNMSVersion() {
        if(CRAFTBUKKIT_NMS_VERSION == null)
            CRAFTBUKKIT_NMS_VERSION = MinecraftBukkitVersion.getCurrentVersion().getVersion();
        return CRAFTBUKKIT_NMS_VERSION;
    }

    public static Class<?> getClass(String className) {
        try {
            return MinecraftReflection.class.getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new MoonLakeException("Cannot find class " + className, e);
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

    public static Class<?> getMinecraftPlayerAbilitiesClass() {
        return getMinecraftClass("PlayerAbilities");
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
        return getMinecraftClass("Vec3D");
    }

    public static Class<?> getMinecraftWorldType() { // TODO FuzzyReflect
        return getMinecraftClass("WorldType");
    }

    public static Class<?> getIChatBaseComponentClass() {
        return getMinecraftClass("IChatBaseComponent");
    }

    public static Class<?> getChatMessageClass() {
        return getMinecraftClass("ChatMessage");
    }

    public static Class<?> getCraftChatMessageClass() {
        return getCraftBukkitClass("util.CraftChatMessage");
    }

    public static Class<?> getChatComponentTextClass() {
        return getMinecraftClass("ChatComponentText");
    }

    public static Class<?> getChatSerializerClass() {
        try {
            // v1_8_R2 以上的版本是这个类
            getMinecraftClass("IChatBaseComponent$ChatSerializer");
        } catch (Exception e) {
        }
        // 否则上面的异常则获取这个
        return getMinecraftClass("ChatSerializer");
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

    public static Object getEntityPlayer(Player player) {
        Validate.notNull(player, "The player object is null.");
        if(entityPlayerHandleMethod == null)
            entityPlayerHandleMethod = Accessors.getMethodAccessor(getCraftPlayerClass(), "getHandle");
        return entityPlayerHandleMethod.invoke(player);
    }

    public static Object getNetworkManager(MoonLakePlayer player) {
        Validate.notNull(player, "The player object is null.");
        return getNetworkManager(player.getBukkitPlayer());
    }

    public static Object getPlayerConnection(MoonLakePlayer player) {
        Validate.notNull(player, "The player object is null.");
        return getPlayerConnection(player.getBukkitPlayer());
    }

    public static Object getNetworkManager(Player player) {
        Validate.notNull(player, "The player object is null.");
        return getNetworkManager(getEntityPlayer(player));
    }

    public static Object getNetworkManager(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(networkManagerField == null)
            networkManagerField = Accessors.getFieldAccessor(getPlayerConnectionClass(), getNetworkManagerClass(), true);
        return networkManagerField.get(getPlayerConnection(nmsPlayer));
    }

    public static Object getPlayerConnection(Player player) {
        Object nmsPlayer = getEntityPlayer(player);
        return getPlayerConnection(nmsPlayer);
    }

    public static Object getPlayerConnection(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(playerConnectionField == null)
            playerConnectionField = Accessors.getFieldAccessor(getMinecraftEntityPlayerClass(), getPlayerConnectionClass(), true);
        return playerConnectionField.get(nmsPlayer);
    }

    public static void sendPacket(Player[] players, Object nmsPacket) {
        Validate.notNull(players, "The players object is null.");
        for(Player player : players)
            sendPacket(getEntityPlayer(player), nmsPacket);
    }

    public static void sendPacket(Player player, Object nmsPacket) {
        Validate.notNull(player, "The player object is null.");
        sendPacket(getEntityPlayer(player), nmsPacket);
    }

    public static void sendPacket(Object nmsPlayer, Object nmsPacket) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        Validate.notNull(nmsPacket, "The nms packet object is null.");
        if(sendPacketMethod == null)
            sendPacketMethod = Accessors.getMethodAccessor(getPlayerConnectionClass(), "sendPacket", getPacketClass());
        sendPacketMethod.invoke(getPlayerConnection(nmsPlayer), nmsPacket);
    }

    public static boolean is(Class<?> clazz, Object obj) {
        return !(clazz == null || obj == null) && clazz.isAssignableFrom(obj.getClass());
    }

    public static boolean isEntityPlayer(Object obj) {
        return is(getMinecraftEntityPlayerClass(), obj);
    }

    public static boolean isPacket(Object obj) {
        return is(getPacketClass(), obj);
    }

    public static boolean isBlockPosition(Object obj) {
        return is(getMinecraftBlockPositionClass(), obj);
    }

    @SuppressWarnings("unchecked")
    public static Object enumValueOf(Class<? extends Enum<?>> enumClass, String name) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(name, "The name object is null.");
        if(enumConstantDirectoryMethod == null)
            enumConstantDirectoryMethod = Accessors.getMethodAccessor(Class.class, "enumConstantDirectoryMethod");
        Object obj = ((Map<String, ?>) enumConstantDirectoryMethod.invoke(enumClass)).get(name);
        if(obj != null)
            return obj;
        throw new IllegalArgumentException("No enum constant " + enumClass.getCanonicalName() + "." + name);
    }

    public static <T extends Enum<T>> T enumValueOfType(Class<T> enumClass, String name) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(name, "The name object is null.");
        return Enum.valueOf(enumClass, name);
    }

    public static Class<?> getArrayClass(Class<?> type) {
        Validate.notNull(type, "The class type object is null.");
        return Array.newInstance(type, 0).getClass();
    }

    public static ClassSource getClassSource() {
        if(source == null)
            source = ClassSource.fromClassLoader();
        return source;
    }
}
