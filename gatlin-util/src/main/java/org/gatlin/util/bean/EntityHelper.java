package org.gatlin.util.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gatlin.util.reflect.EntityField;
import org.gatlin.util.reflect.FieldHelper;

public class EntityHelper {

	private static final Map<Class<?>, BeanEntity> entities = new HashMap<Class<?>, BeanEntity>();

	public static final BeanEntity getEntity(Class<?> clazz) {
		synchronized (clazz) {
			BeanEntity entity = entities.get(clazz);
			if (null != entity) 
				return entity;
			entity = _parseEntity(clazz);
			entities.put(clazz, entity);
			return entity;
		}
	}
	
	private static BeanEntity _parseEntity(Class<?> clazz) {
		List<EntityField> fields = FieldHelper.getFields(clazz);
		List<BeanCol> cols = new ArrayList<BeanCol>();
		fields.forEach(field -> cols.add(new BeanCol(field)));
		return new BeanEntity(cols);
	}
}
