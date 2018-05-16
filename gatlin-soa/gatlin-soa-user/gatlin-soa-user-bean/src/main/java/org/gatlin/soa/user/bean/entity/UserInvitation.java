package org.gatlin.soa.user.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

/**
 * 用户邀请表
 * <pre>
 * invitor 和 invitee 做唯一索引
 * </pre>
 * 
 * @author lynn
 */
public class UserInvitation implements Identifiable<String> {

	private static final long serialVersionUID = 1905258522210894033L;

	@Id
	private String id;
	private long invitor;
	private long invitee;
	private int created;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public long getInvitor() {
		return invitor;
	}

	public void setInvitor(long invitor) {
		this.invitor = invitor;
	}

	public long getInvitee() {
		return invitee;
	}

	public void setInvitee(long invitee) {
		this.invitee = invitee;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return this.id;
	}
}
