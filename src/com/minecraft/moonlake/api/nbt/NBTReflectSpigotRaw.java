package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.validate.Validate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>NBTReflectSpigotRaw</h1>
 * NBT Spigot 反射源实现类
 *
 * @version 1.0
 * @author Month_Light
 */
final class NBTReflectSpigotRaw extends NBTReflect {

    private Class<?> CLASS_NBTBASE;
    private Class<?> CLASS_NBTTAGBYTE;
    private Class<?> CLASS_NBTTAGSHORT;
    private Class<?> CLASS_NBTTAGINT;
    private Class<?> CLASS_NBTTAGLONG;
    private Class<?> CLASS_NBTTAGFLOAT;
    private Class<?> CLASS_NBTTAGDOUBLE;
    private Class<?> CLASS_NBTTAGSTRING;
    private Class<?> CLASS_NBTTAGBYTEARRAY;
    private Class<?> CLASS_NBTTAGINTARRAY;
    private Class<?> CLASS_NBTTAGLIST;
    private Class<?> CLASS_NBTTAGCOMPOUND;
    private Constructor<?> CONSTRUCTOR_NBTTAGBYTE;
    private Constructor<?> CONSTRUCTOR_NBTTAGSHORT;
    private Constructor<?> CONSTRUCTOR_NBTTAGINT;
    private Constructor<?> CONSTRUCTOR_NBTTAGLONG;
    private Constructor<?> CONSTRUCTOR_NBTTAGFLOAT;
    private Constructor<?> CONSTRUCTOR_NBTTAGDOUBLE;
    private Constructor<?> CONSTRUCTOR_NBTTAGSTRING;
    private Constructor<?> CONSTRUCTOR_NBTTAGBYTEARRAY;
    private Constructor<?> CONSTRUCTOR_NBTTAGINTARRAY;
    private Constructor<?> CONSTRUCTOR_NBTTAGLIST;
    private Constructor<?> CONSTRUCTOR_NBTTAGCOMPOUND;
    private Constructor<?> CONSTRUCTOR_NBTREADLIMITER;
    private Field FIELD_NBTTAGBYTE_DATA;
    private Field FIELD_NBTTAGSHORT_DATA;
    private Field FIELD_NBTTAGINT_DATA;
    private Field FIELD_NBTTAGLONG_DATA;
    private Field FIELD_NBTTAGFLOAT_DATA;
    private Field FIELD_NBTTAGDOUBLE_DATA;
    private Field FIELD_NBTTAGSTRING_DATA;
    private Field FIELD_NBTTAGBYTEARRAY_DATA;
    private Field FIELD_NBTTAGINTARRAY_DATA;
    private Field FIELD_NBTTAGLIST_DATA;
    private Field FIELD_NBTTAGLIST_TYPE;
    private Field FIELD_NBTTAGCOMPOUND_DATA;
    private Method METHOD_NBTBASE_GETTYPEID;
    private Method METHOD_NBTBASE_CREATETAG;
    private Method METHOD_NBTBASE_CLONE;
    private Method METHOD_NBTBASE_WRITE;
    private Method METHOD_NBTBASE_LOAD;

