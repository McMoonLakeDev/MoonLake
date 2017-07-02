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
import com.minecraft.moonlake.MoonLakePluginDebug;
import com.minecraft.moonlake.api.Valuable;
import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.chat.adapter.ChatAdapter;
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
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <h1>MinecraftReflection</h1>
 * Minecraft 底层反射效用类 (函数暂时不提供doc文档)
 *
 * @version 1.0.1
 * @author Month_Light
 */
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
    private static volatile MethodAccessor playerConnectionSendPacketMethod;
    private static volatile MethodAccessor craftItemStackAsBukkitCopyMethod;
    private static volatile MethodAccessor craftItemStackAsCraftMirrorMethod;
    private static volatile MethodAccessor craftItemStackAsNMSCopyMethod;
    private static volatile MethodAccessor chatMessageTypeFormByteMethod;
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
    private static volatile MethodAccessor iAttributeGetNameMethod;
    private static volatile MethodAccessor worldServerHandleMethod;
    private static volatile MethodAccessor worldGetTileEntityMethod;
    private static volatile MethodAccessor itemCooldownHasMethod;
    private static volatile MethodAccessor itemCooldownSetMethod;
    private static volatile MethodAccessor worldAddEntityMethod;
    private static volatile MethodAccessor itemStackSaveMethod;
    private static volatile MethodAccessor entityHandleMethod;
    private static volatile MethodAccessor itemGetByIdMethod;
    private static volatile MethodAccessor enumOrdinalMethod;
    private static volatile FieldAccessor packetPlayInCustomPayloadPacketDataSerializerField;
    private static volatile FieldAccessor packetPlayInCustomPayloadChannelField;
    private static volatile FieldAccessor playerConnectionNetworkManagerChannelField;
    private static volatile FieldAccessor serverConnectionNetworkManagerListField;
    private static volatile FieldAccessor playerConnectionNetworkManagerField;
    private static volatile FieldAccessor minecraftServerServerConnectionField;
    private static volatile FieldAccessor entityPlayerPlayerConnectionField;
    private static volatile FieldAccessor entityHumanItemCooldownField;
    private static volatile FieldAccessor craftMetaSkullProfileField;
    private static volatile FieldAccessor itemCooldownInfoTickField;
    private static volatile FieldAccessor itemCooldownMapField;
    private static volatile FieldAccessor itemCooldownTickField;
    private static volatile FieldAccessor craftServerConsoleField;
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

    public static Class<?> getCraftMetaSkullClass() {
        return getCraftBukkitClass("inventory.CraftMetaSkull");
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

    public static Class<?> getMinecraftItemCooldownInfoClass() throws IllegalBukkitVersionException {
        if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
            throw new IllegalBukkitVersionException("The item cool down not support 1.8 or old bukkit version.");
        return getMinecraftClass("ItemCooldown$Info");
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

    public static Class<?> getChatMessageTypeClass() {
        // 这个类只有 mc1.12+ 版本才有
        return getMinecraftClass("ChatMessageType");
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

    public static Class<?> getServerConnectionClass() {
        return getMinecraftClass("ServerConnection");
    }

    public static Class<?> getMinecraftServerClass() {
        return getMinecraftClass("MinecraftServer");
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

    public static FieldAccessor getPacketPlayInCustomPayloadChannelField() {
        if(packetPlayInCustomPayloadChannelField == null)
            packetPlayInCustomPayloadChannelField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(getMinecraftClass("PacketPlayInCustomPayload"), true).getFieldByType("channel", String.class));
        return packetPlayInCustomPayloadChannelField;
    }

    public static FieldAccessor getPacketPlayInCustomPayloadPacketDataSerializerField() {
        if(packetPlayInCustomPayloadPacketDataSerializerField == null)
            packetPlayInCustomPayloadPacketDataSerializerField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(getMinecraftClass("PacketPlayInCustomPayload"), true).getFieldByType("data", getPacketDataSerializerClass()));
        return packetPlayInCustomPayloadPacketDataSerializerField;
    }

    public static FieldAccessor getCraftMetaSkullProfileField() {
        if(craftMetaSkullProfileField == null)
            craftMetaSkullProfileField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(getCraftMetaSkullClass(), true).getFieldByType("profile", GameProfile.class));
        return craftMetaSkullProfileField;
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

    public static int getItemCooldown(HumanEntity humanEntity, Material type) {
        Validate.notNull(humanEntity, "The human entity object is null.");
        Object entityHuman = getEntity(humanEntity);
        return getItemCooldown(entityHuman, type);
    }

    @SuppressWarnings("unchecked")
    public static int getItemCooldown(Object entityHuman, Material type) {
        Validate.notNull(type, "The item type object is null.");
        Object item = itemGetByMaterial(type);
        Object itemCooldown = getEntityHumanItemCooldown(entityHuman); // 如果不支持直接抛出异常
        if(itemCooldownTickField == null)
            itemCooldownTickField = Accessors.getFieldAccessor(getMinecraftItemCooldownClass(), int.class, true);
        if(itemCooldownMapField == null)
            itemCooldownMapField = Accessors.getFieldAccessor(getMinecraftItemCooldownClass(), Map.class, true);
        if(itemCooldownInfoTickField == null)
            itemCooldownInfoTickField = Accessors.getFieldAccessor(getMinecraftItemCooldownInfoClass(), 1, true); // 第二个字段 b
        // 算法摘自 https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/commits/be9ef980b9aa272acf298a337da8157c6a620e95#src/main/java/org/bukkit/craftbukkit/entity/CraftHumanEntity.java
        Object info = ((Map) itemCooldownMapField.get(itemCooldown)).get(item);
        if(info == null)
            return 0;
        int infoTick = (int) itemCooldownInfoTickField.get(info);
        int tick = (int) itemCooldownTickField.get(itemCooldown);
        return Math.max(0, infoTick - tick);
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

    public static Object getGenericAttributes(AttributeType type) {
        Validate.notNull(type, "The attribute type object is null.");
        Class<?> iAttributeClass = getMinecraftIAttributeClass();
        if(iAttributeGetNameMethod == null)
            iAttributeGetNameMethod = Accessors.getMethodAccessor(iAttributeClass, "getName");
        FieldAccessor[] array = Accessors.getFieldAccessorArray(getMinecraftGenericAttributesClass(), iAttributeClass, true);
        for(FieldAccessor field : array) {
            Object iAttribute = field.get(null);
            if(type.getName().equals(iAttributeGetNameMethod.invoke(iAttribute)))
                return iAttribute;
        }
        throw new UnsupportedOperationException("The attribute type not support bukkit version: " + type.getName());
    }

    public static void setAttributeValue(LivingEntity livingEntity, AttributeType attributeType, double value) {
        Validate.notNull(livingEntity, "The living entity object is null.");
        Validate.notNull(attributeType, "The attribute type object is null.");
        attributeType.isSupported(); // 检测属性类型是否支持服务端版本
        double finalValue = attributeType.getSafeValue(value);
        Object attributeInstance =  getAttributeInstance(getEntity(livingEntity), attributeType.getIAttribute());
        if(attributeInstance == null) // 实体不存在这个属性
            throw new IllegalStateException("The living entity not found attribute type: " + attributeType);
        if(attributeInstanceSetValueMethod == null)
            attributeInstanceSetValueMethod = Accessors.getMethodAccessor(getMinecraftAttributeInstanceClass(), "setValue", double.class);
        attributeInstanceSetValueMethod.invoke(attributeInstance, finalValue);
    }

    public static double getAttributeValue(LivingEntity livingEntity, AttributeType attributeType) {
        Validate.notNull(livingEntity, "The living entity object is null.");
        Validate.notNull(attributeType, "The attribute type object is null.");
        attributeType.isSupported(); // 检测属性类型是否支持服务端版本
        Object attributeInstance =  getAttributeInstance(getEntity(livingEntity), attributeType.getIAttribute());
        if(attributeInstance == null) // 实体不存在这个属性
            throw new IllegalStateException("The living entity not found attribute type: " + attributeType);
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
        BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return getTileEntity(world, blockPosition.asNMS());
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
            worldAddEntityMethod = Accessors.getMethodAccessor(getMinecraftWorldClass(), "addEntity", getMinecraftEntityClass(), CreatureSpawnEvent.SpawnReason.class);
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

    public static ChatAdapter getJsonAdapter(String json) {
        return ChatSerializer.jsonAdapter(json);
    }

    public static ChatAdapter getObjectAdapter(Object obj) {
        return ChatSerializer.objectAdapter(obj);
    }

    public static Object getIChatBaseComponentFromJson(String json) {
        return ChatSerializer.iCBCFromJson(json);
    }

    public static Object getIChatBaseComponentFromString(String string) {
        Validate.notNull(string, "The string object is null.");
        return getIChatBaseComponentFromJson("{\"text\":\"" + string + "\"}");
    }

    public static Object chatMessageTypeFromByte(byte type) {
        // 这个函数 mc1.12+ 版本才有
        if(chatMessageTypeFormByteMethod == null)
            chatMessageTypeFormByteMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getChatMessageTypeClass(), true).getMethodByParameters("a", getChatMessageTypeClass(), new Class[] { byte.class}));
        return chatMessageTypeFormByteMethod.invoke(null, type);
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
            nbtCompressedStreamToolsWriteMethod = Accessors.getMethodAccessorOrNull(getNBTCompressedStreamToolsClass(), "a", getNBTTagCompoundClass(), OutputStream.class);
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
        if(itemStackSaveMethod == null) {
            itemStackSaveMethod = Accessors.getMethodAccessorOrNull(getMinecraftItemStackClass(), "save", getNBTTagCompoundClass());
            if(itemStackSaveMethod == null)
                itemStackSaveMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(getMinecraftItemStackClass(), true).getMethodByParameters("save", getNBTTagCompoundClass(), new Class[] { getNBTTagCompoundClass() }));
        }
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
        return getEntity(player);
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

    public static Object getMinecraftServer() {
        if(craftServerConsoleField == null)
            craftServerConsoleField = Accessors.getFieldAccessor(Bukkit.getServer().getClass(), getMinecraftServerClass(), true);
        return craftServerConsoleField.get(Bukkit.getServer());
    }

    public static Object getMinecraftServerConnection() {
        if(minecraftServerServerConnectionField == null)
            minecraftServerServerConnectionField = Accessors.getFieldAccessor(getMinecraftServerClass(), getServerConnectionClass(), true);
        return minecraftServerServerConnectionField.get(getMinecraftServer());
    }

    public static Channel getNetworkManagerChannel(Player player) {
        Validate.notNull(player, "The player object is null.");
        return getNetworkManagerChannel(getEntityPlayer(player));
    }

    public static Channel getNetworkManagerChannel(Object nmsPlayer) {
        Validate.notNull(nmsPlayer, "The nms player object is null.");
        if(playerConnectionNetworkManagerChannelField == null)
            playerConnectionNetworkManagerChannelField = Accessors.getFieldAccessor(getNetworkManagerClass(), Channel.class, true);
        return (Channel) playerConnectionNetworkManagerChannelField.get(getNetworkManager(nmsPlayer));
    }

    public static Channel getNetworkManagerChannelObj(Object obj) {
        Validate.notNull(obj, "The object is null.");
        if(playerConnectionNetworkManagerChannelField == null)
            playerConnectionNetworkManagerChannelField = Accessors.getFieldAccessor(getNetworkManagerClass(), Channel.class, true);
        return (Channel) playerConnectionNetworkManagerChannelField.get(obj);
    }

    public static List getNetworkManagerList() {
        return getNetworkManagerList(getMinecraftServerConnection());
    }

    public static List getNetworkManagerList(Object nmsServerConnection) {
        Validate.notNull(nmsServerConnection, "The nms server connection object is null.");
        return (List) getServerConnectionNetworkManagerListField().get(nmsServerConnection);
    }

    public static FieldAccessor getServerConnectionNetworkManagerListField() {
        if(serverConnectionNetworkManagerListField == null) try {
            serverConnectionNetworkManagerListField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(getServerConnectionClass(), true).getFieldListByParamType("List<NetworkManager>", getNetworkManagerClass()));
        } catch (Exception e) {
        }
        if(serverConnectionNetworkManagerListField == null) try {
            serverConnectionNetworkManagerListField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(getServerConnectionClass(), true).getFieldListByType(List.class).get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverConnectionNetworkManagerListField;
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
        if(playerConnectionSendPacketMethod == null)
            playerConnectionSendPacketMethod = Accessors.getMethodAccessor(getPlayerConnectionClass(), "sendPacket", getPacketClass());
        playerConnectionSendPacketMethod.invoke(getPlayerConnection(nmsPlayer), nmsPacket);
    }

    public static boolean is(Class<?> clazz, Object obj) {
        return !(clazz == null || obj == null) && clazz.isAssignableFrom(obj.getClass());
    }

    public static boolean isICBC(Object obj) {
        return is(getIChatBaseComponentClass(), obj);
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

    private static MethodAccessor getEnumConstantDirectoryMethod() {
        if(enumConstantDirectoryMethod == null)
            enumConstantDirectoryMethod = Accessors.getMethodAccessor(Class.class, "enumConstantDirectory");
        return enumConstantDirectoryMethod;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> Map<String, T> enumMap(Class<T> enumClass) {
        Validate.notNull(enumClass, "The enum class object is null.");
        try {
            return (Map<String, T>) getEnumConstantDirectoryMethod().invoke(enumClass);
        } catch (Exception e) {
            MoonLakePluginDebug.debug(e);
        }
        return null;
    }

    public static <T extends Enum<T>> T enumOfName(Class<T> enumClass, String name) {
        return enumOfName(enumClass, name, null);
    }

    public static <T extends Enum<T>> T enumOfName(Class<T> enumClass, String name, T def) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(name, "The name object is null.");
        try {
            Map<String, T> enumMap = enumMap(enumClass);
            if(enumMap == null || enumMap.isEmpty())
                return def;
            Iterator<T> iterator = enumMap.values().iterator();
            while(iterator.hasNext()) {
                T type = iterator.next();
                if(StringUtil.isEquals(type.name(), name))
                    return type;
            }
        } catch (Exception e) {
            MoonLakePluginDebug.debug(e);
        }
        return def;
    }

    public static <T extends Enum<T>> T enumOfNameAny(Class<?> enumClass, String name) {
        return enumOfNameAny(enumClass, name, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumOfNameAny(Class<?> enumClass, String name, T def) {
        return enumOfName((Class<T>) enumClass, name, def);
    }

    public static <T extends Enum<T>> T enumOfOrigin(Class<T> enumClass, int origin) {
        return enumOfOrigin(enumClass, origin, null);
    }

    public static <T extends Enum<T>> T enumOfOrigin(Class<T> enumClass, int origin, T def) {
        Validate.notNull(enumClass, "The enum class object is null.");
        try {
            if(enumOrdinalMethod == null)
                enumOrdinalMethod = Accessors.getMethodAccessor(Enum.class, "ordinal");
            Map<String, T> enumMap = enumMap(enumClass);
            if(enumMap == null || enumMap.isEmpty())
                return def;
            Iterator<T> iterator = enumMap.values().iterator();
            while(iterator.hasNext()) {
                T value = iterator.next();
                int valueOrigin = (int) enumOrdinalMethod.invoke(value);
                if(valueOrigin == origin)
                    return value;
            }
        } catch (Exception e) {
            MoonLakePluginDebug.debug(e);
        }
        return def;
    }

    public static <T extends Enum<T>> T enumOfOriginAny(Class<?> enumClass, int origin) {
        return enumOfOriginAny(enumClass, origin, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumOfOriginAny(Class<?> enumClass, int origin, T def) {
        return enumOfOrigin((Class<T>) enumClass, origin, def);
    }

    public static <V, T extends Enum<T> & Valuable<V>> T enumOfValuable(Class<T> enumClass, V value) {
        return enumOfValuable(enumClass, value, null);
    }

    public static <V, T extends Enum<T> & Valuable<V>> T enumOfValuable(Class<T> enumClass, V value, T def) {
        Validate.notNull(enumClass, "The enum class object is null.");
        Validate.notNull(value, "The value object is null.");
        try {
            Map<String, T> enumMap = enumMap(enumClass);
            if(enumMap == null || enumMap.isEmpty())
                return def;
            Iterator<T> iterator = enumMap.values().iterator();
            while(iterator.hasNext()) {
                T type = iterator.next();
                if(StringUtil.isEquals(type.value(), value))
                    return type;
            }
        } catch (Exception e) {
            MoonLakePluginDebug.debug(e);
        }
        return def;
    }

    public static <V, T extends Enum<T> & Valuable<V>> T enumOfValuableAny(Class<?> enumClass, V value) {
        return enumOfValuableAny(enumClass, value, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends Enum<T> & Valuable<V>> T enumOfValuableAny(Class<?> enumClass, V value, T def) {
        return enumOfValuable((Class<T>) enumClass, value, def);
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
