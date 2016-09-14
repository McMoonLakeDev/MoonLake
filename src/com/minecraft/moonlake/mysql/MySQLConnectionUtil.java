package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;
import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLDatabase;
import com.minecraft.moonlake.api.mysql.MySQLManager;
import com.minecraft.moonlake.api.mysql.query.MySQLQuery;
import com.minecraft.moonlake.api.mysql.query.MySQLQueryCreate;
import com.minecraft.moonlake.reflect.Reflect;

import java.sql.Connection;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLConnectionUtil implements MySQLConnection, MoonLakeCore {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final String charset;
    private Connection connection;
    private boolean isOpen;

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    public MySQLConnectionUtil(String host, int port, String database, String username, String password) {

        this(host, port, database, username, password, "utf-8");
    }

    public MySQLConnectionUtil(String host, int port, String database, String username, String password, String charset) {

        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.charset = charset;
        this.isOpen = false;
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
     * 获取此 MySQL 连接对象的地址
     *
     * @return 地址
     */
    @Override
    public String getHost() {

        return host;
    }

    /**
     * 获取此 MySQL 连接对象的地址端口
     *
     * @return 端口
     */
    @Override
    public int getPort() {

        return port;
    }

    /**
     * 获取此 MySQL 连接对象的数据库名
     *
     * @return 数据库名
     */
    @Override
    public String getDB() {

        return database;
    }

    /**
     * 获取此 MySQL 连接对象的用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {

        return username;
    }

    /**
     * 获取此 MySQL 连接对象的用户密码
     *
     * @return 用户密码
     */
    @Override
    public String getPassword() {

        return password;
    }

    /**
     * 获取此 MySQL 连接对象的数据库
     *
     * @return 数据库对象
     */
    @Override
    public MySQLDatabase getDatabase() {

        if(!isOpening()) {

            open();
        }
        return new MySQLDatabaseUtil(this);
    }

    /**
     * 执行此 MySQL 连接对象的任务
     *
     * @param query 任务
     * @return 任务对象
     */
    protected final <T extends MySQLQuery> T query(Class<T> query) {

        if(!isOpening()) {

            open();
        }
        T t = null;

        try {

            t = (T) Reflect.getConstructor(query, MySQLConnection.class).newInstance(this);
        }
        catch (Exception e) {

            getInstance().getMLogger().warn("实例化数据库连接对象的任务类时异常: " + e.getMessage());
        }
        return t;
    }

    /**
     * 执行此 MySQL 连接对象的创建任务
     *
     * @return 创建任务
     */
    @Override
    public MySQLQueryCreate queryCreate() {

        return query(MySQLQueryCreate.class);
    }

    /**
     * 将此 MySQL 连接对象进行打开并连接
     */
    @Override
    public void open() {

        open(false);
    }

    /**
     * 将此 MySQL 连接对象进行打开并连接
     *
     * @param checkExistsAndCreate 是否检测数据库存在和进行创建
     */
    @Override
    public void open(boolean checkExistsAndCreate) {

        try {

            if(checkExistsAndCreate) {

                MySQLConnection mysql = MySQLManager.getConnection(host, port, "mysql", username, password, charset);
                mysql.queryCreate().database(database).execute();
                mysql.close();
            }
            connection = MySQLManager.getConnectionFromJDBC(host, String.valueOf(port), database, username, password, charset);
        }
        catch (Exception e) {

            getInstance().getMLogger().warn("获取数据库连接对象时异常: " + e.getMessage());
        }
        finally {

            isOpen = true;
        }
    }

    /**
     * 关闭并释放 MySQL 连接对象
     */
    @Override
    public void close() {

        if(connection != null) {

            try {

                connection.close();
            }
            catch (Exception e) {

                getInstance().getMLogger().warn("关闭数据库连接对象时异常: " + e.getMessage());
            }
            finally {

                isOpen = false;
            }
        }
    }

    /**
     * 获取此 MySQL 连接对象是否已经打开
     *
     * @return true 则已经打开 else 没有
     */
    @Override
    public boolean isOpening() {

        return isOpen;
    }

    /**
     * 获取此 MySQL 连接对象的实现连接字段
     *
     * @return 连接对象
     */
    @Override
    public Connection getConnection() {

        return connection;
    }
}
