package org.gatlin.dao.mybatis.provider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.MsUtil;
import org.gatlin.dao.mybatis.entity.EntityColumn;
import org.gatlin.dao.mybatis.entity.EntityHelper;
import org.gatlin.dao.mybatis.entity.EntityTable;
import org.gatlin.util.lang.StringUtil;

public abstract class SQLProvider<T> {

	protected Class<?> mapperClass;
	protected DaoAccessor daoAccessor;
	protected Map<String, Class<?>> entityClassMap = new ConcurrentHashMap<String, Class<?>>();
	
	public SQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		this.mapperClass = mapperClass;
		this.daoAccessor = daoAccessor;
	}
	
	public String dynamicSQL() {
		return "dynamicSQL";
	}

	/**
	 * 真实有效的 sql
	 */
	public abstract T effectiveSQL(MappedStatement ms, Class<?> daoClass);

	public Class<?> getEntityClass(MappedStatement ms) {
		String msId = ms.getId();
		if (entityClassMap.containsKey(msId))
			return entityClassMap.get(msId);
		else {
			Class<?> mapperClass = MsUtil.getMapperClass(msId);
			Type[] types = mapperClass.getGenericInterfaces();
			for (Type type : types) {
				if (type instanceof ParameterizedType) {
					ParameterizedType t = (ParameterizedType) type;
					if (t.getRawType() == this.mapperClass
							|| this.mapperClass.isAssignableFrom((Class<?>) t.getRawType())) {
						Class<?> returnType = (Class<?>) t.getActualTypeArguments()[1];
						EntityHelper.initEntityNameMap(returnType, daoAccessor.getConfig());
						entityClassMap.put(msId, returnType);
						return returnType;
					}
				}
			}
		}
		throw new CodeException("无法获取 " + msId + " 方法的泛型信息!");
	}
	
	public EntityTable getEntityTable(MappedStatement ms) { 
		Class<?> clazz = getEntityClass(ms);
		return null == clazz ? null : EntityHelper.getEntityTable(clazz);
	}

	/**
	 * 设置返回值类型 - 为了让typeHandler在select时有效，改为设置resultMap
	 */
	protected void setResultType(MappedStatement ms, Class<?> entityClass) {
		EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		resultMaps.add(entityTable.getResultMap(ms.getConfiguration()));
		MetaObject metaObject = SystemMetaObject.forObject(ms);
		metaObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
	}
	
	protected String getBindCache(EntityColumn column) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_cache\" ");
        sql.append("value=\"").append(column.getProperty()).append("\"/>");
        return sql.toString();
    }
	
	protected String insertIntoTable(Class<?> entityClass, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }
	
	protected String replaceIntoTable(Class<?> entityClass, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("REPLACE INTO ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }
	
	protected String insertColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) 
                continue;
            sql.append(column.getColumn() + ",");
        }
        sql.append("</trim>");
        return sql.toString();
    }
	
	protected String getIfCacheNotNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache != null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }
	
	protected String getIfNotNull(EntityColumn column, String contents, boolean empty) {
        return getIfNotNull(null, column, contents, empty);
    }
	
	protected String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.hasText(entityName))
            sql.append(entityName).append(".");
        sql.append(column.getProperty()).append(" != null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtil.hasText(entityName))
                sql.append(entityName).append(".");
            sql.append(column.getProperty()).append(" != '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }
	
	protected String getIfCacheIsNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache == null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }
	
	protected String getIfIsNull(EntityColumn column, String contents, boolean empty) {
        return getIfIsNull(null, column, contents, empty);
    }
	
	protected String getIfIsNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.hasText(entityName))
            sql.append(entityName).append(".");
        sql.append(column.getProperty()).append(" == null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" or ");
            if (StringUtil.hasText(entityName))
                sql.append(entityName).append(".");
            sql.append(column.getProperty()).append(" == '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }
	
	protected String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(_getAllColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }
	
	private String _getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnList)
            sql.append(entityColumn.getColumn()).append(",");
        return sql.substring(0, sql.length() - 1);
    }
	
	protected String fromTable(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(tableName(entityClass));
        sql.append(" ");
        return sql.toString();
    }
	
	protected String tableName(Class<?> entityClass) {
        return EntityHelper.getEntityTable(entityClass).getName();
    }
	
	protected String wherePKColumn(Class<?> entityClass) {
		StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        EntityColumn column = EntityHelper.getPKColumn(entityClass);
        sql.append(column.getColumnEqualsHolder());
        sql.append("</where>");
        return sql.toString();
    }
	
	protected String whereColumnIn(String col) {
		StringBuilder sql = new StringBuilder();
    	sql.append("WHERE ").append(col).append(" IN");
    	sql.append("<foreach collection=\"collection\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">");
    		sql.append("#{item}");
    	sql.append("</foreach> ");
        return sql.toString();
    }
	
	protected String updateTable(Class<?> entityClass, String tableName) {
		StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }
	
	protected String updateSetColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (column.isId() || !column.isUpdatable()) 
            	continue;
            sql.append(column.getColumnEqualsHolder(entityName) + ",");
        }
        sql.append("</set>");
        return sql.toString();
    }
	
	protected String deleteFromTable(Class<?> entityClass, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }
}
