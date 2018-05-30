package org.gatlin.soa.config.bean.model;

import java.io.Serializable;

import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.config.bean.entity.CfgBank;

public class BankInfo implements Serializable {

	private static final long serialVersionUID = 6545383702075671136L;

	private String id;
	private String name;
	private boolean valid;
	private int created;
	private ResourceInfo icon;
	
	public BankInfo() {}
	
	public BankInfo(CfgBank bank, ResourceInfo icon) {
		this.id = bank.getId();
		this.name = bank.getName();
		this.valid = bank.isValid();
		this.created = bank.getCreated();
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public ResourceInfo getIcon() {
		return icon;
	}

	public void setIcon(ResourceInfo icon) {
		this.icon = icon;
	}
}
