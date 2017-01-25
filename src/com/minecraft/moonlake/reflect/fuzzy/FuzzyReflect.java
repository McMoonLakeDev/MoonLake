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


package com.minecraft.moonlake.reflect.fuzzy;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import static com.minecraft.moonlake.reflect.Reflect.*;
import static com.minecraft.moonlake.reflect.Reflect.DataType.*;

public class FuzzyReflect<T> {

    private Class<T> source;
    private boolean forceAccess;

    public FuzzyReflect(Class<T> source, boolean forceAccess) {

        Validate.notNull(source, "The source class object is null.");

        this.source = source;
        this.forceAccess = forceAccess;
    }

    public FuzzyReflect(Class<T> source) {

        this(source, false);
    }

    public Class<?> getSource() {

        return source;
    }

    public boolean isForceAccess() {

        return forceAccess;
    }

    public void setForceAccess(boolean forceAccess) {

        this.forceAccess = forceAccess;
    }

    //
    // Static Method

    public static <T> FuzzyReflect<T> fromClass(Class<T> source) {

        Validate.notNull(source, "The source object is null.");

        return new FuzzyReflect<>(source, false);
    }

    public static <T> FuzzyReflect<T> fromClass(Class<T> source, boolean forceAccess) {

        Validate.notNull(source, "The source object is null.");

        return new FuzzyReflect<>(source, forceAccess);
    }

    public static FuzzyReflect<?> fromObject(Object object) {

        Validate.notNull(object, "The object is null.");

        return new FuzzyReflect<>(object.getClass());
    }

    public static FuzzyReflect<?> fromObject(Object object, boolean forceAccess) {

        Validate.notNull(object, "The object is null.");

        return new FuzzyReflect<>(object.getClass(), forceAccess);
    }

    public static FuzzyReflect<?> fromPackage(PackageType type, String className) {

        Validate.notNull(type, "The package type object is null.");
        Validate.notNull(className, "The class name object is null.");

        try {

            return new FuzzyReflect<>(type.getClass(className), false);
        }
        catch (Exception e) {

            throw new MoonLakeException("The package not exists '" + className + "' class.", e);
        }
    }

    public static FuzzyReflect<?> fromPackage(PackageType type, String className, boolean forceAccess) {

        Validate.notNull(type, "The package type object is null.");
        Validate.notNull(className, "The class name object is null.");

        try {

            return new FuzzyReflect<>(type.getClass(className), forceAccess);
        }
        catch (Exception e) {

            throw new MoonLakeException("The package not exists '" + className + "' class.", e);
        }
    }
    ///

    public Set<Constructor<?>> findConstructors() {

        if(forceAccess)
            return arrayToSet(source.getDeclaredConstructors());
        else
            return arrayToSet(source.getConstructors());
    }

