package org.gatlin.soa.user.bean.info;

import java.io.Serializable;

import org.gatlin.soa.user.bean.entity.UserInfo;

public class UserTips implements Serializable {

	private static final long serialVersionUID = 9013229915480608208L;

	private long uid;
	private String avatar;
	private String nickname;
	
	public UserTips() {}
	
	public UserTips(UserInfo info) {
		this.uid = info.getId();
		this.avatar = info.getAvatar();
		this.nickname = info.getNickname();
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
