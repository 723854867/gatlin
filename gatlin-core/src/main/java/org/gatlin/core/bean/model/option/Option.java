package org.gatlin.core.bean.model.option;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gatlin.core.util.Assert;

@SuppressWarnings("unchecked")
public class Option<VALUE> implements Serializable {

	private static transient final Map<String, Option<?>> options = new ConcurrentHashMap<String, Option<?>>();

	private static final long serialVersionUID = -4013391922070099509L;
	
	protected String key;
	protected VALUE defaultValue;
	protected Class<VALUE> clazz;
	
	protected Option() {}
	
	/**
	 * 子类
	 */
	protected Option(String key) {
		if (null != options.putIfAbsent(key, this))
			throw new RuntimeException("rapid option duplicated, option must be unique!");
		this.key = key;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<VALUE>) generics[0];
	}
	
	/**
	 * 没有默认值
	 */
	protected Option(String key, Class<VALUE> clazz) {
		if (null != options.putIfAbsent(key, this))
			throw new RuntimeException("rapid option duplicated, option must be unique!");
		this.key = key;
		this.clazz = clazz;
	}

	/**
	 * 有默认值
	 */
	public Option(String key, VALUE defaultValue) {
		Assert.notNull(defaultValue);
		if (null != options.putIfAbsent(key, this))
			throw new RuntimeException("rapid option [" + key + "] duplicated, option  must be unique!");
		this.key = key;
		this.defaultValue = defaultValue;
		this.clazz = (Class<VALUE>) defaultValue.getClass();
	}

	public String key() {
		return key;
	}
	
	public Class<VALUE> clazz() {
		return clazz;
	}

	public VALUE defaultValue() {
		return defaultValue;
	}
	
	public void defaultValue(VALUE defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public static final <OPTION extends Option<VALUE>, VALUE> OPTION option(String key) {
		Option<?> option = options.get(key);
		return null == option ? null : (OPTION) option;
	}
}
