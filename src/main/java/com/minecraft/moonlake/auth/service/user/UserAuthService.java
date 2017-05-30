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


package com.minecraft.moonlake.auth.service.user;

import com.minecraft.moonlake.auth.data.GameProfile;
import com.minecraft.moonlake.auth.data.Property;
import com.minecraft.moonlake.auth.exception.MoonLakeAuthException;
import com.minecraft.moonlake.auth.response.MojangBaseResponse;
import com.minecraft.moonlake.auth.service.MoonLakeAuthBaseService;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <h1>UserAuthService</h1>
 * 用户认证服务类
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeAuthBaseService
 */
public class UserAuthService extends MoonLakeAuthBaseService {

    private final static String URL_REFRESH = "https://authserver.mojang.com/refresh";
    private final static String URL_SIGNOUT = "https://authserver.mojang.com/signout";
    private final static String URL_VALIDATE = "https://authserver.mojang.com/validate";
    private final static String URL_INVALIDATE = "https://authserver.mojang.com/invalidate";
    private final static String URL_AUTHENTICATE = "https://authserver.mojang.com/authenticate";

    private String id;
    private String username;
    private String password;
    private String clientToken;
    private String accessToken;
    private boolean loggedIn;
    private GameProfile selectedProfile;
    private List<Property> properties = new ArrayList<>();
    private List<GameProfile> profiles = new ArrayList<>();

    /**
     * 用户认证服务类构造函数
     */
    public UserAuthService() {
        this(Proxy.NO_PROXY);
    }

    /**
     * 用户认证服务类构造函数
     *
     * @param proxy 代理对象
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     */
    public UserAuthService(Proxy proxy) {
        this(UUID.randomUUID().toString(), proxy);
    }

    /**
     * 用户认证服务类构造函数
     *
     * @param clientToken 客户端令牌
     */
    public UserAuthService(String clientToken) {
        this(clientToken, Proxy.NO_PROXY);
    }

    /**
     * 用户认证服务类构造函数
     *
     * @param clientToken 客户端令牌
     * @param proxy 代理对象
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     */
    public UserAuthService(String clientToken, Proxy proxy) {
        super(proxy);
        this.clientToken = clientToken;
    }

    /**
     * 获取此用户认证服务的用户 Id
     *
     * @return 用户 Id
     */
    public String getId() {
        return id;
    }

    /**
     * 获取此用户认证服务的用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取此用户认证服务的用户密码
     *
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取此用户认证服务的访问令牌
     *
     * @return 访问令牌
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 获取此用户认证服务的客户端令牌
     *
     * @return 客户端令牌
     */
    public String getClientToken() {
        return clientToken;
    }

    /**
     * 获取此用户认证服务是否已经登录
     *
     * @return 是否已经登录
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * 获取此用户认证服务的游戏档案属性列表
     *
     * @return 属性列表
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * 获取此用户认证服务的可用游戏档案列表
     *
     * @return 可用游戏档案列表
     */
    public List<GameProfile> getAvailableProfiles() {
        return profiles;
    }

    /**
     * 获取此用户认证服务的选中游戏档案
     *
     * @return 选中游戏档案
     */
    public GameProfile getSelectedProfile() {
        return selectedProfile;
    }

    /**
     * 设置此用户认证服务的用户名
     *
     * @param username 用户名
     * @throws IllegalStateException 如果已经登录则抛出异常
     */
    public void setUsername(String username) {
        checkLoginState("用户名");
        this.username = username;
    }

    /**
     * 设置此用户认证服务的用户密码
     *
     * @param password 用户密码
     * @throws IllegalStateException 如果已经登录则抛出异常
     */
    public void setPassword(String password) {
        checkLoginState("密码");
        this.password = password;
    }

    /**
     * 设置此用户认证服务的访问令牌
     *
     * @param accessToken 访问令牌
     * @throws IllegalStateException 如果已经登录则抛出异常
     */
    public void setAccessToken(String accessToken) {
        checkLoginState("访问令牌");
        this.accessToken = accessToken;
    }

    /**
     * 将当前用户认证服务的用户信息开始向 HTTP 认证
     *
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果用户名是无效的则抛出异常
     * @throws MoonLakeAuthException 如果用户密码是无效的则抛出异常
     * @throws MoonLakeAuthException 如果访问令牌是无效的则抛出异常
     * @throws MoonLakeAuthException 如果无效的客户端令牌则抛出异常
     */
    public void login() throws MoonLakeAuthException {
        checkStringBlank(username, new MoonLakeAuthException("无效的用户名."));
        if(!isBlank(accessToken)) {
            loginWithToken();
        } else {
            checkStringBlank(password, new MoonLakeAuthException("无效的密码."));
            loginWithPassword();
        }
    }

