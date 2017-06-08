/*
 * Copyright (C) 2016 The MoonLake Authors
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
 
 
package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * <h1>SkullExpression</h1>
 * 头颅物品栈接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class SkullExpression extends CraftExpression implements SkullLibrary {

    /**
     * 头颅物品栈接口实现类构造函数
     */
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

    protected GameProfile createGameProfile(String data) {

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", data));

        return gameProfile;
    }

    protected GameProfile createGameProfile(String value, String signature) {

        if(signature == null) {

            return createGameProfile(value);
        }
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        return gameProfile;
    }

    @Override
    public ItemStack createSkullWithOwner(String skullOwner, String displayName) {

        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        ItemStack itemStack = createSkull(skullOwner);

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createSkullWithSkin(String data) throws MoonLakeException {

        return setSkullWithSkin(createSkull(), data);
    }

    @Override
    public ItemStack createSkullWithSkins(String value, String signature) throws MoonLakeException {

        return setSkullWithSkin(createSkull(), value, signature);
    }

    @Override
    public ItemStack createSkullWithSkin(String data, String displayName) throws MoonLakeException {

        Validate.notNull(data, "The itemstack skull skin data object is null.");
        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        if(data.isEmpty()) {

            return createSkull(displayName);
        }
        ItemStack itemStack = createSkullWithSkin(data);
        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createSkullWithSkins(String value, String signature, String displayName) throws MoonLakeException {

        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        ItemStack itemStack = createSkullWithSkins(value, signature);
        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
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
    public ItemStack setSkullWithSkin(ItemStack itemStack, String data) throws MoonLakeException {

        Validate.notNull(data, "The itemstack skull skin data object is null.");
        Validate.isTrue(itemStack.getType() == Material.SKULL_ITEM, "The itemstack type not is skull.");

        if(data.isEmpty()) {

            return createSkull();
        }
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        try {

            //Reflect.getField("CraftMetaSkull", Reflect.PackageType.CRAFTBUKKIT_INVENTORY, true, "profile").set(skullMeta, createGameProfile(data));
            MinecraftReflection.getCraftMetaSkullProfileField().set(skullMeta, createGameProfile(data));
        }
        catch (Exception e) {

            throw new MoonLakeException("The create skull with skin meta exception.", e);
        }
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    @Override
    public ItemStack setSkullWithSkin(ItemStack itemStack, String value, String signature) throws MoonLakeException {

        Validate.notNull(value, "The itemstack skull skin value object is null.");
        Validate.isTrue(itemStack.getType() == Material.SKULL_ITEM, "The itemstack type not is skull.");

        if(signature == null) {

            return setSkullWithSkin(itemStack, value);
        }
        if(value.isEmpty()) {

            return createSkull();
        }
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        try {

            //Reflect.getField("CraftMetaSkull", Reflect.PackageType.CRAFTBUKKIT_INVENTORY, true, "profile").set(skullMeta, createGameProfile(value, signature));
            MinecraftReflection.getCraftMetaSkullProfileField().set(skullMeta, createGameProfile(value, signature));
        }
        catch (Exception e) {

            throw new MoonLakeException("The create skull with skin meta exception.", e);
        }
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}
