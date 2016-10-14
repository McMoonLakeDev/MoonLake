package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.api.nbt.NBTList;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * <h1>AttributeExpression</h1>
 * 物品栈属性支持库接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class AttributeExpression implements AttributeLibrary {

    public AttributeExpression() {

    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        nbtCompound.put("Unbreakable", unbreakable ? 1 : 0);

        NBTFactory.get().write(itemStack, nbtCompound);

        return itemStack;
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().read(itemStack);

        if(nbtCompound == null) {

            return false;
        }
        return nbtCompound.getByte("Unbreakable") == 1;
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(attribute, "The itemstack attribute object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
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
        int version = Reflect.getServerVersionNumber();
        NBTCompound attributeNewCompound = NBTFactory.newCompound();
        AttributeModify.Slot attributeSlot = attribute.getSlot().get();

        if(attributeSlot != null && attributeSlot != AttributeModify.Slot.ALL && version >= 9) {

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

        NBTFactory.get().write(itemStack, nbtCompound);

        return itemStack;
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
    public Set<AttributeModify> getAttributes(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Set<AttributeModify> attributeModifyList = new HashSet<>();
        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        NBTList attributeModifiers = nbtCompound.getList("AttributeModifiers");

        if(attributeModifiers == null || attributeModifiers.isEmpty()) {

            return attributeModifyList;
        }
        int version = Reflect.getServerVersionNumber();

        for(final Object attributeObject : attributeModifiers) {

            if(attributeObject instanceof NBTCompound) {

                NBTCompound attributeCompound = (NBTCompound) attributeObject;
                AttributeModify.Type attributeType = AttributeModify.Type.fromType(attributeCompound.getString("AttributeName"));

                if(attributeType != null) {

                    AttributeModify.Slot attributeSlot = null;

                    if(version >= 9) {

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

        Validate.notNull(type, "The itemstack attribute type object is null.");

        Set<AttributeModify> attributeModifies = getAttributes(itemStack);

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
            effectNewCompound.put("Id", effect.getId().get());
            effectNewCompound.put("Amplifier", effect.getAmplifier().get());
            effectNewCompound.put("Duration", effect.getDuration().get());
            effectNewCompound.put("Ambient", (byte) (effect.getAmbient().get() ? 1 : 0));
            effectNewCompound.put("ShowParticles", (byte) (effect.getShowParticles().get() ? 1 : 0));

            customPotionEffects.add(effectNewCompound);
        }
        nbtCompound.put("CustomPotionEffects", customPotionEffects);

        NBTFactory.get().write(itemStack, nbtCompound);

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
    @SuppressWarnings("deprecation")
    public Set<PotionEffectCustom> getCustomPotion(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.isTrue(ItemLibraryFactorys.item().isPotion(itemStack), "The itemstack material object not potion.");

        Set<PotionEffectCustom> effects = new HashSet<>();
        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
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
    public boolean hasCustomPotion(ItemStack itemStack, PotionEffectType effectType) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        Set<PotionEffectCustom> effects = getCustomPotion(itemStack);

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
}
