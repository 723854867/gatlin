package org.gatlin.soa.user.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.bean.enums.OS;

/**
 * 用户设备信息:同一个设备只能登陆一个用户
 * 
 * <pre>
 * type 和 uid 做唯一索引
 * token 做 unique 索引
 * </pre>
 * 
 * @author lynn
 */
public class UserDevice implements Identifiable<String> {

	private static final long serialVersionUID = 4745108999226672474L;

	@Id
	private String token;
	private OS os;
	private long uid;
	private Client client;
	private long username;
	private DeviceType type;
	private int created;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public long getUsername() {
		return username;
	}

	public void setUsername(long username) {
		this.username = username;
	}
	
	public DeviceType getType() {
		return type;
	}
	
	public void setType(DeviceType type) {
		this.type = type;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return this.token;
	}
}
