package com.minecraft.moonlake.manager;

import com.minecraft.moonlake._temp.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class LocationManager extends MoonLakeManager {

    /**
     * 将字符串数据转换到位置对象
     *
     * @param data 字符串数据
     * @return 位置对象 异常则返回 null
     */
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
    public static String toDataBit(Location location, int bit, boolean angle) {

        if(location != null) {

            if(angle) {

                return
                        location.getWorld().getName() + "," +
                        Util.rounding(location.getX(), bit) + "," +
                        Util.rounding(location.getY(), bit) + "," +
                        Util.rounding(location.getZ(), bit) + "," +
                        Util.rounding(location.getYaw(), bit) + "," +
                        Util.rounding(location.getPitch(), bit);
            }
            else {

                return
                        location.getWorld().getName() + "," +
                        Util.rounding(location.getX(), bit) + "," +
                        Util.rounding(location.getY(), bit) + "," +
                        Util.rounding(location.getZ(), bit);
            }
        }
        return "none,0,0,0,0,0";
    }
}
