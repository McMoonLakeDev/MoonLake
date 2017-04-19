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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTReflect;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.wrapper.BlockPosition;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.particle.ParticleEffect;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
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
    private static volatile ConstructorAccessor<?> itemStackConstructor;
    private static volatile ConstructorAccessor<?> chatMessageConstructor;
    private static volatile MethodAccessor nbtCompressedStreamToolsWriteMethod;
    private static volatile MethodAccessor nbtCompressedStreamToolsReadMethod;
    private static volatile MethodAccessor entityLivingGetAttributeInstanceMethod;
    private static volatile MethodAccessor craftItemStackAsBukkitCopyMethod;
    private static volatile MethodAccessor craftItemStackAsCraftMirrorMethod;
    private static volatile MethodAccessor craftItemStackAsNMSCopyMethod;
    private static volatile MethodAccessor attributeInstanceGetValueMethod;
    private static volatile MethodAccessor attributeInstanceSetValueMethod;
    private static volatile MethodAccessor enumGamemodeGetByIdMethod;
    private static volatile MethodAccessor enumConstantDirectoryMethod;
    private static volatile MethodAccessor mojangsonParserParserMethod;
    private static volatile MethodAccessor entityHumanGetProfileMethod;
    private static volatile MethodAccessor worldTypeGetByTypeMethod;
    private static volatile MethodAccessor entityGetBukkitEntityMethod;
    private static volatile MethodAccessor enumParticleGetByIdMethod;
    private static volatile MethodAccessor itemStackCraftStackMethod;
    private static volatile MethodAccessor entityPlayerHandleMethod;
    private static volatile MethodAccessor worldServerHandleMethod;
    private static volatile MethodAccessor worldGetTileEntityMethod;
    private static volatile MethodAccessor itemCooldownHasMethod;
    private static volatile MethodAccessor itemCooldownGetMethod;
    private static volatile MethodAccessor itemCooldownSetMethod;
    private static volatile MethodAccessor worldAddEntityMethod;
    private static volatile MethodAccessor itemStackSaveMethod;
    private static volatile MethodAccessor entityHandleMethod;
    private static volatile MethodAccessor sendPacketMethod;
    private static volatile MethodAccessor itemGetByIdMethod;
    private static volatile FieldAccessor playerConnectionNetworkManagerField;
    private static volatile FieldAccessor entityPlayerPlayerConnectionField;
    private static volatile FieldAccessor entityHumanItemCooldownField;
    private static volatile FieldAccessor entityPlayerLocaleField;
    private static volatile FieldAccessor entityPlayerPingField;

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

    public static Class<?> getMinecraftClassLater(MinecraftVersion mcVer, String className) {
        if(MoonLakeAPI.currentMCVersion().isLater(mcVer))
            return getMinecraftClass(className);
        return null;
    }

    public static Class<?> getMinecraftClassOrLater(MinecraftVersion mcVer, String className) {
        if(MoonLakeAPI.currentMCVersion().isOrLater(mcVer))
            return getMinecraftClass(className);
        return null;
    }

    public static Class<?> getMinecraftClassBuilder(SingleParamBuilder<Class<?>, MinecraftBukkitVersion> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build(MoonLakeAPI.currentBukkitVersion());
    }

    public static Class<?> getMinecraftClassLater(MinecraftBukkitVersion bukkitVer, String className) {
        if(MoonLakeAPI.currentBukkitVersion().isLater(bukkitVer))
            return getMinecraftClass(className);
        return null;
    }

    public static Class<?> getMinecraftClassOrLater(MinecraftBukkitVersion bukkitVer, String className) {
        if(MoonLakeAPI.currentBukkitVersion().isOrLater(bukkitVer))
            return getMinecraftClass(className);
        return null;
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

    public static Class<?> getMinecraftItemClass() {
        return getMinecraftClass("Item");
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

    public static Class<?> getMinecraftEntityLivingClass() {
        return getMinecraftClass("EntityLiving");
    }

    public static Class<?> getMinecraftEntityHumanClass() {
        return getMinecraftEntityPlayerClass().getSuperclass();
    }

    public static Class<?> getMinecraftEntityPlayerClass() {
        return getMinecraftClass("EntityPlayer");
    }

    public static Class<?> getMinecraftEntityTypesClass() {
        return getMinecraftClass("EntityTypes");
    }

    public static Class<?> getMinecraftEntityInsentientClass() {
        return getMinecraftClass("EntityInsentient");
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

    public static Class<?> getMinecraftIAttributeClass() {
        return getMinecraftClass("IAttribute");
    }

    public static Class<?> getMinecraftAttributeInstanceClass() {
        return getMinecraftClass("AttributeInstance");
    }

    public static Class<?> getMinecraftGenericAttributesClass() {
        return getMinecraftClass("GenericAttributes");
    }

    public static Class<?> getMinecraftItemCooldownClass() throws IllegalBukkitVersionException {
        if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
            throw new IllegalBukkitVersionException("The item cool down not support 1.8 or old bukkit version.");
        return getMinecraftClass("ItemCooldown");
    }

    public static Class<?> getMinecraftEnumParticleClass() {
        return getMinecraftClass("EnumParticle");
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
            return getMinecraftClass("IChatBaseComponent$ChatSerializer");
        } catch (Exception e) {
        }
        // 否则上面的异常则获取这个
        return getMinecraftClass("ChatSerializer");
    }

    public static Class<?> getEnumGamemodeClass() {
        try {
            return getMinecraftClass("WorldSettings$EnumGamemode");
        } catch (Exception e) {
        }
        return getMinecraftClass("EnumGamemode");
    }

    public static Class<?> getNBTBaseClass() {
        return getMinecraftClass("NBTBase");
    }

    public static Class<?> getNBTTagByteClass() {
        return getMinecraftClass("NBTTagByte");
    }

    public static Class<?> getNBTTagShortClass() {
        return getMinecraftClass("NBTTagShort");
    }

    public static Class<?> getNBTTagIntClass() {
        return getMinecraftClass("NBTTagInt");
    }

    public static Class<?> getNBTTagLongClass() {
        return getMinecraftClass("NBTTagLong");
    }

    public static Class<?> getNBTTagFloatClass() {
        return getMinecraftClass("NBTTagFloat");
    }

    public static Class<?> getNBTTagDoubleClass() {
        return getMinecraftClass("NBTTagDouble");
    }

    public static Class<?> getNBTTagStringClass() {
        return getMinecraftClass("NBTTagString");
    }

    public static Class<?> getNBTTagByteArrayClass() {
        return getMinecraftClass("NBTTagByteArray");
    }

    public static Class<?> getNBTTagIntArrayClass() {
        return getMinecraftClass("NBTTagIntArray");
    }

    public static Class<?> getNBTTagListClass() {
        return getMinecraftClass("NBTTagList");
    }

    public static Class<?> getNBTTagCompoundClass() {
        return getMinecraftClass("NBTTagCompound");
    }

    public static Class<?> getNBTCompressedStreamToolsClass() { // TODO FuzzyReflect
        return getMinecraftClass("NBTCompressedStreamTools");
    }

    public static Class<?> getNBTReadLimiterClass() {
        return getMinecraftClass("NBTReadLimiter");
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

    public static Class<?> getPathfinderGoalSelectorClass() {
        return getMinecraftClass("PathfinderGoalSelector");
    }

    public static Class<?> getUnsafeListClass() {
        return getCraftBukkitClass("util.UnsafeList");
    }

    @Nullable
    public static <T extends PacketPlayOutBukkit> Class<?> getPacketClassFromPacketWrapper(Class<T> packetWrapper) {
        try {
            return getMinecraftClass(packetWrapper.getSimpleName());
        } catch (Exception e) {
        }
        try {
            return (Class<?>) Accessors.getFieldAccessor(packetWrapper, Class.class, true).get(null);
        } catch (Exception e) {
        }
        return null;
    }

    @Nullable
    public static <T extends PacketPlayOutBukkit> Class<?> getPacketClassFromPacketWrapper(T packetWrapper) {
        return packetWrapper.getPacketClass();
    }

    @SuppressWarnings("deprecation")
    public static Object itemGetByMaterial(Material material) {
        Validate.notNull(material, "The material object is null.");
        return itemGetById(material.getId());
    }

    public static Object enumParticleGetByParticle(ParticleEffect particle) {
        Validate.notNull(particle, "The particle object is null.");
        return enumParticleGetById(particle.getId());
    }

    public static Object enumParticleGetById(int id) {
        if(enumParticleGetByIdMethod == null)
            enumParticleGetByIdMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getMinecraftEnumParticleClass(), true).getMethodByParameters("a", getMinecraftEnumParticleClass(), new Class[] { int.class }));
        return enumParticleGetByIdMethod.invoke(null, id);
    }

    public static Object itemGetById(int id) {
        if(itemGetByIdMethod == null)
            itemGetByIdMethod = Accessors.getMethodAccessor(getMinecraftItemClass(), "getById", int.class);
        return itemGetByIdMethod.invoke(null, id);
    }

    public static boolean hasItemCooldown(HumanEntity humanEntity, Material type) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        return hasItemCooldown(entityHuman, type);
    }

    public static boolean hasItemCooldown(Object entityHuman, Material type) {
        Validate.notNull(type, "The item type object is null.");
        Object item = itemGetByMaterial(type);
        Object itemCooldown = getEntityHumanItemCooldown(entityHuman); // 如果不支持直接抛出异常
        if(itemCooldownHasMethod == null)
            itemCooldownHasMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getMinecraftItemCooldownClass(), true).getMethodByParameters("a", boolean.class, new Class[] { getMinecraftItemClass() }));
        return (boolean) itemCooldownHasMethod.invoke(itemCooldown, item);
    }

    public static float getItemCooldown(HumanEntity humanEntity, Material type) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        return getItemCooldown(entityHuman, type);
    }

    public static float getItemCooldown(Object entityHuman, Material type) {
        Validate.notNull(type, "The item type object is null.");
        Object item = itemGetByMaterial(type);
        Object itemCooldown = getEntityHumanItemCooldown(entityHuman); // 如果不支持直接抛出异常
        if(itemCooldownGetMethod == null)
            itemCooldownGetMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getMinecraftItemCooldownClass(), true).getMethodByParameters("a", float.class, new Class[] { getMinecraftItemClass(), float.class }));
        return (float) itemCooldownGetMethod.invoke(itemCooldown, item, 0.0f);
    }

    public static void setItemCooldown(HumanEntity humanEntity, Material type, int tick) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        setItemCooldown(entityHuman, type, tick);
    }

    public static void setItemCooldown(Object entityHuman, Material type, int tick) {
        Validate.notNull(type, "The item type object is null.");
        Object item = itemGetByMaterial(type);
        Object itemCooldown = getEntityHumanItemCooldown(entityHuman); // 如果不支持直接抛出异常
        if(itemCooldownSetMethod == null)
            itemCooldownSetMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getMinecraftItemCooldownClass(), true).getMethodByParameters("a", Void.class, new Class[] { getMinecraftItemClass(), int.class }));
        itemCooldownSetMethod.invoke(itemCooldown, item, tick);
    }

    public static Object getEntityHumanItemCooldown(HumanEntity humanEntity) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        return getEntityHumanItemCooldown(entityHuman);
    }

    public static Object getEntityHumanItemCooldown(Object entityHuman) {
        Validate.notNull(entityHuman, "The entity human object is null.");
        Validate.isTrue(MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9), "The item cool down not support 1.8 or old bukkit version.");
        if(entityHumanItemCooldownField == null)
            entityHumanItemCooldownField = Accessors.getFieldAccessor(getMinecraftEntityHumanClass(), getMinecraftItemCooldownClass(), true);
        return entityHumanItemCooldownField.get(entityHuman);
    }

    public static int getPlayerPing(Player player) {
        Validate.notNull(player, "The player object is null.");
        return getPlayerPing(getEntityPlayer(player));
    }

    public static String getPlayerLocale(Player player) {
        Validate.notNull(player, "The player object is null.");
        return getPlayerLocale(getEntityPlayer(player));
    }

    public static int getPlayerPing(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(entityPlayerPingField == null)
            entityPlayerPingField = Accessors.getFieldAccessor(getMinecraftEntityPlayerClass(), "ping", true);
        return (int) entityPlayerPingField.get(nmsPlayer);
    }

    public static String getPlayerLocale(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(entityPlayerLocaleField == null)
            entityPlayerLocaleField = Accessors.getFieldAccessor(getMinecraftEntityPlayerClass(), "locale", true);
        return (String) entityPlayerLocaleField.get(nmsPlayer);
    }

    public static GameProfile getEntityHumanProfile(HumanEntity humanEntity) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        return getEntityHumanProfile(entityHuman);
    }

    public static GameProfile getEntityHumanProfile(Object entityHuman) {
        Validate.notNull(entityHuman, "Th entity human object is null.");
        if(entityHumanGetProfileMethod == null)
            entityHumanGetProfileMethod = Accessors.getMethodAccessor(getMinecraftEntityHumanClass(), "getProfile");
        return (GameProfile) entityHumanGetProfileMethod.invoke(entityHuman);
    }

    public static void setAttributeValue(LivingEntity livingEntity, AttributeType attributeType, double value) {
        Validate.notNull(livingEntity, "The living entity object is null.");
        Validate.notNull(attributeType, "The attribute type object is null.");
        attributeType.isSupported(); // 检测属性类型是否支持服务端版本
        double finalValue = attributeType.getSafeValue(value);
        Object attributeInstance =  getAttributeInstance(getEntity(livingEntity), attributeType.getIAttributeField().get(null));
        if(attributeInstanceSetValueMethod == null)
            attributeInstanceSetValueMethod = Accessors.getMethodAccessor(getMinecraftAttributeInstanceClass(), "setValue", double.class);
        attributeInstanceSetValueMethod.invoke(attributeInstance, finalValue);
    }

    public static double getAttributeValue(LivingEntity livingEntity, AttributeType attributeType) {
        Validate.notNull(livingEntity, "The living entity object is null.");
        Validate.notNull(attributeType, "The attribute type object is null.");
        attributeType.isSupported(); // 检测属性类型是否支持服务端版本
        Object attributeInstance =  getAttributeInstance(getEntity(livingEntity), attributeType.getIAttributeField().get(null));
        if(attributeInstanceGetValueMethod == null)
            attributeInstanceGetValueMethod = Accessors.getMethodAccessor(getMinecraftAttributeInstanceClass(), "getValue");
        return (double) attributeInstanceGetValueMethod.invoke(attributeInstance);
    }

    public static Object getAttributeInstance(Object entityLiving, Object iAttribute) {
        Validate.notNull(entityLiving, "The entity living object is null.");
        Validate.notNull(iAttribute, "The attribute object is null.");
        if(entityLivingGetAttributeInstanceMethod == null)
            entityLivingGetAttributeInstanceMethod = Accessors.getMethodAccessor(getMinecraftEntityLivingClass(), "getAttributeInstance", getMinecraftIAttributeClass());
        return entityLivingGetAttributeInstanceMethod.invoke(entityLiving, iAttribute);
    }

    public static Object getTileEntity(Location location) {
        Validate.notNull(location, "The location object is null.");
        Object world = getWorldServer(location.getWorld());
        Object blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return getTileEntity(world, blockPosition);
    }

    public static Object getTileEntity(Object world, Object blockPosition) {
        Validate.notNull(world, "The world object is null.");
        Validate.notNull(blockPosition, "The block position object is null.");
        if(worldGetTileEntityMethod == null)
            worldGetTileEntityMethod = Accessors.getMethodAccessor(getMinecraftWorldClass(), "getTileEntity", getMinecraftBlockPositionClass());
        return worldGetTileEntityMethod.invoke(world, blockPosition);
    }

    public static boolean addEntity(Object world, Object entity) {
        return addEntity(world, entity, null);
    }

    public static boolean addEntity(Object world, Object entity, CreatureSpawnEvent.SpawnReason spawnReason) {
        Validate.notNull(world, "The world object is null.");
        Validate.notNull(entity, "The entity object is null.");
        if(worldAddEntityMethod == null)
            worldAddEntityMethod = Accessors.getMethodAccessor(getMinecraftEntityClass(), "addEntity", getMinecraftEntityClass(), CreatureSpawnEvent.SpawnReason.class);
        return (boolean) worldAddEntityMethod.invoke(world, entity, (spawnReason == null ? CreatureSpawnEvent.SpawnReason.DEFAULT : spawnReason));
    }

    public static Entity getBukkitEntity(Object entity) {
        Validate.notNull(entity, "The entity object is null.");
        if(entityGetBukkitEntityMethod == null)
            entityGetBukkitEntityMethod = Accessors.getMethodAccessor(getMinecraftEntityClass(), "getBukkitEntity");
        return (Entity) entityGetBukkitEntityMethod.invoke(entity);
    }

    public static Object worldTypeGetByType(String type) {
        Validate.notNull(type, "The type object is null.");
        if(worldTypeGetByTypeMethod == null)
            worldTypeGetByTypeMethod = Accessors.getMethodAccessor(getMinecraftWorldType(), "getType", String.class);
        return worldTypeGetByTypeMethod.invoke(null, type);
    }

    public static Object enumGamemodeGetById(int id) {
        if(enumGamemodeGetByIdMethod == null)
            enumGamemodeGetByIdMethod = Accessors.getMethodAccessor(getEnumGamemodeClass(), "getById", int.class);
        return enumGamemodeGetByIdMethod.invoke(null, id);
    }

    public static Object getChatMessage(String text, Object... params) {
        Validate.notNull(text, "The text object is null.");
        if(chatMessageConstructor == null)
            chatMessageConstructor = Accessors.getConstructorAccessor(getChatMessageClass(), String.class, Object[].class);
        return chatMessageConstructor.invoke(text, params);
    }

    public static Object getIChatBaseComponentFromJson(String json) {
        return ChatSerializer.fromJson(json);
    }

    public static Object getIChatBaseComponentFromString(String string) {
        Validate.notNull(string, "The string object is null.");
        return getIChatBaseComponentFromJson("{\"text\":\"" + string + "\"}");
    }

    public static Object newNBTTagCompound() {
        return NBTTagReflection.getNBTTagCompoundConstructor().invoke();
    }

    public static Object parserGsonNBTTag(String nbtTag) {
        Validate.notNull(nbtTag, "The nbt tag object is null.");
        if(mojangsonParserParserMethod == null)
            mojangsonParserParserMethod = Accessors.getMethodAccessor(getMinecraftClass("MojangsonParser"), "parse", String.class);
        return mojangsonParserParserMethod.invoke(null, nbtTag);
    }

    public static NBTCompound readInputStreamToNBTCompound(InputStream is) {
        Object handle = readInputStreamToNBT(is);
        return handle == null ? null : NBTReflect.fromNBTCompound(handle);
    }

    public static Object readInputStreamToNBT(InputStream is) {
        Validate.notNull(is, "The input stream object is null.");
        if(nbtCompressedStreamToolsReadMethod == null) {
            nbtCompressedStreamToolsReadMethod = Accessors.getMethodAccessorOrNull(getNBTCompressedStreamToolsClass(), "a", InputStream.class);
            if(nbtCompressedStreamToolsReadMethod == null)
                nbtCompressedStreamToolsReadMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getNBTCompressedStreamToolsClass(), true).getMethodByParameters("a", getNBTTagCompoundClass(), new Class[] { InputStream.class }));
        }
        return nbtCompressedStreamToolsReadMethod.invoke(null, is);
    }

    public static void writeOutputStreamFromNBTCompound(NBTCompound nbtCompound, OutputStream os) {
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        writeOutputStreamFromNBT(nbtCompound.getHandleCopy(), os);
    }

    public static void writeOutputStreamFromNBT(Object nbtCompound, OutputStream os) {
        Validate.notNull(os, "The output stream object is null.");
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        if(nbtCompressedStreamToolsWriteMethod == null) {
            nbtCompressedStreamToolsWriteMethod = Accessors.getMethodAccessor(getNBTCompressedStreamToolsClass(), "a", getNBTTagCompoundClass(), OutputStream.class);
            if(nbtCompressedStreamToolsWriteMethod == null)
                nbtCompressedStreamToolsWriteMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getNBTCompressedStreamToolsClass(), true).getMethodByParameters("a", Void.class, new Class[] { getNBTTagCompoundClass(), OutputStream.class }));
        }
        nbtCompressedStreamToolsWriteMethod.invoke(null, nbtCompound, os);
    }

    public static void saveItemStack(ItemStack itemStack, NBTCompound nbtCompound) {
        Validate.notNull(itemStack, "The itemstack object is null");
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        Object nmsItemStack = asNMSCopy(itemStack);
        saveItemStack(nmsItemStack, nbtCompound.getHandle()); // getHandle 返回参数 NBTCompound 的 handle 对象而不是 copy 新实例
    }

    public static void saveItemStack(Object itemStack, Object nbtCompound) {
        Validate.notNull(itemStack, "The itemstack object is null");
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        if(itemStackSaveMethod == null)
            itemStackSaveMethod = Accessors.getMethodAccessor(getMinecraftItemStackClass(), "save", getNBTTagCompoundClass());
        itemStackSaveMethod.invoke(itemStack, nbtCompound);
    }

    public static ItemStack createStack(NBTCompound nbtCompound) {
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        Object itemStack = createStack(nbtCompound.getHandleCopy()); // getHandleCopy 返回参数 NBTCompound 的 handle 对象新实例而不是同对象
        return itemStack == null ? null : asBukkitCopy(itemStack);
    }

    public static Object createStack(Object nbtCompound) {
        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        if(MoonLakeAPI.currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_11_R1)) {
            // 1.11 的版本中底层 ItemStack 类中没有 createStack 函数
            if(itemStackConstructor == null)
                itemStackConstructor = Accessors.getConstructorAccessor(getMinecraftItemStackClass(), getNBTTagCompoundClass());
            return itemStackConstructor.invoke(nbtCompound);

        } else {
            // 1.10 以及旧版本就调用 createStack 函数进行创建就行了
            if(itemStackCraftStackMethod == null)
                itemStackCraftStackMethod = Accessors.getMethodAccessor(getMinecraftItemStackClass(), "createStack", getNBTTagCompoundClass());
            return itemStackCraftStackMethod.invoke(null, nbtCompound);
        }
    }

    public static ItemStack asBukkitCopy(Object itemStack) {
        Validate.notNull(itemStack, "The itemstack object is null.");
        if(craftItemStackAsBukkitCopyMethod == null)
            craftItemStackAsBukkitCopyMethod = Accessors.getMethodAccessor(getCraftItemStackClass(), "asBukkitCopy", getMinecraftItemStackClass());
        return (ItemStack) craftItemStackAsBukkitCopyMethod.invoke(null, itemStack);
    }

    public static Object asNMSCopy(ItemStack itemStack) {
        Validate.notNull(itemStack, "The itemstack object is null.");
        if(craftItemStackAsNMSCopyMethod == null)
            craftItemStackAsNMSCopyMethod = Accessors.getMethodAccessor(getCraftItemStackClass(), "asNMSCopy", ItemStack.class);
        return craftItemStackAsNMSCopyMethod.invoke(null, itemStack);
    }

    public static ItemStack asCraftMirror(Object itemStack) {
        Validate.notNull(itemStack, "The itemstack object is null.");
        if(craftItemStackAsCraftMirrorMethod == null)
            craftItemStackAsCraftMirrorMethod = Accessors.getMethodAccessor(getCraftItemStackClass(), "asCraftMirror", getMinecraftItemStackClass());
        return (ItemStack) craftItemStackAsCraftMirrorMethod.invoke(null, itemStack);
    }

    public static Object getWorldServer(World world) {
        Validate.notNull(world, "The player object is null.");
        if(worldServerHandleMethod == null)
            worldServerHandleMethod = Accessors.getMethodAccessor(getCraftWorldClass(), "getHandle");
        return worldServerHandleMethod.invoke(world);
    }

    public static Object getEntity(Entity entity) {
        Validate.notNull(entity, "The entity object is null.");
        if(entityHandleMethod == null)
            entityHandleMethod = Accessors.getMethodAccessor(getCraftEntityClass(), "getHandle");
        return entityHandleMethod.invoke(entity);
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
        if(playerConnectionNetworkManagerField == null)
            playerConnectionNetworkManagerField = Accessors.getFieldAccessor(getPlayerConnectionClass(), getNetworkManagerClass(), true);
        return playerConnectionNetworkManagerField.get(getPlayerConnection(nmsPlayer));
    }

    public static Object getPlayerConnection(Player player) {
        Object nmsPlayer = getEntityPlayer(player);
        return getPlayerConnection(nmsPlayer);
    }

    public static Object getPlayerConnection(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(entityPlayerPlayerConnectionField == null)
            entityPlayerPlayerConnectionField = Accessors.getFieldAccessor(getMinecraftEntityPlayerClass(), getPlayerConnectionClass(), true);
        return entityPlayerPlayerConnectionField.get(nmsPlayer);
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
    public static Object enumValueOfClass(Class<?> enumClass, String name) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(name, "The name object is null.");
        Validate.isTrue(enumClass.isEnum(), "The enum class not is enum type.");
        return enumValueOfEnumClass((Class<? extends Enum<?>>) enumClass, name);
    }

    @SuppressWarnings("unchecked")
    public static Object enumValueOfEnumClass(Class<? extends Enum<?>> enumClass, String name) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(name, "The name object is null.");
        if(enumConstantDirectoryMethod == null)
            enumConstantDirectoryMethod = Accessors.getMethodAccessor(Class.class, "enumConstantDirectory");
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
