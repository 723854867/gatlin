package org.gatlin.dao.mongo.dao;

import org.gatlin.dao.bean.entity.MongoKey;
import org.gatlin.dao.mongo.MongoCondition;
import org.gatlin.dao.mongo.MongoDao;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

@Component
@Conditional(MongoCondition.class)
public class MongoKeyDao extends MongoDao<String, MongoKey> {
	
	private static final String NAME			= "name";
	private static final String VALUE			= "value";

	public MongoKeyDao() {
		super("keys");
	}
	
	public long getAndInc(String key, Number number) { 
		MongoKey mongoKey = mongo.findOneAndUpdate(collection, Filters.eq(NAME, key), Updates.inc(VALUE, number), 
				new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER).projection(Projections.excludeId()), clazz);
		return null == mongoKey ? 0 : mongoKey.getValue();
	}
}
