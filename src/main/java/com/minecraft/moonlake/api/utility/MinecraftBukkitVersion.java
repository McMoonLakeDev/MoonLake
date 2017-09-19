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

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>MinecraftBukkitVersion</h1>
 * Minecraft Bukkt 版本集类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class MinecraftBukkitVersion implements Comparable<MinecraftBukkitVersion> {

    private final static Pattern VERSION_PATTERN = Pattern.compile("(?i)^v(\\d+)_(\\d+)_r(\\d+)$");

    /**
     * Minecraft Bukkit v1_8_R1 版本 (MC: 1.8.0 - 1.8.2)
     */
    public final static MinecraftBukkitVersion V1_8_R1 = new MinecraftBukkitVersion("v1_8_R1");

    /**
     * Minecraft Bukkit v1_8_R2 版本 (MC: 1.8.3 - 1.8.7)
     */
    public final static MinecraftBukkitVersion V1_8_R2 = new MinecraftBukkitVersion("v1_8_R2");

    /**
     * Minecraft Bukkit v1_8_R3 版本 (MC: 1.8.8 - 1.8.9)
     */
    public final static MinecraftBukkitVersion V1_8_R3 = new MinecraftBukkitVersion("v1_8_R3");

    /**
     * Minecraft Bukkit v1_9_R1 版本 (MC: 1.9.0 - 1.9.3)
     */
    public final static MinecraftBukkitVersion V1_9_R1 = new MinecraftBukkitVersion("v1_9_R1");

    /**
     * Minecraft Bukkit v1_9_R2 版本 (MC: 1.9.4 Only)
     */
    public final static MinecraftBukkitVersion V1_9_R2 = new MinecraftBukkitVersion("v1_9_R2");

    /**
     * Minecraft Bukkit v1_10_R1 版本 (MC: 1.10.0 - 1.10.2)
     */
    public final static MinecraftBukkitVersion V1_10_R1 = new MinecraftBukkitVersion("v1_10_R1");

    /**
     * Minecraft Bukkit v1_11_R1 版本 (MC: 1.11.0 - 1.11.2)
     */
    public final static MinecraftBukkitVersion V1_11_R1 = new MinecraftBukkitVersion("v1_11_R1");

    /**
     * Minecraft Bukkit v1_12_R1 版本 (MC: 1.12.0 - 1.12.1) // 当前最新版本为 1.12.1
     */
    public final static MinecraftBukkitVersion V1_12_R1 = new MinecraftBukkitVersion("v1_12_R1");

    /**
     * Minecraft Bukkit 未知版本
     */
    public final static MinecraftBukkitVersion UNKNOWN = new MinecraftBukkitVersion(-1, -1, -1);

    private final static NavigableMap<MinecraftVersion, MinecraftBukkitVersion> lookup = createLookup();
    private static NavigableMap<MinecraftVersion, MinecraftBukkitVersion> createLookup() {

        LookupTreeMap map = new LookupTreeMap();
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 8, 0),
                new MinecraftVersion(1, 8, 1),
                new MinecraftVersion(1, 8, 2)
                // ---> net.minecraft.server.v1_8_R1
        }, V1_8_R1);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 8, 3),
                new MinecraftVersion(1, 8, 4),
                new MinecraftVersion(1, 8, 5),
                new MinecraftVersion(1, 8, 6),
                new MinecraftVersion(1, 8, 7),
                // ---> net.minecraft.server.v1_8_R2
        }, V1_8_R2);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 8, 8),
                new MinecraftVersion(1, 8, 9),
                // ---> net.minecraft.server.v1_8_R3
        }, V1_8_R3);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 9, 0),
                new MinecraftVersion(1, 9, 1),
                new MinecraftVersion(1, 9, 2),
                new MinecraftVersion(1, 9, 3),
                // ---> net.minecraft.server.v1_9_R1
        }, V1_9_R1);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 9, 4),
                // ---> net.minecraft.server.v1_9_R2
        }, V1_9_R2);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 10, 0),
                new MinecraftVersion(1, 10, 1),
                new MinecraftVersion(1, 10, 2),
                // ---> net.minecraft.server.v1_10_R1
        }, V1_10_R1);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 11, 0),
                new MinecraftVersion(1, 11, 1),
                new MinecraftVersion(1, 11, 2),
                // ---> net.minecraft.server.v1_11_R1
        }, V1_11_R1);
        map.put(new MinecraftVersion[] {
                new MinecraftVersion(1, 12, 0),
                new MinecraftVersion(1, 12, 1),
                new MinecraftVersion(1, 12, 1),
                // ---> net.minecraft.server.v1_12_R1
        }, V1_12_R1);
        return map;
    }

    /**
     * <h1>LookupTreeMap</h1>
     * Minecraft 版本对应 Bukkit 版本树形图
     *
     * @version 1.0
     * @author Month_Light
     */
    private final static class LookupTreeMap extends TreeMap<MinecraftVersion, MinecraftBukkitVersion> {

        private LookupTreeMap() {

            super();
        }

        public void put(MinecraftVersion[] keys, MinecraftBukkitVersion value) {
            for(MinecraftVersion key : keys)
                super.put(key, value);
        }
    }

    /**
     * 获取指定 Minecraft 版本在树形图对应的 Bukkit 版本
     *
     * @param version Minecraft 版本
     * @return 对应 Bukkit 版本 | {@link #UNKNOWN}
     */
    public static MinecraftBukkitVersion lookup(MinecraftVersion version) {

        Map.Entry<MinecraftVersion, MinecraftBukkitVersion> entry = lookup.floorEntry(version);
        return entry != null ? entry.getValue() : MinecraftBukkitVersion.UNKNOWN;
    }

    private static MinecraftBukkitVersion currentVersion;

    /**
     * 获取当前服务器的 Minecraft Bukkit 版本
     *
     * @return 服务器 Bukkit 版本 | null
     */
    public static MinecraftBukkitVersion getCurrentVersion() {

        if(currentVersion == null) {

            String[] packageSplit = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
            currentVersion = new MinecraftBukkitVersion(packageSplit[packageSplit.length - 1]);
        }
        return currentVersion;
    }

    /**
     * 获取当前服务器的 Minecraft Bukkit 版本是否在战斗更新版本或之后
     *
     * @return 是否在战斗更新版本或之后
     * @see MinecraftVersion#COMBAT_UPDATE
     */
    public static boolean isLaterCombat() {

        return getCurrentVersion().isLater(V1_8_R3);
    }

    private final int major;
    private final int minor;
    private final int release;

    /**
     * Minecraft Bukkt 版本集类构造函数
     *
     * @param versionOnly 版本号 (例如: v1_8_R1)
     * @throws IllegalArgumentException 如果版本号对象为 {@code null} 则抛出异常
     * @throws IllegalStateException 如果版本号不是正确的格式或 {@code parse} 时错误则抛出异常
     */
    private MinecraftBukkitVersion(String versionOnly) throws IllegalStateException {

        Validate.notNull(versionOnly, "The version only object is null.");

        Matcher matcher = VERSION_PATTERN.matcher(versionOnly);
        if(matcher.matches()) {
            try {
                this.major = Integer.parseInt(matcher.group(1));
                this.minor = Integer.parseInt(matcher.group(2));
                this.release = Integer.parseInt(matcher.group(3));
                return;
            } catch (Exception e) {
                throw new IllegalStateException("Cannot parse " + matcher, e);
            }
        }
        throw new IllegalStateException("Corrupt MC Bukkit Version: " + versionOnly);
    }

    /**
     * Minecraft Bukkt 版本集类构造函数
     *
     * @param major 主版本号
     * @param minor 次版本号
     * @param release 发行号
     */
    public MinecraftBukkitVersion(int major, int minor, int release) {

        this.major = major;
        this.minor = minor;
        this.release = release;
    }

    /**
     * 获取当前 Minecraft Bukkit 版本的主版本号
     *
     * @return 主版本号
     */
    public int getMajor() {

        return major;
    }

    /**
     * 获取当前 Minecraft Bukkit 版本的次版本号
     *
     * @return 次版本号
     */
    public int getMinor() {

        return minor;
    }

    /**
     * 获取当前 Minecraft Bukkit 版本的发行号
     *
     * @return 发行号
     */
    public int getRelease() {

        return release;
    }

    /**
     * 获取当前 Minecraft Bukkit 版本的字符串
     *
     * @return 版本字符串
     */
    public String getVersion() {

        return String.format("v%s_%s_R%s", major, minor, release);
    }

    /**
     * 获取当前 Minecraft Bukkit 版本是否在参数 {@code other} 版本之后
     *
     * <p>
     *     {@code if(other ==} {@link #V1_10_R1}{@code )}
     *     then {@link #getCurrentVersion()} {@code >} {@link #V1_10_R1} {@code = true}
     * </p>
     *
     * @param other 其他版本
     * @return 是否在参数版本之后
     * @see #compareTo(MinecraftBukkitVersion)
     */
    public boolean isLater(MinecraftBukkitVersion other) {

        return other != null && compareTo(other) > 0;
    }

    /**
     * <h1>获取当前 Minecraft Bukkit 版本是否在参数 {@code other} 版本或之后</h1>
     *
     * <p>
     *     {@code if(other ==} {@link #V1_10_R1}{@code )}
     *     then {@link #getCurrentVersion()} {@code ≥} {@link #V1_10_R1} {@code = true}
     * </p>
     *
     * @param other 其他版本
     * @return 是否在参数版本或之后
     * @see #compareTo(MinecraftBukkitVersion)
     */
    public boolean isOrLater(MinecraftBukkitVersion other) {

        return other != null && compareTo(other) >= 0;
    }

    /**
     * <h1>获取当前 Minecraft Bukkit 版本是否在参数 {@code min} 版本和参数 {@code max} 范围</h1>
     *
     * <p>举一个简单的例子: {@link #getCurrentVersion()}.isRange({@link #V1_8_R1}, {@link #V1_10_R1})</p>
     * <p>当你服务端为 {@link #V1_8_R3} 那么结果为 {@code true}, 如为 {@link #V1_8_R1}、{@link #V1_10_R1} 结果为 {@code false}</p>
     *
     * @param min 最小版本
     * @param max 最大版本
     * @return 是否在参数 {@code min} 版本和参数 {@code max} 范围
     * @see #compareTo(MinecraftBukkitVersion)
     */
    public boolean isRange(MinecraftBukkitVersion min, MinecraftBukkitVersion max) {

        return min != null && max != null && (compareTo(min) > 0 && compareTo(max) < 0);
    }

    /**
     * <h1>获取当前 Minecraft Bukkit 版本是否在参数 {@code min} 版本和参数 {@code max} 范围或</h1>
     *
     * <p>举一个简单的例子: {@link #getCurrentVersion()}.isOrRange({@link #V1_8_R1}, {@link #V1_10_R1})</p>
     * <p>当你服务端为 {@link #V1_8_R3} 那么结果为 {@code true}, 或为 {@link #V1_8_R1}、{@link #V1_10_R1} 同样结果</p>
     *
     * @param min 最小版本
     * @param max 最大版本
     * @return 是否在参数 {@code min} 版本和参数 {@code max} 范围或
     * @see #compareTo(MinecraftBukkitVersion)
     */
    public boolean isOrRange(MinecraftBukkitVersion min, MinecraftBukkitVersion max) {

        return min != null && max != null && (compareTo(min) >= 0 && compareTo(max) <= 0);
    }

    @Override
    public int compareTo(MinecraftBukkitVersion o) {

        if(o == null)
            return 1;
        return ComparisonChain.start()
                .compare(major, o.major)
                .compare(minor, o.minor)
                .compare(release, o.release)
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof MinecraftBukkitVersion) {
            MinecraftBukkitVersion other = (MinecraftBukkitVersion) obj;
            return major == other.major && minor == other.minor && release == other.release;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + release;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(MC Bukkit: %s)", getVersion());
    }
}
