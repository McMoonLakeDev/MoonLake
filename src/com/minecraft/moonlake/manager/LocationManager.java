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
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * <h1>LocationManager</h1>
 * 位置管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class LocationManager extends MoonLakeManager {

    /**
     * 位置管理实现类构造函数
     */
    private LocationManager() {

    }

    /**
     * 将字符串数据转换到位置对象
     *
     * @param data 字符串数据
     * @return 位置对象 异常则返回 null
     */
    @Deprecated
    public static Location fromData(String data) {

        if(data != null && data.contains(",")) {

            String[] datas = data.replaceAll(" ", "").split(",");
            Location location = null;

            if(!datas[0].equalsIgnoreCase("none")) {

                if(datas.length == 4) {

                    World world = Bukkit.getServer().getWorld(datas[0]);
                    Double x = Double.parseDouble(datas[1]);
                    Double y = Double.parseDouble(datas[2]);
                    Double z = Double.parseDouble(datas[3]);

                    location = new Location(world, x, y, z);
                }
                else if(datas.length == 6) {

                    World world = Bukkit.getServer().getWorld(datas[0]);
                    Double x = Double.parseDouble(datas[1]);
                    Double y = Double.parseDouble(datas[2]);
                    Double z = Double.parseDouble(datas[3]);
                    Float yaw = Float.parseFloat(datas[4]);
                    Float pitch = Float.parseFloat(datas[5]);

                    location = new Location(world, x, y, z, yaw, pitch);
                }
            }
            return location;
        }
        return null;
    }

    /**
     * 将位置对象转换到字符串数据
     *
     * @param location 位置对象
     * @return 字符串数据 异常或没有则返回 "none,0,0,0,0,0"
     */
    @Deprecated
    public static String toData(Location location) {

        return toDataBit(location, 3);
    }

    /**
     * 将位置对象转换到字符串数据
     *
     * @param location 位置对象
     * @param bit 保留的位数
     * @return 字符串数据 异常或没有则返回 "none,0,0,0,0,0"
     */
    @Deprecated
    public static String toDataBit(Location location, int bit) {

        return toDataBit(location, bit, true);
    }

    /**
     * 将位置对象转换到字符串数据
     *
     * @param location 位置对象
     * @param bit 保留的位数
     * @param angle 是否保留角度
     * @return 字符串数据 异常或没有则返回 "none,0,0,0,0,0"
     */
    @Deprecated
    public static String toDataBit(Location location, int bit, boolean angle) {

        if(location != null) {

            if(angle) {

                return
                        location.getWorld().getName() + "," +
                                StringUtil.rounding(location.getX(), bit) + "," +
                                StringUtil.rounding(location.getY(), bit) + "," +
                                StringUtil.rounding(location.getZ(), bit) + "," +
                                StringUtil.rounding(location.getYaw(), bit) + "," +
                                StringUtil.rounding(location.getPitch(), bit);
            }
            else {

                return
                        location.getWorld().getName() + "," +
                                StringUtil.rounding(location.getX(), bit) + "," +
                                StringUtil.rounding(location.getY(), bit) + "," +
                                StringUtil.rounding(location.getZ(), bit);
            }
        }
        return "none,0,0,0,0,0";
    }
}
