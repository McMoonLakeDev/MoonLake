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

import com.minecraft.moonlake.validate.Validate;

import java.io.File;

public class I18nFactory {

    private I18nFactory() {
    }

    public static I18n newInstance(File file) {

        Validate.notNull(file, "The file object is null.");
        Validate.isTrue(file.exists(), "The file not exists.");

        return new I18nExpression(file);
    }

    public static I18n newInstance(String file) {

        Validate.notNull(file, "The file object is null.");

        return newInstance(new File(file));
    }

    public static I18n newInstance(File file, String prefix) {

        Validate.notNull(prefix, "The prefix object is null.");

        I18n i18n = newInstance(file);

        if(i18n != null) {

            i18n.reload(prefix);
        }
        return i18n;
    }

    public static I18n newInstance(String file, String prefix) {

        Validate.notNull(file, "The file object is null.");

        return newInstance(new File(file), prefix);
    }
}
