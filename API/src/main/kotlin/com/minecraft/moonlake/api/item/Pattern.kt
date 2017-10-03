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

        WHITE(15),
        ORANGE(14),
        MAGENTA(13),
        LIGHT_BLUE(12),
        YELLOW(11),
        LIME(10),
        PINK(9),
        GRAY(8),
        SILVER(7),
        CYAN(6),
        PURPLE(5),
        BLUE(4),
        BROWN(3),
        GREEN(2),
        RED(1),
        BLACK(0),
        ;

        override fun value(): Int
                = data
    }

    enum class Type(val identifier: String) : Valuable<String> {

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
