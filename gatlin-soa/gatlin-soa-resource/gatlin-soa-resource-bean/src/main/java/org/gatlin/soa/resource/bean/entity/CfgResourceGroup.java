package org.gatlin.soa.resource.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgResourceGroup implements Entity<Integer> {

	private static final long serialVersionUID = 467120302120809297L;

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int created;
	private int updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return this.id;
	}
}
