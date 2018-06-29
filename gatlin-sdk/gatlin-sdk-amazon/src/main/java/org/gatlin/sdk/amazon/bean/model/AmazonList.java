package org.gatlin.sdk.amazon.bean.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.gatlin.sdk.amazon.AmazonUtil;

public class AmazonList<E extends HashMap<String, String>> extends ArrayList<E> {

	private static final long serialVersionUID = 4462861927372174803L;
	
	private String prefix;
	
	public AmazonList() {
		this("member");
	}

	public AmazonList(String prefix) {
		this.prefix = prefix;
	}
	
	public Map<String, String> params(String prefix) {
		Map<String, String> params = new HashMap<String, String>();
		for (int idx = 1; idx <= size(); idx++) {
			E ele = get(idx - 1);
			params.putAll(AmazonUtil.wrap(prefix + "." + this.prefix + "." + idx, ele));
		}
		return params;
	}
}
