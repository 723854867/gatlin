package org.gatlin.web.util.bean.param;

import org.gatlin.soa.bean.param.SoaParam;

public class ResourceSearcher extends SoaParam {

	private static final long serialVersionUID = -7722967233692883158L;

	private Integer id;
	private String name;
	private Integer owner;
	private Integer cfgResourceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	
	public Integer getCfgResourceId() {
		return cfgResourceId;
	}
	
	public void setCfgResourceId(Integer cfgResourceId) {
		this.cfgResourceId = cfgResourceId;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != id)
			this.query.eq("id", id);
		if (null != name)
			this.query.like("name", name);
		if (null != cfgResourceId)
			this.query.eq("cfg_id", cfgResourceId);
		if (null != owner)
			this.query.eq("owner", owner);
		this.query.orderByAsc("priority");
	}
}
