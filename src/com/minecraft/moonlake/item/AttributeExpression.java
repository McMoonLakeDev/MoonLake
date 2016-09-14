package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.AttributeLibrary;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class AttributeExpression implements AttributeLibrary {

    public AttributeExpression() {

    }

    @Override
    public void setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;

        if(itemMeta != null) {

            itemMeta.spigot().setUnbreakable(unbreakable);
            itemStack.setItemMeta(itemMeta);
        }
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
}
