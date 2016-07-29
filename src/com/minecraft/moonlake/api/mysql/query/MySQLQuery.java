package com.minecraft.moonlake.api.mysql.query;

/**
 * Created by MoonLake on 2016/7/28.
 */
public interface MySQLQuery {

    /**
     * 执行 MySQL 的查询
     *
     * @return 对象
     */
    Object execute() throws Exception;

    /**
     * 执行 MySQL 的查询
     *
     * @param entityClass 实体类
     * @param <T> 实体类
     * @return 实体类对象实例
     * @throws Exception
     */
    <T extends Object> T execute(Class<? extends T> entityClass) throws Exception;
}
