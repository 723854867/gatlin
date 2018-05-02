package org.gatlin.web.util.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.web.multipart.MultipartFile;

public class ResourceUploadParam extends SoaParam {

	private static final long serialVersionUID = -4042539380420527261L;

	private long owner;
	@NotEmpty
	private String name;
	@Min(0)
	private int priority;
	@Min(0)
	private int cfgResourceId;
	@NotNull
	private MultipartFile source;
	
	public long getOwner() {
		return owner;
	}
	
	public void setOwner(long owner) {
		this.owner = owner;
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
	
	public int getCfgResourceId() {
		return cfgResourceId;
	}
	
	public void setCfgResourceId(int cfgResourceId) {
		this.cfgResourceId = cfgResourceId;
	}
	
	public MultipartFile getSource() {
		return source;
	}
	
	public void setSource(MultipartFile source) {
		this.source = source;
	}
}
