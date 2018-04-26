package org.gatlin.soa.resource.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class PubResource implements Entity<Long> {

	private static final long serialVersionUID = 4984065305004886040L;

	@Id
	@GeneratedValue
	private long id;
	private String url;
	private String path;
	private int uploadType;
	private int resourceId;
	private int priority;
	private long owner;
	private Long major;
	private String link;
	private int created;
	private int updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public void setPath(String path) {
		this.path = path;
	}

	public int getUploadType() {
		return uploadType;
	}

	public void setUploadType(int uploadType) {
		this.uploadType = uploadType;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public long getOwner() {
		return owner;
	}
	
	public void setOwner(long owner) {
		this.owner = owner;
	}
	
	public Long getMajor() {
		return major;
	}
	
	public void setMajor(Long major) {
		this.major = major;
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

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public Long key() {
		return this.id;
	}
}
