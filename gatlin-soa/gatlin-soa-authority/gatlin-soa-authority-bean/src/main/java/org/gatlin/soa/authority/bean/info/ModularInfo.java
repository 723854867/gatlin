package org.gatlin.soa.authority.bean.info;

import java.io.Serializable;

import javax.persistence.Id;

import org.gatlin.soa.authority.bean.entity.CfgModular;

public class ModularInfo implements Serializable {

	private static final long serialVersionUID = 484229075835506408L;

	@Id
	private int id;
	private String url;
	private String name;
	private int priority;
	private int parent;
	
	public ModularInfo() {}
	
	public ModularInfo(CfgModular modular) {
		this.id = modular.getId();
		this.url = modular.getUrl();
		this.name = modular.getName();
		this.priority = modular.getPriority();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
}
