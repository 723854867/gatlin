package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityTable;

public class DeleteByKeysSQLProvider extends SQLProvider<String> {

	public DeleteByKeysSQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
		EntityTable table = getEntityTable(ms);
		Class<?> entityClass = table.getEntityClass();
        StringBuilder sql = new StringBuilder();
        sql.append(deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(whereColumnIn(table.getpKColumn().getColumn()));
        return sql.toString();
    }
}
