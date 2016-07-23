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

    private FireworkMeta fireworkMeta;

    public FireworkBuilder() {

    }

    public FireworkBuilder setPower(int power) {

        this.fireworkMeta.setPower(power);

        return this;
    }

    public FireworkBuilder addEffect(FireworkEffect fireworkEffect) {

        this.fireworkMeta.addEffect(fireworkEffect);

        return this;
    }

    public FireworkBuilder clearEffects() {

        this.fireworkMeta.clearEffects();

        return this;
    }

    public ItemStack buildToItemStack() {

        ItemStack firework = new ItemBuilder(Material.FIREWORK).build();
        firework.setItemMeta(fireworkMeta);
        return firework.clone();
    }

    public Firework buildToFirework(Location location) {

        Firework firework = location.getWorld().spawn(location, Firework.class);
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }
}
