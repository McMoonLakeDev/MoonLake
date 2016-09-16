package com.minecraft.moonlake.api.mysql.query;

import com.minecraft.moonlake.api.mysql.MySQLConnection;
import com.minecraft.moonlake.api.mysql.MySQLTable;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/7/28.
 */
public class MySQLQueryInsert extends MySQLQueryAbstract {

    private boolean first;
    private List<Object> values;
    private final MySQLTable table;

    public MySQLQueryInsert(MySQLConnection connection, MySQLTable table) {

        super(connection, "insert into " + table.getName() + " (");

        this.first = true;
        this.table = table;
        this.values = new ArrayList<>();
    }

    /**
     * 执行 MySQL 的插入表的指定键
     *
     * @param field 键
     * @return 实例
     */
    public MySQLQueryInsert field(String field) {

        sql = (sql + field + ", ");

        return this;
    }

    /**
     * 执行 MySQL 的插入表的指定值
     *
     * @param value 值
     * @return 实例
     */
    public MySQLQueryInsert value(Object value) {

        values.add(value);

        sql = sql.substring(0, sql.length() - 1);

        if(!first) {

            sql += ", ?)";
        }
        else {

            sql = sql.substring(0, sql.length() - 1);
            sql += ") values (?)";

            first = false;
        }
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

            getInstance().getMLogger().warn("执行数据库的声明对象的插入语句时异常: " + e.getMessage());
        }
        return null;
    }
}
