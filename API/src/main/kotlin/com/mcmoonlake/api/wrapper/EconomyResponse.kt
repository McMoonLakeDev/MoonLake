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

package com.mcmoonlake.api.wrapper

data class EconomyResponse(
        val amount: Double,
        val balance: Double,
        val type: Type,
        val errorMessage: String? = null) {

    fun transactionSuccess(): Boolean
            = type == Type.SUCCESS

    enum class Type {

        /**
         * Economy Response Type: Success (经济回应类型: 成功)
         */
        SUCCESS,
        /**
         * Economy Response Type: Failure (经济回应类型: 失败)
         */
        FAILURE,
        /**
         * Economy Response Type: Not Implemented (经济回应类型: 未实现)
         */
        NOT_IMPLEMENTED,
        /**
         * Economy Response Type: NULL (经济回应类型: NULL)
         */
        NULL,
        ;
    }
}
