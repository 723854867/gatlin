package org.gatlin.util.io.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gatlin.util.reflect.EntityField;
import org.gatlin.util.reflect.FieldHelper;

class EntityHelper {

	private static final Map<Class<?>, ExcelEntity> entities = new HashMap<Class<?>, ExcelEntity>();

	static ExcelEntity getEntity(Class<?> clazz) {
		synchronized (clazz) {
			ExcelEntity entity = entities.get(clazz);
			if (null != entity) 
				return entity;
			entity = _parseEntity(clazz);
			entities.put(clazz, entity);
			return entity;
		}
	}
	
	private static ExcelEntity _parseEntity(Class<?> clazz) {
		List<EntityField> fields = FieldHelper.getFields(clazz);
		List<ExcelCol> cols = new ArrayList<ExcelCol>();
		fields.forEach(field -> cols.add(new ExcelCol(field)));
		return new ExcelEntity(cols);
	}
}