    /**
     * NBT Spigot 反射源实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTReflectSpigotRaw() throws NBTInitializeException {

        try {

            PackageType MINECRAFT_SERVER = PackageType.MINECRAFT_SERVER;

            // NBT Tag Class
            CLASS_NBTBASE = MINECRAFT_SERVER.getClass("NBTBase");
            CLASS_NBTTAGBYTE = MINECRAFT_SERVER.getClass("NBTTagByte");
            CLASS_NBTTAGSHORT = MINECRAFT_SERVER.getClass("NBTTagShort");
            CLASS_NBTTAGINT = MINECRAFT_SERVER.getClass("NBTTagInt");
            CLASS_NBTTAGLONG = MINECRAFT_SERVER.getClass("NBTTagLong");
            CLASS_NBTTAGFLOAT = MINECRAFT_SERVER.getClass("NBTTagFloat");
            CLASS_NBTTAGDOUBLE = MINECRAFT_SERVER.getClass("NBTTagDouble");
            CLASS_NBTTAGSTRING = MINECRAFT_SERVER.getClass("NBTTagString");
            CLASS_NBTTAGBYTEARRAY = MINECRAFT_SERVER.getClass("NBTTagByteArray");
            CLASS_NBTTAGINTARRAY = MINECRAFT_SERVER.getClass("NBTTagIntArray");
            CLASS_NBTTAGLIST = MINECRAFT_SERVER.getClass("NBTTagList");
            CLASS_NBTTAGCOMPOUND = MINECRAFT_SERVER.getClass("NBTTagCompound");

            // NBT Tag Constructor
            CONSTRUCTOR_NBTTAGBYTE = getConstructor(CLASS_NBTTAGBYTE, byte.class);
            CONSTRUCTOR_NBTTAGSHORT = getConstructor(CLASS_NBTTAGSHORT, short.class);
            CONSTRUCTOR_NBTTAGINT = getConstructor(CLASS_NBTTAGINT, int.class);
            CONSTRUCTOR_NBTTAGLONG = getConstructor(CLASS_NBTTAGLONG, long.class);
            CONSTRUCTOR_NBTTAGFLOAT = getConstructor(CLASS_NBTTAGFLOAT, float.class);
            CONSTRUCTOR_NBTTAGDOUBLE = getConstructor(CLASS_NBTTAGDOUBLE, double.class);
            CONSTRUCTOR_NBTTAGSTRING = getConstructor(CLASS_NBTTAGSTRING, String.class);
            CONSTRUCTOR_NBTTAGBYTEARRAY = getConstructor(CLASS_NBTTAGBYTEARRAY, byte[].class);
            CONSTRUCTOR_NBTTAGINTARRAY = getConstructor(CLASS_NBTTAGINTARRAY, int[].class);
            CONSTRUCTOR_NBTTAGLIST = getConstructor(CLASS_NBTTAGLIST);
            CONSTRUCTOR_NBTTAGCOMPOUND = getConstructor(CLASS_NBTTAGCOMPOUND);

            // NBT Tag Field Data
            FIELD_NBTTAGBYTE_DATA = getField(CLASS_NBTTAGBYTE, true, "data");
            FIELD_NBTTAGSHORT_DATA = getField(CLASS_NBTTAGSHORT, true, "data");
            FIELD_NBTTAGINT_DATA = getField(CLASS_NBTTAGINT, true, "data");
            FIELD_NBTTAGLONG_DATA = getField(CLASS_NBTTAGLONG, true, "data");
            FIELD_NBTTAGFLOAT_DATA = getField(CLASS_NBTTAGFLOAT, true, "data");
            FIELD_NBTTAGDOUBLE_DATA = getField(CLASS_NBTTAGDOUBLE, true, "data");
            FIELD_NBTTAGSTRING_DATA = getField(CLASS_NBTTAGSTRING, true, "data");
            FIELD_NBTTAGBYTEARRAY_DATA = getField(CLASS_NBTTAGBYTEARRAY, true, "data");
            FIELD_NBTTAGINTARRAY_DATA = getField(CLASS_NBTTAGINTARRAY, true, "data");
            FIELD_NBTTAGLIST_DATA = getField(CLASS_NBTTAGLIST, true, "list");
            FIELD_NBTTAGLIST_TYPE = getField(CLASS_NBTTAGLIST, true, "type");
            FIELD_NBTTAGCOMPOUND_DATA = getField(CLASS_NBTTAGCOMPOUND, true, "map");

            // NBT Tag Method
            METHOD_NBTBASE_GETTYPEID = getMethod(CLASS_NBTBASE, "getTypeId");
            METHOD_NBTBASE_CREATETAG = getDeclaredMethod(CLASS_NBTBASE, "createTag", byte.class);
            METHOD_NBTBASE_CLONE = getMethod(CLASS_NBTBASE, "clone");
            METHOD_NBTBASE_WRITE = getDeclaredMethod(CLASS_NBTBASE, "write", DataOutput.class);

            // NBT ReadLimiter Class
            Class<?> CLASS_NBTREADLIMITER = MINECRAFT_SERVER.getClass("NBTReadLimiter");

            CONSTRUCTOR_NBTREADLIMITER = getConstructor(CLASS_NBTREADLIMITER, long.class);
            METHOD_NBTBASE_LOAD = getDeclaredMethod(CLASS_NBTBASE, "load", DataInput.class, int.class, CLASS_NBTREADLIMITER);
        }
        catch (Exception e) {

            throw new NBTInitializeException("The nbt spigot reflect raw initialize exception.", e);
        }
    }

    private Object createTag0(Constructor<?> target, Object... args) throws NBTException {

        try {

            return target.newInstance(args);
        }
        catch (Exception e) {

            throw new NBTException("The nbt create tag exception.", e);
        }
    }

    @Override
    public Object createTagByte(Byte value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGBYTE, (byte) (value == null ? 0 : value));
    }

    @Override
    public Object createTagShort(Short value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGSHORT, (short) (value == null ? 0 : value));
    }

    @Override
    public Object createTagInt(Integer value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGINT, (int) (value == null ? 0 : value));
    }

    @Override
    public Object createTagLong(Long value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGLONG, (long) (value == null ? 0L : value));
    }

    @Override
    public Object createTagFloat(Float value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGFLOAT, (float) (value == null ? 0f : value));
    }

    @Override
    public Object createTagDouble(Double value) throws NBTException {

        return createTag0(CONSTRUCTOR_NBTTAGDOUBLE, (double) (value == null ? 0d : value));
    }

    @Override
    public Object createTagString(String value) throws NBTException {

        Validate.notNull(value, "The nbt tag string data value object is null.");

        return createTag0(CONSTRUCTOR_NBTTAGSTRING, value);
    }

    @Override
    public Object createTagByteArray(byte[] value) throws NBTException {

        Validate.notNull(value, "The nbt tag byte[] data value object is null.");

        return createTag0(CONSTRUCTOR_NBTTAGBYTEARRAY, new Object[] { value });
    }

    @Override
    public Object createTagIntArray(int[] value) throws NBTException {

        Validate.notNull(value, "The nbt tag int[] data value object is null.");

        return createTag0(CONSTRUCTOR_NBTTAGINTARRAY, new Object[] { value });
    }

    @Override
    public Object getValue(Object tag) throws NBTException {

        if(tag == null) {

            return null;
        }
        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        try {

            switch (getTagType(tag)) {

                case 1:
                    return FIELD_NBTTAGBYTE_DATA.get(tag);
                case 2:
                    return FIELD_NBTTAGSHORT_DATA.get(tag);
                case 3:
                    return FIELD_NBTTAGINT_DATA.get(tag);
                case 4:
                    return FIELD_NBTTAGLONG_DATA.get(tag);
                case 5:
                    return FIELD_NBTTAGFLOAT_DATA.get(tag);
                case 6:
                    return FIELD_NBTTAGDOUBLE_DATA.get(tag);
                case 7:
                    return FIELD_NBTTAGBYTEARRAY_DATA.get(tag);
                case 8:
                    return FIELD_NBTTAGSTRING_DATA.get(tag);
                case 9:
                    return fromNBTList(tag);
                case 10:
                    return fromNBTCompound(tag);
                case 11:
                    return FIELD_NBTTAGINTARRAY_DATA.get(tag);
                default:
                    throw new NBTException("The nbt tag object is unknow class: " + tag.getClass());
            }
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag object get value exception.", e);
        }
    }

    @Override
    protected void setRawValue(Object tag, Object value) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        try {

            switch (getTagType(tag)) {

                case 1:
                    FIELD_NBTTAGBYTE_DATA.set(tag, value); break;
                case 2:
                    FIELD_NBTTAGSHORT_DATA.set(tag, value); break;
                case 3:
                    FIELD_NBTTAGINT_DATA.set(tag, value); break;
                case 4:
                    FIELD_NBTTAGLONG_DATA.set(tag, value); break;
                case 5:
                    FIELD_NBTTAGFLOAT_DATA.set(tag, value); break;
                case 6:
                    FIELD_NBTTAGDOUBLE_DATA.set(tag, value); break;
                case 7:
                    FIELD_NBTTAGBYTEARRAY_DATA.set(tag, value); break;
                case 8:
                    FIELD_NBTTAGSTRING_DATA.set(tag, value); break;
                case 9:
                    NBTList list = fromNBTList(tag);
                    list.clear();
                    list.addAll((Collection) value);
                    break;
                case 10:
                    NBTCompound compound = fromNBTCompound(tag);
                    compound.clear();
                    compound.putAll((Map) value);
                    break;
                case 11:
                    FIELD_NBTTAGINTARRAY_DATA.get(tag); break;
                default:
                    throw new NBTException("The nbt tag object is unknow class: " + tag.getClass());
            }
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag object set value exception.", e);
        }
    }

    @Override
    public byte getTagType(Object tag) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        byte type = 0;

        try {

            type = (byte) METHOD_NBTBASE_GETTYPEID.invoke(tag);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag get type exception.", e);
        }
        return type;
    }

    @Override
    public Object createTagOfType(byte type) throws NBTException {

        try {

            return METHOD_NBTBASE_CREATETAG.invoke(null, type);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag create exception.", e);
        }
    }

    @Override
    public Object cloneTag(Object tag) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            return METHOD_NBTBASE_CLONE.invoke(tag);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag clone exception.", e);
        }
    }

    @Override
    public Object createTagCompound() throws NBTException {

        try {

            return CONSTRUCTOR_NBTTAGCOMPOUND.newInstance();
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag compound create exception.", e);
        }
    }

    @Override
    public Object createTagList() throws NBTException {

        try {

            return CONSTRUCTOR_NBTTAGLIST.newInstance();
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list create exception.", e);
        }
    }

    @Override
    public Map<String, Object> getHandleMap(Object nbtTagCompound) throws NBTException {

        Validate.notNull(nbtTagCompound, "The nbt tag compound object is null.");

        try {

            return (Map) FIELD_NBTTAGCOMPOUND_DATA.get(nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag compound get map field exception.", e);
        }
    }

    @Override
    public List<Object> getHandleList(Object nbtTagList) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            return (List) FIELD_NBTTAGLIST_DATA.get(nbtTagList);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list get list field exception.", e);
        }
    }

    @Override
    public byte getNBTTagListType(Object nbtTagList) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            return (byte) FIELD_NBTTAGLIST_TYPE.get(nbtTagList);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list get type exception.", e);
        }
    }

    @Override
    public void setNBTTagListType(Object nbtTagList, byte type) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            FIELD_NBTTAGLIST_TYPE.set(nbtTagList, type);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list set type exception.", e);
        }
    }

    @Override
    public boolean isNBTTag(Object tag) {

        return tag != null && CLASS_NBTBASE.isInstance(tag);
    }

    @Override
    public void readInputToTag(DataInput input, Object tag) throws IOException {

        Validate.notNull(input, "The data input object is null.");
        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            long readLimit = 4611686018427387903L;
            METHOD_NBTBASE_LOAD.invoke(tag, input, 0, CONSTRUCTOR_NBTREADLIMITER.newInstance(readLimit));
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag read input to tag exception.", e);
        }
    }

    @Override
    public void writeTagDataToOutput(DataOutput output, Object tag) throws IOException {

        Validate.notNull(output, "The data output object is null.");
        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            METHOD_NBTBASE_WRITE.invoke(tag, output);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag write tag to output exception.", e);
        }
    }

    @Override
    @Deprecated
    public void setTagName(Object tag, String name) throws NBTException {


    }

    @Override
    @Deprecated
    public String getTagName(Object tag) throws NBTException {

        return "";
    }
}
