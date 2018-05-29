package org.gatlin.util.reflect;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.BeanCol;
import org.gatlin.util.bean.BeanEntity;
import org.gatlin.util.bean.EntityHelper;
import org.gatlin.util.bean.anno.Date;
import org.gatlin.util.bean.model.Pair;

import net.sf.cglib.beans.BeanMap;

public class BeanUtil {

	private static Map<Pair<String, String>, Converter> CACHE = new HashMap<Pair<String, String>, Converter>();
	
	public static String parse(List<?> list, String fieldSeperator, String entitySeperator) {
		StringBuilder builder = new StringBuilder();
		list.forEach(item -> {
			Map<String, Object> map = beanToMap(item, false);
			BeanEntity entity = EntityHelper.getEntity(item.getClass());
			List<BeanCol> cols = entity.getCols();
			cols.sort((o1, o2) -> o1.getScale() - o2.getScale());
			for (BeanCol col : cols) {
				Object value = map.get(col.getField().getName());
				if (null == value)
					continue;
				builder.append(value.toString()).append(fieldSeperator);
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(entitySeperator);
		});
		return builder.deleteCharAt(builder.length() - 1).toString();
	}
	
	public static <T> Map<String, Object> beanToTreeMap(T bean, boolean includeNil) {
		Map<String, Object> map = new TreeMap<String, Object>();
		_wrap(map, BeanMap.create(bean), includeNil);
		return map;
	}
	
	/**
	 * 以 get 方法的返回值为准，而不是变量的真实值
	 * 
	 * @param bean
	 * @param includeNil
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean, boolean includeNil) {
		Map<String, Object> map = new HashMap<String, Object>();
		_wrap(map, BeanMap.create(bean), includeNil);
		return map;
	}
	
	private static void _wrap(Map<String, Object> map, BeanMap beanMap, boolean includeNil) { 
		for (Object key : beanMap.keySet())  {
			Object value = beanMap.get(key);
			if (!includeNil && null == value)
				continue;
			map.put(key.toString(), value);
		}
	}

	/**
	 * 将map装换为javabean对象
	 * 
	 * @param map
	 * @param bean
	 * @return
	 */
	public static <T> T mapToBean(Map<String, String> map, T bean) {
		Map<String, Object> temp = _convert(map, bean);
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(temp);
		return bean;
	}
	
	private static Map<String, Object> _convert(Map<String, String> map, Object bean) {
		Class<?> clazz = bean.getClass();
		String className = clazz.getName();
		Method[] methods = clazz.getMethods();

		Map<String, Object> temp = new HashMap<String, Object>();
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String field = entry.getKey();
			Pair<String, String> pair = new Pair<String, String>(className, field);
			Converter converter = CACHE.get(pair);
			if (null == converter) {
				for (Method method : methods) {
					String methodName = method.getName();
					if (!methodName.startsWith("set") || !method.getName().toUpperCase().equals("SET" + field.toUpperCase()))
						continue;
					Class<?> c = method.getParameterTypes()[0];
					Date date = method.getAnnotation(Date.class);
					if (null != date) {
						converter = new DateConverter(date.javaType(), date.targetType());
						CACHE.put(pair, converter);
						break;
					} else {
						if (c == int.class || c == Integer.class) {
							converter = IntConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == float.class || c == Float.class) {
							converter = FloatConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == long.class || c == Long.class) {
							converter = LongConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == double.class || c == Double.class) {
							converter = DoubleConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == short.class || c == Short.class) {
							converter = ShortConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == byte.class || c == Byte.class) {
							converter = ByteConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == boolean.class || c == Boolean.class) {
							converter = BoolConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == BigDecimal.class) {
							converter = BigDecimalConverter.INSTANCE;
							CACHE.put(pair, converter);
						} else {
							converter = StrConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						}
					}
				}
			}
			if (null != converter)
				temp.put(field, converter.convert(entry.getValue()));
		}
		
		return temp;
	}
	
	private static interface Converter {
		Object convert(String value);
	}
	
	private static class IntConverter implements Converter {
		private static final IntConverter INSTANCE = new IntConverter();
		@Override
		public Object convert(String value) {
			return Integer.parseInt(value);
		}
	}
	
	private static class DoubleConverter implements Converter {
		private static final DoubleConverter INSTANCE = new DoubleConverter();
		@Override
		public Object convert(String value) {
			return Double.parseDouble(value);
		}
	}
	
	private static class LongConverter implements Converter {
		private static final LongConverter INSTANCE = new LongConverter();

		@Override
		public Object convert(String value) {
			return Long.parseLong(value);
		}
	}
	
	private static class FloatConverter implements Converter {
		private static final FloatConverter INSTANCE = new FloatConverter();

		@Override
		public Object convert(String value) {
			return Float.parseFloat(value);
		}
	}
	
	private static class BoolConverter implements Converter {
		private static final BoolConverter INSTANCE = new BoolConverter();

		@Override
		public Object convert(String value) {
			return Boolean.parseBoolean(value);
		}
	}
	
	private static class ByteConverter implements Converter {
		private static final ByteConverter INSTANCE = new ByteConverter();

		@Override
		public Object convert(String value) {
			return Byte.parseByte(value);
		}
	}
	
	private static class ShortConverter implements Converter {
		private static final ShortConverter INSTANCE = new ShortConverter();
		@Override
		public Object convert(String value) {
			return Short.parseShort(value);
		}
	}
	
	private static class StrConverter implements Converter {
		private static final StrConverter INSTANCE = new StrConverter();
		@Override
		public Object convert(String value) {
			return value;
		}
	}
	
	private static class BigDecimalConverter implements Converter {
		private static final BigDecimalConverter INSTANCE = new BigDecimalConverter();
		@Override
		public Object convert(String value) {
			return new BigDecimal(value);
		}
	}
	
	private static class DateConverter implements Converter {
		private String javaType;
		private String targetType;
		public DateConverter(String javaType, String targetType) {
			this.javaType = javaType;
			this.targetType = targetType;
		}
		@Override
		public Object convert(String value) {
			return DateUtil.convert(value, targetType, javaType);
		}
	}
}
