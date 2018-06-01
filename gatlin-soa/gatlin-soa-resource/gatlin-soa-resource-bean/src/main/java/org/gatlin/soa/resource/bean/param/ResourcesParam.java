package org.gatlin.soa.resource.bean.param;

import java.util.HashSet;
import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;

public class ResourcesParam extends SoaParam {

	private static final long serialVersionUID = -7722967233692883158L;

	private Integer id;
	private String name;
	private Integer type;
	private Set<String> owners;
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
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Set<String> getOwners() {
		return owners;
	}
	
	public void setOwners(Set<String> owners) {
		this.owners = owners;
	}
	
	public Set<Integer> getCfgIds() {
		return cfgIds;
	}
	
	public void setCfgIds(Set<Integer> cfgIds) {
		this.cfgIds = cfgIds;
	}
	
	public void addOwner(String owner) {
		if (null == owners)
			this.owners = new HashSet<String>();
		this.owners.add(owner);
	}
	
	public void addCfgId(int cfgId) {
		if (null == cfgIds)
			this.cfgIds = new HashSet<Integer>();
		this.cfgIds.add(cfgId);
	}
	
	@Override
	public void verify() {
		super.verify();
	}
}
