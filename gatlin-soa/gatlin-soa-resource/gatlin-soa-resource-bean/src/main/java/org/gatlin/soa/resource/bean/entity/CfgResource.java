package org.gatlin.soa.resource.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgResource implements Entity<Integer> {

	private static final long serialVersionUID = 429288989882702100L;

	@Id
	private int id;
	private String name;
	private int minimum;
	private int maximum;
	private int cacheSize;
	private String cacheUnit;
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

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public String getCacheUnit() {
		return cacheUnit;
	}

	public void setCacheUnit(String cacheUnit) {
		this.cacheUnit = cacheUnit;
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
