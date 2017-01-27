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


package com.minecraft.moonlake.api.player.depend;

/**
 * <h1>EconomyVaultPlayerResponse</h1>
 * 经济 Vault 玩家回应类 # 依赖 <a href="https://github.com/MilkBowl/Vault" target="_blank">Vault</a> 插件和 <a href="https://github.com/MilkBowl/VaultAPI" target="_blank">VaultAPI</a> 的 <a href="https://github.com/MilkBowl/VaultAPI/blob/master/src/main/java/net/milkbowl/vault/economy/Economy.java" target="_blank">Economy</a>
 *
 * @version 1.0
 * @author Month_Light
 */
public class EconomyVaultPlayerResponse {

    private final double amount;
    private final double balance;
    private final ResponseType type;
    private final String errorMessage;

    /**
     * 经济 Vault 玩家回应类构造函数
     *
     * @param amount 数量
     * @param balance 余额
     * @param type 回应类型
     * @param errorMessage 错误消息
     */
    public EconomyVaultPlayerResponse(double amount, double balance, ResponseType type, String errorMessage) {

        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取操作修改的数量
     *
     * @return 数量
     */
    public double getAmount() {

        return amount;
    }

    /**
     * 获取操作后的余额
     *
     * @return 余额
     */
    public double getBalance() {

        return balance;
    }

    /**
     * 获取操作的回应类型
     *
     * @return 回应类型
     */
    public ResponseType getType() {

        return type;
    }

    /**
     * 获取操作的错误消息
     *
     * @return 错误消息
     */
    public String getErrorMessage() {

        return errorMessage;
    }

    /**
     * 获取操作是否交易成功
     *
     * @return 是否成功
     */
    public boolean transactionSuccess() {

        return type == ResponseType.SUCCESS;
    }

    /**
     * <h1>ResponseType</h1>
     * 回应类型枚举类
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum ResponseType {

        SUCCESS(1),
        FAILURE(2),
        NOT_IMPLEMENTED(3);

        private final int id;

        /**
         * 回应类型枚举类构造函数
         *
         * @param id Id
         */
        ResponseType(int id) {

            this.id = id;
        }

        /**
         * 获取此回应类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }
    }
}
