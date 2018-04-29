package org.gatlin.web.util.bean.param;

import org.gatlin.soa.bean.param.SoaParam;

public class ResourceSearcher extends SoaParam {

	private static final long serialVersionUID = -7722967233692883158L;

	private Integer id;
	private String name;
	private Integer cfgId;
	private Integer owner;

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

	public Integer getCfgId() {
		return cfgId;
	}
	
	public void setCfgId(Integer cfgId) {
		this.cfgId = cfgId;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != id)
			this.query.eq("id", id);
		if (null != name)
			this.query.like("name", name);
		if (null != cfgId)
			this.query.eq("cfg_id", cfgId);
		if (null != owner)
			this.query.eq("owner", owner);
		this.query.orderByAsc("priority");
	}
}
