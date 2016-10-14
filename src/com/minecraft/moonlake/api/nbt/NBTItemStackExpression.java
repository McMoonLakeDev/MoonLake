package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>NBTItemStackExpression</h1>
 * NBT 物品栈接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NBTItemStackExpression implements NBTItemStack {

    private Class<?> CLASS_CRAFTITEMSTACK;
    private Class<?> CLASS_ITEMSTACK;
    private Class<?> CLASS_ITEMMETA;
    private Class<?> CLASS_ITEM;
    private Constructor<?> CONSTRUCTOR_CRAFTITEMSTACK;
    private Constructor<?> CONSTRUCTOR_ITEMSTACK;
    private Method METHOD_ASNMSCOPY;
    private Method METHOD_ASCRAFTMIRROR;
    private Method METHOD_GETBYID;
    private Field FIELD_ITEMHANDLE;
    private Field FIELD_TAG;

    public NBTItemStackExpression() throws NBTInitializeException {

        try {

            // NBT ItemStack Class
            CLASS_CRAFTITEMSTACK = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            CLASS_ITEMSTACK = PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            CLASS_ITEMMETA = Class.forName("org.bukkit.inventory.meta.ItemMeta");
            CLASS_ITEM = PackageType.MINECRAFT_SERVER.getClass("Item");

            // NBT ItemStack Method
            METHOD_ASNMSCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asNMSCopy", ItemStack.class);
            METHOD_ASCRAFTMIRROR = getMethod(CLASS_CRAFTITEMSTACK, "asCraftMirror", CLASS_ITEMSTACK);
            METHOD_GETBYID = getMethod(CLASS_ITEM, "getById", int.class);

            if(METHOD_ASNMSCOPY == null || METHOD_ASCRAFTMIRROR == null) {
                // NBT ItemStack Constructor
                CONSTRUCTOR_CRAFTITEMSTACK = getDeclaredConstructor(CLASS_CRAFTITEMSTACK, CLASS_ITEMSTACK);
                CONSTRUCTOR_ITEMSTACK = getDeclaredConstructor(CLASS_ITEMSTACK, CLASS_ITEM, int.class, int.class);
            }
            // NBT ItemStack Field
            FIELD_TAG = getField(CLASS_ITEMSTACK, true, "tag");
            FIELD_ITEMHANDLE = getField(CLASS_CRAFTITEMSTACK, true, "handle");
        }
        catch (Exception e) {

            throw new NBTInitializeException("The nbt itemstack reflect raw initialize exception.", e);
        }
    }

    private Object getTag(Object nmsItemStack) throws NBTException {

        try {

            return FIELD_TAG.get(nmsItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTag(Object nmsItemStack, Object nbtTagCompound) throws NBTException {

        try {

            FIELD_TAG.set(nmsItemStack, nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object createNMSItemStack(ItemStack itemStack) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            if (METHOD_ASNMSCOPY != null) {

                return METHOD_ASNMSCOPY.invoke(null, itemStack);
            }
            else {

                int amount = itemStack.getAmount();
                byte data = itemStack.getData().getData();
                Object ITEM = METHOD_GETBYID.invoke(null, itemStack.getTypeId());

                return CONSTRUCTOR_ITEMSTACK.newInstance(ITEM, amount, (int) data);
            }
        }
        catch (Exception e) {

            throw new NBTException("The create nms itemstack exception.", e);
        }
    }

    @Override
    public ItemStack createCraftItemStack(Object nmsItemStack) throws NBTException {

        Validate.notNull(nmsItemStack, "The nms itemstack object is null.");

        try {

            if(METHOD_ASCRAFTMIRROR != null) {

                return (ItemStack) METHOD_ASCRAFTMIRROR.invoke(null, nmsItemStack);
            }
            else {

                return (ItemStack) CONSTRUCTOR_CRAFTITEMSTACK.newInstance(nmsItemStack);
            }
        }
        catch (Exception e) {

            throw new NBTException("The create craftbukkit itemstack exception.", e);
        }
    }

    private Object getHandle(ItemStack itemStack) throws NBTException {

        try {

            return FIELD_ITEMHANDLE.get(itemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get nms itemstack handle exception.", e);
        }
    }

    @Override
    public ItemStack createCraftItemStack(ItemStack itemStack) throws NBTException {

        return createCraftItemStack(createNMSItemStack(itemStack));
    }

    @Override
    public void setTag(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            if(CLASS_CRAFTITEMSTACK.isInstance(itemStack)) {

                setTagCraftBukkit(itemStack, nbtTagCompound);
            }
            else if(CLASS_ITEMMETA != null) {

                setTagOrigin(itemStack, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    @Override
    public Object getTag(ItemStack itemStack) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            if(CLASS_CRAFTITEMSTACK.isInstance(itemStack)) {

                return getTagCraftBukkit(itemStack);
            }
            else if(CLASS_ITEMMETA != null) {

                return getTagOrigin(itemStack);
            }
            return null;
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTagCraftBukkit(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object NMSItemStack = getHandle(itemStack);
            setTag(NMSItemStack, nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    private Object getTagCraftBukkit(ItemStack itemStack) throws NBTException {

        try {

            Object NMSItemStack = getHandle(itemStack);
            return getTag(NMSItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTagOrigin(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        if(nbtTagCompound == null) {

            itemStack.setItemMeta(null);
        }
        else {

            ItemStack copyNMSItemStack = createCraftItemStack(itemStack);

            try {

                setTagCraftBukkit(copyNMSItemStack, nbtTagCompound);
                ItemMeta itemMeta = copyNMSItemStack.getItemMeta();
                itemStack.setItemMeta(itemMeta);
            }
            catch (Exception e) {

                throw new NBTException("The set itemstack nbt tag exception.", e);
            }
        }
    }

    private Object getTagOrigin(ItemStack itemStack) throws NBTException {

        ItemStack copyNMSItemStack = createCraftItemStack(itemStack);

        try {

            ItemMeta itemMeta = itemStack.getItemMeta();
            copyNMSItemStack.setItemMeta(itemMeta);
            return getTagCraftBukkit(copyNMSItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }
}
