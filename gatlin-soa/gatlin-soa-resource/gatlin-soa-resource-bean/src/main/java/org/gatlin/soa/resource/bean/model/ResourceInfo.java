package org.gatlin.soa.resource.bean.model;

import java.io.Serializable;

import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.entity.ResourceRoute;

public class ResourceInfo implements Serializable {

	private static final long serialVersionUID = -8713101241024832832L;

	private String id;
	private int cfgId;
	private String url;
	private String name;
	private String link;
	private int priority;
	private int created;
	
	public ResourceInfo() {}
	
	public ResourceInfo(Resource resource, ResourceRoute route) {
		this.id = resource.getId();
		this.cfgId = resource.getCfgId();
		this.url = resource.getUrl();
		this.name = resource.getName();
		this.priority = resource.getPriority();
		this.created = resource.getCreated();
		if (null != route)
			this.link = route.getLink();
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
