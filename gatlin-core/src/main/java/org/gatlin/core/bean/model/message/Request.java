package org.gatlin.core.bean.model.message;

import org.gatlin.core.bean.entity.LogRequest;

/**
 * 请求
 * 
 * @author lynn
 */
public interface Request extends Message {
	
	void dispose();
	
	void init(LogRequest meta, Object... attaches);
	
	/**
	 * 基本的参数验证，返回错误描述，如果返回为 null 则表示验证成功
	 */
	default void verify() {
	}
}
