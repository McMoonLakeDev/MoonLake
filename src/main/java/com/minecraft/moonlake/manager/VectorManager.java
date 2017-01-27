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

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <h1>VectorManager</h1>
 * 矢量管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class VectorManager extends MoonLakeManager {

    /**
     * 矢量管理实现类构造函数
     */
    private VectorManager() {

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
     * 将一个矢量对象绕 X 轴向指定角度旋转
     *
     * @param vector 矢量
     * @param angle 角度
     * @return 旋转角度后的矢量
     */
    public static Vector rotateAroundAxisX(Vector vector, double angle) {

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = vector.getY() * cos - vector.getZ() * sin;
        double z = vector.getY() * sin + vector.getZ() * cos;
        return vector.setY(y).setZ(z);
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

    /**
     * 将一个矢量对象绕 Z 轴向指定角度旋转
     *
     * @param vector 矢量
     * @param angle 角度
     * @return 旋转角度后的矢量
     */
    public static Vector rotateAroundAxisZ(Vector vector, double angle) {

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos - vector.getY() * sin;
        double y = vector.getX() * sin + vector.getY() * cos;
        return vector.setX(x).setY(y);
    }

    /**
     * 将一个矢量对象绕 XYZ 轴向指定角度旋转
     *
     * @param vector 矢量
     * @param angleX X 角度
     * @param angleY Y 角度
     * @param angleZ Z 角度
     * @return 旋转角度后的矢量
     */
    public static Vector rotateVector(Vector vector, double angleX, double angleY, double angleZ) {

        rotateAroundAxisX(vector, angleX);
        rotateAroundAxisY(vector, angleY);
        rotateAroundAxisZ(vector, angleZ);
        return vector;
    }

    /**
     * 将一个矢量对象绕位置的角旋转
     *
     * @param vector 矢量
     * @param location 位置
     * @return 旋转角度后的矢量
     */
    public static Vector rotateVector(Vector vector, Location location) {

        return rotateVector(vector, location.getYaw(), location.getPitch());
    }

    /**
     * 将一个矢量对象绕 Yaw 和 Ptich 角向指定角度旋转
     *
     * @param vector 矢量
     * @param yawDegrees 偏航角度
     * @param pitchDegrees 俯仰角度
     * @return 旋转角度后的矢量
     */
    public static Vector rotateVector(Vector vector, float yawDegrees, float pitchDegrees) {

        double yaw = Math.toRadians(-1f * (yawDegrees + 90f));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX = vector.getX();
        double initialY = vector.getY();
        double initialZ = vector.getZ();

        double x = initialX * cosPitch - initialY * sinPitch;
        double y = initialX * sinPitch + initialY * cosPitch;

        initialX = x;

        double z = initialZ * cosYaw - initialX * sinYaw;

        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
    }

    /**
     * 将一个矢量对象的角度转换到 X 轴
     *
     * @param vector 矢量
     * @return X 轴
     */
    public static double angleToAxisX(Vector vector) {

        return Math.atan2(vector.getX(), vector.getY());
    }
}
