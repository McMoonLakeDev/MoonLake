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

import java.util.Collections;
import java.util.Map;

public abstract class ClassSource {

    public static ClassSource fromClassLoader() {

        return fromClassLoader(ClassSource.class.getClassLoader());
    }

    public static ClassSource fromClassLoader(ClassLoader classLoader) {

        return new ClassSource() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                return classLoader.loadClass(name);
            }
        };
    }

    public static ClassSource fromMap(Map<String, Class<?>> map) {

        return new ClassSource() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                Class<?> loaded = map == null ? null : map.get(name);
                if(loaded == null)
                    throw new ClassNotFoundException("The specified class could not be found by this ClassLoader.");
                return loaded;
            }
        };
    }

    public static ClassSource empty() {

        return fromMap(Collections.emptyMap());
    }

    public abstract Class<?> loadClass(String name) throws ClassNotFoundException;
}
