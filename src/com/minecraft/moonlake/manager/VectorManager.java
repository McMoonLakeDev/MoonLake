package com.minecraft.moonlake.manager;

import org.bukkit.util.Vector;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class VectorManager extends MoonLakeManager {

    /**
     * 将 Bukkit 的 Vector 对象转换为 XYZ 坐标
     *
     * @param obuVector Vector 对象
     * @param isDouble 是否 Double 坐标
     * @return 坐标数据 ("0,0,0")
     */
    public static String toXYZ(Vector obuVector, boolean isDouble) {

        String data = "";

        if(isDouble) {

            data = obuVector.getX() + "," + obuVector.getY() + "," + obuVector.getZ();
        }
        else {

            data = obuVector.getBlockX() + "," + obuVector.getBlockY() + "," + obuVector.getBlockZ();
        }
        return data;
    }

    /**
     * 将 XYZ 字符串坐标数据转换到 Bukkit 的 Vector 对象
     *
     * @param data 字符串数据
     * @return Vector 对象 序列化失败则返回 null
     */
    public static Vector fromXYZ(String data) {

        if(!data.contains(",")) return null;
        Vector vector = null;

        try {

            String[] datas = data.replaceAll(" ", "").split(",");
            vector = new Vector(

                    Double.parseDouble(datas[0]),
                    Double.parseDouble(datas[1]),
                    Double.parseDouble(datas[2])
            );
        }
        catch (Exception e) {

        }
        return vector;
    }

    /**
     * 获取随机矢量对象
     *
     * @return 随机矢量
     */
    public static Vector getRandomVector() {

        double x = RandomManager.getRandom().nextDouble() * 2d - 1d;
        double y = RandomManager.getRandom().nextDouble() * 2d - 1d;
        double z = RandomManager.getRandom().nextDouble() * 2d - 1d;

        return new Vector(x, y, z).normalize();
    }

    /**
     * 获取随机圆环矢量对象
     *
     * @return 随机圆环矢量
     */
    public static Vector getRandomCircleVector() {

        double rnd = RandomManager.getRandom().nextDouble() * 2d * Math.PI;
        double x = Math.cos(rnd);
        double z = Math.sin(rnd);

        return new Vector(x, 0d, z);
    }

    /**
     * 将一个矢量对象绕 Y 轴向指定角度旋转
     *
     * @param vector 矢量
     * @param angle 角度
     * @return 旋转角度后的矢量
     */
    public static Vector rotateAroundAxisY(Vector vector, double angle) {

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }
}
