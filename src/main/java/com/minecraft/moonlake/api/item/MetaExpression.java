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

import com.minecraft.moonlake.api.item.meta.MetaLibrary;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.manager.RandomManager;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/**
 * <h1>MetaExpression</h1>
 * 物品栈元属性接口实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
class MetaExpression extends AttributeExpression implements MetaLibrary {

    /**
     * 物品栈元属性接口实现类构造函数
     */
    public MetaExpression() {

    }

    @Override
    public ItemStack setDisplayName(ItemStack itemStack, String displayName) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(displayName, "The itemstack displayName object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null) {

            itemMeta.setDisplayName(StringUtil.toColor(displayName));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    @Override
    public ItemStack setLocalizedName(ItemStack itemStack, String localizedName) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(localizedName, "The itemstack localizedName object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        nbtCompound.put("LocName", localizedName);
        nbtCompound.write(itemStack);

        return itemStack;
    }

    @Override
    public String getLocalizedName(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().read(itemStack);
        return nbtCompound != null ? nbtCompound.getString("LocName") : null;
    }

    @Override
    public boolean hasLocalizedName(ItemStack itemStack) {

        return getLocalizedName(itemStack) != null;
    }

    @Override
    public ItemStack setAmount(ItemStack itemStack, int amount) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        itemStack.setAmount(amount);

        return itemStack;
    }

    @Override
    public ItemStack setDurability(ItemStack itemStack, int durability) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        itemStack.setDurability((short) durability);

        return itemStack;
    }

    @Override
    public int getDurability(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return itemStack.getDurability();
    }

    @Override
    public ItemStack resetDurability(ItemStack itemStack) {

        return setDurability(itemStack, itemStack.getType().getMaxDurability()); // set durability to max be recipe itemstack
    }

    @Override
    public ItemStack addDurability(ItemStack itemStack, int durability) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return setDurability(itemStack, itemStack.getDurability() - durability); // set durability subtract be add durability
    }

    @Override
    public ItemStack takeDurability(ItemStack itemStack, int durability) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return setDurability(itemStack, itemStack.getDurability() + durability); // set durability add be add durability
    }

    @Override
    public List<String> getLore(ItemStack itemStack) {

        return getLore(itemStack, false);
    }

    @Override
    public List<String> getLore(ItemStack itemStack, boolean ignoreColor) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null || !itemMeta.hasLore()) {

            return null;
        }
        if(!ignoreColor) {

            return itemMeta.getLore();
        }
        return StringUtil.stripColor(itemMeta.getLore());
    }

    @Override
    public ItemStack setLore(ItemStack itemStack, String... lore) {

        return setLore(itemStack, Arrays.asList(lore));
    }

    @Override
    public ItemStack setLore(ItemStack itemStack, Collection<? extends String> lore) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(lore, "The itemstack lore object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null) {

            itemMeta.setLore(new ArrayList<>(lore));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, String... lore) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(lore, "The itemstack lore object is null.");

        if(lore.length <= 0) {

            return itemStack;
        }
        List<String> sourceLore = getLore(itemStack);

        if(sourceLore == null) {

            sourceLore = new ArrayList<>();
        }
        sourceLore.addAll(Arrays.asList(lore));

        return setLore(itemStack, sourceLore);
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, Collection<? extends String> lore) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(lore, "The itemstack lore object is null.");

        if(lore.size() <= 0) {

            return itemStack;
        }
        List<String> sourceLore = getLore(itemStack);

        if(sourceLore == null) {

            sourceLore = new ArrayList<>();
        }
        sourceLore.addAll(lore);

        return setLore(itemStack, sourceLore);
    }

    @Override
    public ItemStack clearLore(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return setLore(itemStack, new ArrayList<>());
    }

    @Override
    public boolean hasLore(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        List<String> lore = getLore(itemStack);

        return lore != null && lore.size() > 0;
    }

    @Override
    public boolean containLore(ItemStack itemStack, String... lore) {

        Validate.notNull(lore, "The itemstack lore object is null.");

        return containLore(itemStack, Arrays.asList(lore));
    }

    @Override
    public boolean containLore(ItemStack itemStack, Collection<? extends String> lore) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(lore, "The itemstack lore object is null.");

        List<String> sourceLore = getLore(itemStack);

        if(sourceLore == null) {

            return false;
        }
        boolean state = false;

        for(final String str : lore) {

            state = sourceLore.contains(str);

            if(!state) {

                break;
            }
        }
        return state;
    }

    @Override
    public boolean containLoreIgnoreColor(ItemStack itemStack, String... lore) {

        Validate.notNull(lore, "The itemstack lore object is null.");

        return containLoreIgnoreColor(itemStack, Arrays.asList(lore));
    }

    @Override
    public boolean containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(lore, "The itemstack lore object is null.");

        List<String> sourceLore = getLore(itemStack, true);

        if(sourceLore == null) {

            return false;
        }
        boolean state = false;

        for(final String str : lore) {

            state = sourceLore.contains(str);

            if(!state) {

                break;
            }
        }
        return state;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Map<Enchantment, Integer> enchantmentMap = itemStack.getEnchantments();

        return enchantmentMap != null && enchantmentMap.size() > 0 ? enchantmentMap : null;
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        itemStack.addUnsafeEnchantment(enchantment, level);

        return itemStack;
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        Validate.notNull(enchantmentMap, "The itemstack enchantment map object is null.");

        if(!enchantmentMap.isEmpty()) {

            for(Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {

                itemStack = addEnchantment(itemStack, entry.getKey(), entry.getValue());
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(enchantment, "The itemstack enchantment object is null.");
        Validate.isTrue(level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel(), "The itemstack enchantment level not safe.");
        Validate.isTrue(enchantment.canEnchantItem(itemStack), "The itemstack not ench this enchantment: " + enchantment.getName());

        itemStack.addUnsafeEnchantment(enchantment, level);

        return itemStack;
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        Validate.notNull(enchantmentMap, "The itemstack enchantment map object is null.");

        if(enchantmentMap.size() > 0) {

            for(Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {

                itemStack = addSafeEnchantment(itemStack, entry.getKey(), entry.getValue());
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Enchantment enchantment) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        itemStack.removeEnchantment(enchantment);

        return itemStack;
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments) {

        Validate.notNull(enchantments, "The itemstack enchantment collection object is null.");

        if(enchantments.size() > 0) {

            for(final Enchantment enchantment : enchantments) {

                itemStack = removeEnchantment(itemStack, enchantment);
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack clearEnchantment(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack enchantment collection object is null.");

        Map<Enchantment, Integer> enchantmentMap = getEnchantments(itemStack);

        if(enchantmentMap != null && enchantmentMap.size() > 0) {

            for(final Enchantment enchantment : enchantmentMap.keySet()) {

                itemStack = removeEnchantment(itemStack, enchantment);
            }
        }
        return itemStack;
    }

    @Override
    public boolean hasEnchantment(ItemStack itemStack, Enchantment enchantment) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchant(enchantment);
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return addEnchantment(itemStack, enchantment.as(), level);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return addSafeEnchantment(itemStack, enchantment.as(), level);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return removeEnchantment(itemStack, enchantment.as());
    }

    @Override
    public boolean hasEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return hasEnchantment(itemStack, enchantment.as());
    }

    @Override
    public Set<ItemFlag> getFlags(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {

            return new HashSet<>();
        }
        return itemMeta.getItemFlags();
    }

    @Override
    public ItemStack addFlags(ItemStack itemStack, ItemFlag... flags) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(flags, "The itemstack flag object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {

            return itemStack;
        }
        itemMeta.addItemFlags(flags);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public ItemStack addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(flags, "The itemstack flag object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {

            return itemStack;
        }
        itemMeta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, ItemFlag... flags) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(flags, "The itemstack flag object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {

            return itemStack;
        }
        itemMeta.removeItemFlags(flags);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(flags, "The itemstack flag object is null.");

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {

            return itemStack;
        }
        itemMeta.removeItemFlags(flags.toArray(new ItemFlag[flags.size()]));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public boolean hasFlags(ItemStack itemStack, ItemFlag... flags) {

        Validate.notNull(flags, "The itemstack flag object is null.");

        Set<ItemFlag> flagSet = getFlags(itemStack);

        if(flagSet == null && flags.length > 0) {

            return false;
        }
        boolean state = false;

        if(flags.length > 0) {

            for(final ItemFlag flag : flags) {

                state = flagSet.contains(flag);

                if(!state) {

                    break;
                }
            }
        }
        return state;
    }

    @Override
    public ItemStack clearFlags(ItemStack itemStack) {

        Set<ItemFlag> flagSet = getFlags(itemStack);

        if(flagSet == null || flagSet.size() <= 0) {

            return itemStack;
        }
        return removeFlags(itemStack, flagSet);
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, Color color) {

        Validate.notNull(itemStack, "The itemstack flag object is null.");
        Validate.isTrue(ItemLibraryFactorys.item().isLeatherArmor(itemStack), "The itemstack type not leather.");
        Validate.notNull(color, "The itemstack leather color object is null.");

        /*if(!itemStack.hasItemMeta()) {

            return itemStack;
        }*/
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);

        return itemStack;
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, int red, int green, int blue) {

        return setLeatherColor(itemStack, Color.fromRGB(red, green, blue));
    }

    @Override
    public ItemStack setLeatherColorFromRandom(ItemStack itemStack) {

        return setLeatherColor(itemStack, RandomManager.nextColor());
    }
}
