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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.api.nbt.NBTList;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * <h1>AttributeExpression</h1>
 * 物品栈属性支持库接口实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
class AttributeExpression implements AttributeLibrary {

    /**
     * 物品栈属性支持库接口实现类构造函数
     */
    public AttributeExpression() {

    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);

        setUnbreakable(nbtCompound, unbreakable);

        nbtCompound.write(itemStack);

        return itemStack;
    }

    @Override
    public void setUnbreakable(NBTCompound nbtCompound, boolean unbreakable) {

        Validate.checkNotNull(nbtCompound).put("Unbreakable", unbreakable ? 1 : 0);
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().read(itemStack);

        if(nbtCompound == null) {

            return false;
        }
        return isUnbreakable(nbtCompound);
    }

    @Override
    public boolean isUnbreakable(NBTCompound nbtCompound) {

        return Validate.checkNotNull(nbtCompound).getByte("Unbreakable") == 1;
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(attribute, "The itemstack attribute object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);

        setAttribute(nbtCompound, attribute);

        nbtCompound.write(itemStack);

        return itemStack;
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify attribute) {

        Validate.notNull(nbtCompound, "The nbc compound object is null.");
        Validate.notNull(attribute, "The attribute object is null.");

        NBTList attributeModifiers = nbtCompound.getList("AttributeModifiers");

        if(attributeModifiers == null) {

            attributeModifiers = NBTFactory.newList();
        }
        int attributeModifiersSize = attributeModifiers.size();
        AttributeModify.Type attributeType = attribute.getType().get();

        if(attributeModifiersSize > 0) {

            for(int i = 0; i < attributeModifiersSize; i++) {

                Object attributeObject = attributeModifiers.get(i);

                if(!(attributeObject instanceof NBTCompound)) {

                    continue;
                }
                NBTCompound attributeCompound = (NBTCompound) attributeObject;
                String attributeName = attributeCompound.getString("AttributeName");

                if(attributeName != null && attributeName.equals(attributeType.getAttributeName())) {

                    attributeModifiers.remove(i);
                    break;
                }
            }
        }
        boolean slotSupport = MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9); // 服务器 MC 版本是在 1.9 或之后则支持 slot 标签
        NBTCompound attributeNewCompound = NBTFactory.newCompound();
        AttributeModify.Slot attributeSlot = attribute.getSlot().get();

        if(attributeSlot != null && attributeSlot != AttributeModify.Slot.ALL && slotSupport) {

            attributeNewCompound.put("Slot", attributeSlot.getSlot());
        }
        attributeNewCompound.put("Name", attributeType.getName());
        attributeNewCompound.put("AttributeName", attributeType.getAttributeName());
        attributeNewCompound.put("Amount", attribute.getAmount().get());
        attributeNewCompound.put("Operation", attribute.getOperation().get().getOperation());

        UUID uuid = attribute.getUUID().getValue();

        if(uuid == null) {

            uuid = UUID.randomUUID();
        }
        attributeNewCompound.put("UUIDMost", uuid.getMostSignificantBits());
        attributeNewCompound.put("UUIDLeast", uuid.getLeastSignificantBits());
        attributeModifiers.add(attributeNewCompound);
        nbtCompound.put("AttributeModifiers", attributeModifiers);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        return setAttribute(itemStack, type, null, operation, amount);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        Validate.notNull(type, "The itemstack attribute type object is null.");
        Validate.notNull(operation, "The itemstack attribute opreation object is null.");

        return setAttribute(itemStack, new AttributeModify(type, slot, operation, amount));
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        setAttribute(nbtCompound, type, null, operation, amount);
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        Validate.notNull(type, "The itemstack attribute type object is null.");
        Validate.notNull(operation, "The itemstack attribute opreation object is null.");

        setAttribute(nbtCompound, new AttributeModify(type, slot, operation, amount));
    }

    @Override
    public Set<AttributeModify> getAttributes(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return getAttributes(NBTFactory.get().readSafe(itemStack));
    }

    @Override
    public Set<AttributeModify> getAttributes(NBTCompound nbtCompound) {

        Validate.notNull(nbtCompound, "The nbt compound object is null.");

        Set<AttributeModify> attributeModifyList = new HashSet<>();
        NBTList attributeModifiers = nbtCompound.getList("AttributeModifiers");

        if(attributeModifiers == null || attributeModifiers.isEmpty()) {

            return attributeModifyList;
        }
        boolean slotSupport = MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9); // 服务器 MC 版本是在 1.9 或之后则支持 slot 标签

        for(final Object attributeObject : attributeModifiers) {

            if(attributeObject instanceof NBTCompound) {

                NBTCompound attributeCompound = (NBTCompound) attributeObject;
                AttributeModify.Type attributeType = AttributeModify.Type.fromType(attributeCompound.getString("AttributeName"));

                if(attributeType != null) {

                    AttributeModify.Slot attributeSlot = null;

                    if(slotSupport) {

                        attributeSlot = AttributeModify.Slot.fromType(attributeCompound.getString("Slot"));
                    }
                    double amount = attributeCompound.getDouble("Amount");
                    AttributeModify.Operation attributeOperation = AttributeModify.Operation.fromValue(attributeCompound.getInt("Operation"));

                    attributeModifyList.add(new AttributeModify(attributeType, attributeSlot == null ? AttributeModify.Slot.ALL : attributeSlot, attributeOperation, amount));
                }
            }
        }
        return attributeModifyList;
    }

    @Override
    public boolean hasAttribute(ItemStack itemStack, AttributeModify.Type type) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return hasAttribute(NBTFactory.get().readSafe(itemStack), type);
    }

    @Override
    public boolean hasAttribute(NBTCompound nbtCompound, AttributeModify.Type type) {

        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        Validate.notNull(type, "The itemstack attribute type object is null.");

        Set<AttributeModify> attributeModifies = getAttributes(nbtCompound);

        if(attributeModifies == null || attributeModifies.isEmpty()) {

            return false;
        }
        boolean result = false;

        for(final AttributeModify attribute : attributeModifies) {

            result = attribute.getType().get() == type;

            if(result) {

                break;
            }
        }
        return result;
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects) {

        Validate.notNull(effects, "The itemstack potion effect object is null.");

        return setCustomPotion(itemStack, Arrays.asList(effects));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.isTrue(ItemLibraryFactorys.item().isPotion(itemStack), "The itemstack material object not potion.");

        if(effects == null) {

            return itemStack;
        }
        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);

        setCustomPotion(nbtCompound, effects);

        nbtCompound.write(itemStack);

        return itemStack;
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return setCustomPotion(itemStack, new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return setCustomPotion(itemStack, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        return setCustomPotion(itemStack, effectType.to(), amplifier, duration, ambient, showParticles);
    }

    @Override
    public Set<PotionEffectCustom> getCustomPotion(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.isTrue(ItemLibraryFactorys.item().isPotion(itemStack), "The itemstack material object not potion.");

        return getCustomPotion(NBTFactory.get().readSafe(itemStack));
    }

    @Override
    public boolean hasCustomPotion(ItemStack itemStack, PotionEffectType effectType) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        return hasCustomPotion(NBTFactory.get().readSafe(itemStack), effectType);
    }

    @Override
    public boolean hasCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        return hasCustomPotion(itemStack, effectType.to());
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectCustom... effects) {

        setCustomPotion(nbtCompound, Arrays.asList(effects));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setCustomPotion(NBTCompound nbtCompound, Collection<? extends PotionEffectCustom> effects) {

        Validate.notNull(nbtCompound, "The nbt compound object is null.");

        NBTList customPotionEffects = nbtCompound.getList("CustomPotionEffects");

        if(customPotionEffects == null || customPotionEffects.size() <= 0) {

            customPotionEffects = NBTFactory.newList();

            if(!effects.isEmpty()) {

                for(final PotionEffectCustom effect : effects) {

                    PotionEffectType effectType = effect.getType();

                    if(effectType != null) {

                        nbtCompound.put("Potion", "minecraft:" + effectType.getTagName());
                        break;
                    }
                }
            }
        }
        for(final PotionEffectCustom effect : effects) {

            NBTCompound effectNewCompound = NBTFactory.newCompound();
            effectNewCompound.put("Id", effect.getId());
            effectNewCompound.put("Amplifier", effect.getAmplifier());
            effectNewCompound.put("Duration", effect.getDuration().get());
            effectNewCompound.put("Ambient", (byte) (effect.getAmbient().get() ? 1 : 0));
            effectNewCompound.put("ShowParticles", (byte) (effect.getShowParticles().get() ? 1 : 0));

            customPotionEffects.add(effectNewCompound);
        }
        nbtCompound.put("CustomPotionEffects", customPotionEffects);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration) {

        setCustomPotion(nbtCompound, effectType, amplifier, duration, false);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        setCustomPotion(nbtCompound, new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        setCustomPotion(nbtCompound, effectType, amplifier, duration, false);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        setCustomPotion(nbtCompound, effectType.to(), amplifier, duration, ambient, showParticles);
    }

    @Override
    @SuppressWarnings("deprecation")
    public Set<PotionEffectCustom> getCustomPotion(NBTCompound nbtCompound) {

        Validate.notNull(nbtCompound, "The nbt compound object is null.");

        Set<PotionEffectCustom> effects = new HashSet<>();
        NBTList customPotionEffects = nbtCompound.getList("CustomPotionEffects");

        if(customPotionEffects == null || customPotionEffects.isEmpty()) {

            return effects;
        }
        for(final Object effectObject : customPotionEffects) {

            if(effectObject instanceof NBTCompound) {

                NBTCompound effectCompound = (NBTCompound) effectObject;
                PotionEffectType effectType = PotionEffectType.fromId(effectCompound.getInt("Id"));

                if(effectType != null) {

                    int amplifier = effectCompound.getByte("Amplifier");
                    int duration = effectCompound.getInt("Duration");
                    boolean ambient = effectCompound.getBoolean("Ambient");
                    boolean showParticles = effectCompound.getBoolean("ShowParticles");
                    effects.add(new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
                }
            }
        }
        return effects;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType) {

        Validate.notNull(nbtCompound, "The nbt compound object is null.");
        Validate.notNull(effectType, "The effect type object is null.");

        Set<PotionEffectCustom> effects = getCustomPotion(nbtCompound);

        if(effects == null || effects.isEmpty()) {

            return false;
        }
        boolean result = false;

        for(final PotionEffectCustom effect : effects) {

            result = effect.getType() == effectType;

            if(result) {

                break;
            }
        }
        return result;
    }

    @Override
    public boolean hasCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        return hasCustomPotion(nbtCompound, effectType.to());
    }
}
