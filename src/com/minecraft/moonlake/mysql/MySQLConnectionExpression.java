package com.minecraft.moonlake.mysql;

import com.minecraft.moonlake.mysql.exception.MySQLException;
import com.minecraft.moonlake.mysql.exception.MySQLInitializeException;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

/**
 * Created by MoonLake on 2016/9/28.
 */
public class MySQLConnectionExpression implements MySQLConnection {

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

    public MySQLConnectionExpression(String username, String password) throws MySQLInitializeException {

        this("localhost", 3306, username, password, MYSQL_DEFAULT_CHARSET);
    }

    public MySQLConnectionExpression(String username, String password, String charset) throws MySQLInitializeException {

        this("localhost", 3306, username, password, charset);
    }

    public MySQLConnectionExpression(String username, String password, Charset charset) throws MySQLInitializeException {

        this("localhost", 3306, username, password, charset);
    }

    public MySQLConnectionExpression(String host, int port, String username, String password) throws MySQLInitializeException {

        this(host, port, username, password, MYSQL_DEFAULT_CHARSET);
    }

    public MySQLConnectionExpression(String host, int port, String username, String password, String charset) throws MySQLInitializeException {

        this(host, port, username, password, Charset.forName(charset));
    }

    public MySQLConnectionExpression(String host, int port, String username, String password, Charset charset) throws MySQLInitializeException {

        Validate.notNull(host, "The mysql host object is null.");
        Validate.isTrue(port >= 0 && port <= 65535, "The mysql port is illegal value. (must: 0 - 65535)");
        Validate.notNull(username, "The mysql username object is null.");
        Validate.notNull(password, "The mysql password object is null.");
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
    public boolean isPromptlyClose() {

        return promptlyClose;
    }

    @Override
    public void setPromptlyClose(boolean flag) {

        this.promptlyClose = flag;
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
    public synchronized boolean dispatchStatement(String sql) throws MySQLException {

        Validate.notNull(sql, "The mysql sql object is null.");
        Validate.isTrue(!sql.isEmpty(), "The mysql sql object is empty.");

        checkConnection();

        Statement statement = null;

        try {

            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e) {

            throw new MySQLException("The mysql dispatch statement sql exception.", e);
        }
        finally {

            if(isPromptlyClose()) {

                close(statement);
            }
        }
        return false;
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
    public synchronized void dispose() throws MySQLException {

        if(connection != null) {

            try {

                connection.close();
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

                statement.close();
            }
            catch (Exception e) {

                throw new MySQLException("The mysql statement dispose close exception.", e);
            }
        }
    }

    private void close(ResultSet resultSet) {

        if(resultSet != null) {

            try {

                resultSet.close();
            }
            catch (Exception e) {

                throw new MySQLException("The mysql result set dispose close exception.", e);
            }
        }
    }
}
