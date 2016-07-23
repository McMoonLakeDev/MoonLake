package com.minecraft.moonlake.api.itemlib.firework;

import com.minecraft.moonlake.api.itemlib.ItemBuilder;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by MoonLake on 2016/7/22.
 */
public class FireworkBuilder {

    public FireworkBuilder() {

    }

    public ItemStack buildToItemStack(int power, FireworkEffect... effects) {

        ItemStack firework = new ItemBuilder(Material.FIREWORK).build();
        FireworkMeta fireworkMeta = (FireworkMeta) firework.getItemMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffects(effects);
        firework.setItemMeta(fireworkMeta);
        return firework.clone();
    }

    public Firework buildToFirework(int power, Location location, FireworkEffect... effects) {

        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffects(effects);
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }
}
