package com.minecraft.moonlake._temp.itemlib.potion;

import com.minecraft.moonlake._temp.type.potion.PotionEnum;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * <h1>提供药水物品栈的API函数 (创建、添加效果等等)</h1>
 * @version 1.0
 * @author Month_Light
 */
public interface Potionlib {

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion 药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    ItemStack createPotion(PotionEnum potion, String potionEffect);

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion 药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 数量
     * @return 药水 ItemStack
     */
    ItemStack createPotion(PotionEnum potion, String potionEffect, int amount);

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion 药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @param name 药水名称
     * @return 药水 ItemStack
     */
    ItemStack createPotion(PotionEnum potion, String potionEffect, int amount, String name);

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    ItemStack createBasePotion(String potionEffect);

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @return 药水 ItemStack
     */
    ItemStack createBasePotion(String potionEffect, int amount);

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @param name 药水名称
     * @return 药水 ItemStack
     */
    ItemStack createBasePotion(String potionEffect, int amount, String name);

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    ItemStack createSplashPotion(String potionEffect);

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @return 药水 ItemStack
     */
    ItemStack createSplashPotion(String potionEffect, int amount);

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @param name 药水名称
     * @return 药水 ItemStack
     */
    ItemStack createSplashPotion(String potionEffect, int amount, String name);

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    ItemStack createLingeringPotion(String potionEffect);

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @return 药水 ItemStack
     */
    ItemStack createLingeringPotion(String potionEffect, int amount);

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount 药水数量
     * @param name 药水名称
     * @return 药水 ItemStack
     */
    ItemStack createLingeringPotion(String potionEffect, int amount, String name);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param name 药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, String name, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion 药水类型
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, String name, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, String name, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, String name, CustomPotionEffect... customPotionEffect);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount 药水数量
     * @param name 药水名称
     * @param id 药水ID
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion 药水物品栈
     * @param customPotionEffect 自定义药水效果数组
     * @return 添加药水效果后的 ItemStack
     */
    ItemStack addCustomPotion(ItemStack potion, CustomPotionEffect... customPotionEffect);

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion 药水物品栈
     * @param id 效果ID
     * @param amplifier 效果等级
     * @param duration 效果时间
     * @return 添加药水效果后的 ItemStack
     */
    ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration);

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion 药水物品栈
     * @param id 效果ID
     * @param amplifier 效果等级
     * @param duration 效果时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 添加药水效果后的 ItemStack
     */
    ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration, boolean showParticles);

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion 药水物品栈
     * @param id 效果ID
     * @param amplifier 效果等级
     * @param duration 效果时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 添加药水效果后的 ItemStack
     */
    ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration, boolean ambient, boolean showParticles);

    /**
     * 获取药水物品栈的自定义药水效果集合
     *
     * @param potion 药水物品栈
     * @return 自定义药水效果集合 如果药水没有自定义效果则返回空集合
     */
    Set<CustomPotionEffect> getCustomPoionEffectList(ItemStack potion);
}
