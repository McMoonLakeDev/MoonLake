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

import com.minecraft.moonlake.api.item.firework.FireworkBuilder;
import com.minecraft.moonlake.api.item.firework.FireworkType;
import com.minecraft.moonlake.manager.RandomManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * <h1>FireworkBuilderWrapped</h1>
 * 烟花建造接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class FireworkBuilderWrapped implements FireworkBuilder {

    private final FireworkEffect.Builder builder;
    private int power;

    /**
     * 烟花建造接口实现类构造函数
     */
    public FireworkBuilderWrapped() {

        this.power = 1;
        this.builder = FireworkEffect.builder();
    }

    protected FireworkEffect.Builder get() {

        return builder;
    }

    @Override
    public FireworkBuilder withColor(Color color) {

        get().withColor(color);

        return this;
    }

    @Override
    public FireworkBuilder withColor(int red, int green, int blue) {

        get().withColor(Color.fromRGB(red, green, blue));

        return this;
    }

    @Override
    public FireworkBuilder withColorRandom() {

        return withColor(RandomManager.nextColor());
    }

    @Override
    public FireworkBuilder withType(FireworkType fireworkType) {

        get().with(FireworkEffect.Type.valueOf(fireworkType.name()));

        return this;
    }

    @Override
    public FireworkBuilder withPower(int power) {

        Validate.isTrue(power >= 0 && power < 128, "The firework power value is illegal. (must: 0 - 127)");

        this.power = power;

        return this;
    }

    @Override
    public FireworkBuilder withTrail() {

        get().withTrail();

        return this;
    }

    @Override
    public FireworkBuilder withTrail(boolean trail) {

        get().trail(trail);

        return this;
    }

    @Override
    public FireworkBuilder withFlicker() {

        get().withFlicker();

        return this;
    }

    @Override
    public FireworkBuilder withFlicker(boolean flicker) {

        get().flicker(flicker);

        return this;
    }

    @Override
    public FireworkBuilder withFade(Color color) {

        get().withFade(color);

        return this;
    }

    @Override
    public FireworkBuilder withFade(int red, int green, int blue) {

        get().withFade(Color.fromRGB(red, green, blue));

        return this;
    }

    @Override
    public FireworkBuilder withFadeRandom() {

        return withFade(RandomManager.nextColor());
    }

    @Override
    public void launch(Location location) {

        launch(location, 1);
    }

    @Override
    public void launch(Location location, int amount) {

        Validate.notNull(location, "The firework location object is null.");

        FireworkEffect effect = builder.build();

        if(amount <= 0) amount = 1;

        for(int i = amount; i > 0; i--) {

            Firework firework = location.getWorld().spawn(location, Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(power);
            fireworkMeta.addEffects(effect);
            firework.setFireworkMeta(fireworkMeta);
        }
    }
}
