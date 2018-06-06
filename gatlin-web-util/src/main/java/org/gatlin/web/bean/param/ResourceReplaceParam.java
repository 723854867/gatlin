package org.gatlin.web.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.util.lang.StringUtil;
import org.springframework.web.multipart.MultipartFile;

public class ResourceReplaceParam extends SoaSidParam {

	private static final long serialVersionUID = 4099609243779018993L;

	private String name;
	private String link;
	@Min(0)
	private int priority;
	@NotNull
	private MultipartFile source;

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

	public MultipartFile getSource() {
		return source;
	}

	public void setSource(MultipartFile source) {
		this.source = source;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (!StringUtil.hasText(name))
			this.name = source.getOriginalFilename();
	}
}
