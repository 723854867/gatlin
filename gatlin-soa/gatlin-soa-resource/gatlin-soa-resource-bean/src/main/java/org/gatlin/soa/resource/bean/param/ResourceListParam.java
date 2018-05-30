package org.gatlin.soa.resource.bean.param;

import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.CollectionUtil;

public class ResourceListParam extends SoaParam {

	private static final long serialVersionUID = -7722967233692883158L;

	private Integer id;
	private String name;
	private Integer owner;
	private Set<Integer> cfgIds;

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
	
	public Set<Integer> getCfgIds() {
		return cfgIds;
	}
	
	public void setCfgIds(Set<Integer> cfgIds) {
		this.cfgIds = cfgIds;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != id)
			this.query.eq("id", id);
		if (null != name)
			this.query.like("name", name);
		if (!CollectionUtil.isEmpty(cfgIds))
			this.query.in("cfg_id", cfgIds);
		if (null != owner)
			this.query.eq("owner", owner);
		this.query.orderByAsc("priority");
	}
}
