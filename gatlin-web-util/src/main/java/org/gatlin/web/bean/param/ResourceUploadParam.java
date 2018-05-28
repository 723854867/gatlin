package org.gatlin.web.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.web.multipart.MultipartFile;

public class ResourceUploadParam extends SoaParam {

	private static final long serialVersionUID = -4042539380420527261L;

	private String id;
	private String owner;
	@NotEmpty
	private String name;
	private String link;
	@Min(0)
	private int priority;
	@Min(0)
	private int cfgResourceId;
	@NotNull
	private MultipartFile source;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
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
