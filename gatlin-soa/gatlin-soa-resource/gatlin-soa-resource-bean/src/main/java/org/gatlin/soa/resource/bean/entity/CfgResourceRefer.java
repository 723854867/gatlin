package org.gatlin.soa.resource.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 资源上传引用
 * 
 * @author lynn
 */
public class CfgResourceRefer implements Entity<Integer> {

	private static final long serialVersionUID = 7575887375520827560L;

	@Id
	@GeneratedValue
	private int id;
	private int uploadType;
	private int resourceId;
	private int created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public int getCreated() {
		return created;
	}
	
	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public Integer key() {
		return this.id;
	}
}
