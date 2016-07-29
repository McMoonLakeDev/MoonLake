package com.minecraft.moonlake.api.mysql.query;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;
import com.minecraft.moonlake.api.mysql.MySQLConnection;

/**
 * Created by MoonLake on 2016/7/28.
 */
public abstract class MySQLQueryAbstract implements MySQLQuery, MoonLakeCore {

    protected final MySQLConnection connection;
    protected String sql;

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    public MySQLQueryAbstract(MySQLConnection connection, String sql) {

        this.sql = sql;
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
     * 执行 MySQL 的任务
     *
     * @return 对象
     */
    public Object execute() throws Exception {

        if(connection == null) {

            throw new Exception("The mysql connection is null.");
        }
        if(!connection.isOpening()) {

            connection.open();
        }
        return null;
    }

    /**
     * 执行 MySQL 的查询 (注意：没有实现)
     *
     * @param entityClass 实体类
     * @param <T> 实体类
     * @return 实体类对象实例
     * @throws Exception
     */
    @Deprecated
    public <T extends Object> T execute(Class<? extends T> entityClass) throws Exception {

        return null;
    }

    /**
     * 关闭并释放 MySQL 连接对象
     */
    protected void close() {

        if(connection != null) {

            connection.close();
        }
    }
}
