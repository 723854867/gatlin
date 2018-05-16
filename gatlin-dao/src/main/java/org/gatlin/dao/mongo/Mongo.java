package org.gatlin.dao.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.Paginate;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.DaoConsts.Options;
import org.gatlin.dao.bean.enums.Comparison;
import org.gatlin.dao.bean.model.Condition;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.bean.model.Pair;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
@Conditional(MongoCondition.class)
public class Mongo {

	private MongoClient mongo;
	private MongoDatabase connection;
	
	@PostConstruct
	public void init() {
		String db = GatlinConfigration.get(Options.MONGO_DB);
		String username = GatlinConfigration.get(Options.MONGO_DB);
		String password = GatlinConfigration.get(Options.MONGO_PASSWORD);
		if (StringUtil.hasText(username) && StringUtil.hasText(password)) {
			MongoCredential credential = MongoCredential.createCredential(username, db, password.toCharArray());
			ServerAddress address = new ServerAddress(GatlinConfigration.get(Options.MONGO_HOST));
			this.mongo = new MongoClient(address, credential, MongoClientOptions.builder().build());
		} else 
			this.mongo = new MongoClient(GatlinConfigration.get(Options.MONGO_HOST));
		this.connection = mongo.getDatabase(db);
	}
	
	public void insertOne(String collectionName, Object object) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.insertOne(serial(object));
	}
	
	public void insertMany(String collectionName, Collection<?> objects) {
		MongoCollection<Document> collection = collection(collectionName);
		List<Document> list = new ArrayList<Document>(objects.size());
		for (Object object : objects)
			list.add(serial(object));
		collection.insertMany(list);
	}
	
	public <T> List<T> find(String collectionName, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find();
		List<T> list = new ArrayList<T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <T> List<T> find(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		List<T> list = new ArrayList<T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <KEY, T extends Identifiable<KEY>> Map<KEY, T> findMap(String collectionName, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find();
		Map<KEY, T> map = new HashMap<KEY, T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			T t = SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz);
			map.put(t.key(), t);
		}
		return map;
	}
	
	public <KEY, T extends Identifiable<KEY>> Map<KEY, T> findMap(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		Map<KEY, T> map = new HashMap<KEY, T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			T t = SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz);
			map.put(t.key(), t);
		}
		return map;
	}
	
	public void bulkUpdateOne(String collectionName, Map<Bson, Bson> updates) {
		MongoCollection<Document> collection = collection(collectionName);
		List<UpdateOneModel<Document>> list = new ArrayList<UpdateOneModel<Document>>(updates.size());
		for (java.util.Map.Entry<Bson, Bson> entry : updates.entrySet())
			list.add(new UpdateOneModel<Document>(entry.getKey(), entry.getValue()));
		collection.bulkWrite(list);
	}
	
	public <KEY, MODEL extends Identifiable<KEY>> long bulkReplaceOne(String collectionName, Map<KEY, MODEL> replaces) {
		MongoCollection<Document> collection = collection(collectionName);
		List<ReplaceOneModel<Document>> list = new ArrayList<ReplaceOneModel<Document>>(replaces.size());
		for (MODEL model : replaces.values()) 
			list.add(new ReplaceOneModel<Document>(Filters.eq("_id", model.key()), serial(model), new UpdateOptions().upsert(true)));
		BulkWriteResult result = collection.bulkWrite(list);
		return result.getModifiedCount();
	}
	
	/**
	 * 分页显示：不排序
	 */
	public <T> List<T> paging(String collectionName, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find().skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	/**
	 * 分页显示：不排序
	 */
	public <T> List<T> paging(String collectionName, Bson filter, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	/**
	 * 分页显示：排序
	 */
	public <T> List<T> pagingAndSort(String collectionName, Bson sort, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find().sort(sort).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <T> Paginate<T> query(String collectionName, Query query, Class<T> clazz) {
		MongoCollection<Document> collection = collection(collectionName);
		List<Condition> conditions = query.getConditions();
		List<Bson> filters = new ArrayList<Bson>();
		for (Condition condition : conditions) {
			Comparison comparison = Comparison.match(condition.getComparison());
			switch (comparison) {
			case lt:
				filters.add(Filters.lt(condition.getCol(), condition.getValue()));
				break;
			case lte:
				filters.add(Filters.lte(condition.getCol(), condition.getValue()));
				break;
			case gt:
				filters.add(Filters.gt(condition.getCol(), condition.getValue()));
				break;
			case gte:
				filters.add(Filters.gte(condition.getCol(), condition.getValue()));
				break;
			case eq:
				filters.add(Filters.eq(condition.getCol(), condition.getValue()));
				break;
			case neq:
				filters.add(Filters.ne(condition.getCol(), condition.getValue()));
				break;
			case like:
				filters.add(Filters.regex(condition.getCol(), condition.getValue().toString()));
				break;
			case in:
				filters.add(Filters.in(condition.getCol(), condition.getValue()));
				break;
			case nin:
				filters.add(Filters.nin(condition.getCol(), condition.getValue()));
				break;
			case all:
				filters.add(Filters.all(condition.getCol(), condition.getValue()));
				break;
			default:
				break;
			}
		}
		Paginate<T> paginate = new Paginate<T>();
		Bson filter = CollectionUtil.isEmpty(filters) ? null : Filters.and(filters);
		long total = null == filter ? collection.count() : collection.count(filter);
		if (total <= 0)
			return paginate;
		paginate.calculate(query.getPage(), query.getPageSize(), total);
		FindIterable<Document> iterable = null == filter ? collection.find() : collection.find(filter);
		List<Pair<String, Boolean>> orders = query.getOrderBys();
		if (!CollectionUtil.isEmpty(orders)) {
			for (Pair<String, Boolean> pair : orders) 
				iterable.sort(pair.getValue() ? Sorts.ascending(pair.getKey()) : Sorts.descending(pair.getKey()));
		}
		iterable.skip(paginate.getPageStart()).limit(query.getPageSize()).batchSize(50);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			paginate.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return paginate;
	}
	
	/**
	 * 分页显示：排序
	 */
	public <T> List<T> pagingAndSort(String collectionName, Bson filter, Bson sort, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).sort(sort).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public long count(String collectionName) {
		MongoCollection<Document> collection = collection(collectionName);
		return collection.count();
	}
	
	public long count(String collectionName, Bson filter) {
		MongoCollection<Document> collection = collection(collectionName);
		return collection.count(filter);
	}
	
	public <T> T findOne(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		Document document = iterable.first();
		return null == document ? null : deserial(document, clazz);
	}
	
	public <T> T findOne(String collectionName, Bson filter, Bson sort, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).sort(sort);
		Document document = iterable.first();
		return null == document ? null : deserial(document, clazz);
	}
	
	public long replaceOne(String collectionName, Bson filter, Object replacement) { 
		MongoCollection<Document> collection = collection(collectionName);
		UpdateResult result = collection.replaceOne(filter, serial(replacement));
		Assert.isTrue(result.getModifiedCount() <= 1);
		return result.getModifiedCount();
	}
	
	public void replaceOne(String collectionName, Bson filter, Object replacement, UpdateOptions options) {
		MongoCollection<Document> collection = collection(collectionName);
		collection.replaceOne(filter, serial(replacement), options);
	}
	
	public <T> T findOneAndUpdate(String collectionName, Bson filter, Bson update, FindOneAndUpdateOptions options, Class<T> clazz) { 
		MongoCollection<Document> collection = collection(collectionName);
		Document document = collection.findOneAndUpdate(filter, update, options);
		return null == document ? null : deserial(document, clazz);
	}
	
	public void update(String collectionName, Bson filter, Bson update) {
		MongoCollection<Document> collection = collection(collectionName);
		collection.updateMany(filter, update);
	}
	
	public long deleteMany(String collectionName, Bson filter) {
		MongoCollection<Document> collection = collection(collectionName);
		DeleteResult result = collection.deleteMany(filter);
		return result.getDeletedCount();
	}
	
	public long deleteOne(String collectionName, Bson filter) {
		MongoCollection<Document> collection = collection(collectionName);
		DeleteResult result = collection.deleteOne(filter);
		Assert.isTrue(result.getDeletedCount() <= 1);
		return result.getDeletedCount();
	}
	
	public Document serial(Object model) {
		String json = SerializeUtil.GSON.toJson(model);
		return Document.parse(json);
	}
	
	public <T> T deserial(Document document, Class<T> clazz) {
		return SerializeUtil.GSON.fromJson(document.toJson(), clazz);
	}
	
	public MongoCollection<Document> collection(String collectionName) {
		return connection.getCollection(collectionName);
	}
}
