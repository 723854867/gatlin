package org.gatlin.dao.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class User implements Identifiable<Long> {

	private static final long serialVersionUID = 1291603665465971543L;

	@Id
	@GeneratedValue
	private long userId;
	private int age;
	private String loginName;

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public Long key() {
		return this.userId;
	}
}
