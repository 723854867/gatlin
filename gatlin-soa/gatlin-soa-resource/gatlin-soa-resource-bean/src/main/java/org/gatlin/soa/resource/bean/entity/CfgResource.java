package org.gatlin.soa.resource.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.bean.enums.CacheUnit;

public class CfgResource implements Identifiable<Integer> {

	private static final long serialVersionUID = 429288989882702100L;

	@Id
	private int id;
	private int type;
	private String name;
	private int minimum;
	private int maximum;
	private int cacheSize;
	private String directory;
	private CacheUnit cacheUnit;
	private int created;
	private int updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
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

	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public CacheUnit getCacheUnit() {
		return cacheUnit;
	}
	
	public void setCacheUnit(CacheUnit cacheUnit) {
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
