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


package com.minecraft.moonlake.auth.service.profile;

import com.minecraft.moonlake.auth.data.*;
import com.minecraft.moonlake.auth.exception.*;
import com.minecraft.moonlake.auth.response.MojangBaseResponse;
import com.minecraft.moonlake.auth.response.ProfileHistoryResponse;
import com.minecraft.moonlake.auth.response.ProfileSearchResponse;
import com.minecraft.moonlake.auth.service.MoonLakeAuthBaseService;
import com.minecraft.moonlake.auth.service.mc.MinecraftAuthService;
import com.minecraft.moonlake.auth.util.UUIDSerializer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.Proxy;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * <h1>ProfileAuthService</h1>
 * 档案认证服务类
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeAuthBaseService
 */
public class ProfileAuthService extends MoonLakeAuthBaseService {

    private final static String URL_PROFILES = "https://api.mojang.com/profiles/minecraft";
    private final static String URL_PROFILE_TIME = "https://api.mojang.com/users/profiles/minecraft";
    private final static String URL_PROFILE_HISTORY = "https://api.mojang.com/user/profiles/%1$s/names";
    private final static int MAX_FAIL_COUNT = 3;
    private final static int DELAY_BETWEEN_PAGES = 100;
    private final static int DELAY_BETWEEN_FAILURES = 750;
    private final static int PROFILES_PER_REQUEST = 100;

    /**
     * 档案认证服务类构造函数
     */
    public ProfileAuthService() {
        super();
    }

    /**
     * 档案认证服务类构造函数
     *
     * @param proxy 代理对象
     * @throws IllegalArgumentException 如果代理对象为 {@code null} 则抛出异常
     */
    public ProfileAuthService(Proxy proxy) {
        super(proxy);
    }

