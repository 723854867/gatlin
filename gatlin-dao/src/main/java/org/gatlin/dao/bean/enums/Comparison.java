package org.gatlin.dao.bean.enums;

/**
 * 比较符
 */
public enum Comparison {

	// 小于
	lt(1),
	// 小于等于
	lte(2),
	// 大于
	gt(4),
	// 大于等于
	gte(8),
	// 等于
	eq(16),
	// 不等于
	neq(32),
	// like 运算
	like(64),
	// in
	in(128),
	// not in
	nin(256),
	// 包含
	all(512);
	
	private int mark;
	
	private Comparison(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final Comparison match(int type) {
		for (Comparison temp : Comparison.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
