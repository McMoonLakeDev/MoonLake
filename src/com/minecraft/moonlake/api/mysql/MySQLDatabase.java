package com.minecraft.moonlake.api.mysql;

import java.util.Set;

/**
 * Created by MoonLake on 2016/7/28.
 */
public interface MySQLDatabase {

    /**
     * 获取此数据库指定名称的表
     *
     * @param name 表名称
     * @return 表对象 异常或没有则返回 null
     */
    MySQLTable getTable(String name);

    /**
     * 获取此数据库的所有表集合
     *
     * @return 表集合 异常或没有则返回空集合
     */
    Set<MySQLTable> getTables();

    /**
     * 获取此数据库是否拥有表
     *
     * @return true 则拥有表 else 没有
     */
    boolean hasTable();

    /**
     * 将此数据库创建指定名称的表
     *
     * @param name 表名
     * @return 表对象
     */
    MySQLTable createTable(String name);
}
