package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityTable;

public class DeleteByQuerySQLProvider extends SQLProvider<String> {

	public DeleteByQuerySQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
		EntityTable table = getEntityTable(ms);
		Class<?> entityClass = table.getEntityClass();
        StringBuilder sql = new StringBuilder();
        sql.append(deleteFromTable(entityClass, tableName(entityClass)));
        sql.append("<if test=\"null != conditions and !conditions.isEmpty\">");
			sql.append("<where>"
				+ "<foreach item=\"item\" collection=\"conditions\" separator=\" AND \">"
					+ "<choose>"
						+ "<when test=\"item.comparison==1\">"
							+ "<![CDATA[`${item.col}`<#{item.value}]]>"
						+ "</when>"
						+ "<when test=\"item.comparison==2\">"
							+ "<![CDATA[`${item.col}`<=#{item.value}]]>"
						+ "</when>"
						+ "<when test=\"item.comparison==4\">"
							+ "<![CDATA[`${item.col}`>#{item.value}]]>"
						+ "</when>"
						+ "<when test=\"item.comparison==8\">"
							+ "<![CDATA[`${item.col}`>=#{item.value}]]>"
						+ "</when>"
						+ "<when test=\"item.comparison==16\">"
							+ "`${item.col}`=#{item.value}"
						+ "</when>"
						+ "<when test=\"item.comparison==32\">"
							+ "`${item.col}`!=#{item.value}"
						+ "</when>"
						+ "<when test=\"item.comparison==64\">"
							+ "`${item.col}` LIKE concat(concat('%',#{item.value}),'%')"
						
						+ "</when>"
						+ "<when test=\"item.comparison==128\">"
							+ "${item.col} IN ("
								+ "<foreach item=\"item1\" collection=\"item.value\" separator=\",\">"
									+ "#{item1}"
								+ "</foreach>"
							+ ")"
						+ "</when>"
						+ "<otherwise>"
							+ "${item.col} NOT IN ("
								+ "<foreach item=\"item1\" collection=\"item.value\" separator=\",\">"
									+ "#{item1}"
								+ "</foreach>"
							+ ")"
						+ "</otherwise>"
					+ "</choose>" 
				+ "</foreach>"
			+ "</where>");
		sql.append("</if>");
        return sql.toString();
	}
}
