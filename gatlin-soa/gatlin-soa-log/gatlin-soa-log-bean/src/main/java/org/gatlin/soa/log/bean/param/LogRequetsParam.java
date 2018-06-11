package org.gatlin.soa.log.bean.param;

import java.util.Map;
import java.util.Map.Entry;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.CollectionUtil;

public class LogRequetsParam extends SoaParam {

	private static final long serialVersionUID = 4130521588598567409L;

	private String ip;
	private String id;
	private String desc;
	private String path;
	private Integer end;
	private Integer start;
	private Map<String, String> conditions;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	public Map<String, String> getConditions() {
		return conditions;
	}
	
	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	@Override
	public void verify() {
		super.verify();
		if (null != ip)
			this.query.eq("ip", ip);
		if (null != id)
			this.query.eq("id", id);
		if (null != desc)
			this.query.like("desc", desc);
		if (null != path)
			this.query.like("path", path);
		if (null != end)
			this.query.lte("created", end);
		if (null != start)
			this.query.gte("created", start);
		this.query.orderByDesc("created");
		if (!CollectionUtil.isEmpty(conditions)) {
			for (Entry<String, String> entry : conditions.entrySet()) 
				this.query.eq(entry.getKey(), entry.getValue());
		}
	}
}
