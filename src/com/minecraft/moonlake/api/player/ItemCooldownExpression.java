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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.ability.ItemCooldown;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>ItemCooldownExpression</h1>
 * 玩家物品栈冷却能力接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class ItemCooldownExpression implements ItemCooldown {

    private Class<?> CLASS_ITEM;
    private Class<?> CLASS_ENTITYHUMAN;
    private Class<?> CLASS_CRAFTPLAYER;
    private Class<?> CLASS_ENTITYPLAYER;
    private Class<?> CLASS_ITEMCOOLDOWN;
    private Class<?> CLASS_CRAFTMAGICNUMBERS;
    private Method METHOD_GETHANDLE;
    private Method METHOD_GETITEM;
    private Method METHOD_TARGET;
    private Method METHOD_A0;
    private Method METHOD_A1;

    /**
     * 玩家物品栈冷却能力接口实现类构造函数
     *
     * @throws IllegalBukkitVersionException 如果服务端版本不支持则抛出异常
     */
    public ItemCooldownExpression() throws IllegalBukkitVersionException {

        try {

            if(getServerVersionNumber() <= 8) {

                throw new IllegalBukkitVersionException("The player item cooldown not support 1.8 and old version bukkit.");
            }
            CLASS_ITEM = PackageType.MINECRAFT_SERVER.getClass("Item");
            CLASS_ENTITYHUMAN = PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_ITEMCOOLDOWN = PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
            CLASS_CRAFTMAGICNUMBERS = PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");

            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            METHOD_GETITEM = getMethod(CLASS_CRAFTMAGICNUMBERS, "getItem", Material.class);
            METHOD_TARGET = getMethod(CLASS_ENTITYHUMAN, getItemCooldownMethodName());
            METHOD_A0 = getMethod(CLASS_ITEMCOOLDOWN, "a", CLASS_ITEM, int.class);
            METHOD_A1 = getMethod(CLASS_ITEMCOOLDOWN, "a", CLASS_ITEM);
        }
        catch (Exception e) {

            throw new IllegalBukkitVersionException("The player item cooldown not support your bukkit.", e);
        }
    }

    @Override
    public void setItemCooldown(String player, Material material, int tick) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(material, "The material object is null.");

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        String itemCooldownMethodName = getItemCooldownMethodName();

        if(itemCooldownMethodName == null) {

            throw new IllegalBukkitVersionException("The method has item cooldown not support your bukkit.");
        }
        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(target);
            Object nmsItemCooldown = METHOD_TARGET.invoke(nmsPlayer);

            METHOD_A0.invoke(nmsItemCooldown, METHOD_GETITEM.invoke(null, material), tick);
        }
        catch (Exception e) {

            throw new MoonLakeException("The set player '" + player + "' item cooldown exception.", e);
        }
    }

    @Override
    public boolean hasItemCooldown(String player, Material material) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(material, "The material object is null.");

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        String itemCooldownMethodName = getItemCooldownMethodName();

        if(itemCooldownMethodName == null) {

            throw new IllegalBukkitVersionException("The method has item cooldown not support your bukkit.");
        }
        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(target);
            Object nmsItemCooldown = METHOD_TARGET.invoke(nmsPlayer);

            return (boolean) METHOD_A1.invoke(nmsItemCooldown, METHOD_GETITEM.invoke(null, material));
        }
        catch (Exception e) {

            throw new MoonLakeException("The get player '" + player + "' has item cooldown exception.", e);
        }
    }

    private String getItemCooldownMethodName() {

        String version = getServerVersion();

        return    version.equals("v1_9_R1") ? "da"
                : version.equals("v1_9_R2") ? "db"
                : version.equals("v1_10_R1") ? "df"
                : null;
    }
}
