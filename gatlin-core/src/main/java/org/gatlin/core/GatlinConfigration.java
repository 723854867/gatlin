package org.gatlin.core;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.core.util.Assert;
import org.gatlin.util.Consts;
import org.gatlin.util.lang.StringUtil;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class GatlinConfigration {
	
	private static final Map<String, String> properties = new HashMap<String, String>();
	public static final ConfigurableConversionService CONVERSION_SERVICE = new DefaultConversionService();
	public static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();
	
	static {
		loadProperties("classpath*:conf/*.properties");
	}
	
	public static final void loadProperties(String path) { 
		try {
			Resource[] resources = RESOLVER.getResources(path);
			for (Resource resource : resources) {
				InputStream in = resource.getInputStream();
				Properties properties = new Properties();
				properties.load(new InputStreamReader(in, Consts.UTF_8));
				for (Entry<Object, Object> entry : properties.entrySet()) {
					String value = entry.getValue().toString();
					String cvalue = GatlinConfigration.properties.get(entry.getKey().toString());
					if (StringUtil.hasText(cvalue))
						value = cvalue + "," + value;
					GatlinConfigration.properties.put(entry.getKey().toString(), value);
				}
				in.close();
			}
		} catch (Exception e) {
			throw new CodeException(e);
		}
	}
	
	public static final Properties getProperties(String path) { 
		try {
			Resource resource = RESOLVER.getResource(path);
			InputStream in = resource.getInputStream();
			Properties properties = new Properties();
			properties.load(new InputStreamReader(in, Consts.UTF_8));
			in.close();
			return properties;
		} catch (Exception e) {
			throw new CodeException(e);
		}
	}
	
	public static final <T> T get(Option<T> option) {
		String property = properties.get(option.key());
		T value = null == property ? option.defaultValue() : CONVERSION_SERVICE.convert(property, option.clazz());
		return Assert.notNull("property [" + option.key() + "] is no specified!", value);
	}
	
	public static final String get(String key) {
		return get(key, String.class);
	}
	
	public static final String get(String key, String defaultValue) {
		return get(key, String.class, defaultValue);
	}
	
	public static final <T> T get(String key, Class<T> clazz) {
		String property = properties.get(key);
		T value = null == property ? null : CONVERSION_SERVICE.convert(property, clazz);
		return Assert.notNull("property [" + key + "] is no specified!", value);
	}
	
	public static final <T> T get(String key, Class<T> clazz, T defaultValue) {
		String property = properties.get(key);
		return null == property ? defaultValue : CONVERSION_SERVICE.convert(property, clazz);
	}
	
	public static final Resource[] getResources(String locationPattern) {
		try {
			return RESOLVER.getResources(locationPattern);
		} catch (Exception e) {
			throw new CodeException(e);
		}
	}
}
