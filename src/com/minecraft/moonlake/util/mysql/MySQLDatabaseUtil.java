package com.minecraft.moonlake.util.mysql;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;
import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLDatabase;
import com.minecraft.moonlake.api.mysql.MySQLTable;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLDatabaseUtil implements MySQLDatabase, MoonLakeCore {

    private MySQLConnection connection;

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    public MySQLDatabaseUtil(MySQLConnection connection) {

        this.connection = connection;
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
     * 获取此数据库指定名称的表
     *
     * @param name 表名称
     * @return 表对象 异常或没有则返回 null
     */
    @Override
    public MySQLTable getTable(String name) {

        for(MySQLTable table1 : getTables()) {

            if(table1.getName().equals(name)) {

                return table1;
            }
        }
        return null;
    }

    /**
     * 获取此数据库的所有表集合
     *
     * @return 表集合 异常或没有则返回空集合
     */
    @Override
    public Set<MySQLTable> getTables() {

        try {

            DatabaseMetaData dbmd = connection.getConnection().getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, null, new String[] { "TABLE" });
            Set<MySQLTable> tableSet = new HashSet<>();

            while(resultSet.next()) {

                tableSet.add(new MySQLTableUtil(connection, this, resultSet.getString("TABLE_NAME")));
            }
            return tableSet;
        }
        catch (Exception e) {

            getInstance().log("获取此数据库对象的表时异常: " + e.getMessage());
        }
        return new HashSet<>();
    }

    /**
     * 获取此数据库是否拥有表
     *
     * @return true 则拥有表 else 没有
     */
    @Override
    public boolean hasTable() {

        try {

            DatabaseMetaData dbmd = connection.getConnection().getMetaData();
            ResultSet resultSet = dbmd.getTables(connection.getDB(), null, null, new String[] { "TABLE" });

            boolean result = resultSet.next();
            resultSet.close();

            return result;
        }
        catch (Exception e) {

            getInstance().log("获取此数据库对象的表时异常: " + e.getMessage());
        }
        return false;
    }

    /**
     * 将此数据库创建指定名称的表
     *
     * @param name 表名
     * @return 表对象
     */
    @Override
    public MySQLTable createTable(String name) {

        connection.queryCreate().table(name).execute();

        return new MySQLTableUtil(connection, this, name);
    }
}
