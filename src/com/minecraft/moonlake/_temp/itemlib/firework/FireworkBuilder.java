package com.minecraft.moonlake._temp.itemlib.firework;

import com.minecraft.moonlake._temp.itemlib.ItemBuilder;
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

    /**
     * 将指定烟花效果构建为物品栈对象
     *
     * @param power 强度
     * @param effects 烟花效果
     * @return 物品栈
     */
    public ItemStack buildToItemStack(int power, FireworkEffect... effects) {

        ItemStack firework = new ItemBuilder(Material.FIREWORK).build();
        FireworkMeta fireworkMeta = (FireworkMeta) firework.getItemMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffects(effects);
        firework.setItemMeta(fireworkMeta);
        return firework.clone();
    }

    /**
     * 将指定烟花效果构建为烟花实体对象
     *
     * @param power 强度
     * @param location 位置
     * @param effects 烟花效果
     * @return 烟花实体
     */
    public Firework buildToFirework(int power, Location location, FireworkEffect... effects) {

        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffects(effects);
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }
}
