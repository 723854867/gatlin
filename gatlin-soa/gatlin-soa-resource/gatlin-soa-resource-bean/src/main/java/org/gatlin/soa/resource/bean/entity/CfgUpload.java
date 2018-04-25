package org.gatlin.soa.resource.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 资源上传配置
 * 
 * @author lynn
 */
public class CfgUpload implements Entity<Integer> {

	private static final long serialVersionUID = 7786898347611801753L;

	@Id
	// 上传类型
	private int type;
	// 引用类型名，如果为空则直接使用 CfgResource 的name
	private String name;
	private int created;
	private int updated;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	public Integer key() {
		return this.type;
	}
}
