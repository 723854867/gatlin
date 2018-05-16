package org.gatlin.dao.mongo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.dao.Dao;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.util.bean.Identifiable;

import com.mongodb.client.model.Filters;

public class MongoDao<KEY, ENTITY extends Identifiable<KEY>> implements Dao<KEY, ENTITY> {
	
	protected static final String FIELD_ID					= "_id";
	
	@Resource
	protected Mongo mongo;
	protected String collection;
	protected Class<ENTITY> clazz;
	
	@SuppressWarnings("unchecked")
	public MongoDao(String collection) {
		this.collection = collection;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<ENTITY>) generics[1];
	}

	@Override
	public long insert(ENTITY entity) {
		mongo.insertOne(collection, entity);
		return 0;
	}

	@Override
	public void batchInsert(Collection<ENTITY> entities) {
		mongo.insertMany(collection, entities);
	}

	@Override
	public ENTITY getByKey(KEY key) {
		return mongo.findOne(collection, Filters.eq(FIELD_ID, key), clazz);
	}

	@Override
	public Map<KEY, ENTITY> getByKeys(Collection<KEY> keys) {
		return mongo.findMap(collection, MongoUtil.or(FIELD_ID, keys), clazz);
	}
	
	@Override
	public Map<KEY, ENTITY> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ENTITY> getAllList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY queryUnique(Query query) {
		return null;
	}

	@Override
	public Map<KEY, ENTITY> query(Query query) {
		throw new UnsupportedOperationException("mongo db not support query!");
	}

	@Override
	public List<ENTITY> queryList(Query query) {
		return mongo.query(collection, query, clazz);
	}

	@Override
	public long update(ENTITY entity) {
		return mongo.replaceOne(collection, Filters.eq(FIELD_ID, entity.key()), entity);
	}

	@Override
	public int updateMap(Map<KEY, ENTITY> entities) {
		return (int) mongo.bulkReplaceOne(collection, entities);
	}
	
	@Override
	public int updateCollection(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deleteByKey(KEY key) {
		return mongo.deleteOne(collection, Filters.eq(FIELD_ID, key));
	}
	
	@Override
	public long deleteByQuery(Query query) {
		return 0;
	}

	@Override
	public long deleteByKeys(Collection<KEY> keys) {
		return mongo.deleteMany(collection, MongoUtil.or(FIELD_ID, keys));
	}
}
