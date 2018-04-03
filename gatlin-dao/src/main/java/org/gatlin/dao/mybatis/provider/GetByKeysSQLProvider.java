package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityHelper;

public class GetByKeysSQLProvider extends SQLProvider<String> {

	public GetByKeysSQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass));
        sql.append(whereColumnIn(EntityHelper.getEntityTable(entityClass).getpKColumn().getColumn()));
        return sql.toString();
    }
}
