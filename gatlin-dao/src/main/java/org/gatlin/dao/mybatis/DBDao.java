package org.gatlin.dao.mybatis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.gatlin.core.bean.Entity;
import org.gatlin.dao.Dao;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.dao.mybatis.provider.BatchInsertSQLProvider;
import org.gatlin.dao.mybatis.provider.DeleteByKeySQLProvider;
import org.gatlin.dao.mybatis.provider.DeleteByKeysSQLProvider;
import org.gatlin.dao.mybatis.provider.GetAllSQLProvider;
import org.gatlin.dao.mybatis.provider.GetByKeySQLProvider;
import org.gatlin.dao.mybatis.provider.GetByKeysSQLProvider;
import org.gatlin.dao.mybatis.provider.InsertSQLProvider;
import org.gatlin.dao.mybatis.provider.QuerySQLProvider;
import org.gatlin.dao.mybatis.provider.UpdateCollectionSQLProvider;
import org.gatlin.dao.mybatis.provider.UpdateMapSQLProvider;
import org.gatlin.dao.mybatis.provider.UpdateSQLProvider;

public interface DBDao<KEY, ENTITY extends Entity<KEY>> extends Dao<KEY, ENTITY> {

	@Override
	@InsertProvider(type = InsertSQLProvider.class, method = "dynamicSQL")
	long insert(ENTITY entity);
	
	/**
	 * 该方法及支持主键自增又支持已经设置了主键的批量插入，但是不支持混搭(即同时存在有主键和未设置主键的，一旦出现这种情况，entity 中主键值是不可预测的)
	 */
	@Override
	@InsertProvider(type = BatchInsertSQLProvider.class, method = "dynamicSQL")
	void batchInsert(Collection<ENTITY> entities);
	
	@Override
	@SelectProvider(type = GetAllSQLProvider.class, method = "dynamicSQL")
	Map<KEY, ENTITY> getAll();
	
	@Override
	@SelectProvider(type = GetAllSQLProvider.class, method = "dynamicSQL")
	List<ENTITY> getAllList();
	
	@Override
	@SelectProvider(type = GetByKeySQLProvider.class, method = "dynamicSQL")
	ENTITY getByKey(KEY key);
	
	@Override
	@SelectProvider(type = QuerySQLProvider.class, method = "dynamicSQL")
	ENTITY queryUnique(Query query);
	
	@Override
	@SelectProvider(type = QuerySQLProvider.class, method = "dynamicSQL")
	Map<KEY, ENTITY> query(Query query);
	
	@Override
	@SelectProvider(type = QuerySQLProvider.class, method = "dynamicSQL")
	List<ENTITY> queryList(Query query);
	
	@Override
	@SelectProvider(type = GetByKeysSQLProvider.class, method = "dynamicSQL")
	Map<KEY, ENTITY> getByKeys(Collection<KEY> keys);
	
	@Override
	@UpdateProvider(type = UpdateSQLProvider.class, method = "dynamicSQL")
	long update(ENTITY entity);
	
	@Override
	@UpdateProvider(type = UpdateMapSQLProvider.class, method = "dynamicSQL")
	int updateMap(@Param("map") Map<KEY, ENTITY> map);
	
	@Override
	@UpdateProvider(type = UpdateCollectionSQLProvider.class, method = "dynamicSQL")
	int updateCollection(Collection<ENTITY> entities);
	
	@Override
	@DeleteProvider(type = DeleteByKeySQLProvider.class, method = "dynamicSQL")
	long deleteByKey(KEY key);
	
	@Override
	long deleteByQuery(Query query);
	
	@Override
	@DeleteProvider(type = DeleteByKeysSQLProvider.class, method = "dynamicSQL")
	long deleteByKeys(Collection<KEY> keys);
}
