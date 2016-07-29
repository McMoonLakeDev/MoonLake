package com.minecraft.moonlake.api.mysql.query;

import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLTable;
import com.minecraft.moonlake.api.mysql.resultset.MySQLResultSet;
import com.minecraft.moonlake.util.mysql.MySQLResultSetUtil;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLQuerySelect extends MySQLQueryAbstract {

    private boolean and;
    private List<Object> values;
    private final MySQLTable table;

    public MySQLQuerySelect(MySQLConnection connection, MySQLTable table) {

        this(connection, table, "*");
    }

    public MySQLQuerySelect(MySQLConnection connection, MySQLTable table, String selection) {

        super(connection, "select " + selection + " from " + table.getName());

        this.and = false;
        this.table = table;
        this.values = new ArrayList<>();
    }

    /**
     * 执行 MySQL 的选择表的指定键的值
     *
     * @param key 键
     * @param value 值
     * @return 实例
     */
    public MySQLQuerySelect where(String key, Object value) {

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
     * @return 结果集对象
     */
    @Override
    public MySQLResultSet execute() {

        try {

            super.execute();

            PreparedStatement pst = connection.getConnection().prepareStatement(sql);

            int i = 1;

            for(Object object : values) {

                pst.setObject(i, object);

                i++;
            }
            return new MySQLResultSetUtil(pst.executeQuery());
        }
        catch (Exception e) {

            getInstance().log("执行数据库的声明对象的选择语句时异常: " + e.getMessage());
        }
        return null;
    }

    /**
     * 执行 MySQL 的查询 (注意拥有实体类的无参构造)
     *
     * @param entityClass 实体类
     * @param <T> 实体类
     * @return 实体类对象实例 异常或没有则返回 null
     */
    public <T extends Object> T execute(Class<? extends T> entityClass) {

        try {

            MySQLResultSet resultSet = execute();

            if(resultSet.next()) {

                T instance = entityClass.getConstructor().newInstance();
                Field[] entityClassFields = entityClass.getFields();

                for(Field field : entityClassFields) {

                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(field.getName()));
                }
                return instance;
            }
        }
        catch (Exception e) {

            getInstance().log("执行数据库的声明对象的选择语句时异常: " + e.getMessage());
        }
        return null;
    }
}
