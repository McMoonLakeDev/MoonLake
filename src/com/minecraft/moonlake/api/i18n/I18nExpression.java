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


package com.minecraft.moonlake.api.i18n;

import com.minecraft.moonlake.manager.IoManager;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class I18nExpression implements I18n {

    private final File langFile;
    private final Map<String, String> langCacheMap;

    public I18nExpression(File lang) throws IllegalArgumentException {

        Validate.notNull(lang, "The lang object is null.");
        Validate.isTrue(lang.exists(), "The lang file not exists.");

        this.langFile = lang;
        this.langCacheMap = new HashMap<>();
    }

    @Override
    public File getFile() {

        return langFile;
    }

    @Override
    public void reload() {

        reload("");
    }

    @Override
    public void reload(String prefix) {

        Validate.notNull(prefix, "The prefix object is null.");

        this.langCacheMap.clear();
        this.langCacheMap.putAll(IoManager.readLangFile(prefix, langFile));
    }

    @Override
    public String t(String key) {

        return t(key, new Object[] { });
    }

    @Override
    public String t(String key, Object... args) {

        String value = handlerGetValue(key);

        if(value == null) {

            return "";
        }
        return StringUtil.messageFormat(value, args);
    }

    @Override
    public boolean has(String key) {

        return langCacheMap.containsKey(key);
    }

    protected String handlerGetValue(String key) {

        return has(key) ? langCacheMap.get(key) : null;
    }
}
