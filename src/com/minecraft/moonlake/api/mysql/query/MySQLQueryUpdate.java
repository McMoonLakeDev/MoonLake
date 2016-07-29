package com.minecraft.moonlake.api.mysql.query;

import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLTable;
import com.minecraft.moonlake.exception.IllegalMySQLException;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLQueryUpdate extends MySQLQueryAbstract {

    private boolean and;
    private boolean comma;
    private List<Object> values;
    private final MySQLTable table;

    public MySQLQueryUpdate(MySQLConnection connection, MySQLTable table) {

        super(connection, "update " + table.getName() + " set");

        this.and = false;
        this.comma = false;
        this.table = table;
        this.values = new ArrayList<>();
    }

    /**
     * 执行 MySQL 的更新表的指定字段的值
     *
     * @param field 字段
     * @param value 值
     * @return 实例
     */
    public MySQLQueryUpdate set(String field, Object value) {

        if(comma) {

            sql += ",";
        }
        values.add(value);

        sql = (sql + " " + field + "=?");

        comma = true;

        return this;
    }

    /**
     * 执行 MySQL 的更新表的指定字段的加上值
     *
     * @param field 字段
     * @param value 加上值
     * @return 实例
     */
    public MySQLQueryUpdate add(String field, Number value) {

        if(comma) {

            sql += ",";
        }
        values.add(value);

        sql = (sql + " " + field + "=" + field + "+?");

        comma = true;

        return this;
    }

    /**
     * 执行 MySQL 的更新表的指定字段的减少值
     *
     * @param field 字段
     * @param value 减少值
     * @return 实例
     */
    public MySQLQueryUpdate subtract(String field, Number value) {

        if(comma) {

            sql += ",";
        }
        values.add(value);

        sql = (sql + " " + field + "=" + field + "-?");

        comma = true;

        return this;
    }

    /**
     * 执行 MySQL 的更新表的指定字段的乘以值
     *
     * @param field 字段
     * @param value 乘以值
     * @return 实例
     */
    public MySQLQueryUpdate multiply(String field, Number value) {

        if(comma) {

            sql += ",";
        }
        values.add(value);

        sql = (sql + " " + field + "=" + field + "*?");

        comma = true;

        return this;
    }

    /**
     * 执行 MySQL 的更新表的指定字段的除以值
     *
     * @param field 字段
     * @param value 除以值
     * @return 实例
     */
    public MySQLQueryUpdate divide(String field, Number value) throws IllegalMySQLException {

        if(value.doubleValue() == 0d) {

            throw new IllegalMySQLException("Can not be divided by zero.");
        }
        if(comma) {

            sql += ",";
        }
        values.add(value);

        sql = (sql + " " + field + "=" + field + "/?");

        comma = true;

        return this;
    }

    /**
     * 执行 MySQL 的更新表的判断指定键的值
     *
     * @param key 键
     * @param value 值
     * @return 实例
     */
    public MySQLQueryUpdate where(String key, Object value) {

        if(!and) {

            sql += " where";
        }
        else {

            sql += " and";
        }
        sql = (sql + " " + key + "=");

        values.add(value);

        sql += "?";

        and = true;

        return this;
    }

    /**
     * 执行 MySQL 的任务
     *
     * @return 对象
     */
    @Override
    public Object execute() {

        try {

            super.execute();

            PreparedStatement pst = connection.getConnection().prepareStatement(sql);

            int i = 1;

            for(Object object : values) {

                pst.setObject(i, object);

                i++;
            }
            pst.executeUpdate();
            pst.close();
        }
        catch (Exception e) {

            getInstance().log("执行数据库的声明对象的选择语句时异常: " + e.getMessage());
        }
        return null;
    }
}
