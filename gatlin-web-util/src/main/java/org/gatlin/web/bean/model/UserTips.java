package org.gatlin.web.bean.model;

import java.io.Serializable;

import org.gatlin.soa.bean.User;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.user.bean.entity.UserInfo;

public class UserTips implements Serializable {

	private static final long serialVersionUID = 8604335739915886591L;

	private long id;
	private int created;
	private String nickname;
	private ResourceInfo avatar;
	
	public UserTips() {}
	
	public UserTips(UserInfo user) {
		this.id = user.getId();
		this.created = user.getCreated();
		this.nickname = user.getNickname();
	}
	
	public UserTips(User user, ResourceInfo resource) {
		this.id = user.getId();
		this.avatar = resource;
		this.created = user.getCreated();
		this.nickname = user.getNickname();
	}
	
	public UserTips(UserInfo user, ResourceInfo resource) {
		this.id = user.getId();
		this.avatar = resource;
		this.created = user.getCreated();
		this.nickname = user.getNickname();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getCreated() {
		return created;
	}
	
	public void setCreated(int created) {
		this.created = created;
	}
	
	public ResourceInfo getAvatar() {
		return avatar;
	}
	
	public void setAvatar(ResourceInfo avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
