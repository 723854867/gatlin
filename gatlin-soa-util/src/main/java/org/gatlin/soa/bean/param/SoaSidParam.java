package org.gatlin.soa.bean.param;

import javax.validation.constraints.NotEmpty;

public class SoaSidParam extends SoaParam {

	private static final long serialVersionUID = 8149472965992837861L;

	@NotEmpty
	private String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
