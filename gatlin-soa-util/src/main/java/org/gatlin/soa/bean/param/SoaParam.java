package org.gatlin.soa.bean.param;

import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.dao.bean.Searcher;
import org.gatlin.soa.bean.User;

public class SoaParam extends Searcher {

	private static final long serialVersionUID = -4443407529395114149L;

	private User user;
	private String token;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void init(LogRequest meta, Object... attaches) {
		super.init(meta, attaches);
		int idx = -1;
		int max = attaches.length;
		while (++idx < max) {
			Object attach = attaches[idx];
			if (null == attach)
				continue;
			if (idx == 0) 
				this.token = attach.toString();
			else if (idx == 1)
				this.user = (User) attach;
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.user = null;
	}
}
