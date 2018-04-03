package org.gatlin.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.gatlin.core.bean.Entity;
import org.gatlin.dao.bean.model.Query;

public interface Dao<KEY, ENTITY extends Entity<KEY>> {

	/**
	 * 插入一个对象
	 */
	long insert(ENTITY entity);
	
	/**
	 * 插入多个对象
	 */
	void batchInsert(Collection<ENTITY> entities);
	
	/**
	 * 获取所有数据
	 */
	Map<KEY, ENTITY> getAll();
	
	/**
	 * 获取所有数据
	 */
	List<ENTITY> getAllList();
	
	/**
	 * 根据主键获取对象
	 */
	ENTITY getByKey(KEY key);
	
	/**
	 * 复杂查询:确定查询结果只有一个的时候使用
	 */
	ENTITY queryUnique(Query<?> query);
	
	/**
	 * 复杂查询：返回的是一个map
	 */
	Map<KEY, ENTITY> query(Query<?> query);
	
	/**
	 * 复杂查询:返回的是一个list
	 */
	List<ENTITY> queryList(Query<?> query);
	
	/**
	 * 一次获取多条数据
	 */
	Map<KEY, ENTITY> getByKeys(Collection<KEY> keys);
	
	/**
	 * 更新对象：是更新对象的所有属性，因此要注意每个属性是否都是最新的
	 */
	long update(ENTITY entity);
	
	/**
	 * 批量更新
	 */
	int updateMap(Map<KEY, ENTITY> entities);
	
	/**
	 * 批量更新
	 */
	int updateCollection(Collection<ENTITY> entities);
	
	/**
	 * 根据主键删除
	 */
	long deleteByKey(KEY key);
	
	/**
	 * 根据条件删除
	 */
	long deleteByQuery(Query<?> query);
	
	/**
	 * 根据主键批量删除
	 */
	long deleteByKeys(Collection<KEY> keys);
}
