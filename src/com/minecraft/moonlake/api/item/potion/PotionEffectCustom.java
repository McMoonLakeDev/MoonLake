package com.minecraft.moonlake.api.item.potion;

import com.minecraft.moonlake.property.Property;
import com.minecraft.moonlake.property.ReadOnlyObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;

/**
 * Created by MoonLake on 2016/9/15.
 */
public class PotionEffectCustom {

    private ReadOnlyObjectProperty<Byte> idProperty;
    private Property<Byte> amplifierProperty;
    private Property<Integer> durationProperty;
    private Property<Boolean> ambientProperty;
    private Property<Boolean> showParticlesProperty;

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration) {

        this(id, amplifier, duration, false);
    }

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient) {

        this(id, amplifier, duration, ambient, false);
    }

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient, boolean showParticles) {

        this(PotionEffectType.fromId(id), amplifier, duration, ambient, showParticles);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration) {

        this(type, amplifier, duration, false);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient) {

        this(type, amplifier, duration, ambient, false);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(type, "The potion effect type object is null.");

        this.idProperty = new SimpleObjectProperty<>((byte) type.getId());
        this.amplifierProperty = new SimpleObjectProperty<>((byte) amplifier);
        this.durationProperty = new SimpleObjectProperty<>(duration);
        this.ambientProperty = new SimpleObjectProperty<>(ambient);
        this.showParticlesProperty = new SimpleObjectProperty<>(showParticles);
    }

    public ReadOnlyObjectProperty<Byte> getId() {

        return idProperty;
    }

    public Property<Byte> getAmplifier() {

        return amplifierProperty;
    }

    public Property<Integer> getDuration() {

        return durationProperty;
    }

    public Property<Boolean> getAmbient() {

        return ambientProperty;
    }

    public Property<Boolean> getShowParticles() {

        return showParticlesProperty;
    }
}

