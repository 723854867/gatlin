package org.gatlin.dao.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.dao.Dao;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.serial.SerializeUtil;
import org.gatlin.util.serial.Serializer;

@SuppressWarnings("unchecked")
public class RedisDao<KEY, ENTITY extends Identifiable<KEY>> implements Dao<KEY, ENTITY> {
	
	@Resource
	protected Redis redis;
	protected byte[] mapperKey;
	protected Class<ENTITY> clazz;
	@Resource
	protected Serializer serializer;
	
	public RedisDao(String mapperKey) {
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<ENTITY>) generics[1];
		this.mapperKey = SerializeUtil.REDIS.encode(mapperKey);
	}

	@Override
	public long insert(ENTITY entity) {
		byte[] data = serializer.serial(entity);
		return redis.hset(mapperKey, entity.key(), data);
	}

	@Override
	public void batchInsert(Collection<ENTITY> entities) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (ENTITY entity : entities) 
			map.put(entity.key(), serializer.serial(entities));
		redis.hmset(mapperKey, map);
	}

	@Override
	public ENTITY getByKey(KEY field) {
		byte[] data = redis.hget(mapperKey, field);
		return null == data ? null : serializer.deserial(data, clazz);
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
		throw new UnsupportedOperationException("Redis unsupport query!");
	}

	@Override
	public Map<KEY, ENTITY> query(Query query) {
		throw new UnsupportedOperationException("Redis unsupport query!");
	}

	@Override
	public List<ENTITY> queryList(Query query) {
		throw new UnsupportedOperationException("Redis unsupport query!");
	}

	@Override
	public Map<KEY, ENTITY> getByKeys(Collection<KEY> keys) {
		List<byte[]> list = redis.hmget(mapperKey, keys.toArray());
		Map<KEY, ENTITY> map = new HashMap<KEY, ENTITY>();
		for (byte[] data : list) {
			if (null == data)
				continue;
			ENTITY entity = serializer.deserial(data, clazz);
			map.put(entity.key(), entity);
		}
		return map;
	}

	@Override
	public long update(ENTITY entity) {
		return this.insert(entity);
	}

	@Override
	public int updateMap(Map<KEY, ENTITY> entities) {
		return 0;
	}

	@Override
	public int updateCollection(Collection<ENTITY> entities) {
		return 0;
	}

	@Override
	public long deleteByKey(KEY field) {
		return redis.hdel(mapperKey, field);
	}
	
	@Override
	public long deleteByQuery(Query query) {
		return 0;
	}

	@Override
	public long deleteByKeys(Collection<KEY> fields) {
		return redis.hdel(mapperKey, fields.toArray());
	}
}
