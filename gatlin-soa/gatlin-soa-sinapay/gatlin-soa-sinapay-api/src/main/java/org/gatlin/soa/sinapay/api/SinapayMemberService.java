package org.gatlin.soa.sinapay.api;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;

/**
 * 新浪支付会员服务
 * 
 * @author lynn
 */
public interface SinapayMemberService {

	/**
	 * 使用UID激活新浪会员
	 * 
	 * @param tid 用户在当前应用中的唯一编号
	 * @param type 用户类型
	 * @param ip 激活时客户端ip
	 * 
	 * @return 新浪会员编号
	 */
	String activate(String tid, MemberType type, String ip);
	
	/**
	 * 用户实名认证
	 * 
	 * @param realname 真实姓名
	 * @param identity 身份证号
	 * @param ip 认证客户端ip
	 */
	void realname(String tid, String realname, String identity, String ip);
	
	SinaUser user(String tid, MemberType type);
}
