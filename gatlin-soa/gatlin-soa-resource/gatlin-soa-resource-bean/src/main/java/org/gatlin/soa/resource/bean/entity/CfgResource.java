package org.gatlin.soa.resource.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 资源配置
 * 
 * @author lynn
 */
public class CfgResource implements Entity<Integer> {

	private static final long serialVersionUID = 7786898347611801753L;

	@Id
	@GeneratedValue
	private int id;
	// 资源名
	private String name;
	// 上传最低数量限制
	private int minimumNum;
	// 上传最大数量限制
	private int maximumNum;
	// 单个资源大小限制
	private int cacheSize;
	// 资源大小单位
	private String cacheUnit;
	private int created;
	private int updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinimumNum() {
		return minimumNum;
	}

	public void setMinimumNum(int minimumNum) {
		this.minimumNum = minimumNum;
	}

	public int getMaximumNum() {
		return maximumNum;
	}

	public void setMaximumNum(int maximumNum) {
		this.maximumNum = maximumNum;
	}

	public int getCacheSize() {
		return cacheSize;
	}
	
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	public String getCacheUnit() {
		return cacheUnit;
	}
	
	public void setCacheUnit(String cacheUnit) {
		this.cacheUnit = cacheUnit;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public Integer key() {
		return this.id;
	}
}
