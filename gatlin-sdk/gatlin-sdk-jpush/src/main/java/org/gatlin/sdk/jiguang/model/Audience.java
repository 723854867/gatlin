package org.gatlin.sdk.jiguang.model;

import java.util.HashMap;

public class Audience extends HashMap<String, String[]> {

	private static final long serialVersionUID = -2355415795398468848L;
	
	public Audience tag(String...tags) {
		this.put("tag", tags);
		return this;
	}
	
	public Audience tagAnd(String...tags) {
		this.put("tag_and", tags);
		return this;
	}
	
	public Audience tagNot(String...tags) {
		this.put("tag_not", tags);
		return this;
	}
	
	public Audience alias(String...alias) {
		this.put("alias", alias);
		return this;
	}
	
	public Audience segment(String...segments) {
		this.put("segment", segments);
		return this;
	}
	
	public Audience abtest(String...abtests) {
		this.put("abtest", abtests);
		return this;
	}
	
	public Audience registrationId(String...registrationIds) {
		this.put("registration_id", registrationIds);
		return this;
	}
}