    /**
     * 从指定用户名向 HTTP 查找游戏档案数据
     *
     * @param name 用户名
     * @param callback 游戏档案回调对象
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByName(String name, ProfileLookupCallback callback) {
        findProfileByName(name, callback, false);
    }

    /**
     * 从指定用户名向 HTTP 查找游戏档案数据
     *
     * @param name 用户名
     * @param callback 游戏档案回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByName(String name, ProfileLookupCallback callback, boolean async) {
        validate(name, "名称对象不能为 null 值.");
        findProfilesByName(new String[] { name }, callback, async);
    }

    /**
     * 从指定用户名数组向 HTTP 查找游戏档案数据
     *
     * @param names 用户名数组
     * @param callback 游戏档案回调对象
     * @throws IllegalArgumentException 如果用户名数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfilesByName(String[] names, ProfileLookupCallback callback) {
        findProfilesByName(names, callback, false);
    }

    /**
     * 从指定用户名数组向 HTTP 查找游戏档案数据
     *
     * @param names 用户名数组
     * @param callback 游戏档案回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果用户名数组对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfilesByName(String[] names, ProfileLookupCallback callback, boolean async) {
        validate(names, "名称数组不能为 null 值.");
        validate(callback, "游戏档案查询回调对象不能为 null 值.");
        final Set<String> nameSet = new HashSet<>();
        for(String name : names)
            if(name != null && !name.isEmpty())
                nameSet.add(name.toLowerCase());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(Set<String> request : fromRequest(nameSet, PROFILES_PER_REQUEST)) {
                    int failedCount = 0;
                    boolean tryAgain = true;
                    while(failedCount < MAX_FAIL_COUNT && tryAgain) {
                        tryAgain = false;
                        try {
                            ProfileSearchResponse response = makeRequest(getProxy(), URL_PROFILES, request, ProfileSearchResponse.class);
                            failedCount = 0;
                            Set<String> missing = new HashSet<>(request);
                            for(GameProfile profile : response.getProfiles()) {
                                missing.remove(profile.getName().toLowerCase());
                                callback.onLookupSucceeded(profile);
                            }
                            for(String name : missing)
                                callback.onLookupFailed(new GameProfile((UUID) null, name), new MoonLakeProfileNotFoundException("服务器没有存在名为 '" + name + "' 的游戏档案."));
                            try {
                                Thread.sleep(DELAY_BETWEEN_PAGES);
                            } catch (InterruptedException e) {
                            }
                        } catch (MoonLakeRequestException e) {
                            failedCount++;
                            if(failedCount >= MAX_FAIL_COUNT) {
                                for(String name : request)
                                    callback.onLookupFailed(new GameProfile((UUID) null, name), e);
                            } else {
                                try {
                                    Thread.sleep(DELAY_BETWEEN_FAILURES);
                                } catch (InterruptedException e1) {
                                }
                                tryAgain = true;
                            }
                        }
                    }
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定时间戳向 HTTP 查找指定用户名的游戏档案数据
     *
     * @param name 用户名
     * @param callback 游戏档案回调对象
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByTimestamp(String name, ProfileLookupCallback callback) {
        findProfileByTimestamp(name, callback, false);
    }

    /**
     * 从指定时间戳向 HTTP 查找指定用户名的游戏档案数据
     *
     * @param name 用户名
     * @param callback 游戏档案回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByTimestamp(String name, ProfileLookupCallback callback, boolean async) {
        findProfileByTimestamp(name, -1L, callback, async);
    }

    /**
     * 从指定时间戳向 HTTP 查找指定用户名的游戏档案数据
     *
     * @param name 用户名
     * @param timestamp 时间戳
     * @param callback 游戏档案回调对象
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByTimestamp(String name, long timestamp, ProfileLookupCallback callback) {
        findProfileByTimestamp(name, timestamp, callback, false);
    }

    /**
     * 从指定时间戳向 HTTP 查找指定用户名的游戏档案数据
     *
     * @param name 用户名
     * @param timestamp 时间戳
     * @param callback 游戏档案回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果游戏档案回调对象为 {@code null} 则抛出异常
     */
    public void findProfileByTimestamp(String name, long timestamp, ProfileLookupCallback callback, boolean async) {
        validate(name, "名称对象不能为 null 值.");
        validate(callback, "游戏档案查询回调对象不能为 null 值.");
        final String finalUrl = URL_PROFILE_TIME + "/" + name + (timestamp < 0L ? "" : ("?at=" + timestamp));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ProfileTimestampResponse response = makeRequest(getProxy(), finalUrl, null, ProfileTimestampResponse.class);
                    callback.onLookupSucceeded(new GameProfile(response.id, response.name));
                } catch (Exception e) {
                    callback.onLookupFailed(new GameProfile((UUID) null, name), e);
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案向 HTTP 查找档案名称历史记录
     *
     * @param profile 游戏档案
     * @param callback 档案历史记录回调对象
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果档案历史记录回调对象为 {@code null} 则抛出异常
     */
    public void findNameHistoryByProfile(GameProfile profile, ProfileHistoryCallback callback) {
        findNameHistoryByProfile(profile, callback, false);
    }

    /**
     * 从指定游戏档案向 HTTP 查找档案名称历史记录
     *
     * @param profile 游戏档案
     * @param callback 档案历史记录回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果档案历史记录回调对象为 {@code null} 则抛出异常
     */
    public void findNameHistoryByProfile(GameProfile profile, ProfileHistoryCallback callback, boolean async) {
        validate(profile, "游戏档案对象不能为 null 值.");
        validate(callback, "档案历史回调对象不能为 null 值.");
        findNameHistoryById(profile.getId(), callback, async);
    }

    /**
     * 从指定用户 UUID 向 HTTP 查找档案名称历史记录
     *
     * @param id 用户 Id
     * @param callback 档案历史记录回调对象
     * @throws IllegalArgumentException 如果用户 Id 对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果档案历史记录回调对象为 {@code null} 则抛出异常
     */
    public void findNameHistoryById(UUID id, ProfileHistoryCallback callback) {
        findNameHistoryById(id, callback, false);
    }

    /**
     * 从指定用户 UUID 向 HTTP 查找档案名称历史记录
     *
     * @param id 用户 Id
     * @param callback 档案历史记录回调对象
     * @param async 是否异步
     * @throws IllegalArgumentException 如果用户 Id 对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果档案历史记录回调对象为 {@code null} 则抛出异常
     */
    public void findNameHistoryById(UUID id, ProfileHistoryCallback callback, boolean async) {
        validate(id, "目标 UUID 对象不能为 null 值.");
        final String finalUrl = String.format(URL_PROFILE_HISTORY, UUIDSerializer.fromUUID(id));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ProfileHistoryResponse response = makeRequest(getProxy(), finalUrl, null, ProfileHistoryResponse.class);
                    callback.onLookupSucceeded(id, new ProfileHistoryList(Arrays.asList(response.getHistories())));
                } catch (Exception e) {
                    callback.onLookupFailed(id, e);
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤源图片
     *
     * @param name 用户名
     * @param callback 皮肤源图片回调对象
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinRawTextureByName(String name, SkinRawImageCallback<String> callback) throws MoonLakeAuthException {
        findSkinRawTextureByName(name, callback, false);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤源图片
     *
     * @param name 用户名
     * @param callback 皮肤源图片回调对象
     * @param async 是否异步
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinRawTextureByName(String name, SkinRawImageCallback<String> callback, boolean async) throws MoonLakeAuthException {
        validate(name, "用户名对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findProfileByName(name, new ProfileLookupCallback() {
                    @Override
                    public void onLookupSucceeded(GameProfile profile) {
                        try {
                            findSkinRawTextureByProfile(profile, new SkinRawImageCallback<GameProfile>() {
                                @Override
                                public void onLookupSucceeded(GameProfile param, BufferedImage skinRawImage) {
                                    callback.onLookupSucceeded(param.getName(), skinRawImage);
                                }

                                @Override
                                public void onLookupFailed(GameProfile param, Exception ex) {
                                    callback.onLookupFailed(param.getName(), ex);
                                }
                            }, false); // false 不再使用新的线程, 而在当前线程
                        } catch (MoonLakeAuthException e) {
                            callback.onLookupFailed(name, e);
                        }
                    }

                    @Override
                    public void onLookupFailed(GameProfile profile, Exception ex) {
                        callback.onLookupFailed(profile.getName(), ex);
                    }
                });
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案查找用户的皮肤源图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏
     * @param callback 皮肤源图片回调对象
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinRawTextureByProfile(GameProfile profile, SkinRawImageCallback<GameProfile> callback) throws MoonLakeAuthException {
        findSkinRawTextureByProfile(profile, callback, false);
    }

    /**
     * 从指定游戏档案查找用户的皮肤源图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏
     * @param callback 皮肤源图片回调对象
     * @param async 是否异步
     * @throws MoonLakeAuthException 如果认证错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinRawTextureByProfile(GameProfile profile, SkinRawImageCallback<GameProfile> callback, boolean async) throws MoonLakeAuthException {
        validate(profile, "游戏档案对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean existGetSucceed = false;
                ProfileTexture skinTexture = null;
                Map<TextureType, ProfileTexture> textures = profile.getTextures();
                if(!textures.isEmpty() && (skinTexture = textures.get(TextureType.SKIN)) != null && !isBlank(skinTexture.getUrl())) {
                    try {
                        BufferedImage image = getSkinRawTextureByProfile(profile);
                        callback.onLookupSucceeded(profile, image);
                        existGetSucceed = true;
                    } catch (MoonLakeSkinException e) {
                    }
                }
                if(existGetSucceed)
                    return;
                // 当前游戏档案不存在材质属性数据则进行获取
                try {
                    MinecraftAuthService minecraftAuthService = new MinecraftAuthService(getProxy());
                    minecraftAuthService.fillProfileProperties(profile);
                    minecraftAuthService.fillProfileTextures(profile);
                    BufferedImage image = getSkinRawTextureByProfile(profile);
                    callback.onLookupSucceeded(profile, image);
                } catch (Exception e) {
                    callback.onLookupFailed(profile, e);
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案获取用户的皮肤源图片
     *
     * @param profile 游戏档案
     * @return 皮肤源图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinRawTextureByProfile(GameProfile profile) throws MoonLakeSkinException {
        validate(profile, "游戏档案对象不能为 null 值.");
        try {
            ProfileTexture skinTexture = null;
            Map<TextureType, ProfileTexture> textures = profile.getTextures();
            if(textures.isEmpty() || (skinTexture = textures.get(TextureType.SKIN)) == null || isBlank(skinTexture.getUrl()))
                throw new MoonLakeSkinNotFoundException("游戏档案对象不存在任何皮肤材质数据.");
            return ImageIO.read(new URL(skinTexture.getUrl()));
        } catch (Exception e) {
            throw new MoonLakeSkinException("获取游戏档案的皮肤材质数据时错误.", e);
        }
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤头像图片
     *
     * @param name 用户名
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByName(String name, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByName(name, 8, callback);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤头像图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByName(String name, int zoom, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByName(name, zoom, true, callback);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤头像图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByName(String name, int zoom, boolean helmet, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByName(name, zoom, helmet, callback, false);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤头像图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤头像图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByName(String name, int zoom, boolean helmet, SkinRawImageCallback<String> callback, boolean async) throws MoonLakeSkinException {
        validate(name, "用户名对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findProfileByName(name, new ProfileLookupCallback() {
                    @Override
                    public void onLookupSucceeded(GameProfile profile) {
                        try {
                            findSkinHeadTextureByProfile(profile, zoom, helmet, new SkinRawImageCallback<GameProfile>() {
                                @Override
                                public void onLookupSucceeded(GameProfile param, BufferedImage headImage) {
                                    callback.onLookupSucceeded(param.getName(), headImage);
                                }

                                @Override
                                public void onLookupFailed(GameProfile param, Exception ex) {
                                    callback.onLookupFailed(param.getName(), ex);
                                }
                            });
                        } catch (MoonLakeAuthException e) {
                            callback.onLookupFailed(name, e);
                        }
                    }

                    @Override
                    public void onLookupFailed(GameProfile profile, Exception ex) {
                        callback.onLookupFailed(profile.getName(), ex);
                    }
                });
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案查找用户的皮肤头像图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByProfile(GameProfile profile, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByProfile(profile, 8, callback);
    }

    /**
     * 从指定游戏档案查找用户的皮肤头像图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByProfile(GameProfile profile, int zoom, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByProfile(profile, zoom, true, callback);
    }

    /**
     * 从指定游戏档案查找用户的皮肤头像图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤头像图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByProfile(GameProfile profile, int zoom, boolean helmet, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinHeadTextureByProfile(profile, zoom, helmet, callback, false);
    }

    /**
     * 从指定游戏档案查找用户的皮肤头像图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤头像图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤头像图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinHeadTextureByProfile(GameProfile profile, int zoom, boolean helmet, SkinRawImageCallback<GameProfile> callback, boolean async) throws MoonLakeSkinException {
        validate(profile, "游戏档案对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean existGetSucceed = false;
                ProfileTexture skinTexture = null;
                Map<TextureType, ProfileTexture> textures = profile.getTextures();
                if(!textures.isEmpty() && (skinTexture = textures.get(TextureType.SKIN)) != null && !isBlank(skinTexture.getUrl())) {
                    try {
                        BufferedImage image = getSkinRawTextureByProfile(profile);
                        BufferedImage headImage = getSkinHeadTextureByRaw(image, zoom, helmet);
                        callback.onLookupSucceeded(profile, headImage);
                        existGetSucceed = true;
                    } catch (MoonLakeSkinException e) {
                    }
                }
                if(existGetSucceed)
                    return;
                // 当前游戏档案不存在材质属性数据则进行获取
                try {
                    MinecraftAuthService minecraftAuthService = new MinecraftAuthService(getProxy());
                    minecraftAuthService.fillProfileProperties(profile);
                    minecraftAuthService.fillProfileTextures(profile);
                    BufferedImage image = getSkinRawTextureByProfile(profile);
                    BufferedImage headImage = getSkinHeadTextureByRaw(image, zoom, helmet);
                    callback.onLookupSucceeded(profile, headImage);
                } catch (Exception e) {
                    callback.onLookupFailed(profile, e);
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案获取用户的皮肤头像图片
     *
     * @param profile 游戏档案
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByProfile(GameProfile profile) throws MoonLakeSkinException {
        return getSkinHeadTextureByProfile(profile, 8);
    }

    /**
     * 从指定游戏档案获取用户的皮肤头像图片
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByProfile(GameProfile profile, int zoom) throws MoonLakeSkinException {
        return getSkinHeadTextureByProfile(profile, zoom, true);
    }

    /**
     * 从指定游戏档案获取用户的皮肤头像图片
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByProfile(GameProfile profile, int zoom, boolean helmet) throws MoonLakeSkinException {
        validate(profile, "游戏档案对象不能为 null 值.");
        BufferedImage skinRawImage = getSkinRawTextureByProfile(profile);
        return getSkinHeadTextureByRaw(skinRawImage, zoom, helmet);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤头像图片
     *
     * @param skinRawImage 皮肤源图片
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByRaw(BufferedImage skinRawImage) throws MoonLakeSkinException {
        return getSkinHeadTextureByRaw(skinRawImage, 8);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤头像图片
     *
     * @param skinRawImage 皮肤源图片
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByRaw(BufferedImage skinRawImage, int zoom) throws MoonLakeSkinException {
        return getSkinHeadTextureByRaw(skinRawImage, zoom, true);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤头像图片
     *
     * @param skinRawImage 皮肤源图片
     * @param zoom 放大倍数 (默认 8 倍, 64x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @return 皮肤头像图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinHeadTextureByRaw(BufferedImage skinRawImage, int zoom, boolean helmet) throws MoonLakeSkinException {
        validate(skinRawImage, "皮肤材质源图片对象不能为 null 值.");
        Boolean skinVer = getSkinRawImageVer(skinRawImage);
        if(skinVer == null)
            throw new MoonLakeSkinException("错误的皮肤材质源图片大小, 应为 64x64 或 64x32 大小.");
        if(zoom <= 0)
            zoom = 1;
        if(skinRawImage.getType() != BufferedImage.TYPE_INT_ARGB)
            skinRawImage = conversionImage(skinRawImage, BufferedImage.TYPE_INT_ARGB);
        // 如果皮肤源图片的类型不为 INT_ARGB 通道则进行重绘并转换
        BufferedImage skinHeadImage = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        skinHeadImage.setRGB(0, 0, 8, 8, skinRawImage.getRGB(8, 8, 8, 8, null, 0, 8), 0, 8);
        int[] helmetRgb = helmet ? skinRawImage.getRGB(40, 8, 8, 8, null, 0, 8) : null;
        if(helmet)
            drawHelmetImage(skinHeadImage, helmetRgb, 8, 8, 0, 0, 0, 8);
        // 将 8x8 像素的头像图片进行放大处理
        return resizeImage(skinHeadImage, zoom);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByName(name, 2, callback);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, int zoom, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByName(name, zoom, true, callback);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, int zoom, boolean helmet, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByName(name, zoom, helmet, callback, false);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤模型图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, int zoom, boolean helmet, SkinRawImageCallback<String> callback, boolean async) throws MoonLakeSkinException {
        findSkinModel2DTextureByName(name, zoom, helmet, false, callback, async);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, int zoom, boolean helmet, boolean slim, SkinRawImageCallback<String> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByName(name, zoom, helmet, slim, callback, false);
    }

    /**
     * 从指定用户名向 HTTP 查找用户的皮肤模型 2D 图片
     *
     * @param name 用户名
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @param callback 皮肤模型图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果用户名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByName(String name, int zoom, boolean helmet, boolean slim, SkinRawImageCallback<String> callback, boolean async) throws MoonLakeSkinException {
        validate(name, "用户名对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findProfileByName(name, new ProfileLookupCallback() {
                    @Override
                    public void onLookupSucceeded(GameProfile profile) {
                        try {
                            findSkinModel2DTextureByProfile(profile, zoom, helmet, slim, new SkinRawImageCallback<GameProfile>() {
                                @Override
                                public void onLookupSucceeded(GameProfile param, BufferedImage headImage) {
                                    callback.onLookupSucceeded(param.getName(), headImage);
                                }

                                @Override
                                public void onLookupFailed(GameProfile param, Exception ex) {
                                    callback.onLookupFailed(param.getName(), ex);
                                }
                            });
                        } catch (MoonLakeAuthException e) {
                            callback.onLookupFailed(name, e);
                        }
                    }

                    @Override
                    public void onLookupFailed(GameProfile profile, Exception ex) {
                        callback.onLookupFailed(profile.getName(), ex);
                    }
                });
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByProfile(profile, 2, callback);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, int zoom, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByProfile(profile, zoom, true, callback);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByProfile(profile, zoom, helmet, callback, false);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param callback 皮肤模型图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet, SkinRawImageCallback<GameProfile> callback, boolean async) throws MoonLakeSkinException {
        findSkinModel2DTextureByProfile(profile, zoom, helmet, false, callback, async);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @param callback 皮肤模型图片回调对象
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet, boolean slim, SkinRawImageCallback<GameProfile> callback) throws MoonLakeSkinException {
        findSkinModel2DTextureByProfile(profile, zoom, helmet, slim, callback, false);
    }

    /**
     * 从指定游戏档案查找用户的皮肤模型 2D 图片, 如果档案不存在皮肤则请求 HTTP 获取数据
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @param callback 皮肤模型图片回调对象
     * @param async 是否异步
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果皮肤模型图片回调对象为 {@code null} 则抛出异常
     */
    public void findSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet, boolean slim, SkinRawImageCallback<GameProfile> callback, boolean async) throws MoonLakeSkinException {
        validate(profile, "游戏档案对象不能为 null 值.");
        validate(callback, "皮肤源图片回调对象不能为 null 值.");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean existGetSucceed = false;
                ProfileTexture skinTexture = null;
                Map<TextureType, ProfileTexture> textures = profile.getTextures();
                if(!textures.isEmpty() && (skinTexture = textures.get(TextureType.SKIN)) != null && !isBlank(skinTexture.getUrl())) {
                    try {
                        BufferedImage image = getSkinRawTextureByProfile(profile);
                        BufferedImage modelImage = getSkinModel2DTextureByRaw(image, zoom, helmet, slim);
                        callback.onLookupSucceeded(profile, modelImage);
                        existGetSucceed = true;
                    } catch (MoonLakeSkinException e) {
                    }
                }
                if(existGetSucceed)
                    return;
                // 当前游戏档案不存在材质属性数据则进行获取
                try {
                    MinecraftAuthService minecraftAuthService = new MinecraftAuthService(getProxy());
                    minecraftAuthService.fillProfileProperties(profile);
                    minecraftAuthService.fillProfileTextures(profile);
                    BufferedImage image = getSkinRawTextureByProfile(profile);
                    BufferedImage modelImage = getSkinModel2DTextureByRaw(image, zoom, helmet, slim);
                    callback.onLookupSucceeded(profile, modelImage);
                } catch (Exception e) {
                    callback.onLookupFailed(profile, e);
                }
            }
        };
        start(runnable, async);
    }

