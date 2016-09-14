package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class SkullExpression extends CraftExpression implements SkullLibrary {

    public SkullExpression() {

    }

    @Override
    public ItemStack createSkull() {

        return create(Material.SKULL_ITEM, 3);
    }

    @Override
    public ItemStack createSkull(String skullOwner) {

        Validate.notNull(skullOwner, "The itemstack skull owner object is null.");

        ItemStack itemStack = createSkull();

        if(!itemStack.hasItemMeta()) {

            return null;
        }
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(skullOwner);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    @Override
    public ReadOnlyStringProperty getSkullOwner(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack skull object is null.");

        if(itemStack.getType() != Material.SKULL_ITEM || !itemStack.hasItemMeta()) {

            return null;
        }
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if(!skullMeta.hasOwner()) {

            return null;
        }
        return new SimpleStringProperty(skullMeta.getOwner());
    }
}
