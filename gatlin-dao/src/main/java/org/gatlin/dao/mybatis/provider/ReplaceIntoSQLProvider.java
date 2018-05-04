package org.gatlin.dao.mybatis.provider;

import java.util.Set;

import javax.persistence.GeneratedValue;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityColumn;
import org.gatlin.dao.mybatis.entity.EntityHelper;

public class ReplaceIntoSQLProvider extends SQLProvider<String> {

	public ReplaceIntoSQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        sql.append(replaceIntoTable(entityClass, tableName(entityClass)));
        sql.append(insertColumns(entityClass));
        sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : columns) {
            if (!column.isInsertable())
                continue;
            if (column.getEntityField().isAnnotationPresent(GeneratedValue.class))
                sql.append(getIfCacheNotNull(column, column.getColumnHolder(null, "_cache", ",")));
            else 
                sql.append(getIfNotNull(column, column.getColumnHolder(null, null, ","), false));
            if (column.getEntityField().isAnnotationPresent(GeneratedValue.class)) 
                sql.append(getIfCacheIsNull(column, column.getColumnHolder() + ","));
            else 
                sql.append(getIfIsNull(column, column.getColumnHolder(null, null, ","), false));
        }
        sql.append("</trim>");
        return sql.toString();
    }
}
