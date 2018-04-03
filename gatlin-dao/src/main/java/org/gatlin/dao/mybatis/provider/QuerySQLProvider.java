package org.gatlin.dao.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityTable;

public class QuerySQLProvider extends SQLProvider<String> {

	public QuerySQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
		EntityTable table = getEntityTable(ms);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
			sql.append("<choose>");
				sql.append("<when test=\"null == cols or cols.isEmpty\">");
					sql.append("*");
				sql.append("</when>");
				sql.append("<otherwise>");
					sql.append("<foreach item=\"item\" collection=\"cols\" separator=\",\">");
						sql.append("${item}");
					sql.append("</foreach>");
				sql.append("</otherwise>");
			sql.append("</choose>");
		sql.append(" FROM ").append(tableName(table.getEntityClass()));
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
		sql.append("<if test=\"null != groupBys and !groupBys.isEmpty\">");
			sql.append(" GROUP BY ");
			sql.append("<foreach item=\"item\" collection=\"groupBys\" separator=\",\">");
				sql.append("${item}");
			sql.append("</foreach>");
		sql.append("</if>");
		sql.append("<if test=\"null != orderBys and !orderBys.isEmpty\">");
			sql.append(" ORDER BY ");
			sql.append("<foreach item=\"item\" collection=\"orderBys\" separator=\",\">");
				sql.append("<choose>");
					sql.append("<when test=\"item.value\">");
						sql.append("${item.key} ASC ");
					sql.append("</when>");
					sql.append("<otherwise>");
						sql.append("${item.key} DESC ");
					sql.append("</otherwise>");
				sql.append("</choose>");
			sql.append("</foreach>");
		sql.append("</if>");
		sql.append("<if test=\"null != limit\">");
			sql.append(" LIMIT #{limit} ");
		sql.append("</if>");
		sql.append("<if test=\"lock\">");
			sql.append(" FOR UPDATE ");
		sql.append("</if>");
		return sql.toString();
	}

}
