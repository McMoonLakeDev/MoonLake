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


package com.minecraft.moonlake.auth.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minecraft.moonlake.auth.response.MojangBaseResponse;
import com.minecraft.moonlake.auth.response.MojangStatusResponse;
import com.minecraft.moonlake.auth.response.ProfileHistoryResponse;
import com.minecraft.moonlake.auth.response.ProfileSearchResponse;
import com.minecraft.moonlake.auth.exception.MoonLakeInvalidCredentialsException;
import com.minecraft.moonlake.auth.exception.MoonLakeRequestException;
import com.minecraft.moonlake.auth.exception.MoonLakeServiceUnavailableException;
import com.minecraft.moonlake.auth.exception.MoonLakeUserMigratedException;
import com.minecraft.moonlake.auth.util.UUIDSerializer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * <h1>MoonLakeAuthBaseService</h1>
 * 月色之湖认证基础服务类
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeAuthService
 */
public abstract class MoonLakeAuthBaseService implements MoonLakeAuthService {

    private final static Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeAdapter(UUID.class, new UUIDSerializer())
                .registerTypeAdapter(ProfileSearchResponse.class, new ProfileSearchResponse.Serializer())
                .registerTypeAdapter(ProfileHistoryResponse.class, new ProfileHistoryResponse.Serializer())
                .registerTypeAdapter(MojangStatusResponse.class, new MojangStatusResponse.Serializer())
                .create();
    }

    private Proxy proxy;

    /**
     * 月色之湖认证基础服务类构造函数
     */
    protected MoonLakeAuthBaseService() {
        this.proxy = Proxy.NO_PROXY;
    }

    /**
     * 月色之湖认证基础服务类构造函数
     *
     * @param proxy 代理
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     */
    public MoonLakeAuthBaseService(Proxy proxy) {
        if(proxy == null)
            throw new IllegalArgumentException("代理对象不能为 null 值.");
        this.proxy = proxy;
    }

    @Override
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * 验证指定 Object 对象是否为 {@code null} 则抛出异常
     *
     * @param obj 对象
     * @param message 异常信息
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    protected static void validate(Object obj, String message) throws IllegalArgumentException {
        if(obj == null)
            throw new IllegalArgumentException(message);
    }

    /**
     * 验证指定布尔表达式是否为 {@code true} 否则抛出异常
     *
     * @param expression 表达式
     * @param message 异常信息
     * @throws IllegalArgumentException 如果布尔表达式不为 {@code true} 则抛出异常
     */
    protected static void validateTrue(boolean expression, String message) throws IllegalArgumentException {
        if(!expression)
            throw new IllegalArgumentException(message);
    }

    /**
     * 验证指定代理和链接对象是否为 {@code null} 则抛出异常
     *
     * @param proxy 代理对象
     * @param url 链接对象
     * @throws IllegalArgumentException 如果代理或链接对象为 {@code null} 则抛出异常
     */
    private static void validateProxyAndURL(Proxy proxy, String url) throws IllegalArgumentException {
        validate(proxy, "代理对象不能为 null 值.");
        validate(url, "目标 URL 对象不能为 null 值.");
    }

    /**
     * 创建以默认代理连接的 HTTP 连接对象
     *
     * @param url 目标链接
     * @return HTTP 连接对象
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static HttpURLConnection createURLConnection(String url) throws IOException {
        return createURLConnection(Proxy.NO_PROXY, url);
    }

    /**
     * 创建以指定代理连接的 HTTP 连接对象
     *
     * @param proxy 代理对象
     * @param url 目标链接
     * @return HTTP 连接对象
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static HttpURLConnection createURLConnection(Proxy proxy, String url) throws IOException {
        validateProxyAndURL(proxy, url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setUseCaches(false);
        return connection;
    }

    /**
     * 获取指定 HTTP 连接对象的请求输入流结果
     *
     * @param connection 连接对象
     * @return 请求结果
     * @throws IOException 如果 IO 错误则抛出异常
     */
    private static String getConnectionResult(HttpURLConnection connection) throws IOException {
        InputStream input = null;
        try {
            try {
                input = connection.getInputStream();
            } catch (Exception e) {
                input = connection.getErrorStream();
            }
            if(input != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null)
                    builder.append(line).append("\n");
                return builder.toString();
            } else {
                return "";
            }
        } finally {
            if(input != null) try {
                input.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 向指定链接发送 GET 模式的 HTTP 连接请求
     *
     * @param url 目标链接
     * @return GET 请求结果
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static String fromGetRequest(String url) throws IOException {
        return fromGetRequest(Proxy.NO_PROXY, url);
    }

    /**
     * 向指定链接发送以指定代理 GET 模式的 HTTP 连接请求
     *
     * @param proxy 代理对象
     * @param url 目标链接
     * @return GET 请求结果
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static String fromGetRequest(Proxy proxy, String url) throws IOException {
        validateProxyAndURL(proxy, url);
        HttpURLConnection connection = createURLConnection(proxy, url);
        connection.setDoInput(true);
        return getConnectionResult(connection);
    }

    /**
     * 向指定链接发送 POST 模式的 HTTP 连接请求
     *
     * @param url 目标链接
     * @param postData POST 数据
     * @param contentType 正文类型
     * @return POST 请求结果
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static String fromPostRequest(String url, String postData, String contentType) throws IOException {
        return fromPostRequest(Proxy.NO_PROXY, url, postData, contentType);
    }

    /**
     * 向指定链接发送以指定代理 POST 模式的 HTTP 连接请求
     *
     * @param proxy 代理对象
     * @param url 目标链接
     * @param postData POST 数据
     * @param contentType 正文类型
     * @return POST 请求结果
     * @throws IOException 如果 IO 错误则抛出异常
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果目标链接对象为 {@code null} 则抛出异常
     */
    private static String fromPostRequest(Proxy proxy, String url, String postData, String contentType) throws IOException {
        validateProxyAndURL(proxy, url);
        byte[] bytes = postData.getBytes(Charset.forName("utf-8"));
        HttpURLConnection connection = createURLConnection(proxy, url);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream out = null;
        try {
            out = connection.getOutputStream();
            out.write(bytes);
        } finally {
            if(out != null) try {
                out.close();
            } catch (Exception e) {
            }
        }
        return getConnectionResult(connection);
    }

    /**
     * 获取当前月色之湖认证基础服务的 GSON 对象
     *
     * @return Gson
     */
    protected static Gson getGson() {
        return GSON;
    }

    /**
     * 获取指定字符串是否为空白内容
     *
     * @param str 字符串
     * @return 是否为空白内容
     */
    protected static boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    /**
     * 向指定链接以指定代理发送 HTTP 请求
     *
     * @param proxy 代理对象
     * @param url 目标链接
     * @param request 请求数据
     * @throws MoonLakeRequestException 如果请求错误则抛出异常
     */
    protected static void makeRequest(Proxy proxy, String url, Object request) throws MoonLakeRequestException {
        makeRequest(proxy, url, request, MojangBaseResponse.class);
    }

    /**
     * 向指定链接以指定代理发送 HTTP 请求
     *
     * @param proxy 代理对象
     * @param url 目标链接
     * @param request 请求数据
     * @param responseClass 响应类
     * @param <T> 响应类
     * @return 响应结果
     * @throws MoonLakeRequestException 如果请求错误则抛出异常
     */
    protected static <T extends MojangBaseResponse> T makeRequest(Proxy proxy, String url, Object request, Class<T> responseClass) throws MoonLakeRequestException {
        MojangBaseResponse response = null;
        try {
            String data = request == null ? fromGetRequest(proxy, url) : fromPostRequest(proxy, url, GSON.toJson(request), "application/json");
            response = GSON.fromJson(data, responseClass);
        } catch (Exception e) {
            throw new MoonLakeServiceUnavailableException("无法创建服务请求: " + url, e);
        }
        if(response != null && !isBlank(response.getError())) {
            if(!response.getError().equals("ForbiddenOperationException"))
                throw new MoonLakeRequestException(response.getErrorMessage());
            if(response.getCause() != null && response.getCause().equals("UserMigratedException"))
                throw new MoonLakeUserMigratedException(response.getErrorMessage());
            else
                throw new MoonLakeInvalidCredentialsException(response.getErrorMessage());
        }
        return (T) response;
    }

    /**
     * 开始运行指定 Runnable 对象
     *
     * @param runnable Runnable
     * @param async 异步
     */
    protected void start(final Runnable runnable, final boolean async) {

    }
}
