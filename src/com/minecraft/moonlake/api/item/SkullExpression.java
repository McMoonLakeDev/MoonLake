package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.exception.NotImplementedException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.net.URL;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/9/13.
 */
class SkullExpression extends CraftExpression implements SkullLibrary {

    public SkullExpression() {

    }

    @Override
    public ItemStack createSkull() {

        return create(Material.SKULL_ITEM, 3);
    }

    @Override
    public ItemStack createSkull(String displayName) {

        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        return create(Material.SKULL_ITEM, 3, 1, displayName);
    }

    @Override
    public ItemStack createSkullWithOwner(String skullOwner) {

        Validate.notNull(skullOwner, "The itemstack skull owner object is null.");

        ItemStack itemStack = createSkull();
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(skullOwner);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    @Override
    public ItemStack createSkullWithOwner(String skullOwner, String displayName) {

        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        ItemStack itemStack = createSkull(skullOwner);

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createSkullWithSkin(String skinURL, String displayName) throws MoonLakeException {

        Validate.notNull(skinURL, "The itemstack skull skin url object is null.");
        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        if(skinURL.isEmpty()) {

            return createSkull(displayName);
        }
        String targetURL = "{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}";

        ItemStack itemStack = createSkull(displayName);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", Base64.getEncoder().encodeToString(targetURL.getBytes())));

        try {

            Reflect.getField(SkullMeta.class, true, "profile").set(skullMeta, gameProfile);
        }
        catch (Exception e) {

            throw new MoonLakeException("The set itemstack skull skin textures exception.", e);
        }
        return itemStack;
    }

    @Override
    public ItemStack createSkullWithSkin(URL skinURL, String displayName) {

        Validate.notNull(skinURL, "The itemstack skull skin url object is null.");

        return createSkullWithSkin(skinURL.toString(), displayName);
    }

    @Override
    public String getSkullOwner(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack skull object is null.");
        Validate.isTrue(itemStack.getType() == Material.SKULL_ITEM, "The itemstack type not is skull.");

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if(!skullMeta.hasOwner()) {

            return null;
        }
        return skullMeta.getOwner();
    }

    @Override
    public String getSkullSkinURL(ItemStack itemStack) {

        throw new NotImplementedException();
    }
}
