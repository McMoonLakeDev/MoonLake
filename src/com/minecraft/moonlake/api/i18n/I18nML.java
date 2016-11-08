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

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.manager.IoManager;
import com.minecraft.moonlake.util.StringUtil;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.InputStream;

/**
 * <h1>I18nML</h1>
 * 国际化语言月色之湖类
 *
 * @version 1.0
 * @author Month_Light
 */
@SuppressWarnings("deprecation")
public final class I18nML {

    /**
     * 月色之湖国际化语言的前缀
     */
    public final static String PREFIX;
    private final static MoonLakePlugin MAIN;
    private final static I18n I18N_ML;

    static {

        MAIN = MoonLakePlugin.getInstances().getPlugin();
        PREFIX = StringUtil.format("%1$sMoonLake %2$s>> %3$s", ChatColor.DARK_AQUA, ChatColor.GRAY, ChatColor.RESET);

        if(!MAIN.getDataFolder().exists()) {

            MAIN.getDataFolder().mkdirs();
        }
        String language = MAIN.getConfiguration().getLanguage() + ".properties";
        File languageFile = new File(MAIN.getDataFolder() + File.separator + "languages" + File.separator, language);

        if(!languageFile.getParentFile().exists()) {

            languageFile.getParentFile().mkdirs();
        }
        if(!languageFile.exists()) {

            if(language.startsWith("zh_CN")) {

                IoManager.outInputStream(languageFile, MAIN.getResource("zh_CN.properties"));
            }
            else {

                InputStream is = MAIN.getResource(language);

                if(is == null) {

                    MAIN.getMLogger().error("The language file '" + language + "' not exists, be load 'zh_CN' language.");

                    languageFile = new File(MAIN.getDataFolder() + File.separator + "languages" + File.separator, "zh_CN.properties");

                    if(!languageFile.exists()) {

                        IoManager.outInputStream(languageFile, MAIN.getResource("zh_CN.properties"));
                    }
                }
                else {

                    IoManager.outInputStream(languageFile, is);
                }
            }
        }
        I18N_ML = new I18nExpression(languageFile);
        I18N_ML.reload(PREFIX);
    }

    /**
     * 国际化语言月色之湖类构造函数
     */
    private I18nML() {
    }

    /**
     * 获取语言文件指定键的值
     *
     * @param key 键
     * @return 值
     */
    public static String t(String key) {

        return I18N_ML.t(key);
    }

    /**
     * 获取语言文件指定键的值
     *
     * @param key 键
     * @param args 格式化参数
     * @return 值
     */
    public static String t(String key, Object... args) {

        return I18N_ML.t(key, args);
    }
}
