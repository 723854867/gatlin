package org.gatlin.soa.authority.bean.param;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;

public class RoleAddParam extends SoaParam {

	private static final long serialVersionUID = -2697177843839151725L;

	@NotEmpty
	private String name;
	
	@NotNull
	private Set<Integer> ids;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Integer> getIds() {
		return ids;
	}
	
	public void setIds(Set<Integer> ids) {
		this.ids = ids;
	}
}
