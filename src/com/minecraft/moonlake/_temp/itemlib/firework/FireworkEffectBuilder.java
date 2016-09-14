package com.minecraft.moonlake._temp.itemlib.firework;

import com.minecraft.moonlake.manager.RandomManager;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;

/**
 * Created by MoonLake on 2016/7/23.
 */
public class FireworkEffectBuilder {

    private FireworkEffect.Builder builder;

    public FireworkEffectBuilder() {

        this.builder = FireworkEffect.builder();
    }

    /**
     * 设置此烟花效果具有的烟花类型
     *
     * @param type 类型
     * @return 实例
     */
    public FireworkEffectBuilder withType(FireworkType type) {

        this.builder.with(FireworkEffect.Type.valueOf(type.name()));

        return this;
    }

    /**
     * 设置此烟花效果具有闪烁效果
     *
     * @return 实例
     */
    public FireworkEffectBuilder withFlicker() {

        return isWithFlicker(true);
    }

    /**
     * 设置此烟花效果是否具有闪烁效果
     *
     * @param flicker 是否
     * @return 实例
     */
    public FireworkEffectBuilder isWithFlicker(boolean flicker) {

        this.builder.flicker(flicker);

        return this;
    }

    /**
     * 设置此烟花效果具有爆裂效果
     *
     * @return 实例
     */
    public FireworkEffectBuilder withTrail() {

        return isWithTrail(true);
    }

    /**
     * 设置此烟花效果是否具有爆裂效果
     *
     * @param trail 是否
     * @return 实例
     */
    public FireworkEffectBuilder isWithTrail(boolean trail) {

        this.builder.trail(trail);

        return this;
    }

    /**
     * 设置此烟花效果具有的颜色
     *
     * @param color 颜色
     * @return 实例
     */
    public FireworkEffectBuilder withColor(Color color) {

        this.builder.withColor(color);

        return this;
    }

    /**
     * 设置此烟花效果具有的颜色
     *
     * @param r 红色值
     * @param g 绿色值
     * @param b 蓝色值
     * @return 实例
     */
    public FireworkEffectBuilder withColorFromRGB(int r, int g, int b) {

        return withColor(Color.fromRGB(r, g, b));
    }

    /**
     * 设置此烟花效果具有的颜色
     *
     * @param b 蓝色值
     * @param g 绿色值
     * @param r 红色值
     * @return 实例
     */
    public FireworkEffectBuilder withColorFromBGR(int b, int g, int r) {

        return withColor(Color.fromBGR(b, g, r));
    }

    /**
     * 设置此烟花效果具有随机颜色
     *
     * @return 实例
     */
    public FireworkEffectBuilder withColorFromRandom() {

        return withColorFromRGB(RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255));
    }

    /**
     * 设置此烟花效果具有的渐变颜色
     *
     * @param color 颜色
     * @return 实例
     */
    public FireworkEffectBuilder withFade(Color color) {

        this.builder.withFade(color);

        return this;
    }

    /**
     * 设置此烟花效果具有的渐变颜色
     *
     * @param r 红色值
     * @param g 绿色值
     * @param b 蓝色值
     * @return 实例
     */
    public FireworkEffectBuilder withFadeFromRGB(int r, int g, int b) {

        return withFade(Color.fromRGB(r, g, b));
    }

    /**
     * 设置此烟花效果具有的渐变颜色
     *
     * @param b 蓝色值
     * @param g 绿色值
     * @param r 红色值
     * @return 实例
     */
    public FireworkEffectBuilder withFadeFromBGR(int b, int g, int r) {

        return withFade(Color.fromBGR(b, g, r));
    }

    /**
     * 设置此烟花效果具有随机渐变颜色
     *
     * @return 实例
     */
    public FireworkEffectBuilder withFadeFromRandom() {

        return withFadeFromRGB(RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255), RandomManager.getRandom().nextInt(255));
    }

    /**
     * 构建此烟花效果对象
     *
     * @return 烟花效果
     */
    public FireworkEffect build() {

        return this.builder.build();
    }
}
