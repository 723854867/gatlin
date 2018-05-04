package org.gatlin.soa.resource.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaSidParam;

public class ResourceLinkParam extends SoaSidParam {

	private static final long serialVersionUID = -3289752144676358194L;

	@NotEmpty
	private String link;
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}