    public Constructor<T> findConstructor() {

        try {

            if(forceAccess)
                return source.getDeclaredConstructor();
            else
                return source.getConstructor();
        }
        catch (Exception e) {

            throw new MoonLakeException("The source class not exists default constructor.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> findConstructorByParameters(Class<?>... args) {

        Validate.notNull(args, "The parameters object is null.");

        Class<?>[] argsPrimitive = getPrimitive(args);

        for(Constructor<?> constructor : findConstructors())
            if(compare(getPrimitive(constructor.getParameterTypes()), argsPrimitive))
                return (Constructor<T>) constructor;

        throw new MoonLakeException("The source class not exists '" + Arrays.toString(args) + "' parameters constructor.");
    }

    public Set<Method> findMethods() {

        if(forceAccess)
            return arrayToSet(source.getDeclaredMethods(), source.getMethods());
        else
            return arrayToSet(source.getMethods());
    }

    public List<Method> findMethodsByReturnType(Class<?> returnType) {

        Validate.notNull(returnType, "The return type object is null.");

        List<Method> methodList = new ArrayList<>();
        Class<?> returnTypePrimitive = getPrimitive(returnType);

        for(Method method : findMethods())
            if(getPrimitive(method.getReturnType()).equals(returnTypePrimitive))
                methodList.add(method);

        return methodList;
    }

    public List<Method> findMethodsByParameters(Class<?>... args) {

        Validate.notNull(args, "The parameters object is null.");

        Class<?>[] argsPrimitive = getPrimitive(args);
        List<Method> methodList = new ArrayList<>();

        for(Method method : findMethods())
            if(compare(getPrimitive(method.getParameterTypes()), argsPrimitive))
                methodList.add(method);

        return methodList;
    }

    public List<Method> findMethodsByParameters(Class<?> returnType, Class<?>... args) {

        Validate.notNull(args, "The parameters object is null.");

        Class<?>[] argsPrimitive = getPrimitive(args);
        List<Method> resultList = new ArrayList<>();
        List<Method> methodList = findMethodsByReturnType(returnType);

        for(Method method : methodList)
            if(compare(getPrimitive(method.getParameterTypes()), argsPrimitive))
                resultList.add(method);

        return resultList;
    }

    public List<Method> findMethodsByName(String name) {

        Validate.notNull(name, "The method name object is null.");

        List<Method> methodList = new ArrayList<>();

        for(Method method : findMethods())
            if(method.getName().equals(name))
                methodList.add(method);

        return methodList;
    }

    public Method findMethodByName(String name) {

        List<Method> methodList = findMethodsByName(name);

        if(methodList.size() > 0)
            return methodList.get(0);
        else
            throw new MoonLakeException();
    }

    public Method findMethodByNameMatch(String nameRegex) {

        Validate.notNull(nameRegex, "The name regex object is null.");

        Pattern pattern = Pattern.compile(nameRegex);

        for(Method method : findMethods())
            if(pattern.matcher(method.getName()).matches())
                return method;

        throw new MoonLakeException();
    }

    public Method findMethodByNameMatchAndReturnType(String nameRegex, Class<?> returnType) {

        Validate.notNull(nameRegex, "The name regex object is null.");

        Pattern pattern = Pattern.compile(nameRegex);
        List<Method> methodList = findMethodsByReturnType(returnType);

        for(Method method : methodList)
            if(pattern.matcher(method.getName()).matches())
                return method;

        throw new MoonLakeException();
    }

    public Method findMethodByNameAndParameters(String name, Class<?>... args) {

        Validate.notNull(args, "The parameters object is null.");

        Class<?>[] argsPrimitive = getPrimitive(args);
        List<Method> methodList = findMethodsByName(name);

        for(Method method : methodList)
            if(compare(getPrimitive(method.getParameterTypes()), argsPrimitive))
                return method;

        throw new MoonLakeException();
    }

    public Method findMethodByNameAndParameters(String name, Class<?> returnType, Class<?>... args) {

        Validate.notNull(name, "The method name object is null.");

        for(Method method : findMethodsByParameters(returnType, args))
            if(method.getName().equals(name))
                return method;

        throw new MoonLakeException();
    }

    public FuzzyMethod<T, Object> findFuzzyMethodByName(String name) {

        return new FuzzyMethod<>(this, findMethodByName(name));
    }

    public FuzzyMethod<T, Object> findFuzzyMethodByNameMatch(String nameRegex) {

        return new FuzzyMethod<>(this, findMethodByNameMatch(nameRegex));
    }

    public <R> FuzzyMethod<T, R> findFuzzyMethodByNameMatchAndReturnType(String nameRegex, Class<R> returnType) {

        return new FuzzyMethod<>(this, findMethodByNameMatchAndReturnType(nameRegex, returnType));
    }

    public FuzzyMethod<T, Object> findFuzzyMethodByNameAndParameters(String name, Class<?>... args) {

        return new FuzzyMethod<>(this, findMethodByNameAndParameters(name, args));
    }

    public <R> FuzzyMethod<T, R> findFuzzyMethodByNameAndParameters(String name, Class<R> returnType, Class<?>... args) {

        return new FuzzyMethod<>(this, findMethodByNameAndParameters(name, returnType, args));
    }

    public Set<Field> findFields() {

        if(forceAccess)
            return arrayToSet(source.getDeclaredFields(), source.getFields());
        else
            return arrayToSet(source.getFields());
    }

    public Set<Field> findFields(Class<?> excludeClass) {

        Validate.notNull(excludeClass, "The exclude class object is null.");

        if(forceAccess) {

            Class<?> currentClass = source;
            Set<Field> fieldSet = new LinkedHashSet<>();

            while (currentClass != excludeClass) {
                fieldSet.addAll(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }
            return fieldSet;
        }
        return findFields();
    }

    public Set<Field> findFieldsByType(Class<?> fieldType) {

        Validate.notNull(fieldType, "The field type object is null.");

        Set<Field> fieldSet = new LinkedHashSet<>();

        for(Field field : findFields())
            if(field.getType().isAssignableFrom(fieldType))
                fieldSet.add(field);

        return fieldSet;
    }

    public Field findFieldByName(String name) {

        Validate.notNull(name, "The field name object is null.");

        for(Field field : findFields())
            if(field.getName().equals(name))
                return field;

        throw new MoonLakeException();
    }

    public Field findFieldByNameMatch(String nameRegex) {

        Validate.notNull(nameRegex, "The name regex object is null.");

        Pattern pattern = Pattern.compile(nameRegex);

        for(Field field : findFields())
            if(pattern.matcher(field.getName()).matches())
                return field;

        throw new MoonLakeException();
    }

    public Field findFieldByNameAndType(String name, Class<?> fieldType) {

        Validate.notNull(name, "The field name object is null.");
        Validate.notNull(fieldType, "The field type object is null.");

        for(Field field : findFields())
            if(field.getName().equals(name) && field.getType().isAssignableFrom(fieldType))
                return field;

        throw new MoonLakeException();
    }

    public FuzzyField<T, Object> findFuzzyFieldByName(String name) {

        return new FuzzyField<>(this, findFieldByName(name));
    }

    public FuzzyField<T, Object> findFuzzyFieldByNameMatch(String nameRegex) {

        return new FuzzyField<>(this, findFieldByNameMatch(nameRegex));
    }

    public <R> FuzzyField<T, R> findFuzzyFieldByNameAndType(String name, Class<R> fieldType) {

        return new FuzzyField<>(this, findFieldByNameAndType(name, fieldType));
    }

    @SuppressWarnings("unchecked")
    private static <T> Set<T> arrayToSet(T[]... array) {

        Set<T> result = new LinkedHashSet<>();

        for(T[] contents : array)
            for(T content : contents)
                result.add(content);

        return result;
    }
}
