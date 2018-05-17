package org.gatlin.soa.sinapay.api;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;

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
	 */
	UserSecurity realname(RealnameParam param);
	
	/**
	 * 绑定银行卡
	 */
	String bankCardBind(BankCardBindParam param, String bankId, Geo geo);
	
	// 确认绑卡
	String bankCardBindConfirm(BankCardConfirmParam param);
	
	// 解绑银行卡
	String bankCardUnbind(SoaSidParam param);
	
	// 确认解绑绑卡
	void bankCardUnbindConfirm(BankCardConfirmParam param);
	
	SinaUser user(String tid, MemberType type);
}
