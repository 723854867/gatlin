package org.gatlin.soa.bean.param;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SoaLidsParam extends SoaParam {

	private static final long serialVersionUID = -8031826777702184338L;

	@NotEmpty
	@Size(min = 1)
	private Set<Long> ids;
	
	public Set<Long> getIds() {
		return ids;
	}
	
	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}
}
