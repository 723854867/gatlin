package org.gatlin.soa.jpush.bean.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.gatlin.util.bean.Identifiable;

/**
 * 极光设备
 * 
 * @author lynn
 */
@Table(name = "jpush_device")
public class JPushDevice implements Identifiable<String> {

	private static final long serialVersionUID = 2174235765882856037L;

	@Id
	private String id;
	private Long uid;
	private String alias;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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
		return this.id;
	}
}
