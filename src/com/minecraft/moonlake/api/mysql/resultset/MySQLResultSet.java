package com.minecraft.moonlake.api.mysql.resultset;

import com.minecraft.moonlake.exception.IllegalMySQLException;

import java.math.BigDecimal;

/**
 * Created by MoonLake on 2016/7/28.
 */
public interface MySQLResultSet {

    /**
     * 获取此结果集是否拥有下一个结果值
     *
     * @throws IllegalMySQLException 数据库异常
     * @return true 则拥有值 else 没有
     */
    boolean next() throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的字符串值
     * 
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 字符串值 异常或没有则返回 null
     */
    String getString(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的布尔值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 布尔值 异常或没有则返回 false
     */
    boolean getBoolean(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的字节值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 字节值 异常或没有则返回 -1
     */
    byte getByte(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的短整数值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 短整数值 异常或没有则返回 -1
     */
    short getShort(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的整数值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 整数值 异常或没有则返回 -1
     */
    int getInt(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的长整数值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 长整数值 异常或没有则返回 -1L
     */
    long getLong(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的单精度浮点数值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 单精度浮点数值 异常或没有则返回 -1f
     */
    float getFloat(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的双精度浮点数值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 双精度浮点数值 异常或没有则返回 -1d
     */
    double getDouble(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的大小数对象
     *
     * @param columnIndex 索引
     * @param scale 规模
     * @throws IllegalMySQLException 数据库异常
     * @return 大小数对象 异常或没有则返回 null
     */
    @Deprecated
    BigDecimal getBigDecimal(int columnIndex, int scale) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的字节数组值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 字节数组值 异常或没有则返回 null
     */
    byte[] getBytes(int columnIndex) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的字符串值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 字符串值 异常或没有则返回 null
     */
    String getString(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的布尔值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 布尔值 异常或没有则返回 false
     */
    boolean getBoolean(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的字节值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 字节值 异常或没有则返回 -1
     */
    byte getByte(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的短整数值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 短整数值 异常或没有则返回 -1
     */
    short getShort(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的整数值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 整数值 异常或没有则返回 -1
     */
    int getInt(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的长整数值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 长整数值 异常或没有则返回 -1L
     */
    long getLong(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的单精度浮点数值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 单精度浮点数值 异常或没有则返回 -1f
     */
    float getFloat(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的双精度浮点数值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 双精度浮点数值 异常或没有则返回 -1d
     */
    double getDouble(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的大小数对象
     *
     * @param columnName 索引名
     * @param scale 规模
     * @throws IllegalMySQLException 数据库异常
     * @return 大小数对象 异常或没有则返回 null
     */
    @Deprecated
    BigDecimal getBigDecimal(String columnName, int scale) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引名的字节数组值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 字节数组值 异常或没有则返回 null
     */
    byte[] getBytes(String columnName) throws IllegalMySQLException;

    /**
     * 获取此结果集对象指定索引的对象值
     *
     * @param columnIndex 索引
     * @throws IllegalMySQLException 数据库异常
     * @return 对象值 异常或没有则返回 null
     */
    Object getObject(int columnIndex);

    /**
     * 获取此结果集对象指定索引名的对象值
     *
     * @param columnName 索引名
     * @throws IllegalMySQLException 数据库异常
     * @return 对象值 异常或没有则返回 null
     */
    Object getObject(String columnName);
    
    /**
     * 关闭并释放 MySQL 结果集对象
     */
    void close()  throws IllegalMySQLException;
}
