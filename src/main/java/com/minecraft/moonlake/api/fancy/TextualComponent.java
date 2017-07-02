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
 
 
package com.minecraft.moonlake.api.fancy;

import com.google.common.collect.ImmutableMap;
import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.json.JsonWrite;
import com.minecraft.moonlake.property.*;

import java.io.IOException;
import java.util.Map;

/**
 * <h1>TextualComponent</h1>
 * 花式消息文本组件
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatComponent}
 */
@Deprecated
public abstract class TextualComponent implements Cloneable {

    /**
     * 获取花式消息文本组件的键
     *
     * @return 键
     */
    public abstract ReadOnlyStringProperty getKey();

    /**
     * 获取花式消息文本组件的只读字符串
     *
     * @return 只读字符串
     */
    public abstract String getReadableString();

    @Override
    public abstract TextualComponent clone() throws CloneNotSupportedException;

    /**
     * 将此花式消息文本组件写出 Json 内容
     *
     * @param jsonWrite Json 写对象
     * @throws IOException 如果写出 IO 错误则抛出异常
     */
    public abstract void writeJson(JsonWrite jsonWrite) throws IOException;

    /**
     * 获取此花式消息文本组件是否正确的键
     *
     * @param key 键
     * @return 是否正确
     */
    static boolean isTextKey(String key) {

        return key.equals("translate") || key.equals("text") || key.equals("score") || key.equals("selector");
    }

    /**
     * 获取此花式消息文本组件是否为翻译文本
     *
     * @param component 花式文本组件对象
     * @return 是否为翻译文本
     */
    static boolean isTranslatableText(TextualComponent component) {

        return component instanceof ComplexTextTypeComponent && ((ComplexTextTypeComponent) component).getKey().equals("translate");
    }

    /**
     * 将指定字符串文本转换为源花式文本组件对象
     *
     * @param text 字符串文本
     * @return 花式文本组件
     */
    public static TextualComponent rawText(String text) {

        return new ArbitraryTextTypeComponent("text", text);
    }

    /**
     * 将指定字符串文本转换为翻译花式文本组件对象
     *
     * @param translateKey 翻译键
     * @return 花式文本组件
     */
    public static TextualComponent localizedText(String translateKey) {

        return new ArbitraryTextTypeComponent("translate", translateKey);
    }

    /**
     * 将指定字符串文本转换为计分板分数花式文本组件对象
     *
     * @param scoreboardObjective 计分板对象内容
     * @return 花式文本组件
     */
    public static TextualComponent objectiveScore(String scoreboardObjective) {

        return objectiveScore("*", scoreboardObjective);
    }

    /**
     * 将指定字符串文本转换为计分板分数花式文本组件对象
     *
     * @param playerName 玩家名
     * @param scoreboardObjective 计分板内容
     * @return 花式文本组件
     */
    public static TextualComponent objectiveScore(String playerName, String scoreboardObjective) {

        return new ComplexTextTypeComponent("score", ImmutableMap.<String, String>builder()
                .put("name", playerName)
                .put("objective", scoreboardObjective)
                .build());
    }

    /**
     * 将指定字符串文本转换为选择器花式文本组件对象
     *
     * @param selector 选择器内容
     * @return 花式文本组件
     */
    public static TextualComponent selector(String selector) {

        return new ArbitraryTextTypeComponent("selector", selector);
    }

    /**
     * <h1>ArbitraryTextTypeComponent</h1>
     * 任意文本类型组件类（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    private final static class ArbitraryTextTypeComponent extends TextualComponent {

        private StringProperty key;
        private StringProperty value;

        /**
         * 任意文本类型组件类构造函数
         *
         * @param key 键
         * @param value 值
         */
        public ArbitraryTextTypeComponent(String key, String value) {

            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
        }

        @Override
        public StringProperty getKey() {

            return key;
        }

        /**
         * 获取花式消息文本组件的值
         *
         * @return 值
         */
        public StringProperty getValue() {

            return value;
        }

        @Override
        public String getReadableString() {

            return getValue().get();
        }

        @Override
        public TextualComponent clone() throws CloneNotSupportedException {

            return new ArbitraryTextTypeComponent(getKey().get(), getValue().get());
        }

        @Override
        public void writeJson(JsonWrite jsonWrite) throws IOException {

            jsonWrite.name(getKey().get()).value(getValue().get());
        }
    }

    /**
     * <h1>ComplexTextTypeComponent</h1>
     * 复杂文本类型组件类（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    private final static class ComplexTextTypeComponent extends TextualComponent {

        private StringProperty key;
        private ObjectProperty<Map<String, String>> values;

        /**
         * 复杂文本类型组件类构造函数
         *
         * @param key 键
         * @param values 值
         */
        public ComplexTextTypeComponent(String key, Map<String, String> values) {

            this.key = new SimpleStringProperty(key);
            this.values = new SimpleObjectProperty<>(values);
        }

        @Override
        public StringProperty getKey() {

            return key;
        }

        /**
         * 获取花式消息文本组件的值
         *
         * @return 值
         */
        public ObjectProperty<Map<String, String>> getValues() {

            return values;
        }

        @Override
        public String getReadableString() {

            return getKey().get();
        }

        @Override
        public TextualComponent clone() throws CloneNotSupportedException {

            return new ComplexTextTypeComponent(getKey().get(), getValues().get());
        }

        @Override
        public void writeJson(JsonWrite jsonWrite) throws IOException {

            jsonWrite.name(getKey().get());
            jsonWrite.beginObject();

            for(Map.Entry<String, String> entry : getValues().get().entrySet()) {

                jsonWrite.name(entry.getKey()).value(entry.getValue());
            }
            jsonWrite.endObject();
        }
    }
}
