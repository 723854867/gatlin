package org.gatlin.soa.bean.param;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SoaIdsParam extends SoaParam {

	private static final long serialVersionUID = 746916206073213544L;

	@NotEmpty
	@Size(min = 1)
	private Set<Integer> ids;
	
	public Set<Integer> getIds() {
		return ids;
	}
	
	public void setIds(Set<Integer> ids) {
		this.ids = ids;
	}
}
