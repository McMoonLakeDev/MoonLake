package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;
import com.minecraft.moonlake.api.mysql.resultset.MySQLResultSet;
import com.minecraft.moonlake.exception.IllegalMySQLException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLResultSetUtil implements MySQLResultSet, MoonLakeCore {

    protected final ResultSet resultSet;

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    public MySQLResultSetUtil(ResultSet resultSet) {

        this.resultSet = resultSet;
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
     * 获取此结果集是否拥有下一个结果值
     *
     * @return true 则拥有值 else 没有
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public boolean next() throws IllegalMySQLException {

        boolean result = false;

        try {

            result = resultSet.next();
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的字符串值
     *
     * @param columnIndex 索引
     * @return 字符串值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public String getString(int columnIndex) throws IllegalMySQLException {

        String result = null;

        try {

            result = resultSet.getString(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的布尔值
     *
     * @param columnIndex 索引
     * @return 布尔值 异常或没有则返回 false
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public boolean getBoolean(int columnIndex) throws IllegalMySQLException {

        boolean result = false;

        try {

            result = resultSet.getBoolean(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的字节值
     *
     * @param columnIndex 索引
     * @return 字节值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public byte getByte(int columnIndex) throws IllegalMySQLException {

        byte result = -1;

        try {

            result = resultSet.getByte(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的短整数值
     *
     * @param columnIndex 索引
     * @return 短整数值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public short getShort(int columnIndex) throws IllegalMySQLException {

        short result = -1;

        try {

            result = resultSet.getShort(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的整数值
     *
     * @param columnIndex 索引
     * @return 整数值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public int getInt(int columnIndex) throws IllegalMySQLException {

        int result = -1;

        try {

            result = resultSet.getInt(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的长整数值
     *
     * @param columnIndex 索引
     * @return 长整数值 异常或没有则返回 -1L
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public long getLong(int columnIndex) throws IllegalMySQLException {

        long result = -1L;

        try {

            result = resultSet.getLong(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的单精度浮点数值
     *
     * @param columnIndex 索引
     * @return 单精度浮点数值 异常或没有则返回 -1f
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public float getFloat(int columnIndex) throws IllegalMySQLException {

        float result = -1f;

        try {

            result = resultSet.getFloat(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的双精度浮点数值
     *
     * @param columnIndex 索引
     * @return 双精度浮点数值 异常或没有则返回 -1d
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public double getDouble(int columnIndex) throws IllegalMySQLException {

        double result = -1d;

        try {

            result = resultSet.getDouble(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的大小数对象
     *
     * @param columnIndex 索引
     * @param scale       规模
     * @return 大小数对象 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws IllegalMySQLException {

        BigDecimal result = null;

        try {

            result = resultSet.getBigDecimal(columnIndex, scale);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的字节数组值
     *
     * @param columnIndex 索引
     * @return 字节数组值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public byte[] getBytes(int columnIndex) throws IllegalMySQLException {

        byte[] result = null;

        try {

            result = resultSet.getBytes(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的字符串值
     *
     * @param columnName 索引名
     * @return 字符串值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public String getString(String columnName) throws IllegalMySQLException {

        String result = null;

        try {

            result = resultSet.getString(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的布尔值
     *
     * @param columnName 索引名
     * @return 布尔值 异常或没有则返回 false
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public boolean getBoolean(String columnName) throws IllegalMySQLException {

        boolean result = false;

        try {

            result = resultSet.getBoolean(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的字节值
     *
     * @param columnName 索引名
     * @return 字节值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public byte getByte(String columnName) throws IllegalMySQLException {

        byte result = -1;

        try {

            result = resultSet.getByte(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的短整数值
     *
     * @param columnName 索引名
     * @return 短整数值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public short getShort(String columnName) throws IllegalMySQLException {

        short result = -1;

        try {

            result = resultSet.getShort(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的整数值
     *
     * @param columnName 索引名
     * @return 整数值 异常或没有则返回 -1
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public int getInt(String columnName) throws IllegalMySQLException {

        int result = -1;

        try {

            result = resultSet.getInt(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的长整数值
     *
     * @param columnName 索引名
     * @return 长整数值 异常或没有则返回 -1L
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public long getLong(String columnName) throws IllegalMySQLException {

        long result = -1L;

        try {

            result = resultSet.getLong(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的单精度浮点数值
     *
     * @param columnName 索引名
     * @return 单精度浮点数值 异常或没有则返回 -1f
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public float getFloat(String columnName) throws IllegalMySQLException {

        float result = -1f;

        try {

            result = resultSet.getLong(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的双精度浮点数值
     *
     * @param columnName 索引名
     * @return 双精度浮点数值 异常或没有则返回 -1d
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public double getDouble(String columnName) throws IllegalMySQLException {

        double result = -1d;

        try {

            result = resultSet.getDouble(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的大小数对象
     *
     * @param columnName 索引名
     * @param scale      规模
     * @return 大小数对象 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public BigDecimal getBigDecimal(String columnName, int scale) throws IllegalMySQLException {

        BigDecimal result = null;

        try {

            result = resultSet.getBigDecimal(columnName, scale);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的字节数组值
     *
     * @param columnName 索引名
     * @return 字节数组值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public byte[] getBytes(String columnName) throws IllegalMySQLException {

        byte[] result = null;

        try {

            result = resultSet.getBytes(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引的对象值
     *
     * @param columnIndex 索引
     * @return 对象值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public Object getObject(int columnIndex) {

        Object result = null;

        try {

            result = resultSet.getObject(columnIndex);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取此结果集对象指定索引名的对象值
     *
     * @param columnName 索引名
     * @return 对象值 异常或没有则返回 null
     * @throws IllegalMySQLException 数据库异常
     */
    @Override
    public Object getObject(String columnName) {

        Object result = null;

        try {

            result = resultSet.getObject(columnName);
        }
        catch (Exception e) {

            throw new IllegalMySQLException(e.getMessage());
        }
        return result;
    }


    /**
     * 关闭并释放 MySQL 结果集对象
     */
    @Override
    public void close() {

        if(resultSet != null) {

            try {

                resultSet.close();
            }
            catch (SQLException e) {

                getInstance().getMLogger().warn("关闭数据库的结果集对象时异常: " + e.getMessage());
            }
        }
    }
}
