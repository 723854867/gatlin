package org.gatlin.dao.mybatis.entity;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.gatlin.dao.bean.anno.NameStyle;
import org.gatlin.dao.mybatis.DaoConfig;
import org.gatlin.dao.mybatis.DaoConfig.Style;
import org.gatlin.dao.mybatis.SqlKeyWords;
import org.gatlin.util.lang.StringUtil;

public class EntityHelper {

	// 实体类 => 表对象
	private static final Map<Class<?>, EntityTable> entityTableMap = new ConcurrentHashMap<Class<?>, EntityTable>();

    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }
	
	public static EntityColumn getPKColumn(Class<?> entityClass) {
        return getEntityTable(entityClass).getpKColumn();
    }
	
	public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null)
            throw new RuntimeException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        return entityTable;
    }
	
	/**
	 * 初始化实体属性
	 */
	public static synchronized void initEntityNameMap(Class<?> entityClass, DaoConfig config) {
		if (entityTableMap.get(entityClass) != null)
			return;
		Style style = config.getNameStyle();
		if (entityClass.isAnnotationPresent(NameStyle.class)) { // style，该注解优先于全局配置
			NameStyle nameStyle = entityClass.getAnnotation(NameStyle.class);
			style = nameStyle.value();
		}
		// 创建并缓存EntityTable
		EntityTable entityTable = null;
		if (entityClass.isAnnotationPresent(Table.class)) {
			Table table = entityClass.getAnnotation(Table.class);
			if (!table.name().equals("")) {
				entityTable = new EntityTable(entityClass);
				entityTable.setTable(table);
			}
		}
		if (entityTable == null) {
			entityTable = new EntityTable(entityClass);
			entityTable.setName(_convertByStyle(entityClass.getSimpleName(), style));
		}
		// 处理所有列
		List<EntityField> fields = FieldHelper.getFields(entityClass);
		for (EntityField field : fields)
			_processField(entityTable, style, field, config.getKeyWordWrapper());
		entityTable.initPropertyMap();
		entityTableMap.put(entityClass, entityTable);
	}

	/**
	 * 处理一列
	 */
	private static void _processField(EntityTable entityTable, Style style, EntityField field, String wrapKeyword) {
		if (field.isAnnotationPresent(Transient.class))
			return;
		EntityColumn entityColumn = new EntityColumn(entityTable);
		entityColumn.setEntityField(field);
		if (field.isAnnotationPresent(Id.class))
			entityColumn.setId(true);
		String columnName = null;
		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			columnName = column.name();
			entityColumn.setUpdatable(column.updatable());
			entityColumn.setInsertable(column.insertable());
		}
		if (!StringUtil.hasText(columnName))
			columnName = _convertByStyle(field.getName(), style);
		if (StringUtil.hasText(wrapKeyword) && SqlKeyWords.containsWord(columnName))
			columnName = MessageFormat.format(wrapKeyword, columnName);
		entityColumn.setProperty(field.getName());
		entityColumn.setColumn(columnName);
		entityColumn.setJavaType(field.getJavaType());
		if (entityColumn.isId())
			entityTable.setpKColumn(entityColumn);
		entityTable.getEntityClassColumns().add(entityColumn);
	}

	public static String _convertByStyle(String str, Style style) {
		switch (style) {
		case camelhump:
			return StringUtil.camel2Underline(str);
		case uppercase:
			return str.toUpperCase();
		case lowercase:
			return str.toLowerCase();
		case camelhumpAndLowercase:
			return StringUtil.camel2Underline(str).toLowerCase();
		case camelhumpAndUppercase:
			return StringUtil.camel2Underline(str).toUpperCase();
		default:
			return str;
		}
	}
}
