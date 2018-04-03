package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

import org.gatlin.soa.user.bean.entity.UserInvitation;

public class RegisterModel implements Serializable {

	private static final long serialVersionUID = 6446583124767668550L;

	private long uid;
	private UserInvitation invitation;
	
	public RegisterModel() {}
	
	public RegisterModel(long uid, UserInvitation invitation) {
		this.uid = uid;
		this.invitation = invitation;
	}
	
	public long getUid() {
		return uid;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public UserInvitation getInvitation() {
		return invitation;
	}
	
	public void setInvitation(UserInvitation invitation) {
		this.invitation = invitation;
	}
}
