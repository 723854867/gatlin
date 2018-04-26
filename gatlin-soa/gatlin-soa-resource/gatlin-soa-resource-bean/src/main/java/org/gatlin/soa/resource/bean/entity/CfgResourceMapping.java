package org.gatlin.soa.resource.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 资源上传引用
 * 
 * @author lynn
 */
public class CfgResourceMapping implements Entity<Integer> {

	private static final long serialVersionUID = 7575887375520827560L;

	@Id
	@GeneratedValue
	private int id;
	private int tid;
	private int type;
	private int groupId;
	private int created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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
