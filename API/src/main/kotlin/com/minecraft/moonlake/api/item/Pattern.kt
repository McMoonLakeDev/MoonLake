/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.Valuable

data class Pattern(val color: Color, val type: Type) {

    enum class Color(val data: Int) : Valuable<Int> {

        /**
         * Banner Color: White (旗帜颜色: 白色)
         */
        WHITE(15),
        /**
         * Banner Color: Orange (旗帜颜色: 橙色)
         */
        ORANGE(14),
        /**
         * Banner Color: Magenta (旗帜颜色: 品红色)
         */
        MAGENTA(13),
        /**
         * Banner Color: Light Blue (旗帜颜色: 淡蓝色)
         */
        LIGHT_BLUE(12),
        /**
         * Banner Color: Yellow (旗帜颜色: 黄色)
         */
        YELLOW(11),
        /**
         * Banner Color: Lime (旗帜颜色: 黄绿色)
         */
        LIME(10),
        /**
         * Banner Color: Pink (旗帜颜色: 粉红色)
         */
        PINK(9),
        /**
         * Banner Color: Gray (旗帜颜色: 灰色)
         */
        GRAY(8),
        /**
         * Banner Color: Silver (旗帜颜色: 淡灰色)
         */
        SILVER(7),
        /**
         * Banner Color: Cyan (旗帜颜色: 青色)
         */
        CYAN(6),
        /**
         * Banner Color: Purple (旗帜颜色: 紫色)
         */
        PURPLE(5),
        /**
         * Banner Color: Blue (旗帜颜色: 蓝色)
         */
        BLUE(4),
        /**
         * Banner Color: Brown (旗帜颜色: 棕色)
         */
        BROWN(3),
        /**
         * Banner Color: Green (旗帜颜色: 绿色)
         */
        GREEN(2),
        /**
         * Banner Color: Red (旗帜颜色: 红色)
         */
        RED(1),
        /**
         * Banner Color: Black (旗帜颜色: 黑色)
         */
        BLACK(0),
        ;

        override fun value(): Int
                = data
    }

    enum class Type(val identifier: String) : Valuable<String> {

        // TODO document not yet available

        BASE("b"),
        SQUARE_BOTTOM_LEFT("bl"),
        SQUARE_BOTTOM_RIGHT("br"),
        SQUARE_TOP_LEFT("tl"),
        SQUARE_TOP_RIGHT("tr"),
        STRIPE_BOTTOM("bs"),
        STRIPE_TOP("ts"),
        STRIPE_LEFT("ls"),
        STRIPE_RIGHT("rs"),
        STRIPE_CENTER("cs"),
        STRIPE_MIDDLE("ms"),
        STRIPE_DOWNRIGHT("drs"),
        STRIPE_DOWNLEFT("dls"),
        STRIPE_SMALL("ss"),
        CROSS("cr"),
        STRAIGHT_CROSS("sc"),
        TRIANGLE_BOTTOM("bt"),
        TRIANGLE_TOP("tt"),
        TRIANGLES_BOTTOM("bts"),
        TRIANGLES_TOP("tts"),
        DIAGONAL_LEFT("ld"),
        DIAGONAL_RIGHT("rd"),
        DIAGONAL_LEFT_MIRROR("lud"),
        DIAGONAL_RIGHT_MIRROR("rud"),
        CIRCLE_MIDDLE("mc"),
        RHOMBUS_MIDDLE("mr"),
        HALF_VERTICAL("vh"),
        HALF_HORIZONTAL("hh"),
        HALF_VERTICAL_MIRROR("vhr"),
        HALF_HORIZONTAL_MIRROR("hhb"),
        BORDER("bo"),
        CURLY_BORDER("cbo"),
        CREEPER("cre"),
        GRADIENT("gra"),
        GRADIENT_UP("gru"),
        BRICKS("bri"),
        SKULL("sku"),
        FLOWER("flo"),
        MOJANG("moj"),
        ;

        override fun value(): String
                = identifier
    }
}
