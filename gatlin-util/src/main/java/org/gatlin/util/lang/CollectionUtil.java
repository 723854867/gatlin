package org.gatlin.util.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {
	
	public static final <T> List<T> emptyList() {
		return new ArrayList<T>();
	}
	
	public static final <T, K> Map<T, K> emptyMap() {
		return new HashMap<T, K>();
	}
	
	public static final boolean isEmpty(Map<?, ?> map) { 
		return null == map || map.isEmpty();
	}
	
	public static final boolean isEmpty(Collection<?> collection) { 
		return null == collection || collection.isEmpty();
	}
	
	public static final Set<String> convertToSet(String... params) {
		Set<String> set = new HashSet<String>();
		for (String object : params)
			set.add(object);
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> Set<T> convertToSet(Object... params) {
		Set<T> set = new HashSet<T>();
		for (Object object : params)
			set.add((T) object);
		return set;
	}
	
	public static final Set<Long> splitIntoLongSet(String value, String regex) {
		Set<Long> set = new HashSet<Long>();
		if (StringUtil.hasText(value)) {
			String[] arr = value.split(regex);
			for (String string : arr)
				set.add(Long.valueOf(string));
		}
		return set;
	}
	
	public static final Set<String> splitIntoStringSet(String value, String regex) {
		Set<String> set = new HashSet<String>();
		if (StringUtil.hasText(value)) {
			String[] arr = value.split(regex);
			for (String string : arr)
				set.add(string);
		}
		return set;
	}
	
	public static final Set<Integer> splitIntoIntegerSet(String value, String regex) {
		Set<Integer> set = new HashSet<Integer>();
		if (StringUtil.hasText(value)) {
			String[] arr = value.split(regex);
			for (String string : arr)
				set.add(Integer.valueOf(string));
		}
		return set;
	}
}
