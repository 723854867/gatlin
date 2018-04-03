package org.gatlin.soa.config.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgGlobal implements Entity<String> {

	private static final long serialVersionUID = 276816685147658470L;

	@Id
	private String key;
	private String value;
	private String desc;
	private int type;
	private boolean visible;
	private int created;
	private int updated;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
		return this.key;
	}
}
