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

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;

import java.util.Map;

class CachedPackage {

    private final Map<String, Optional<Class<?>>> cache;
    private final String packageName;
    private final ClassSource source;

    public CachedPackage(String packageName, ClassSource source) {
        this.cache = Maps.newConcurrentMap();
        this.packageName = packageName;
        this.source = source;
    }

    public void setPackageClass(String className, Class<?> clazz) {
        if(clazz != null)
            this.cache.put(className, Optional.of(clazz));
        else
            this.cache.remove(className);
    }

    public Class<?> getPackageClass(String className) {
        Validate.notNull(className, "The class name object is null.");

        if(this.cache.containsKey(className)) {
            Optional<Class<?>> result = this.cache.get(className);
            if(!result.isPresent())
                throw new MoonLakeException("Cannot find class " + className);
            return result.get();
        }

        try {
            Class<?> clazz = this.source.loadClass(combine(this.packageName, className));
            if(clazz == null)
                throw new IllegalArgumentException("Source " + this.source + " returned null for " + className);
            this.cache.put(className, Optional.of(clazz));
            return clazz;

        } catch (ClassNotFoundException e) {
            this.cache.put(className, Optional.absent());
            throw new MoonLakeException("Cannot find class " + className, e);
        }
    }

    private static String combine(String packageName, String className) {
        if(StringUtil.isEmpty(packageName))
            return className;
        if(StringUtil.isEmpty(className))
            return packageName;
        return packageName + "." + className;
    }
}
