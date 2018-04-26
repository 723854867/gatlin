package org.gatlin.web.util.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.web.multipart.MultipartFile;

public class UploadParam extends SoaParam {

	private static final long serialVersionUID = 5290464922395781523L;

	private long major;
	@Min(0)
	@Max(255)
	private int priority;
	private int uploadType;
	private int resourceId;
	@NotNull
	private MultipartFile source;
	
	public long getMajor() {
		return major;
	}
	
	public void setMajor(long major) {
		this.major = major;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getUploadType() {
		return uploadType;
	}
	
	public void setUploadType(int uploadType) {
		this.uploadType = uploadType;
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	public MultipartFile getSource() {
		return source;
	}
	
	public void setSource(MultipartFile source) {
		this.source = source;
	}
	
	@Override
	public void verify() {
		super.verify();
	}
}
