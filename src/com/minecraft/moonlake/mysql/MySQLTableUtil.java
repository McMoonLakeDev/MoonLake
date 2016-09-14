package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;
import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLDatabase;
import com.minecraft.moonlake.api.mysql.MySQLTable;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryDelete;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryInsert;
import com.minecraft.moonlake.api.mysql.query.MySQLQuerySelect;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryUpdate;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLTableUtil implements MySQLTable, MoonLakeCore {

    private final MySQLConnection connection;
    private final MySQLDatabase database;
    private final String name;

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    public MySQLTableUtil(MySQLConnection connection, MySQLDatabase database, String name) {

        this.connection = connection;
        this.database = database;
        this.name = name;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     */
    @Override
    public MoonLake getInstance() {

        return MAIN;
    }

    /**
     * 获取此 MySQL 表的名称
     *
     * @return 名称
     */
    @Override
    public String getName() {

        return name;
    }

    /**
     * 获取此表的 MySQL 连接对象
     *
     * @return 连接对象
     */
    @Override
    public MySQLConnection getConnection() {

        return connection;
    }

    /**
     * 获取此表的 Database 数据库对象
     *
     * @return 数据库对象
     */
    @Override
    public MySQLDatabase getDatabase() {

        return database;
    }

    /**
     * 执行此 MySQL 连接对象的选择任务
     *
     * @return 选择任务
     */
    @Override
    public MySQLQuerySelect querySelect() {

        return new MySQLQuerySelect(connection, this);
    }

    /**
     * 执行此 MySQL 连接对象的选择任务
     *
     * @param selection 选择对象
     * @return 选择任务
     */
    @Override
    public MySQLQuerySelect querySelect(String selection) {

        return new MySQLQuerySelect(connection, this, selection);
    }

    /**
     * 执行此 MySQL 连接对象的插入任务
     *
     * @return 插入任务
     */
    @Override
    public MySQLQueryInsert queryInsert() {

        return new MySQLQueryInsert(connection, this);
    }

    /**
     * 执行此 MySQL 连接对象的更新任务
     *
     * @return 更新任务
     */
    @Override
    public MySQLQueryUpdate queryUpdate() {

        return new MySQLQueryUpdate(connection, this);
    }

    /**
     * 执行此 MySQL 连接对象的删除任务
     *
     * @return 删除任务
     */
    @Override
    public MySQLQueryDelete queryDelete() {

        return new MySQLQueryDelete(connection, this);
    }
}