    /**
     * 将当前用户认证服务进行登出
     *
     * @throws IllegalStateException 如果没有处于登录状态则抛出异常
     */
    public void logout() throws IllegalStateException {
        if(!loggedIn)
            throw new IllegalStateException("无法登出, 因为当前没有处于登录状态.");
        this.id = null;
        this.loggedIn = false;
        this.accessToken = null;
        this.selectedProfile = null;
        this.profiles.clear();
        this.properties.clear();
    }

    /**
     * 使用当前用户认证服务的用户名和密码进行将访问令牌登出
     *
     * @return 是否成功
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果用户名是无效的则抛出异常
     * @throws MoonLakeAuthException 如果用户密码是无效的则抛出异常
     */
    public boolean signoutToken() throws MoonLakeAuthException {
        checkStringBlank(username, new MoonLakeAuthException("无效的用户名."));
        checkStringBlank(password, new MoonLakeAuthException("无效的密码."));
        try {
            SignoutRequest request = new SignoutRequest(username, password);
            makeRequest(getProxy(), URL_SIGNOUT, request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 使用当前用户认证服务进行将访问令牌失效
     *
     * @return 是否成功
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果访问令牌是无效的则抛出异常
     */
    public boolean invalidateToken() throws MoonLakeAuthException {
        checkStringBlank(accessToken, new MoonLakeAuthException("无效的访问令牌."));
        try {
            InvalidateRequest request = new InvalidateRequest(clientToken, accessToken);
            makeRequest(getProxy(), URL_INVALIDATE, request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证当前用户认证服务的访问令牌是否有效
     *
     * @return 是否验证通过
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果访问令牌是无效的则抛出异常
     */
    public boolean validateToken() throws MoonLakeAuthException {
        checkStringBlank(accessToken, new MoonLakeAuthException("无效的访问令牌."));
        try {
            ValidateRequest request = new ValidateRequest(clientToken, accessToken);
            makeRequest(getProxy(), URL_VALIDATE, request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将当前用户认证服务的用户选择指定游戏档案进行刷新
     *
     * @param profile 游戏档案
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果无效的客户端令牌则抛出异常
     * @throws IllegalStateException 如果没有处于登录状态则抛出异常
     * @throws IllegalStateException 如果当前游戏档案已选择则抛出异常
     * @throws IllegalStateException 如果可用的游戏档案列表不包含参数则抛出异常
     */
    public void selectGameProfile(GameProfile profile) throws MoonLakeAuthException {
        if(!loggedIn)
            throw new IllegalStateException("无法选择游戏档案, 因为当前没有登录.");
        if(selectedProfile != null)
            throw new IllegalStateException("已选择游戏档案时无法再次选择.");
        if(profile != null && profiles.contains(profile)) {
            RefreshRequest request = new RefreshRequest(clientToken, accessToken, profile);
            RefreshResponse response = makeRequest(getProxy(), URL_REFRESH, request, RefreshResponse.class);
            if(response != null && clientToken.equals(response.clientToken)) {
                this.accessToken = response.accessToken;
                this.selectedProfile = response.selectedProfile;
            }
            throw new MoonLakeAuthException("错误: 服务器请求更改客户端令牌.");
        }
        throw new IllegalStateException("无效的游戏档案.");
    }

    @Override
    protected final void start(final Runnable runnable, final boolean async) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "UserAuthService{" +
                "username='" + username + '\'' +
                ", clientToken='" + clientToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", loggedIn=" + loggedIn +
                ", selectedProfile=" + selectedProfile +
                ", profiles=" + profiles +
                '}';
    }

    /**
     * 检测指定字符串是否为空白内容则抛出认证异常
     *
     * @param str 字符串
     * @param e 异常
     * @throws MoonLakeAuthException 抛出认证异常
     */
    private void checkStringBlank(String str, MoonLakeAuthException e) throws MoonLakeAuthException {
        if(isBlank(str))
            throw e;
    }

    /**
     * 检测当前用户认证服务的认证状态
     *
     * @param type 属性类型
     * @throws IllegalStateException 如果已经处于登录状态则抛出异常
     */
    private void checkLoginState(String type) throws IllegalStateException {
        if(loggedIn && selectedProfile != null)
            throw new IllegalStateException("已处于登录状态并已选择游戏档案, 不能再修改" + type + "属性.");
    }

    /**
     * 将当前用户认证服务的用户信息开始向 HTTP 认证 (密码方式)
     *
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果用户名是无效的则抛出异常
     * @throws MoonLakeAuthException 如果用户密码是无效的则抛出异常
     * @throws MoonLakeAuthException 如果无效的客户端令牌则抛出异常
     */
    private void loginWithPassword() throws MoonLakeAuthException {
        checkStringBlank(username, new MoonLakeAuthException("无效的用户名."));
        checkStringBlank(password, new MoonLakeAuthException("无效的密码."));
        AuthenticationRequest request = new AuthenticationRequest(username, password, clientToken);
        AuthenticationResponse response = makeRequest(getProxy(), URL_AUTHENTICATE, request, AuthenticationResponse.class);
        if(response != null && clientToken.equals(response.clientToken))
            loginProperty(response.user, response.accessToken, response.selectedProfile, response.availableProfiles);
        else
            throw new MoonLakeAuthException("错误: 服务器请求更改客户端令牌.");
    }

    /**
     * 将当前用户认证服务的用户信息开始向 HTTP 认证 (令牌方式)
     *
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws MoonLakeAuthException 如果用户名是无效的则抛出异常
     * @throws MoonLakeAuthException 如果访问令牌是无效的则抛出异常
     * @throws MoonLakeAuthException 如果无效的客户端令牌则抛出异常
     */
    private void loginWithToken() throws MoonLakeAuthException {
        if(id == null || id.isEmpty()) {
            checkStringBlank(username, new MoonLakeAuthException("无效的用户名."));
            this.id = username;
        }
        checkStringBlank(accessToken, new MoonLakeAuthException("无效的访问令牌."));
        RefreshRequest request = new RefreshRequest(clientToken, accessToken, null);
        RefreshResponse response = makeRequest(getProxy(), URL_REFRESH, request, RefreshResponse.class);
        if(response != null && clientToken.equals(response.clientToken))
            loginProperty(response.user, response.accessToken, response.selectedProfile, response.availableProfiles);
        else
            throw new MoonLakeAuthException("错误: 服务器请求更改客户端令牌.");
    }

    /**
     * 登录成功则进行设置当前用户认证服务的字段属性
     *
     * @param user 用户
     * @param accessToken 访问令牌
     * @param selectedProfile 选择游戏档案
     * @param availableProfiles 可用游戏档案
     */
    private void loginProperty(User user, String accessToken, GameProfile selectedProfile, GameProfile[] availableProfiles) {
        if(user != null && user.id != null)
            this.id = user.id;
        else
            this.id = username;
        this.loggedIn = true;
        this.accessToken = accessToken;
        this.selectedProfile = selectedProfile;
        this.profiles = availableProfiles != null ? Arrays.asList(availableProfiles) : new ArrayList<>();
        this.properties.clear();
        if(user != null && user.properties != null)
            this.properties.addAll(user.properties);
    }

    /**
     * 代理人实体类
     */
    private static class Agent {
        private String name;
        private int version;

        protected Agent(String name, int version) {
            this.name = name;
            this.version = version;
        }
    }

    /**
     * 用户实体类
     */
    private static class User {
        public String id;
        public List<Property> properties;
    }

    /**
     * 认证请求实体类
     */
    private static class AuthenticationRequest {
        private Agent agent;
        private String username;
        private String password;
        private boolean requestUser;
        private String clientToken;

        protected AuthenticationRequest(String username, String password, String clientToken) {
            this.agent = new Agent("Minecraft", 1);
            this.username = username;
            this.password = password;
            this.requestUser = true;
            this.clientToken = clientToken;
        }
    }

    /**
     * 刷新请求实体类
     */
    private static class RefreshRequest {
        private String clientToken;
        private String accessToken;
        private GameProfile selectedProfile;
        private boolean requestUser;

        protected RefreshRequest(String clientToken, String accessToken, GameProfile selectedProfile) {
            this.clientToken = clientToken;
            this.accessToken = accessToken;
            this.selectedProfile = selectedProfile;
            this.requestUser = true;
        }
    }

    /**
     * 验证请求实体类
     */
    private static class ValidateRequest {
        private String clientToken;
        private String accessToken;

        protected ValidateRequest(String clientToken, String accessToken) {
            this.clientToken = clientToken;
            this.accessToken = accessToken;
        }
    }

    /**
     * 失效令牌验证请求实体类
     */
    private static class InvalidateRequest {
        private String clientToken;
        private String accessToken;

        protected InvalidateRequest(String clientToken, String accessToken) {
            this.clientToken = clientToken;
            this.accessToken = accessToken;
        }
    }

    /**
     * 登出令牌请求实体类
     */
    private static class SignoutRequest {
        private String username;
        private String password;

        protected SignoutRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    /**
     * 认证响应实体类
     */
    private static class AuthenticationResponse extends MojangBaseResponse {
        public String accessToken;
        public String clientToken;
        public GameProfile selectedProfile;
        public GameProfile[] availableProfiles;
        public User user;
    }

    /**
     * 刷新响应实体类
     */
    private static class RefreshResponse extends MojangBaseResponse {
        public String accessToken;
        public String clientToken;
        public GameProfile selectedProfile;
        public GameProfile[] availableProfiles;
        public User user;
    }
}
