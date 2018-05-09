package org.gatlin.soa.config.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgDistrict implements Entity<String> {

	private static final long serialVersionUID = -6400028238240228608L;

	@Id
	private String code;
	private String name;
	private int level;
	private boolean valid;
	private String abname;
	private String parent;
	private int created;
	private int updated;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getAbname() {
		return abname;
	}

	public void setAbname(String abname) {
		this.abname = abname;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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
	public String key() {
		return this.code;
	}
}
