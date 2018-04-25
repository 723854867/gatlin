package org.gatlin.web.util.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.web.multipart.MultipartFile;

public class UploadParam extends SoaParam {

	private static final long serialVersionUID = 5290464922395781523L;

	@Min(0)
	@Max(255)
	private int priority;
	private String linkUrl;
	@NotNull
	private MultipartFile file;
	private MultipartFile link;
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String getLinkUrl() {
		return linkUrl;
	}
	
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public MultipartFile getLink() {
		return link;
	}
	
	public void setLink(MultipartFile link) {
		this.link = link;
	}
	
	@Override
	public void verify() {
		super.verify();
	}
}
