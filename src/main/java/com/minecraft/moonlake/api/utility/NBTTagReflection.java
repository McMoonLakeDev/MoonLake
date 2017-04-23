/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.utility;

import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * <h1>NBTTagReflection</h1>
 * NBT 底层反射效用类 (函数暂时不提供doc文档)
 *
 * @version 1.0
 * @author Month_Light
 */
public class NBTTagReflection {

    private static volatile ConstructorAccessor<?> nbtTagByteConstructor;
    private static volatile ConstructorAccessor<?> nbtTagShortConstructor;
    private static volatile ConstructorAccessor<?> nbtTagIntConstructor;
    private static volatile ConstructorAccessor<?> nbtTagLongConstructor;
    private static volatile ConstructorAccessor<?> nbtTagFloatConstructor;
    private static volatile ConstructorAccessor<?> nbtTagDoubleConstructor;
    private static volatile ConstructorAccessor<?> nbtTagStringConstructor;
    private static volatile ConstructorAccessor<?> nbtTagByteArrayConstructor;
    private static volatile ConstructorAccessor<?> nbtTagIntArrayConstructor;
    private static volatile ConstructorAccessor<?> nbtTagListConstructor;
    private static volatile ConstructorAccessor<?> nbtTagCompoundConstructor;
    private static volatile ConstructorAccessor<?> nbtReadLimiterConstructor;
    private static volatile MethodAccessor nbtBaseGetTypeIdMethod;
    private static volatile MethodAccessor nbtBaseCreateTagMethod;
    private static volatile MethodAccessor nbtBaseCloneMethod;
    private static volatile MethodAccessor nbtBaseWriteMethod;
    private static volatile MethodAccessor nbtBaseLoadMethod;
    private static volatile FieldAccessor nbtTagByteDataField;
    private static volatile FieldAccessor nbtTagShortDataField;
    private static volatile FieldAccessor nbtTagIntDataField;
    private static volatile FieldAccessor nbtTagLongDataField;
    private static volatile FieldAccessor nbtTagFloatDataField;
    private static volatile FieldAccessor nbtTagDoubleDataField;
    private static volatile FieldAccessor nbtTagStringDataField;
    private static volatile FieldAccessor nbtTagByteArrayDataField;
    private static volatile FieldAccessor nbtTagIntArrayDataField;
    private static volatile FieldAccessor nbtTagListDataField;
    private static volatile FieldAccessor nbtTagListTypeField;
    private static volatile FieldAccessor nbtTagCompoundDataField;
    
    static {
    }
    
    private NBTTagReflection() {
    }

