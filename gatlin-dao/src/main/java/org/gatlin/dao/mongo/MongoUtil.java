package org.gatlin.dao.mongo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class MongoUtil {

	public static final Bson or(String field, Collection<?> collection) {
		Set<Bson> set = new HashSet<Bson>();
		for (Object object : collection)
			set.add(Filters.eq(field, object));
		return Filters.or(set);
	}
	
	public static final Bson and(Map<String, Object> properties) {
		Set<Bson> set = new HashSet<Bson>();
		for (Entry<String, Object> entry : properties.entrySet())
			set.add(Filters.eq(entry.getKey(), entry.getValue()));
		return Filters.and(set);
	}
}
