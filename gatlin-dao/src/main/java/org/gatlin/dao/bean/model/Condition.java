package org.gatlin.dao.bean.model;

import java.io.Serializable;

import org.gatlin.dao.bean.enums.Comparison;

public class Condition implements Serializable {
	
	private static final long serialVersionUID = 7167607188227959520L;
	
	private String col;
	private Object value;
	private int comparison;
	
	public Condition() {}
	
	public Condition(String col, Comparison comparison, Object value) {
		this.col = col;
		this.value = value;
		this.comparison = comparison.mark();
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	public int getComparison() {
		return comparison;
	}
	
	public void setComparison(int comparison) {
		this.comparison = comparison;
	}
}
