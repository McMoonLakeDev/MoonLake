package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.CraftLibrary;
import com.minecraft.moonlake.api.item.potion.PotionBase;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/12.
 */
public class CraftExpression extends MetaExpression implements CraftLibrary {

    public CraftExpression() {

    }

    @Override
    public ItemStack create(int id) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material);
    }

    @Override
    public ItemStack create(int id, int data) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data);
    }

    @Override
    public ItemStack create(int id, int data, int amount) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(String type) {

        Validate.notNull(type, "The itemstack type object is null.");
        
        Material material = Material.getMaterial(type);
        return material == null ? null : create(material);
    }

    @Override
    public ItemStack create(String type, int data) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName, String... lore) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(Material material) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material);
    }

    @Override
    public ItemStack create(Material material, int data) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material, 1, (short) data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material, amount, (short) data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName) {

        Validate.notNull(material, "The itemstack material object is null.");

        ItemStack itemStack = new ItemStack(material, amount, (short) data);

        setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        Validate.notNull(material, "The itemstack material object is null.");

        ItemStack itemStack = new ItemStack(material, amount, (short) data);

        setDisplayName(itemStack, displayName);
        setLore(itemStack, lore);

        return itemStack;
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base) {

        return createPotion(type, base, 1);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount) {

        Validate.notNull(type, "The itemstack potion type object is null.");
        Validate.notNull(base, "The itemstack potion base object is null.");

        try {

            ItemStack itemStack = create(type.getMaterial(), 0, amount);

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, itemStack);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(tag, "Potion", base.getValue().get());
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            return (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount, String displayName) {

        ItemStack itemStack = createPotion(type, base, amount);

        setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base) {

        return createPotion(PotionType.POTION, base);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount) {

        return createPotion(PotionType.POTION, base, amount);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.POTION, base, amount, displayName);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base) {

        return createPotion(PotionType.SPLASH_POTION, base);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount) {

        return createPotion(PotionType.SPLASH_POTION, base, amount);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.SPLASH_POTION, base, amount, displayName);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base) {

        return createPotion(PotionType.LINGERING_POTION, base);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount) {

        return createPotion(PotionType.LINGERING_POTION, base, amount);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.LINGERING_POTION, base, amount, displayName);
    }
}
