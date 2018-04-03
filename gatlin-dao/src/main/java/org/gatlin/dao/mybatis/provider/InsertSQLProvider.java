package org.gatlin.dao.mybatis.provider;

import java.util.Set;

import javax.persistence.GeneratedValue;

import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.gatlin.dao.mybatis.DaoAccessor;
import org.gatlin.dao.mybatis.entity.EntityColumn;
import org.gatlin.dao.mybatis.entity.EntityHelper;

public class InsertSQLProvider extends SQLProvider<String> {

	public InsertSQLProvider(Class<?> mapperClass, DaoAccessor mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	@Override
	public String effectiveSQL(MappedStatement ms, Class<?> daoClass) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        //Identity列只能有一个
        Boolean hasIdentityKey = false;
        for (EntityColumn column : columns) {
            if (!column.isInsertable()) 
                continue;
            if (column.getEntityField().isAnnotationPresent(GeneratedValue.class)) {		// 自增处理
            	 sql.append(getBindCache(column));
                 if (hasIdentityKey)
                     throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                 _autoIncrement(ms, column);
                 hasIdentityKey = true;
            }
        }
        sql.append(insertIntoTable(entityClass, tableName(entityClass)));
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

	private void _autoIncrement(MappedStatement ms, EntityColumn column) {
		String keyId = ms.getId() + "!selectKey";
		if (ms.getConfiguration().hasKeyGenerator(keyId))
			return;
		KeyGenerator keyGenerator = new Jdbc3KeyGenerator();
		try {
			MetaObject msObject = SystemMetaObject.forObject(ms);
			msObject.setValue("keyGenerator", keyGenerator);
			msObject.setValue("keyProperties", new String[] {column.getTable().getpKColumn().getProperty()});
			msObject.setValue("keyColumns", new String[] {column.getTable().getpKColumn().getColumn()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
