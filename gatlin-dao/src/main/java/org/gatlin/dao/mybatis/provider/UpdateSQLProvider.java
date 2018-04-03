package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;

public class UpdateSQLProvider extends SQLProvider<String> {

	public UpdateSQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(updateTable(entityClass, tableName(entityClass)));
        sql.append(updateSetColumns(entityClass, null));
        sql.append(wherePKColumn(entityClass));
        return sql.toString();
    }
}
