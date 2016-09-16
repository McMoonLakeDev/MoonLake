package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.AttributeLibrary;
import com.minecraft.moonlake.api.item.AttributeModify;
import com.minecraft.moonlake.api.item.ItemLibraryFactorys;
import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class AttributeExpression implements AttributeLibrary {

    public AttributeExpression() {

    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;

        if(itemMeta != null) {

            itemMeta.spigot().setUnbreakable(unbreakable);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    @Override
    public ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;

        if(itemMeta == null) {

            return new SimpleBooleanProperty(false);
        }
        return new SimpleBooleanProperty(itemMeta.spigot().isUnbreakable());
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(attribute, "The itemstack attribute object is null.");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");
            Class<?> NBTBase = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTBase");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, itemStack);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object attList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "AttributeModifiers", 10);

            if(attList == null) {

                attList = Reflect.instantiateObject(NBTTagList);
            }
            int attSize = (int) Reflect.getMethod(NBTTagList, "size").invoke(attList);

            if(attSize > 0) {

                for(int i = 0; i < attSize; i++) {

                    Object attributeCompound = Reflect.getMethod(NBTTagList, "get", Integer.class).invoke(attList, i);
                    String attributeName = (String) Reflect.getMethod(NBTTagCompound, "getString", String.class).invoke(attributeCompound, "AttributeName");

                    if(attributeName.equals(attribute.getAttributeType().getValue().getAttributeName())) {

                        Reflect.getMethod(NBTTagList, "remove", Integer.class).invoke(attList, i);
                        break;
                    }
                }
            }
            Object attributeNewCompound = Reflect.instantiateObject(NBTTagCompound);
            int version = Reflect.getServerVersionNumber();

            if(attribute.getAttributeSlot().getValue() != null && attribute.getAttributeSlot().getValue() != AttributeModify.Slot.ALL && version >= 9) {
                // only support 1.9+ version
                Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(attributeNewCompound, "Slot", attribute.getAttributeSlot().getValue().getSlot());
            }
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(attributeNewCompound, "Name", attribute.getAttributeType().getValue().getName());
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(attributeNewCompound, "AttributeName", attribute.getAttributeType().getValue().getAttributeName());
            Reflect.getMethod(NBTTagCompound, "setDouble", String.class, Double.class).invoke(attributeNewCompound, "Amount", attribute.getAmount().get());
            Reflect.getMethod(NBTTagCompound, "setInt", String.class, Integer.class).invoke(attributeNewCompound, "Operation", attribute.getOperation().getValue().getOperation());

            UUID uuid = attribute.getUUIDProperty().getValue();

            if(uuid == null) {

                uuid = UUID.randomUUID();
            }
            Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke(attributeNewCompound, "UUIDMost", uuid.getMostSignificantBits());
            Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke(attributeNewCompound, "UUIDLeast", uuid.getLeastSignificantBits());

            Reflect.getMethod(NBTTagList, "add", NBTTagCompound).invoke(attList, attributeNewCompound);
            Reflect.getMethod(NBTTagCompound, "set", String.class, NBTBase).invoke(tag, "AttributeModifiers", attList);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            itemStack = (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return itemStack;
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        return setAttribute(itemStack, type, null, operation, amount);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        Validate.notNull(type, "The itemstack attribute type object is null.");
        Validate.notNull(operation, "The itemstack attribute opreation object is null.");

        return setAttribute(itemStack, new AttributeModify(type, slot, operation, amount));
    }

    @Override
    public List<AttributeModify> getAttributes(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, itemStack);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object attList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "AttributeModifiers", 10);

            if(attList == null) {

                attList = Reflect.instantiateObject(NBTTagList);
            }
            List<AttributeModify> attributeList = new ArrayList<>();
            int size = (int) Reflect.getMethod(NBTTagList, "size").invoke(attList);
            int version = Reflect.getServerVersionNumber();

            for(int i = 0; i < size; i++) {

                Object att = Reflect.getMethod(NBTTagList, "get", Integer.class).invoke(attList, i);

                if(att != null) {

                    AttributeModify.Type attType = AttributeModify.Type.fromType((String) Reflect.getMethod(NBTTagCompound, "getString", String.class).invoke(att, "AttributeName"));

                    if(attType != null) {

                        AttributeModify.Slot attSlot = null;

                        if(version >= 9) {
                            // only support 1.9+ version
                            attSlot = AttributeModify.Slot.fromType((String) Reflect.getMethod(NBTTagCompound, "getString", String.class).invoke(att, "Slot"));
                        }
                        AttributeModify.Operation attOperation = AttributeModify.Operation.fromValue((Integer) Reflect.getMethod(NBTTagCompound, "getInte", String.class).invoke(att, "Operation"));
                        double amount = (Double) Reflect.getMethod(NBTTagCompound, "getDouble", String.class).invoke(att, "Amount");

                        attributeList.add(new AttributeModify(attType, attSlot == null ? AttributeModify.Slot.ALL : attSlot, attOperation, amount));
                    }
                }
            }
            return attributeList;
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects) {

        Validate.notNull(effects, "The itemstack potion effect object is null.");

        return setCustomPotion(itemStack, Arrays.asList(effects));
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.isTrue(ItemLibraryFactorys.item().isPotion(itemStack), "The itemstack material object not potion.");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, itemStack);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object customEffectList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "CustomPotionEffects", 10);

            if(customEffectList == null || (int) Reflect.getMethod(NBTTagList, "size").invoke(customEffectList) <= 0) {

                customEffectList = Reflect.instantiateObject(NBTTagList);

                if(!effects.isEmpty()) {

                    for(final PotionEffectCustom effect : effects) {

                        PotionEffectType effectType = PotionEffectType.fromId(effect.getId().get());

                        if(effectType != null) {

                            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(tag, "Potion", "minecraft:" + effectType.getTagName());
                            break;
                        }
                    }
                }
            }
            for(final PotionEffectCustom effect : effects) {

                Object effectTag = Reflect.instantiateObject(NBTTagCompound);
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(effectTag, "Id", effect.getId().get());
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(effectTag, "Amplifier", effect.getAmplifier().getValue());
                Reflect.getMethod(NBTTagCompound, "setInt", String.class, Integer.class).invoke(effectTag, "Duration", effect.getDuration().getValue());
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(effectTag, "Ambient", (byte)(effect.getAmbient().getValue() ? 1 : 0));
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(effectTag, "ShowParticles", (byte)(effect.getShowParticles().getValue() ? 1 : 0));

                Reflect.getMethod(NBTTagList, "add", NBTTagCompound).invoke(customEffectList, effectTag);
            }
            Reflect.getMethod(NBTTagCompound, "set", String.class, Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTBase")).invoke(tag, "CustomPotionEffects", customEffectList);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            itemStack = (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return itemStack;
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return setCustomPotion(itemStack, new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
    }
}
