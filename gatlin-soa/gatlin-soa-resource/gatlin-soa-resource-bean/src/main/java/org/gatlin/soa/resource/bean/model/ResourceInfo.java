package org.gatlin.soa.resource.bean.model;

import java.io.Serializable;

import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;

public class ResourceInfo implements Serializable {

	private static final long serialVersionUID = -8713101241024832832L;

	private int type;
	private String id;
	private int cfgId;
	private String url;
	private String name;
	private String link;
	private String owner;
	private int priority;
	private int created;
	
	public ResourceInfo() {}
	
	public ResourceInfo(Resource resource, CfgResource cfgResource) {
		this.type = cfgResource.getType();
		this.id = resource.getId();
		this.cfgId = resource.getCfgId();
		this.url = resource.getUrl();
		this.name = resource.getName();
		this.link = resource.getLink();
		this.owner = resource.getOwner();
		this.priority = resource.getPriority();
		this.created = resource.getCreated();
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
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
}
