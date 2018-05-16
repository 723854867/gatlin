package org.gatlin.soa.resource.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.param.SoaSidParam;

public class ResourceModifyParam extends SoaSidParam {

	private static final long serialVersionUID = 1979697622927062997L;

	private String name;
	private String link;
	@Min(0)
	private Integer priority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
