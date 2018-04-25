package org.gatlin.util.bean.enums;

/**
 * 客户端类型
 * 
 * @author lynn
 */
public enum Client {

	// 浏览器
	BROWSER(1),
	// 原生(自定义)
	ORIGINAL(2); 
	
	private int mark;
	
	private Client(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final Client match(int client) {
		for (Client temp : Client.values()) {
			if (temp.mark == client)
				return temp;
		}
		return null;
	}
}
