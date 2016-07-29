package com.minecraft.moonlake.api.mysql;

import com.minecraft.moonlake.api.mysql.query.MySQLQueryCreate;

import java.sql.Connection;

/**
 * Created by MoonLake on 2016/7/28.
 */
public interface MySQLConnection {

    /**
     * 获取此 MySQL 连接对象的地址
     *
     * @return 地址
     */
    String getHost();

    /**
     * 获取此 MySQL 连接对象的地址端口
     *
     * @return 端口
     */
    int getPort();

    /**
     * 获取此 MySQL 连接对象的数据库名
     *
     * @return 数据库名
     */
    String getDB();

    /**
     * 获取此 MySQL 连接对象的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取此 MySQL 连接对象的用户密码
     *
     * @return 用户密码
     */
    String getPassword();

    /**
     * 获取此 MySQL 连接对象的数据库
     *
     * @return 数据库对象
     */
    MySQLDatabase getDatabase();

    /**
     * 执行此 MySQL 连接对象的创建任务
     *
     * @return 创建任务
     */
    MySQLQueryCreate queryCreate();

    /**
     * 将此 MySQL 连接对象进行打开并连接
     */
    void open();

    /**
     * 将此 MySQL 连接对象进行打开并连接
     *
     * @param checkExistsAndCreate 是否检测数据库存在和进行创建
     */
    void open(boolean checkExistsAndCreate);

    /**
     * 关闭并释放 MySQL 连接对象
     */
    void close();

    /**
     * 获取此 MySQL 连接对象是否已经打开
     *
     * @return true 则已经打开 else 没有
     */
    boolean isOpening();

    /**
     * 获取此 MySQL 连接对象的实现连接字段
     *
     * @return 连接对象
     */
    Connection getConnection();
}
