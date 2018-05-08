package org.gatlin.soa.authority.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaIdParam;

public class ModularModifyParam extends SoaIdParam {

	private static final long serialVersionUID = 2388816560144542705L;

	@NotEmpty
	private String url;
	@NotEmpty
	private String name;
	private String css;
	@Min(0)
	private int priority;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCss() {
		return css;
	}
	
	public void setCss(String css) {
		this.css = css;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
