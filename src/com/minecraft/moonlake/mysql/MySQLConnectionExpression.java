/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.mysql.exception.MySQLException;
import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;
import com.minecraft.moonlake.validate.Validate;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

/**
 * <h1>MySQLConnectionExpression</h1>
 * 数据库连接接口实现类
 *
 * @version 1.1
 * @author Month_Light
 */
class MySQLConnectionExpression implements MySQLConnection {

    /**
     * MySQL Connection Default Charset: UTF-8
     */
    private final static Charset MYSQL_DEFAULT_CHARSET = Charset.forName("utf-8");

    /**
     * MySQL Connection URL: jdbc:mysql://host:port/database?characterEncoding=charset
     */
    private final static String MYSQL_URL_CHARSET = "jdbc:mysql://host:port/database?characterEncoding=charset";

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private String charset;
    private String database;

    private Connection connection;
    private boolean promptlyClose;

    /**
     * 数据库连接接口实现类构造函数
     *
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression() throws MySQLInitializeException {

        this(null, null);
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(@Nullable String username, @Nullable String password) throws MySQLInitializeException {

        this("localhost", 3306, username, password, MYSQL_DEFAULT_CHARSET);
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(@Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        this("localhost", 3306, username, password, charset);
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(@Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        this("localhost", 3306, username, password, charset);
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(String host, int port, @Nullable String username, @Nullable String password) throws MySQLInitializeException {

        this(host, port, username, password, MYSQL_DEFAULT_CHARSET);
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(String host, int port, @Nullable String username, @Nullable String password, String charset) throws MySQLInitializeException {

        this(host, port, username, password, Charset.forName(charset));
    }

    /**
     * 数据库连接接口实现类构造函数
     *
     * @param host 数据库地址
     * @param port 数据库端口
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param charset 数据库编码
     * @throws IllegalArgumentException 如果数据库地址对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果数据库端口值小于 0 或大于 65535 则抛出异常
     * @throws IllegalArgumentException 如果数据库编码对象为 {@code null} 则抛出异常
     * @throws MySQLInitializeException 如果数据库初始化错误则抛出异常
     */
    public MySQLConnectionExpression(String host, int port, @Nullable String username, @Nullable String password, Charset charset) throws MySQLInitializeException {

        Validate.notNull(host, "The mysql host object is null.");
        Validate.isTrue(port >= 0 && port <= 65535, "The mysql port is illegal value. (must: 0 - 65535)");
        Validate.notNull(charset, "The mysql charset object is null.");

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.charset = charset.name();
        this.promptlyClose = true;

        try {

            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {

            throw new MySQLInitializeException("The initialize mysql jdbc driver exception.", e);
        }
    }

    @Override
    public String getHost() {

        return host;
    }

    @Override
    public int getPort() {

        return port;
    }

    @Override
    public String getUsername() {

        return username;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getCharset() {

        return charset;
    }

    @Override
    public void setCharset(String charset) {

        Validate.notNull(charset, "The mysql charset object is null.");

        Charset target = null;

        try {

            target = Charset.forName(charset);
        }
        catch (Exception e) {

            throw new MySQLException("The mysql charset is illegal object: " + charset, e);
        }
        setCharset(target);
    }

    @Override
    public void setCharset(Charset charset) {

        Validate.notNull(charset, "The mysql charset object is null.");

        this.charset = charset.name();
    }

    @Override
    public String getDatabase() {

        return database;
    }

    @Override
    public void setDatabase(String database) {

        setDatabase(database, false);
    }

    @Override
    public void setDatabase(String database, boolean updateConnection) {

        Validate.notNull(database, "The mysql database object is null.");

        this.database = database;

        if(updateConnection) {

            getConnection();
        }
    }

    @Override
    public boolean isPromptlyClose() {

        return promptlyClose;
    }

    @Override
    public void setPromptlyClose(boolean flag) {

        this.promptlyClose = flag;
    }

    @Override
    public synchronized void getConnection() throws MySQLException {

        if(database == null || database.isEmpty()) {

            throw new MySQLException("The mysql database object is null or empty. (value: " + database + ")");
        }
        try {

            connection = DriverManager.getConnection(formatMySQLUrl(), getUsername(), getPassword());
        }
        catch (Exception e) {

            throw new MySQLException("The mysql get connection exception.", e);
        }
    }

    @Override
    public synchronized boolean createDatabase() throws MySQLException {

        return createDatabase(database);
    }

    @Override
    public synchronized boolean createDatabase(String database) throws MySQLException {

        return createDatabase(database, false);
    }

    @Override
    public synchronized boolean createDatabase(String database, boolean isNotExists) throws MySQLException {

        Validate.notNull(database, "The mysql database object is null.");

        String sql = "create database" + (isNotExists ? " if not exists " : " ") + database;

        return dispatchStatement(sql);
    }

    @Override
    public synchronized boolean dispatchStatement(String sql) throws MySQLException {

        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        Statement statement = null;

        try {

            statement = connection.createStatement();

            return statement.executeUpdate(sql) > 0;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql dispatch statement sql exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(statement);
            }
        }
    }

    @Override
    public synchronized boolean dispatchPreparedStatement(String sql, Object... params) throws MySQLException {

        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            if(params != null && params.length > 0) {

                int index = 1;

                for(final Object param : params) {

                    preparedStatement.setObject(index++, param);
                }
            }
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql dispatch prepared statement sql exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(preparedStatement);
            }
        }
    }

    @Override
    public synchronized boolean findTable(String tableName) throws MySQLException {

        Validate.notNull(tableName, "The mysql table name object is null.");

        checkConnection();

        ResultSet resultSet = null;
        Statement statement = null;

        try {

            statement = connection.createStatement();

            resultSet = statement.executeQuery("show tables like '" + tableName + "';");

            return resultSet != null && resultSet.next();
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find table exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(resultSet);
                close(statement);
            }
        }
    }

    @Override
    public synchronized boolean findTables(String[] tableNames) throws MySQLException {

        Validate.notNull(tableNames, "The mysql table names object is null.");

        checkConnection();

        ResultSet resultSet = null;
        Statement statement = null;

        try {

            statement = connection.createStatement();

            resultSet = statement.executeQuery("show tables;");

            List<String> existTables = new ArrayList<>();

            while (resultSet.next()) {

                String existTable = resultSet.getString("Tables_in_" + getDatabase());

                if(existTable != null) {

                    existTables.add(existTable);
                }
            }
            boolean result = false;

            if(existTables.size() > 0) {

                for(String targetTable : tableNames) {

                    result = existTables.contains(targetTable);

                    if(!result) {

                        break;
                    }
                }
            }
            return result;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find table exception.", e);
        }
        finally {

            close(resultSet);
            close(statement);
        }
    }

    @Override
    public synchronized Map<String, Object> findResult(String sql, Object... params) throws MySQLException {

        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            if(params != null && params.length > 0) {

                int index = 1;

                for(final Object param : params) {

                    preparedStatement.setObject(index++, param);
                }
            }
            resultSet = preparedStatement.executeQuery();

            if(resultSet == null) {

                return null;
            }
            Map<String, Object> resultSetMap = new HashMap<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int resultSetMetaDataColumnLength = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {

                for(int i = 0; i < resultSetMetaDataColumnLength; i++) {

                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);

                    if(columnValue == null) {

                        columnValue = "";
                    }
                    resultSetMap.put(columnName, columnValue);
                }
            }
            return resultSetMap;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find result exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(resultSet);
                close(preparedStatement);
            }
        }
    }

    @Override
    public synchronized Set<Map<String, Object>> findResults(String sql, Object... params) throws MySQLException {

        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            if(params != null && params.length > 0) {

                int index = 1;

                for(final Object param : params) {

                    preparedStatement.setObject(index++, param);
                }
            }
            resultSet = preparedStatement.executeQuery();

            if(resultSet == null) {

                return null;
            }
            Set<Map<String, Object>> resultSetSet = new HashSet<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int resultSetMetaDataColumnLength = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {

                Map<String, Object> resultSetMap = new HashMap<>();

                for(int i = 0; i < resultSetMetaDataColumnLength; i++) {

                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);

                    if(columnValue == null) {

                        columnValue = "";
                    }
                    resultSetMap.put(columnName, columnValue);
                }
                resultSetSet.add(resultSetMap);
            }
            return resultSetSet;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find result exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(resultSet);
                close(preparedStatement);
            }
        }
    }

    @Override
    public synchronized <T> T findResult(Class<T> clazz, String sql, Object... params) throws MySQLException {

        Validate.notNull(clazz, "The entity class object is null.");
        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            if(params != null && params.length > 0) {

                int index = 1;

                for(final Object param : params) {

                    preparedStatement.setObject(index++, param);
                }
            }
            resultSet = preparedStatement.executeQuery();

            if(resultSet == null) {

                return null;
            }
            T clazzInstance = null;
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int resultSetMetaDataColumnLength = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {

                clazzInstance = clazz.newInstance();

                for(int i = 0; i < resultSetMetaDataColumnLength; i++) {

                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(clazzInstance, columnValue);
                }
            }
            return clazzInstance;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find result exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(resultSet);
                close(preparedStatement);
            }
        }
    }

    @Override
    public synchronized <T> Set<T> findResults(Class<T> clazz, String sql, Object... params) throws MySQLException {

        Validate.notNull(clazz, "The entity class object is null.");
        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            if(params != null && params.length > 0) {

                int index = 1;

                for(final Object param : params) {

                    preparedStatement.setObject(index++, param);
                }
            }
            resultSet = preparedStatement.executeQuery();

            if(resultSet == null) {

                return null;
            }
            Set<T> clazzInstanceSet = new HashSet<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int resultSetMetaDataColumnLength = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {

                T clazzInstance = clazz.newInstance();

                for(int i = 0; i < resultSetMetaDataColumnLength; i++) {

                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(columnName);

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(clazzInstance, columnValue);
                }
                clazzInstanceSet.add(clazzInstance);
            }
            return clazzInstanceSet;
        }
        catch (Exception e) {

            throw new MySQLException("The mysql find result exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(resultSet);
                close(preparedStatement);
            }
        }
    }

    @Override
    public synchronized Object findSimpleResult(String columnName, String sql, Object... params) throws MySQLException {

        Validate.notNull(columnName, "The column name object is null.");

        Map<String, Object> result = findResult(sql, params);

        return result != null ? result.get(columnName) : null;
    }

    @Override
    public synchronized void dispose() throws MySQLException {

        if(connection != null) {

            try {

                if(!connection.isClosed()) {
                    connection.close();
                }
                connection = null;
            }
            catch (Exception e) {

                throw new MySQLException("The mysql dispose close exception.", e);
            }
        }
    }

    private String formatMySQLUrl() {

        return MYSQL_URL_CHARSET
                .replace("host", getHost())
                .replace("port", Integer.toString(port))
                .replace("database", getDatabase())
                .replace("charset", getCharset());
    }

    private void checkConnection() {

        if(connection == null) {

            throw new MySQLException("The mysql connection object is null. Maybe: getConnection();");
        }
    }

    private void close(Statement statement) {

        if(statement != null) {

            try {

                if(!statement.isClosed()) {
                    statement.close();
                }
            }
            catch (Exception e) {

                throw new MySQLException("The mysql statement dispose close exception.", e);
            }
        }
    }

    private void close(ResultSet resultSet) {

        if(resultSet != null) {

            try {

                if(!resultSet.isClosed()) {
                    resultSet.close();
                }
            }
            catch (Exception e) {

                throw new MySQLException("The mysql result set dispose close exception.", e);
            }
        }
    }
}
