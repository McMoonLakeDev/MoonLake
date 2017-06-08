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


package com.minecraft.moonlake.api.utility;

import com.google.common.collect.ComparisonChain;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>MinecraftVersion</h1>
 * Minecraft 版本集类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MinecraftVersion implements Comparable<MinecraftVersion> {

    /**
     * Minecraft 1.12 : 多彩世界更新
     */
    public final static MinecraftVersion WORLD_COLOR_UPDATE = new MinecraftVersion("1.12");

    /**
     * Minecraft 1.12 : 多彩世界更新
     */
    public final static MinecraftVersion V1_12 = WORLD_COLOR_UPDATE;

    /**
     * Minecraft 1.11 : 探险更新
     */
    public final static MinecraftVersion EXPLORATION_UPDATE = new MinecraftVersion("1.11");

    /**
     * Minecraft 1.11 : 探险更新
     */
    public final static MinecraftVersion V1_11 = EXPLORATION_UPDATE;

    /**
     * Minecraft 1.10 : 霜炙更新
     */
    public final static MinecraftVersion FROSTBURN_UPDATE = new MinecraftVersion("1.10");

    /**
     * Minecraft 1.10 : 霜炙更新
     */
    public final static MinecraftVersion V1_10 = FROSTBURN_UPDATE;

    /**
     * Minecraft 1.9 : 战斗更新
     */
    public final static MinecraftVersion COMBAT_UPDATE = new MinecraftVersion("1.9");

    /**
     * Minecraft 1.9 : 战斗更新
     */
    public final static MinecraftVersion V1_9 = COMBAT_UPDATE;

    /**
     * Minecraft 1.8 : 缤纷更新
     */
    public final static MinecraftVersion BOUNTIFUL_UPDATE = new MinecraftVersion("1.8");

    /**
     * Minecraft 1.8 : 缤纷更新
     */
    public final static MinecraftVersion V1_8 = BOUNTIFUL_UPDATE;

    /**
     * Minecraft 1.7.2 : 改变世界的更新
     *
     * @deprecated 该插件不支持此版本
     */
    @Deprecated
    public final static MinecraftVersion WORLD_UPDATE = new MinecraftVersion("1.7.2");

    /**
     * Minecraft 1.6.1 : 马匹更新
     *
     * @deprecated 该插件不支持此版本
     */
    @Deprecated
    public final static MinecraftVersion HORSE_UPDATE = new MinecraftVersion("1.6.1");

    /**
     * Minecraft 1.5 : 红石更新
     *
     * @deprecated 该插件不支持此版本
     */
    @Deprecated
    public final static MinecraftVersion REDSTONE_UPDATE = new MinecraftVersion("1.5");

    /**
     * Minecraft 1.4.2 : 骇人更新
     *
     * @deprecated 该插件不支持此版本
     */
    @Deprecated
    public final static MinecraftVersion SCARY_UPDATE = new MinecraftVersion("1.4.2");

    private final static Pattern VERSION_PATTERN = Pattern.compile(".*\\(.*MC.\\s*([a-zA-Z0-9\\-\\.]+)\\s*\\)");
    private final int major;
    private final int minor;
    private final int build;
    private final Integer pre;

    private static MinecraftVersion currentVersion;

    /**
     * 获取当前服务器的 Minecraft 版本
     *
     * @return 服务器 MC 版本 | null
     */
    public static MinecraftVersion getCurrentVersion() {

        if(currentVersion == null) {

            Matcher matcher = VERSION_PATTERN.matcher(Bukkit.getServer().getVersion());
            currentVersion = (matcher.matches() && matcher.group(1) != null) ? new MinecraftVersion(matcher.group(1)) : null;
        }
        return currentVersion;
    }

    /**
     * Minecraft 版本集类构造函数
     *
     * @param versionOnly 版本号 (例如: 1.11.2)
     * @throws IllegalArgumentException 如果版本号对象为 {@code null} 则抛出异常
     * @throws IllegalStateException 如果版本号不是正确的格式或 {@code parse} 时错误则抛出异常
     */
    public MinecraftVersion(String versionOnly) throws IllegalStateException {

        String[] section = Validate.checkNotNull(versionOnly).split("-");
        int[] numbers = new int[3];
        Integer pre0 = null;

        try {
            int index = versionOnly.lastIndexOf("-pre");
            String[] elements = index == -1 ? versionOnly.split("\\.") : versionOnly.substring(0, index).split("\\.");
            if(elements.length < 1)
                throw new IllegalStateException("Corrupt MC Version: " + versionOnly);
            for(int i = 0; i < Math.min(numbers.length, elements.length); i++)
                numbers[i] = Integer.parseInt(elements[i].trim());
            if(index != -1)
                pre0 = Integer.parseInt(versionOnly.substring(index + 4)); // +4 = -pre 四个字符的长度

        } catch (Exception e) {
            if(e instanceof NumberFormatException)
                throw new IllegalStateException("Cannot parse " + section[0], e);
            else
                throw e;
        }
        this.major = numbers[0];
        this.minor = numbers[1];
        this.build = numbers[2];
        this.pre = pre0;
    }

    /**
     * Minecraft 版本集类构造函数
     *
     * @param major 主版本号
     * @param minor 次版本号
     * @param build 内部版本
     */
    public MinecraftVersion(int major, int minor, int build) {

        this.major = major;
        this.minor = minor;
        this.build = build;
        this.pre = null;
    }

    /**
     * Minecraft 版本集类构造函数
     *
     * @param major 主版本号
     * @param minor 次版本号
     * @param build 内部版本
     * @param pre 预发布版本号
     */
    public MinecraftVersion(int major, int minor, int build, @Nullable Integer pre) {

        this.major = major;
        this.minor = minor;
        this.build = build;
        this.pre = pre;
    }

    /**
     * 获取当前 Minecraft 版本的主版本号
     *
     * @return 主版本号
     */
    public int getMajor() {

        return major;
    }

    /**
     * 获取当前 Minecraft 版本的次版本号
     *
     * @return 次版本号
     */
    public int getMinor() {

        return minor;
    }

    /**
     * 获取当前 Minecraft 版本的内部版本
     *
     * @return 内部版本
     */
    public int getBuild() {

        return build;
    }

    /**
     * 获取当前 Minecraft 版本的预发布版本
     *
     * @return 预发布版本
     */
    @Nullable
    public Integer getPre() {

        return pre;
    }

    /**
     * 获取当前 Minecraft 版本是否是预发布版本
     *
     * @return 是否为预发布版本
     */
    public boolean isPre() {

        return pre != null;
    }

    /**
     * 获取当前 Minecraft 版本的字符串
     *
     * @return 版本字符串
     */
    public String getVersion() {

        return String.format("%s.%s.%s", major, minor, build) + (isPre() ? "-pre" + pre : "");
    }

    /**
     * 获取当前 Minecraft 版本对应的 Bukkit 版本
     *
     * @return 对应 Bukkit 版本 | {@link MinecraftBukkitVersion#UNKNOWN}
     */
    public MinecraftBukkitVersion getBukkitVersion() {

        return MinecraftBukkitVersion.lookup(this);
    }

    /**
     * <h1>获取当前 Minecraft 版本是否在参数 {@code other} 版本之后</h1>
     *
     * <p>
     *     {@code if(other ==} {@link #V1_10}{@code )}
     *     then {@link #getCurrentVersion()} {@code >} {@link #V1_10} {@code = true}
     * </p>
     *
     * @param other 其他版本
     * @return 是否在参数版本之后
     * @see #compareTo(MinecraftVersion)
     */
    public boolean isLater(MinecraftVersion other) {

        return other != null && compareTo(other) > 0;
    }

    /**
     * <h1>获取当前 Minecraft 版本是否在参数 {@code other} 版本或之后</h1>
     *
     * <p>
     *     {@code if(other ==} {@link #V1_10}{@code )}
     *     then {@link #getCurrentVersion()} {@code ≥} {@link #V1_10} {@code = true}
     * </p>
     *
     * @param other 其他版本
     * @return 是否在参数版本或之后
     * @see #compareTo(MinecraftVersion)
     */
    public boolean isOrLater(MinecraftVersion other) {

        return other != null && compareTo(other) >= 0;
    }

    /**
     * <h1>判断当前版本是否符合指定 Minecraft 版本的主和次版本号</h1>
     *
     * <p>
     *     {@code if(other ==} {@link #V1_10}{@code )}
     *     then {@link #getCurrentVersion()} {@code == 1.10.x} {@code = true}
     * </p>
     *
     * @param other 其他版本
     * @return 是否符合主和次版本号
     */
    public boolean equalsMinor(MinecraftVersion other) {

        return other != null && major == other.major && minor == other.minor;
    }

    @Override
    public int compareTo(MinecraftVersion o) {

        if(o == null)
            return 1;
        return ComparisonChain.start()
                .compare(major, o.major)
                .compare(minor, o.minor)
                .compare(build, o.build)
                .compare(isPre() ? pre : -1, o.isPre() ? o.pre : -1)
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof MinecraftVersion) {
            MinecraftVersion other = (MinecraftVersion) obj;
            return major == other.major && minor == other.minor && build == other.build && isPre() == other.isPre();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + build;
        result = 31 * result + (pre != null ? pre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("(MC: %s)", getVersion());
    }
}
