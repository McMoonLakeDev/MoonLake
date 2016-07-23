package com.minecraft.moonlake.api.itemlib.firework;

import com.minecraft.moonlake.manager.RandomManager;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;

/**
 * Created by MoonLake on 2016/7/23.
 */
public class FireworkEffectBuilder {

    private FireworkEffect.Builder builder;

    public FireworkEffectBuilder() {

    }

    public FireworkEffectBuilder withType(FireworkType type) {

        this.builder.with(FireworkEffect.Type.valueOf(type.name()));

        return this;
    }

    public FireworkEffectBuilder withFlicker() {

        return isWithFlicker(true);
    }

    public FireworkEffectBuilder isWithFlicker(boolean flicker) {

        this.builder.flicker(flicker);

        return this;
    }

    public FireworkEffectBuilder withTrail() {

        return isWithTrail(true);
    }

    public FireworkEffectBuilder isWithTrail(boolean trail) {

        this.builder.trail(trail);

        return this;
    }

    public FireworkEffectBuilder withColor(Color color) {

        this.builder.withColor(color);

        return this;
    }

    public FireworkEffectBuilder withColorFromRGB(int r, int g, int b) {

        return withColor(Color.fromRGB(r, g, b));
    }

    public FireworkEffectBuilder withColorFromBGR(int b, int g, int r) {

        return withColor(Color.fromBGR(b, g, r));
    }

    public FireworkEffectBuilder withColorFromRandom() {

        return withColorFromRGB(RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255));
    }

    public FireworkEffectBuilder withFade(Color color) {

        this.builder.withFade(color);

        return this;
    }

    public FireworkEffectBuilder withFadeFromRGB(int r, int g, int b) {

        return withFade(Color.fromRGB(r, g, b));
    }

    public FireworkEffectBuilder withFadeFromBGR(int b, int g, int r) {

        return withFade(Color.fromBGR(b, g, r));
    }

    public FireworkEffectBuilder withFadeFromRandom() {

        return withFadeFromRGB(RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255));
    }

    public FireworkEffect build() {

        return this.builder.build();
    }
}
