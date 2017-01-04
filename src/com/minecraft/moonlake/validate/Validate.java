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
 
 
package com.minecraft.moonlake.validate;

import com.minecraft.moonlake.value.ObservableBooleanValue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * <hr />
 * <div>
 *     <h1>数据对象验证类</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>主要的作用可以验证对象是否为 {@code null} 或者判断条件表达式则抛出异常</h1>
 *     <p>验证对象是否为 {@code null} 则抛出异常: {@code Validate.notNull(object, "The object is null.");}</p>
 *     <p>判断条件表达式为 {@code false} 则抛出异常: {@code Validate.isTrue(5 == 10, "The expression is not true.");}</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public class Validate {

    /**
     * 数据对象验证类构造函数
     */
    private Validate() {

    }

    /**
     * 判断指定条件表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 条件表达式
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果条件表达式为 {@code false} 则抛出异常
     */
    public static void isTrue(boolean expression, String message, Object value) {

        if(!expression) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定条件表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 条件表达式
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果条件表达式为 {@code false} 则抛出异常
     */
    public static void isTrue(boolean expression, String message, long value) {

        if(!expression) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定条件表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 条件表达式
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果条件表达式为 {@code false} 则抛出异常
     */
    public static void isTrue(boolean expression, String message, double value) {

        if(!expression) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定条件表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 条件表达式
     * @param message 异常消息
     * @throws IllegalArgumentException 如果条件表达式为 {@code false} 则抛出异常
     */
    public static void isTrue(boolean expression, String message) {

        if(!expression) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定条件表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 条件表达式
     * @throws IllegalArgumentException 如果条件表达式为 {@code false} 则抛出异常
     */
    public static void isTrue(boolean expression) {

        if(!expression) {

            throw new IllegalArgumentException("The validated expression is false.");
        }
    }

    /**
     * 判断指定布尔属性是否为 {@code true} 否则抛出异常
     *
     * @param booleanProperty 布尔属性
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果布尔属性为 {@code null | false} 则抛出异常
     */
    public static void isTrue(ObservableBooleanValue booleanProperty, String message, Object value) {

        if(booleanProperty == null || !booleanProperty.getValue()) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定布尔属性是否为 {@code true} 否则抛出异常
     *
     * @param booleanProperty 布尔属性
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果布尔属性为 {@code null | false} 则抛出异常
     */
    public static void isTrue(ObservableBooleanValue booleanProperty, String message, long value) {

        if(booleanProperty == null || !booleanProperty.getValue()) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定布尔属性是否为 {@code true} 否则抛出异常
     *
     * @param booleanProperty 布尔属性
     * @param message 异常消息
     * @param value 异常值
     * @throws IllegalArgumentException 如果布尔属性为 {@code null | false} 则抛出异常
     */
    public static void isTrue(ObservableBooleanValue booleanProperty, String message, double value) {

        if(booleanProperty == null || !booleanProperty.getValue()) {

            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * 判断指定布尔属性是否为 {@code true} 否则抛出异常
     *
     * @param booleanProperty 布尔属性
     * @param message 异常消息
     * @throws IllegalArgumentException 如果布尔属性为 {@code null | false} 则抛出异常
     */
    public static void isTrue(ObservableBooleanValue booleanProperty, String message) {

        if(booleanProperty == null || !booleanProperty.getValue()) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定布尔属性是否为 {@code true} 否则抛出异常
     *
     * @param booleanProperty 布尔属性
     * @throws IllegalArgumentException 如果布尔属性为 {@code null | false} 则抛出异常
     */
    public static void isTrue(ObservableBooleanValue booleanProperty) {

        if(booleanProperty == null || !booleanProperty.getValue()) {

            throw new IllegalArgumentException("The validate expression is null or false.");
        }
    }

    /**
     * 判断指定对象是否不为 {@code null} 否则抛出异常
     *
     * @param object 对象
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static void notNull(Object object) {

        notNull(object, "The validated object is null.");
    }

    /**
     * 判断指定对象是否不为 {@code null} 否则抛出异常
     *
     * @param object 对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static void notNull(Object object, String message) {

        if(object == null) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定数组对象是否不为 {@code empty} 否则抛出异常
     *
     * @param array 数组对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果数组对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Object[] array, String message) {

        if(array == null || array.length == 0) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定数组对象是否不为 {@code empty} 否则抛出异常
     *
     * @param array 数组对象
     * @throws IllegalArgumentException 如果数组对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Object[] array) {

        notEmpty(array, "The validated array is empty.");
    }

    /**
     * 判断指定集合对象是否不为 {@code empty} 否则抛出异常
     *
     * @param collection 集合对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果集合对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Collection collection, String message) {

        if(collection == null || collection.size() == 0) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定集合对象是否不为 {@code empty} 否则抛出异常
     *
     * @param collection 集合对象
     * @throws IllegalArgumentException 如果集合对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Collection collection) {

        notEmpty(collection, "The validated collection is empty.");
    }

    /**
     * 判断指定 Map 集合对象是否不为 {@code empty} 否则抛出异常
     *
     * @param map Map 集合对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果 Map 集合对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Map map, String message) {

        if(map == null || map.size() == 0) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定 Map 集合对象是否不为 {@code empty} 否则抛出异常
     *
     * @param map Map 集合对象
     * @throws IllegalArgumentException 如果 Map 集合对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(Map map) {

        notEmpty(map, "The validated map is empty.");
    }

    /**
     * 判断指定字符串对象是否不为 {@code empty} 否则抛出异常
     *
     * @param string 字符串对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果字符串对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(String string, String message) {

        if(string == null || string.length() == 0) {

            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断指定字符串对象是否不为 {@code empty} 否则抛出异常
     *
     * @param string 字符串对象
     * @throws IllegalArgumentException 如果字符串对象为 {@code null | empty} 则抛出异常
     */
    public static void notEmpty(String string) {

        notEmpty(string, "The validated string is empty.");
    }

    /**
     * 判断指定数组对象的数据是否不为 {@code null} 否则抛出异常
     *
     * @param array 数组对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数组对象的的数据为 {@code null} 则抛出异常
     */
    public static void notNullElements(Object[] array, String message) {

        notNull(array);

        for(int i = 0; i < array.length; ++i) {

            if(array[i] == null) {

                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 判断指定数组对象的数据是否不为 {@code null} 否则抛出异常
     *
     * @param array 数组对象
     * @throws IllegalArgumentException 如果数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数组对象的的数据为 {@code null} 则抛出异常
     */
    public static void notNullElements(Object[] array) {

        notNull(array);

        for(int i = 0; i < array.length; ++i) {

            if(array[i] == null) {

                throw new IllegalArgumentException("The validated array contains null element at index: " + i);
            }
        }
    }

    /**
     * 判断指定集合对象的数据是否不为 {@code null} 否则抛出异常
     *
     * @param collection 集合对象
     * @param message 异常消息
     * @throws IllegalArgumentException 如果数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数组对象的的数据为 {@code null} 则抛出异常
     */
    public static void notNullElements(Collection collection, String message) {

        notNull(collection);

        Iterator it = collection.iterator();

        do {

            if(!it.hasNext()) {

                return;
            }
        }
        while(it.next() != null);

        throw new IllegalArgumentException(message);
    }

    /**
     * 判断指定集合对象的数据是否不为 {@code null} 否则抛出异常
     *
     * @param collection 集合对象
     * @throws IllegalArgumentException 如果数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数组对象的的数据为 {@code null} 则抛出异常
     */
    public static void notNullElements(Collection collection) {

        notNull(collection);

        int i = 0;

        for(Iterator it = collection.iterator(); it.hasNext(); ++i) {

            if(it.next() == null) {

                throw new IllegalArgumentException("The validated collection contains null element at index: " + i);
            }
        }
    }

    /**
     * 判断指定集合对象的数据是否为指定类的实例，否则抛出异常
     *
     * @param collection 集合对象
     * @param clazz 类
     * @param message 异常消息
     * @throws IllegalArgumentException 如果集合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果集合对象的数据不是指定类的实例则抛出异常
     */
    public static void allElementsOfType(Collection collection, Class clazz, String message) {

        notNull(collection);
        notNull(clazz);

        Iterator it = collection.iterator();

        do {

            if(!it.hasNext()) {

                return;
            }
        }
        while(clazz.isInstance(it.next()));

        throw new IllegalArgumentException(message);
    }

    /**
     * 判断指定集合对象的数据是否为指定类的实例，否则抛出异常
     *
     * @param collection 集合对象
     * @param clazz 类
     * @throws IllegalArgumentException 如果集合对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果集合对象的数据不是指定类的实例则抛出异常
     */
    public static void allElementsOfType(Collection collection, Class clazz) {

        notNull(collection);
        notNull(clazz);

        int i = 0;

        for(Iterator it = collection.iterator(); it.hasNext(); ++i) {

            if(!clazz.isInstance(it.next())) {

                throw new IllegalArgumentException("The validated collection contains an element not of type " + clazz.getName() + " at index: " + i);
            }
        }
    }

    /**
     * 检测指定参考对象是否不为 {@code null} 则返回, 否则抛出异常
     *
     * @param reference 参考对象
     * @param <T> 类型
     * @return 参考对象
     * @throws IllegalArgumentException 如果参考对象为 {@code null} 则抛出异常
     */
    public static <T> T checkNotNull(T reference) {

        return checkNotNull(reference, "The validated reference object is null.");
    }

    /**
     * 检测指定参考对象是否不为 {@code null} 则返回, 否则抛出异常
     *
     * @param reference 参考对象
     * @param message 异常消息
     * @param <T> 类型
     * @return 参考对象
     * @throws IllegalArgumentException 如果参考对象为 {@code null} 则抛出异常
     */
    public static <T> T checkNotNull(T reference, String message) {

        if(reference == null) {

            throw new IllegalArgumentException(message);
        }
        return reference;
    }
}
