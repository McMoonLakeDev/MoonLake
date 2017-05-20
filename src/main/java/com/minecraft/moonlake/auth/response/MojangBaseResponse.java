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


package com.minecraft.moonlake.auth.response;

/**
 * <h1>MojangBaseResponse</h1>
 * Mojang 基础响应类
 *
 * @version 1.0
 * @author Month_Light
 * @see Response
 */
public class MojangBaseResponse extends Response {

    private String error;
    private String cause;
    private String errorMessage;

    /**
     * 获取此 Mojang 基础响应的错误
     *
     * @return 错误
     */
    public String getError() {
        return error;
    }

    /**
     * 获取此 Mojang 基础响应的原因
     *
     * @return 原因
     */
    public String getCause() {
        return cause;
    }

    /**
     * 获取此 Mojang 基础响应的错误消息
     *
     * @return 错误消息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 设置此 Mojang 基础响应的错误
     *
     * @param error 错误
     */
    protected void setError(String error) {
        this.error = error;
    }

    /**
     * 设置此 Mojang 基础响应的原因
     *
     * @param cause 原因
     */
    protected void setCause(String cause) {
        this.cause = cause;
    }

    /**
     * 设置此 Mojang 基础响应的错误消息
     *
     * @param errorMessage 错误消息
     */
    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
