package org.gatlin.soa.resource.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class Resource implements Entity<String> {

	private static final long serialVersionUID = 4984065305004886040L;

	@Id
	private String id;
	private int cfgId;
	private long owner;
	private long bytes;
	private String url;
	private String name;
	private String path;
	private int priority;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public int getCfgId() {
		return cfgId;
	}

	public void setCfgId(int cfgId) {
		this.cfgId = cfgId;
	}
	
	public long getOwner() {
		return owner;
	}
	
	public void setOwner(long owner) {
		this.owner = owner;
	}

	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}
	
	public int getUpdated() {
		return updated;
	}
	
	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public String key() {
		return this.id;
	}
}
