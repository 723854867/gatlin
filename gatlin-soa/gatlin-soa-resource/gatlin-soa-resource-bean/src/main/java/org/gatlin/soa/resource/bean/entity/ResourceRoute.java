package org.gatlin.soa.resource.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class ResourceRoute implements Entity<String> {

	private static final long serialVersionUID = -8193627536431815031L;
	
	@Id
	private String resourceId;
	private String link;
	private int created;

	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return this.resourceId;
	}
}
