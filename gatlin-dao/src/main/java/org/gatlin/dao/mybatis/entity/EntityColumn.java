package org.gatlin.dao.mybatis.entity;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.reflect.EntityField;

public class EntityColumn {
	
	private EntityTable table;
	private String property;
	private String column;
	private Class<?> javaType;
	private JdbcType jdbcType;
	private Class<? extends TypeHandler<?>> typeHandler;
	private boolean id = false;
	private boolean insertable = true;
	private boolean updatable = true;
	private EntityField entityField;

	public EntityColumn() {
	}

	public EntityColumn(EntityTable table) {
		this.table = table;
	}

	/**
	 * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @return
	 */
	public String getColumnEqualsHolder(String entityName) {
		return this.column + " = " + getColumnHolder(entityName);
	}

	/**
	 * 返回格式如:#{entityName.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @return
	 */
	public String getColumnHolder(String entityName) {
		return getColumnHolder(entityName, null);
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @param suffix
	 * @return
	 */
	public String getColumnHolder(String entityName, String suffix) {
		return getColumnHolder(entityName, null, null);
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler},
	 *
	 * @param entityName
	 * @param suffix
	 * @return
	 */
	public String getColumnHolderWithComma(String entityName, String suffix) {
		return getColumnHolder(entityName, suffix, ",");
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}+separator
	 *
	 * @param entityName
	 * @param suffix
	 * @param separator
	 * @return
	 */
	public String getColumnHolder(String entityName, String suffix, String separator) {
		StringBuffer sb = new StringBuffer("#{");
		if (StringUtil.hasText(entityName)) {
			sb.append(entityName);
			sb.append(".");
		}
		sb.append(this.property);
		if (StringUtil.hasText(suffix)) 
			sb.append(suffix);
		if (this.jdbcType != null) {
			sb.append(",jdbcType=");
			sb.append(this.jdbcType.toString());
		} else if (this.typeHandler != null) {
			sb.append(",typeHandler=");
			sb.append(this.typeHandler.getCanonicalName());
		} else if (!this.javaType.isArray()) {// 当类型为数组时，不设置javaType#103
			sb.append(",javaType=");
			sb.append(javaType.getCanonicalName());
		}
		sb.append("}");
		if (StringUtil.hasText(separator))
			sb.append(separator);
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityColumn that = (EntityColumn) o;

		if (id != that.id)
			return false;
		if (table != null ? !table.equals(that.table) : that.table != null)
			return false;
		if (property != null ? !property.equals(that.property) : that.property != null)
			return false;
		if (column != null ? !column.equals(that.column) : that.column != null)
			return false;
		if (javaType != null ? !javaType.equals(that.javaType) : that.javaType != null)
			return false;
		if (jdbcType != that.jdbcType)
			return false;
		return !(typeHandler != null ? !typeHandler.equals(that.typeHandler) : that.typeHandler != null);
	}

	@Override
	public int hashCode() {
		int result = table != null ? table.hashCode() : 0;
		result = 31 * result + (property != null ? property.hashCode() : 0);
		result = 31 * result + (column != null ? column.hashCode() : 0);
		result = 31 * result + (javaType != null ? javaType.hashCode() : 0);
		result = 31 * result + (jdbcType != null ? jdbcType.hashCode() : 0);
		result = 31 * result + (typeHandler != null ? typeHandler.hashCode() : 0);
		result = 31 * result + (id ? 1 : 0);
		return result;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @return
	 */
	public String getColumnEqualsHolder() {
		return getColumnEqualsHolder(null);
	}

	/**
	 * 返回格式如:#{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @return
	 */
	public String getColumnHolder() {
		return getColumnHolder(null);
	}

	public EntityField getEntityField() {
		return entityField;
	}

	public void setEntityField(EntityField entityField) {
		this.entityField = entityField;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public EntityTable getTable() {
		return table;
	}

	public void setTable(EntityTable table) {
		this.table = table;
	}

	public Class<? extends TypeHandler<?>> getTypeHandler() {
		return typeHandler;
	}

	public void setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
		this.typeHandler = typeHandler;
	}

	public boolean isId() {
		return id;
	}

	public void setId(boolean id) {
		this.id = id;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}
}
