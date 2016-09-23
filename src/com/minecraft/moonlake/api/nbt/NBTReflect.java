package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTConvertException;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.reflect.Reflect;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by MoonLake on 2016/9/22.
 */
public abstract class NBTReflect {

    private static NBTReflect handle;

    static {

        boolean raw = false;

        try {

            Reflect.getConstructor("NBTTagByte", Reflect.PackageType.MINECRAFT_SERVER, byte.class);
            raw = true;
        }
        catch (Exception e) {

        }
        handle = raw ? new NBTReflectSpigotRaw() : null;
    }

    public static NBTReflect getHandle() {

        return handle;
    }

    public static NBTCompound fromNBTCompound(Object tag) {

        return tag == null ? null : new NBTCompoundExpression(tag);
    }

    public static NBTCompound fromNBTCompoundCopy(Object tag) {

        return tag == null ? null : fromNBTCompound(NBTReflect.getHandle().cloneTag(tag));
    }

    public static NBTList fromNBTList(Object tag) {

        return tag == null ? null : new NBTListExpression(tag);
    }

    public static NBTList fromNBTListCopy(Object tag) {

        return tag == null ? null : new NBTListExpression(NBTReflect.getHandle().cloneTag(tag));
    }

    public Object createTag(Object value) throws NBTConvertException {

        if(value == null) {

            return null;
        }
        else if(value instanceof NBTCompoundExpression) {

            return cloneTag(((NBTCompoundExpression) value).getHandle());
        }
        else if(value instanceof NBTListExpression) {

            return cloneTag(((NBTListExpression) value).getHandle());
        }
        else if(value instanceof Collection) {

            return new NBTListExpression(((Collection) value)).getHandle();
        }
        else if(value instanceof Boolean) {

            if((Boolean) value) {

                return createTagByte((byte) 1);
            }
            return (byte) 0;
        }
        else if(value instanceof Byte) {

            return createTagByte((Byte) value);
        }
        else if(value instanceof Short) {

            return createTagShort((Short) value);
        }
        else if(value instanceof Integer) {

            return createTagInt((Integer) value);
        }
        else if(value instanceof Long) {

            return createTagLong((Long) value);
        }
        else if(value instanceof Float) {

            return createTagFloat((Float) value);
        }
        else if(value instanceof Double) {

            return createTagDouble((Double) value);
        }
        else if(value instanceof byte[]) {

            return createTagByteArray((byte[]) value);
        }
        else if(value instanceof String) {

            return createTagString((String) value);
        }
        else if(value instanceof int[]) {

            return createTagIntArray((int[]) value);
        }
        else if(value instanceof Object[]) {

            return new NBTListExpression(Arrays.asList((Object[]) value)).getHandle();
        }
        throw new NBTConvertException("The nbt convert exception: " + value.getClass().getSimpleName());
    }

    public Object createTag(Object value, byte type) throws NBTConvertException {

        return createTag(convertValue(value, type));
    }

