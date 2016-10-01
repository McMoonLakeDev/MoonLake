package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.mysql.exception.MySQLException;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/9/28.
 */
public interface MySQLConnection {

    /**
     * 获取 MySQL 连接的地址
     *
     * @return 地址
     */
    String getHost();

    /**
     * 获取 MySQL 连接的端口
     *
     * @return 端口
     */
    int getPort();

    /**
     * 获取 MySQL 连接的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取 MySQL 连接的密码
     *
     * @return 密码
     */
    String getPassword();

    /**
     * 获取 MySQL 连接的编码
     *
     * @return 编码
     */
    String getCharset();

    /**
     * 设置 MySQL 连接的编码
     *
     * @param charset 编码
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果编码是未知或非法则抛出异常
     */
    void setCharset(String charset);

    /**
     * 设置 MySQL 连接的编码
     *
     * @param charset 编码
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     */
    void setCharset(Charset charset);

    /**
     * 获取 MySQL 的连接数据库
     *
     * @return 数据库
     */
    String getDatabase();

    /**
     * 设置 MySQL 连接的数据库
     *
     * @param database 数据库
     * @throws IllegalArgumentException 如果数据库对象为 {@code null} 则抛出异常
     */
    void setDatabase(String database);

    /**
     * 获取 MySQL 连接资源是否使用完及时关闭
     *
     * @return 结果
     */
    boolean isPromptlyClose();

    /**
     * 设置 MySQL 连接资源是否使用完及时关闭
     *
     * @param flag 结果
     */
    void setPromptlyClose(boolean flag);

    /**
     * 设置 MySQL 连接的数据库
     *
     * @param database 数据库
     * @param updateConnection 是否更新 MySQL 连接对象
     * @throws IllegalArgumentException 如果数据库对象为 {@code null} 则抛出异常
     */
    void setDatabase(String database, boolean updateConnection);

    /**
     * 初始化 MySQL 连接对象
     *
     * @throws MySQLException 如果初始化时错误则抛出异常
     * @throws MySQLException 如果数据库对象为 {@code null} 或 {@code empty} 则抛出异常
     */
    void getConnection() throws MySQLException;

    /**
     * 处理 MySQL 语句声明
     *
     * @param sql 语句
     * @return 是否执行成功
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     */
    boolean dispatchStatement(String sql) throws MySQLException;

    /**
     * 处理 MySQL 语句制备声明
     *
     * @param sql 语句
     * @param params 参数
     * @return 是否执行成功
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果处理声明时错误则抛出异常
     */
    boolean dispatchPreparedStatement(String sql, Object... params) throws MySQLException;

    /**
     * 查找 MySQL 结果集
     *
     * @param sql 语句
     * @param params 参数
     * @return 结果集
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果查询结果时错误则抛出异常
     */
    Map<String, Object> findResult(String sql, Object... params) throws MySQLException;

    /**
     * 查找 MySQL 结果集
     *
     * @param sql 语句
     * @param params 参数
     * @return 结果集集合
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果查询结果时错误则抛出异常
     */
    Set<Map<String, Object>> findResults(String sql, Object... params) throws MySQLException;

    /**
     * 查询 MySQL 结果集
     *
     * @param clazz 实体类
     * @param sql 语句
     * @param params 参数
     * @param <T> 实体类
     * @return T 实例对象
     * @throws IllegalArgumentException 如果实体类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果查询结果时错误则抛出异常
     */
    <T> T findResult(Class<T> clazz, String sql, Object... params) throws MySQLException;

    /**
     * 查询 MySQL 结果集
     *
     * @param clazz 实体类
     * @param sql 语句
     * @param params 参数
     * @param <T> 实体类
     * @return T 实例对象集合
     * @throws IllegalArgumentException 如果实体类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果查询结果时错误则抛出异常
     */
    <T> Set<T> findResults(Class<T> clazz, String sql, Object... params) throws MySQLException;

    /**
     * 查询 MySQL 单结果集
     *
     * @param columnName 列名字
     * @param sql 语句
     * @param params 参数
     * @return 单结果
     * @throws IllegalArgumentException 如果列名字对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果语句对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果 MySQL 连接对象为 {@code null} 则抛出异常
     * @throws MySQLException 如果查询结果时错误则抛出异常
     */
    Object findSimpleResult(String columnName, String sql, Object... params) throws MySQLException;

    /**
     * 释放 MySQL 连接对象
     *
     * @throws MySQLException 如果释放时错误则抛出异常
     */
    void dispose() throws MySQLException;
}
