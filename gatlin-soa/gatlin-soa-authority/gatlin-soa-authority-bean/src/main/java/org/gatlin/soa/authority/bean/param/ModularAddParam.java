package org.gatlin.soa.authority.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.StringUtil;

public class ModularAddParam extends SoaParam {

	private static final long serialVersionUID = 2273920592031480392L;

	private String css;
	@NotEmpty
	private String url;
	@NotEmpty
	private String name;
	@Min(0)
	private int priority;
	private Integer parent;
	
	public String getCss() {
		return css;
	}
	
	public void setCss(String css) {
		this.css = css;
	}
	
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
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null == css)
			this.css = StringUtil.EMPTY;
	}
}