    public static ConstructorAccessor<?> getNBTTagByteConstructor() {
        if(nbtTagByteConstructor == null)
            nbtTagByteConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagByteClass(), byte.class);
        return nbtTagByteConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagShortConstructor() {
        if(nbtTagShortConstructor == null)
            nbtTagShortConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagShortClass(), short.class);
        return nbtTagShortConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagIntConstructor() {
        if(nbtTagIntConstructor == null)
            nbtTagIntConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagIntClass(), int.class);
        return nbtTagIntConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagLongConstructor() {
        if(nbtTagLongConstructor == null)
            nbtTagLongConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagLongClass(), long.class);
        return nbtTagLongConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagFloatConstructor() {
        if(nbtTagFloatConstructor == null)
            nbtTagFloatConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagFloatClass(), float.class);
        return nbtTagFloatConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagDoubleConstructor() {
        if(nbtTagDoubleConstructor == null)
            nbtTagDoubleConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagDoubleClass(), double.class);
        return nbtTagDoubleConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagStringConstructor() {
        if(nbtTagStringConstructor == null)
            nbtTagStringConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagStringClass(), String.class);
        return nbtTagStringConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagByteArrayConstructor() {
        if(nbtTagByteArrayConstructor == null)
            nbtTagByteArrayConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagByteArrayClass(), byte[].class);
        return nbtTagByteArrayConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagIntArrayConstructor() {
        if(nbtTagIntArrayConstructor == null)
            nbtTagIntArrayConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagIntArrayClass(), int[].class);
        return nbtTagIntArrayConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagListConstructor() {
        if(nbtTagListConstructor == null)
            nbtTagListConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagListClass());
        return nbtTagListConstructor;
    }

    public static ConstructorAccessor<?> getNBTTagCompoundConstructor() {
        if(nbtTagCompoundConstructor == null)
            nbtTagCompoundConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTTagCompoundClass());
        return nbtTagCompoundConstructor;
    }

    public static ConstructorAccessor<?> getNBTReadLimiterConstructor() {
        if(nbtReadLimiterConstructor == null)
            nbtReadLimiterConstructor = Accessors.getConstructorAccessor(MinecraftReflection.getNBTReadLimiterClass(), long.class);
        return nbtReadLimiterConstructor;
    }

    public static MethodAccessor getNBTBaseGetTypeIdMethod() {
        if(nbtBaseGetTypeIdMethod == null)
            nbtBaseGetTypeIdMethod = Accessors.getMethodAccessor(MinecraftReflection.getNBTBaseClass(), "getTypeId");
        return nbtBaseGetTypeIdMethod;
    }

    public static MethodAccessor getNBTBaseCreateTagMethod() {
        if(nbtBaseCreateTagMethod == null)
            nbtBaseCreateTagMethod = Accessors.getMethodAccessor(MinecraftReflection.getNBTBaseClass(), "createTag", byte.class);
        return nbtBaseCreateTagMethod;
    }

    public static MethodAccessor getNBTBaseCloneMethod() {
        if(nbtBaseCloneMethod == null)
            nbtBaseCloneMethod = Accessors.getMethodAccessor(MinecraftReflection.getNBTBaseClass(), "clone");
        return nbtBaseCloneMethod;
    }

    public static MethodAccessor getNBTBaseWriteMethod() {
        if(nbtBaseWriteMethod == null)
            nbtBaseWriteMethod = Accessors.getMethodAccessor(MinecraftReflection.getNBTBaseClass(), "write", DataOutput.class);
        return nbtBaseWriteMethod;
    }

    public static MethodAccessor getNBTBaseLoadMethod() {
        if(nbtBaseLoadMethod == null)
            nbtBaseLoadMethod = Accessors.getMethodAccessor(MinecraftReflection.getNBTBaseClass(), "load", DataInput.class, int.class, MinecraftReflection.getNBTReadLimiterClass());
        return nbtBaseLoadMethod;
    }

    public static FieldAccessor getNBTTagByteDataField() {
        if(nbtTagByteDataField == null)
            nbtTagByteDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagByteClass(), "data", true);
        return nbtTagByteDataField;
    }

    public static FieldAccessor getNBTTagShortDataField() {
        if(nbtTagShortDataField == null)
            nbtTagShortDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagShortClass(), "data", true);
        return nbtTagShortDataField;
    }

    public static FieldAccessor getNBTTagIntDataField() {
        if(nbtTagIntDataField == null)
            nbtTagIntDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagIntClass(), "data", true);
        return nbtTagIntDataField;
    }

    public static FieldAccessor getNBTTagLongDataField() {
        if(nbtTagLongDataField == null)
            nbtTagLongDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagLongClass(), "data", true);
        return nbtTagLongDataField;
    }

    public static FieldAccessor getNBTTagFloatDataField() {
        if(nbtTagFloatDataField == null)
            nbtTagFloatDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagFloatClass(), "data", true);
        return nbtTagFloatDataField;
    }

    public static FieldAccessor getNBTTagDoubleDataField() {
        if(nbtTagDoubleDataField == null)
            nbtTagDoubleDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagDoubleClass(), "data", true);
        return nbtTagDoubleDataField;
    }

    public static FieldAccessor getNBTTagStringDataField() {
        if(nbtTagStringDataField == null)
            nbtTagStringDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagStringClass(), "data", true);
        return nbtTagStringDataField;
    }

    public static FieldAccessor getNBTTagByteArrayDataField() {
        if(nbtTagByteArrayDataField == null)
            nbtTagByteArrayDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagByteArrayClass(), "data", true);
        return nbtTagByteArrayDataField;
    }

    public static FieldAccessor getNBTTagIntArrayDataField() {
        if(nbtTagIntArrayDataField == null)
            nbtTagIntArrayDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagIntArrayClass(), "data", true);
        return nbtTagIntArrayDataField;
    }

    public static FieldAccessor getNBTTagListDataField() {
        if(nbtTagListDataField == null)
            nbtTagListDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagListClass(), "list", true);
        return nbtTagListDataField;
    }

    public static FieldAccessor getNBTTagListTypeField() {
        if(nbtTagListTypeField == null)
            nbtTagListTypeField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagListClass(), "type", true);
        return nbtTagListTypeField;
    }

    public static FieldAccessor getNBTTagCompoundDataField() {
        if(nbtTagCompoundDataField == null)
            nbtTagCompoundDataField = Accessors.getFieldAccessor(MinecraftReflection.getNBTTagCompoundClass(), "map", true);
        return nbtTagCompoundDataField;
    }
}