    public Object convertValue(Object value, byte type) throws NBTConvertException {

        switch (type) {

            case 0:
                if(value == null) {

                    return null;
                }
            case 1:
                if(value instanceof Number) {

                    return ((Number) value).byteValue();
                }
                if(value instanceof String) {

                    return new Byte(((String) value));
                }
            case 2:
                if(value instanceof Number) {

                    return ((Number) value).shortValue();
                }
                if(value instanceof String) {

                    return new Short(((String) value));
                }
            case 3:
                if(value instanceof Number) {

                    return ((Number) value).intValue();
                }
                if(value instanceof String) {

                    return new Integer(((String) value));
                }
            case 4:
                if(value instanceof Number) {

                    return ((Number) value).longValue();
                }
                if(value instanceof String) {

                    return new Long(((String) value));
                }
            case 5:
                if(value instanceof Number) {

                    return ((Number) value).floatValue();
                }
                if(value instanceof String) {

                    return new Float(((String) value));
                }
            case 6:
                if(value instanceof Number) {

                    return ((Number) value).doubleValue();
                }
                if(value instanceof String) {

                    return new Double(((String) value));
                }
            case 7:
                if(value instanceof byte[]) {

                    return value;
                }
                if(value instanceof int[]) {

                    int[] values = (int[]) value;
                    byte[] temp = new byte[values.length];

                    for(int i = 0; i < temp.length; i++) {

                        temp[i] = (byte) values[i];
                    }
                    return temp;
                }
                if(value instanceof Collection) {

                    Collection values = (Collection) value;
                    byte[] temp = new byte[values.size()];
                    int index = 0;

                    for(final Object value0 : values) {

                        if(!(value0 instanceof Number)) {

                            throw new NBTConvertException("The nbt convert exception: " + value0.getClass().getSimpleName());
                        }
                        temp[index++] = ((Number) value0).byteValue();
                    }
                    return temp;
                }
                if(value instanceof String) {

                    return ((String) value).getBytes(Charset.forName("utf-8"));
                }
            case 8:
                return value.toString();
            case 9:
                if(value instanceof Collection || value instanceof Object[]) {

                    return value;
                }
                if(value instanceof byte[]) {

                    List<Byte> list = new ArrayList<>(((byte[]) value).length);

                    for(final byte value0 : ((byte[]) value)){

                        list.add(value0);
                    }
                    return list;
                }
                if(value instanceof int[]) {

                    List<Integer> list = new ArrayList<>(((int[]) value).length);

                    for(final int value0 : ((int[]) value)){

                        list.add(value0);
                    }
                    return list;
                }
            case 10:
                if(value instanceof Map) {

                    return value;
                }
            case 11:
                if(value instanceof int[]) {

                    return value;
                }
                if(value instanceof byte[]) {

                    byte[] values = (byte[]) value;
                    int[] temp = new int[values.length];

                    for(int i = 0; i < temp.length; i++) {

                        temp[i] = values[i];
                    }
                    return temp;
                }
                if(value instanceof Collection) {

                    Collection values = (Collection) value;
                    int[] temp = new int[values.size()];
                    int index = 0;

                    for(final Object value0 : values) {

                        if(!(value0 instanceof Number)) {

                            throw new NBTConvertException("The nbt convert exception: " + value0.getClass().getSimpleName());
                        }
                        temp[index++] = ((Number) value0).intValue();
                    }
                    return temp;
                }
            default:
                throw new NBTConvertException("The nbt convert exception: " + value.getClass().getSimpleName());
        }
    }

    public void setValue(Object tag, Object value) {

        setRawValue(tag, convertValue(value, getTagType(tag)));
    }

    public void writeTagToOutput(DataOutput output, Object tag) throws IOException {

        output.writeByte(getTagType(tag));
        output.writeUTF(getTagName(tag));
        writeTagDataToOutput(output, tag);
    }

    public Object readTagOfType(DataInput input, byte type) throws IOException {

        Object tag = createTagOfType(type);
        readInputToTag(input, type);
        return tag;
    }

    public Object readTag(DataInput input) throws IOException {

        byte type = input.readByte();
        input.readUTF();
        return readTagOfType(input, type);
    }

    public abstract Object createTagByte(Byte handle);

    public abstract Object createTagShort(Short handle);

    public abstract Object createTagInt(Integer handle);

    public abstract Object createTagLong(Long handle);

    public abstract Object createTagFloat(Float handle);

    public abstract Object createTagDouble(Double handle);

    public abstract Object createTagString(String handle);

    public abstract Object createTagByteArray(byte[] handle);

    public abstract Object createTagIntArray(int[] handle);

    public abstract Object getValue(Object tag) throws NBTException;

    protected abstract void setRawValue(Object tag, Object value)  throws NBTException;

    public abstract byte getTagType(Object tag)  throws NBTException;

    public abstract Object createTagOfType(byte type) throws NBTException;

    public abstract Object cloneTag(Object tag) throws NBTException;

    public abstract Object createTagCompound() throws NBTException;

    public abstract Object createTagList() throws NBTException;

    public abstract Map<String, Object> getHandleMap(Object nbtTagCompound) throws NBTException;

    public abstract List<Object> getHandleList(Object nbtTagList) throws NBTException;

    public abstract byte getNBTTagListType(Object nbtTagList) throws NBTException;

    public abstract void setNBTTagListType(Object nbtTagList, byte type) throws NBTException;

    public abstract boolean isNBTTag(Object tag) throws NBTException;

    public abstract void readInputToTag(DataInput input, Object tag) throws IOException;

    public abstract void writeTagDataToOutput(DataOutput output, Object tag) throws IOException;

    public abstract void setTagName(Object tag, String name);

    public abstract String getTagName(Object tag);
}
