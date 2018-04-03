package org.gatlin.dao.mybatis.provider;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityColumn;
import org.gatlin.dao.mybatis.entity.EntityTable;

public class UpdateMapSQLProvider extends SQLProvider<String> {
	
	private static final String foreachSuffix = "</foreach>";
	private static final String foreachPrefix = "<foreach item=\"value\" index=\"key\" collection=\"map\" separator=\",\">";

	public UpdateMapSQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
		EntityTable table = getEntityTable(ms);
		StringBuilder builder = new StringBuilder("REPLACE INTO ").append(table.getName()).append("(");
		Set<EntityColumn> columns = table.getEntityClassColumns();
		for (EntityColumn column : columns)
			builder.append(column.getColumn()).append(",");
		builder.deleteCharAt(builder.length() - 1).append(") VALUES").append(foreachPrefix).append("(");
		for (EntityColumn column : columns) 
			builder.append("#{value.").append(column.getProperty()).append("},");
		builder.deleteCharAt(builder.length() - 1).append(")").append(foreachSuffix);
		return builder.toString();
	}
}
