package com.minecraft.moonlake.api.mysql.query;

import com.minecraft.moonlake.api.mysql.MySQLConnection;

import java.sql.Statement;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLQueryCreate extends MySQLQueryAbstract {

    public MySQLQueryCreate(MySQLConnection connection) {

        super(connection, "");
    }

    /**
     * 执行 MySQL 的创建指定数据库
     *
     * @param name 数据库名
     * @return 实例
     */
    public MySQLQueryCreate database(String name) {

        sql = "create database if not exists " + name;

        return this;
    }

    /**
     * 执行 MySQL 的创建指定表
     *
     * @param name 表名
     * @return 实例
     */
    public MySQLQueryCreate table(String name) {

        sql = "create table if not exists " + name;

        return this;
    }

    /**
     * 执行 MySQL 的任务
     *
     * @return 空对象
     */
    @Override
    public Object execute() {

        try {

            super.execute();

            Statement st = connection.getConnection().createStatement();
            st.executeUpdate(sql);
            st.close();
        }
        catch (Exception e) {

            getInstance().log("执行数据库的声明对象的创建语句时异常: " + e.getMessage());
        }
        return null;
    }
}
