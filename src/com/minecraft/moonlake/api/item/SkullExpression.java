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

    @Override
    public ItemStack createSkullWithOwner(String skullOwner, String displayName) {

        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        ItemStack itemStack = createSkull(skullOwner);

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createSkullWithSkin(String skinURL) throws MoonLakeException {

        Validate.notNull(skinURL, "The itemstack skull skin url object is null.");

        if(skinURL.isEmpty()) {

            return createSkull();
        }
        String targetURL = "{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}";

        ItemStack itemStack = createSkull();
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
    public ItemStack createSkullWithSkin(URL skinURL) throws MoonLakeException {

        Validate.notNull(skinURL, "The itemstack skull skin url object is null.");

        return createSkullWithSkin(skinURL.toString());
    }

    @Override
    public ItemStack createSkullWithSkin(String skinURL, String displayName) throws MoonLakeException {

        Validate.notNull(skinURL, "The itemstack skull skin url object is null.");
        Validate.notNull(displayName, "The itemstack skull displayName object is null.");

        if(skinURL.isEmpty()) {

            return createSkull(displayName);
        }
        ItemStack itemStack = createSkullWithSkin(skinURL);
        itemStack = setDisplayName(itemStack, displayName);
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
