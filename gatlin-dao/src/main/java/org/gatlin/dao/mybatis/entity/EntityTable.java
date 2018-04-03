package org.gatlin.dao.mybatis.entity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public class EntityTable {
	public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
	// 属性和列对应
	protected Map<String, EntityColumn> propertyMap;
	private String name;
	private String orderByClause;
	private String baseSelect;
	// 实体类 => 主键信息
	private EntityColumn pKColumn;
	// 实体类 => 全部列属性
	private Set<EntityColumn> entityClassColumns;
	// resultMap对象
	private ResultMap resultMap;
	// 类
	private Class<?> entityClass;

	public EntityTable(Class<?> entityClass) {
		this.entityClass = entityClass;
		this.entityClassColumns = new LinkedHashSet<EntityColumn>();
	}

	/**
	 * 生成当前实体的resultMap对象
	 */
	public ResultMap getResultMap(Configuration configuration) {
		if (this.resultMap != null)
			return this.resultMap;
		if (entityClassColumns == null || entityClassColumns.size() == 0)
			return null;
		List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
		for (EntityColumn entityColumn : entityClassColumns) {
			String column = entityColumn.getColumn();
			// 去掉可能存在的分隔符
			Matcher matcher = DELIMITER.matcher(column);
			if (matcher.find())
				column = matcher.group(1);
			ResultMapping.Builder builder = new ResultMapping.Builder(configuration, entityColumn.getProperty(), column,
					entityColumn.getJavaType());
			if (entityColumn.getJdbcType() != null) 
				builder.jdbcType(entityColumn.getJdbcType());
			if (entityColumn.getTypeHandler() != null) {
				try {
					builder.typeHandler(getInstance(entityColumn.getJavaType(), entityColumn.getTypeHandler()));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			List<ResultFlag> flags = new ArrayList<ResultFlag>();
			if (entityColumn.isId())
				flags.add(ResultFlag.ID);
			builder.flags(flags);
			resultMappings.add(builder.build());
		}
		ResultMap.Builder builder = new ResultMap.Builder(configuration, "BaseMapperResultMap", this.entityClass, resultMappings, true);
		this.resultMap = builder.build();
		return this.resultMap;
	}

	/**
	 * 初始化 - Example 会使用
	 */
	public void initPropertyMap() {
		propertyMap = new HashMap<String, EntityColumn>(getEntityClassColumns().size());
		for (EntityColumn column : getEntityClassColumns())
			propertyMap.put(column.getProperty(), column);
	}

	/**
	 * 实例化TypeHandler
	 * 
	 * @param javaTypeClass
	 * @param typeHandlerClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> TypeHandler<T> getInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
		if (javaTypeClass != null) {
			try {
				Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
				return (TypeHandler<T>) c.newInstance(javaTypeClass);
			} catch (NoSuchMethodException ignored) {
				// ignored
			} catch (Exception e) {
				throw new TypeException("Failed invoking constructor for handler " + typeHandlerClass, e);
			}
		}
		try {
			Constructor<?> c = typeHandlerClass.getConstructor();
			return (TypeHandler<T>) c.newInstance();
		} catch (Exception e) {
			throw new TypeException("Unable to find a usable constructor for " + typeHandlerClass, e);
		}
	}

	public String getBaseSelect() {
		return baseSelect;
	}

	public void setBaseSelect(String baseSelect) {
		this.baseSelect = baseSelect;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Set<EntityColumn> getEntityClassColumns() {
		return entityClassColumns;
	}

	public void setEntityClassColumns(Set<EntityColumn> entityClassColumns) {
		this.entityClassColumns = entityClassColumns;
	}

	public EntityColumn getpKColumn() {
		return pKColumn;
	}
	
	public void setpKColumn(EntityColumn pKColumn) {
		this.pKColumn = pKColumn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public Map<String, EntityColumn> getPropertyMap() {
		return propertyMap;
	}

	public void setTable(Table table) {
		this.name = table.name();
	}
}
