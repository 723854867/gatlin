package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;

public class GetAllSQLProvider extends SQLProvider<String> {

	public GetAllSQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
		final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass));
        return sql.toString();
	}
}
