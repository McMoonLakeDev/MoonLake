package com.minecraft.moonlake.api.mysql;

import com.minecraft.moonlake.api.mysql.query.MySQLQueryDelete;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryInsert;
import com.minecraft.moonlake.api.mysql.query.MySQLQuerySelect;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryUpdate;

/**
 * Created by MoonLake on 2016/7/28.
 */
public interface MySQLTable {

    /**
     * 获取此 MySQL 表的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取此表的 MySQL 连接对象
     *
     * @return 连接对象
     */
    MySQLConnection getConnection();

    /**
     * 获取此表的 Database 数据库对象
     *
     * @return 数据库对象
     */
    MySQLDatabase getDatabase();

    /**
     * 执行此 MySQL 连接对象的选择任务
     *
     * @return 选择任务
     */
    MySQLQuerySelect querySelect();

    /**
     * 执行此 MySQL 连接对象的选择任务
     *
     * @param selection 选择对象
     * @return 选择任务
     */
    MySQLQuerySelect querySelect(String selection);

    /**
     * 执行此 MySQL 连接对象的插入任务
     *
     * @return 插入任务
     */
    MySQLQueryInsert queryInsert();

    /**
     * 执行此 MySQL 连接对象的更新任务
     *
     * @return 更新任务
     */
    MySQLQueryUpdate queryUpdate();

    /**
     * 执行此 MySQL 连接对象的删除任务
     *
     * @return 删除任务
     */
    MySQLQueryDelete queryDelete();
}
