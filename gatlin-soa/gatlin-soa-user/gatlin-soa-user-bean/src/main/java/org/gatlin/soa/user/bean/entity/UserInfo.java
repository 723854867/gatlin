package org.gatlin.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

/**
 * 用户基本信息
 * 
 * @author lynn
 */
public class UserInfo implements Identifiable<Long> {

	private static final long serialVersionUID = 607168546448230962L;

	@Id
	@GeneratedValue
	private long id;
	private int mod;
	private String pwd;
	private String salt;
	private String nickname;
	private long riskTypeId;
	private int created;
	private int updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public long getRiskTypeId() {
		return riskTypeId;
	}

	public void setRiskTypeId(long riskTypeId) {
		this.riskTypeId = riskTypeId;
	}

	@Override
	public Long key() {
		return id;
	}
}
