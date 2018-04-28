package org.gatlin.web.util.bean.param;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.param.SoaParam;

public class ResourceSearcher extends SoaParam {

	private static final long serialVersionUID = -7722967233692883158L;

	private Integer id;
	private Integer type;
	private Integer owner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}
}
