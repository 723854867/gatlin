package org.gatlin.web.util.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.web.multipart.MultipartFile;

public class ResourceUploadParam extends SoaParam {

	private static final long serialVersionUID = -4042539380420527261L;

	private int id;
	private long owner;
	@Min(0)
	private String name;
	@Min(0)
	private Integer priority;
	private int cfgResourceId;
	private MultipartFile source;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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

	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
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
