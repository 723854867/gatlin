package org.gatlin.dao.bean.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gatlin.dao.bean.enums.Comparison;
import org.gatlin.util.bean.model.Pair;

/**
 * 只支持 and条件查询
 * 
 * @author lynn
 *
 * @param <QUERY>
 */
public class Query implements Serializable {

	private static final long serialVersionUID = 7213510348985683656L;

	private boolean lock;
	private Integer limit;
	private Integer page;
	private Integer pageSize;
	private List<String> cols;
	private List<String> groupBys;
	private List<Condition> conditions;
	private List<Pair<String, Boolean>> orderBys;
	
	public Query() {
		this.cols = new ArrayList<String>();
		this.groupBys = new ArrayList<String>();
		this.conditions = new ArrayList<Condition>();
		this.orderBys = new ArrayList<Pair<String, Boolean>>();
	}
	
	public boolean isLock() {
		return lock;
	}
	
	public Query forUpdate()  {
		this.lock = true;
		return this;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public Query limit(Integer limit) {
		this.limit = limit;
		return this;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public Query page(Integer page) {
		this.page = page;
		return this;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public Query pageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public List<String> getCols() {
		return cols;
	}
	
	public Query cols(List<String> cols) {
		this.cols = cols;
		return this;
	}
	
	public List<String> getGroupBys() {
		return groupBys;
	}
	
	public Query groupBys(List<String> groupBys) {
		this.groupBys = groupBys;
		return this;
	}
	
	public List<Condition> getConditions() {
		return conditions;
	}
	
	public Query addCondition(Condition condition) {
		this.conditions.add(condition);
		return this;
	}
	
	public Query setConditions(List<Condition> conditions) {
		this.conditions = conditions;
		return this;
	}
	
	public List<Pair<String, Boolean>> getOrderBys() {
		return orderBys;
	}
	
	public Query orderBys(List<Pair<String, Boolean>> orderBys) {
		this.orderBys = orderBys;
		return this;
	}
	
	public Query cols(String...cols) {
		for (String col : cols)
			this.cols.add(col);
		return this;
	}
	
	public Query sum(String col) {
		this.cols.add("SUM(" + col + ") " + col);
		return this;
	}
	
	public Query max(String col) {
		this.cols.add("MAX(" + col + ") " + col);
		return this;
	}
	
	public Query min(String col) {
		this.cols.add("MIN(" + col + ") " + col);
		return this;
	}
	
	public Query groupBy(String...cols) {
		for (String col : cols)
			this.groupBys.add(col);
		return this;
	}
	
	public Query orderByAsc(String col) {
		return orderBy(col, true);
	}
	
	public Query orderByAsc(String... cols) {
		for (String col : cols)
			orderBy(col, true);
		return this;
	}
	
	public Query orderByDesc(String col) {
		return orderBy(col, false);
	}
	
	public Query orderByDesc(String... cols) {
		for (String col : cols)
			orderBy(col, false);
		return this;
	}
	
	protected Query orderBy(String col, boolean asc) {
		this.orderBys.add(new Pair<String, Boolean>(col, asc));
		return this;
	}
	
	public Query in(String col, Collection<?> params) {
		this.conditions.add(new Condition(col, Comparison.in, params));
		return this;
	}
	
	public Query notIn(String col, Object... params) {
		this.conditions.add(new Condition(col, Comparison.nin, params));
		return this;
	}
	
	public Query eq(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.eq, value));
		return this;
	}
	
	public Query neq(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.neq, value));
		return this;
	}
	
	public Query lt(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.lt, value));
		return this;
	}
	
	public Query lte(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.lte, value));
		return this;
	}
	
	public Query gt(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.gt, value));
		return this;
	}
	
	public Query gte(String col, Object value) {
		this.conditions.add(new Condition(col, Comparison.gte, value));
		return this;
	}
	
	public Query like(String col, String value) {
		this.conditions.add(new Condition(col, Comparison.like, value));
		return this;
	}
}