    /**
     * 从指定游戏档案获取用户的皮肤模型 2D 图片
     *
     * @param profile 游戏档案
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByProfile(GameProfile profile) throws MoonLakeSkinException {
        return getSkinModel2DTextureByProfile(profile, 2);
    }

    /**
     * 从指定游戏档案获取用户的皮肤模型 2D 图片
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByProfile(GameProfile profile, int zoom) throws MoonLakeSkinException {
        return getSkinModel2DTextureByProfile(profile, zoom, true);
    }

    /**
     * 从指定游戏档案获取用户的皮肤模型 2D 图片
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet) throws MoonLakeSkinException {
        return getSkinModel2DTextureByProfile(profile, zoom, helmet, false);
    }

    /**
     * 从指定游戏档案获取用户的皮肤模型 2D 图片
     *
     * @param profile 游戏档案
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws MoonLakeSkinNotFoundException 如果游戏档案不存在皮肤则抛出异常
     * @throws IllegalArgumentException 如果游戏档案对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByProfile(GameProfile profile, int zoom, boolean helmet, boolean slim) throws MoonLakeSkinException {
        validate(profile, "游戏档案对象不能为 null 值.");
        BufferedImage skinRawImage = getSkinRawTextureByProfile(profile);
        return getSkinModel2DTextureByRaw(skinRawImage, zoom, helmet, slim);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤模型 2D 图片
     *
     * @param skinRawImage 皮肤源图片
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByRaw(BufferedImage skinRawImage) throws MoonLakeSkinException {
        return getSkinModel2DTextureByRaw(skinRawImage, 2);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤模型 2D 图片
     *
     * @param skinRawImage 皮肤源图片
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByRaw(BufferedImage skinRawImage, int zoom) throws MoonLakeSkinException {
        return getSkinModel2DTextureByRaw(skinRawImage, zoom, true);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤模型 2D 图片
     *
     * @param skinRawImage 皮肤源图片
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByRaw(BufferedImage skinRawImage, int zoom, boolean helmet) throws MoonLakeSkinException {
        return getSkinModel2DTextureByRaw(skinRawImage, zoom, helmet, false);
    }

    /**
     * 从指定皮肤源文件获取用户的皮肤模型 2D 图片
     *
     * @param skinRawImage 皮肤源图片
     * @param zoom 放大倍数 (默认 2 倍, 32x64 像素)
     * @param helmet 是否绘制外层皮肤 (默认为 true)
     * @param slim 是否苗条
     * @return 皮肤模型 2D 图片
     * @throws MoonLakeSkinException 如果皮肤错误则抛出异常
     * @throws IllegalArgumentException 如果皮肤源图片对象为 {@code null} 则抛出异常
     */
    public BufferedImage getSkinModel2DTextureByRaw(BufferedImage skinRawImage, int zoom, boolean helmet, boolean slim) throws MoonLakeSkinException {
        // 将指定皮肤材质源图片绘制成 2D 模型图片
        validate(skinRawImage, "皮肤材质源图片对象不能为 null 值.");
        Boolean skinVer = getSkinRawImageVer(skinRawImage);
        if(skinVer == null)
            throw new MoonLakeSkinException("错误的皮肤材质源图片大小, 应为 64x64 或 64x32 大小.");
        if(zoom <= 0)
            zoom = 1;
        if(skinRawImage.getType() != BufferedImage.TYPE_INT_ARGB)
            skinRawImage = conversionImage(skinRawImage, BufferedImage.TYPE_INT_ARGB);
        // 如果皮肤源图片的类型不为 INT_ARGB 通道则进行重绘并转换
        BufferedImage skinModel2DImage = new BufferedImage(16, 32, BufferedImage.TYPE_INT_ARGB);
        if(!skinVer) {
            // 旧版本皮肤 64x32 像素的绘制方式
            skinModel2DImage.setRGB(4, 0, 8, 8, skinRawImage.getRGB(8, 8, 8, 8, null, 0, 8), 0, 8); // 头
            skinModel2DImage.setRGB(4, 8, 8, 12, skinRawImage.getRGB(20, 20, 8, 12, null, 0, 8), 0, 8); // 身体
            BufferedImage armImage = new BufferedImage(slim ? 3 : 4, 12, BufferedImage.TYPE_INT_ARGB); // 手
            armImage.setRGB(0, 0, slim ? 3 : 4, 12, skinRawImage.getRGB(44, 20, slim ? 3 : 4, 12, null, 0, 8), 0, 8);
            skinModel2DImage.getGraphics().drawImage(armImage, slim ? 1 : 0, 8, slim ? 3 : 4, 12, null); // 左手
            skinModel2DImage.getGraphics().drawImage(flippedImage(armImage), 12, 8, slim ? 3 : 4, 12, null); // 右手 (翻转左手)
            BufferedImage legImage = new BufferedImage(4, 12, BufferedImage.TYPE_INT_ARGB); // 腿
            legImage.setRGB(0, 0, 4, 12, skinRawImage.getRGB(4, 20, 4, 12, null, 0, 8), 0, 8);
            skinModel2DImage.getGraphics().drawImage(legImage, 4, 20, 4, 12, null); // 右腿
            skinModel2DImage.getGraphics().drawImage(flippedImage(legImage), 8, 20, 4, 12, null); // 左腿 (翻转右腿)
            if(helmet) // 旧版本只有头是有双层的, 其他没有
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(40, 8, 8, 8, null, 0, 8), 8, 8, 4, 0, 0, 8); // 头外层
        } else {
            // 新版本双层皮肤 64x64 像素的绘制方式
            skinModel2DImage.setRGB(4, 0, 8, 8, skinRawImage.getRGB(8, 8, 8, 8, null, 0, 8), 0, 8); // 头
            skinModel2DImage.setRGB(4, 8, 8, 12, skinRawImage.getRGB(20, 20, 8, 12, null, 0, 8), 0, 8); // 身体
            skinModel2DImage.setRGB(slim ? 1 : 0, 8, slim ? 3 : 4, 12, skinRawImage.getRGB(44, 20, slim ? 3 : 4, 12, null, 0, 8), 0, 8); // 右手
            skinModel2DImage.setRGB(12, 8, slim ? 3 : 4, 12, skinRawImage.getRGB(36, 52, slim ? 3 : 4, 12, null, 0, 8), 0, 8); // 左手
            skinModel2DImage.setRGB(4, 20, 4, 12, skinRawImage.getRGB(4, 20, 4, 12, null, 0, 8), 0, 8); // 右腿
            skinModel2DImage.setRGB(8, 20, 4, 12, skinRawImage.getRGB(20, 52, 4, 12, null, 0, 8), 0, 8); // 左腿
            if(helmet) { // 新版本所有部位都有双层的
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(40, 8, 8, 8, null, 0, 8), 8, 8, 4, 0, 0, 8); // 头外层
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(20, 32, 8, 12, null, 0, 8), 8, 12, 4, 8, 0, 8); // 身体外层
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(44, 32, slim ? 3 : 4, 12, null, 0, 8), slim ? 3 : 4, 12, slim ? 1 : 0, 8, 0, 8); // 右手外层
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(52, 52, slim ? 3 : 4, 12, null, 0, 8), slim ? 3 : 4, 12, 12, 8, 0, 8); // 左手外层
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(4, 36, 4, 12, null, 0, 8), 4, 12, 4, 20, 0, 8); // 右腿外层
                drawHelmetImage(skinModel2DImage, skinRawImage.getRGB(4, 52, 4, 12, null, 0, 8), 4, 12, 8, 20, 0, 8); // 左腿外层
            }
        }
        // 将 16x32 像素的2D模型图片进行放大处理
        return resizeImage(skinModel2DImage, zoom);
    }

    @Override
    protected void start(final Runnable runnable, final boolean async) {
        if(async)
            new Thread(runnable, "ProfileAuthService").start();
        else
            runnable.run();
    }

    /**
     * 格式化名称获取游戏档案的集合请求
     *
     * @param set 名称集合
     * @param size 大小
     * @return 结果
     */
    private static Set<Set<String>> fromRequest(Set<String> set, int size) {
        List<String> list = new ArrayList<>(set);
        Set<Set<String>> request = new HashSet<>();
        for(int i = 0; i < list.size(); i+= size) {
            Set<String> value = new HashSet<>();
            value.addAll(list.subList(i, Math.min(i + size, list.size())));
            request.add(value);
        }
        return request;
    }

    /**
     * 获取指定皮肤源图片的版本
     *
     * @param skinRawImage 皮肤源图片
     * @return 皮肤版本
     */
    private static Boolean getSkinRawImageVer(BufferedImage skinRawImage) {
        // 获取皮肤材质源图片的版本
        // false: 旧版本 64x32 像素
        // true: 新版本 64x64 像素
        // null: 错误的像素大小
        int width = skinRawImage.getWidth();
        int height = skinRawImage.getHeight();
        if(width == 64) {
            if(height == 64)
                return true;
            if(height == 32)
                return false;
        }
        return null;
    }

    /**
     * 将指定图片转换为指定图片类型的图片
     *
     * @param image 图片
     * @param type 转换类型
     * @return 结果图片
     */
    private static BufferedImage conversionImage(BufferedImage image, int type) {
        BufferedImage conversion = new BufferedImage(image.getWidth(), image.getHeight(), type);
        conversion.getGraphics().drawImage(image, 0, 0, null);
        return conversion;
    }

    /**
     * 将指定图片按指定倍数进行缩放大小
     *
     * @param raw 图片源
     * @param zoom 倍数
     * @return 结果图片
     */
    private static BufferedImage resizeImage(BufferedImage raw, int zoom) {
        BufferedImage target = new BufferedImage(raw.getWidth() * zoom, raw.getHeight() * zoom, BufferedImage.TYPE_INT_ARGB);
        Graphics g = target.createGraphics();
        g.drawImage(raw, 0, 0, target.getWidth(), target.getHeight(), null);
        g.dispose();
        return target;
    }

    /**
     * 将指定图片进行水平翻转
     *
     * @param raw 源图片
     * @return 结果图片
     */
    private static BufferedImage flippedImage(BufferedImage raw) {
        int width = raw.getWidth();
        int height = raw.getHeight();
        BufferedImage target = new BufferedImage(width, height, raw.getTransparency());
        Graphics g = target.createGraphics();
        g.drawImage(raw, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return target;
    }

    /**
     * 将指定图片进行绘制外层皮肤数据
     *
     * @param image 待绘制图片
     * @param rgbArray RGB 数组
     * @param width 宽度
     * @param height 高度
     * @param startX 开始 X
     * @param startY 开始 Y
     * @param offset 偏移
     * @param scansize 扫描尺寸
     */
    private static void drawHelmetImage(BufferedImage image, int[] rgbArray, int width, int height, int startX, int startY, int offset, int scansize) {
        /**
         * 算法摘自, 只不过就是添加了验证是否是透明的 rgb 值
         * @see BufferedImage#setRGB(int, int, int, int, int[], int, int)
         */
        int yoff  = offset;
        int off;
        Object pixel = null;
        WritableRaster raster = image.getRaster();
        ColorModel colorModel = image.getColorModel();

        for (int y = startY; y < startY + height; y++, yoff += scansize) {
            off = yoff;
            for (int x = startX; x < startX + width; x++) {
                int rgb = rgbArray[off++];
                if(isInvalidColorRgb(rgb))
                    continue;
                pixel = colorModel.getDataElements(rgb, pixel);
                raster.setDataElements(x, y, pixel);
            }
        }
    }

    /**
     * 验证指定 RGB 值是否为无效
     *
     * @param rgb RGB
     * @return 是否为无效
     */
    private static boolean isInvalidColorRgb(int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = (rgb & 0xff);
        return (r == 0 && g == 0 && b == 0) || (r == 0xff && g == 0xff && b == 0xff);
    }

    /**
     * 档案时间戳响应实体类
     */
    private static class ProfileTimestampResponse extends MojangBaseResponse {
        private UUID id;
        private String name;

        protected ProfileTimestampResponse(UUID id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
