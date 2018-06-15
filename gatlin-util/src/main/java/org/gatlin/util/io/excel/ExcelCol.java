package org.gatlin.util.io.excel;

import java.io.Serializable;

import javax.persistence.Column;

import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.reflect.EntityField;

public class ExcelCol implements Serializable {

	private static final long serialVersionUID = -634504683298440494L;

	private int scale;
	private String name;
	private EntityField field;
	
	public ExcelCol() {}
	
	public ExcelCol(EntityField field) {
		this.field = field;
		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			this.name = column.name();
			this.scale = column.scale();
		}
		if (!StringUtil.hasText(this.name))
			this.name = field.getName();
	}
	
	public int getScale() {
		return scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public EntityField getField() {
		return field;
	}
	
	public void setField(EntityField field) {
		this.field = field;
	}
}
