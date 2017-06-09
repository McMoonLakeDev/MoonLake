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

import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * <h1>SimpleMoonLakePlayer</h1>
 * 月色之湖玩家接口单包装类 - Bukkit 1.8
 *
 * @version 1.0
 * @author Month_Light
 */
public class SimpleMoonLakePlayer_v1_8 extends SimpleMoonLakePlayer {

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.8 构造函数
     *
     * @param name 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_8(String name) throws IllegalArgumentException, PlayerNotOnlineException {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.8 构造函数
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_8(Player player) throws IllegalArgumentException, PlayerNotOnlineException {

        super(player);
    }

    //
    // 这些函数 Bukkit 1.8 均不支持

    @Override
    public void stopSound(String sound) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void stopSound(Sound sound) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public boolean isGliding() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setGliding(boolean gliding) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setGlowing(boolean flag) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public boolean isGlowing() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setInvulnerable(boolean flag) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public boolean isInvulnerable() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public boolean isSilent() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setSilent(boolean flag) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public boolean hasGravity() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setGravity(boolean gravity) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        Validate.notNull(type, "The potion effect type object is null.");

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles));
    }

    @Override
    public Entity getSpectatorTarget() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setSpectatorTarget(Entity entity) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    @SuppressWarnings("deprecation") // 移除过期警告, 1.8 版本此函数没有警告
    public ItemStack getItemInHand() {

        return getBukkitPlayer().getItemInHand();
    }

    @Override
    public ItemStack getItemInMainHand() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public ItemStack getItemInOffHand() {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    @SuppressWarnings("deprecation") // 移除过期警告, 1.8 版本此函数没有警告
    public void setItemInHand(ItemStack itemStack) {

        getBukkitPlayer().setItemInHand(itemStack);
    }

    @Override
    public void setItemInMainHand(ItemStack itemStack) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public void setItemInOffHand(ItemStack itemStack) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    @Override
    public AdvancementProgress getAdvancementProgress(AdvancementKey key) {

        throw new IllegalBukkitVersionException("The method not support 1.8 and old version.");
    }

    ///


    @Override
    public MinecraftVersion mcVersion() {

        return MinecraftVersion.V1_8;
    }
}
