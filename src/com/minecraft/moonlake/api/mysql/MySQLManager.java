package com.minecraft.moonlake.api.mysql;

import com.minecraft.moonlake.manager.MoonLakeManager;
import com.minecraft.moonlake.util.mysql.MySQLConnectionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * Created by MoonLake on 2016/7/28.
 */
public final class MySQLManager extends MoonLakeManager {

    private final static String MYSQL_JDBC_URL = "jdbc:mysql://{0}:{1}/{2}?characterEncoding={3}";

    /**
     * 获取指定连接信息的数据库连接对象
     *
     * @param host 地址
     * @param port 端口
     * @param db 数据库
     * @param username 用户名
     * @param password 用户密码
     * @return 数据库连接对象
     * @throws Exception
     */
    public static MySQLConnection getConnection(String host, int port, String db, String username, String password) {

        return getConnection(host, port, db, username, password, "utf-8");
    }

    /**
     * 获取指定连接信息的数据库连接对象
     *
     * @param host 地址
     * @param port 端口
     * @param db 数据库
     * @param username 用户名
     * @param password 用户密码
     * @param charset 编码
     * @return 数据库连接对象
     * @throws Exception
     */
    public static MySQLConnection getConnection(String host, int port, String db, String username, String password, String charset) {

        return new MySQLConnectionUtil(host, port, db, username, password, charset);
    }

    /**
     * 获取指定连接信息的数据库连接对象
     *
     * @param host 地址
     * @param port 端口
     * @param db 数据库
     * @param username 用户名
     * @param password 用户密码
     * @param charset 编码
     * @return 数据库连接对象
     * @throws Exception
     */
    public static Connection getConnectionFromJDBC(String host, String port, String db, String username, String password, String charset) throws Exception {

        Connection con = null;
        String target = MessageFormat.format(MYSQL_JDBC_URL, host, port, db, charset);

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(target, username, password);
        }
        catch (SQLException e) {

            throw new Exception("连接错误,数据库信息异常: " + e.getMessage());
        }
        return con;
    }
}
