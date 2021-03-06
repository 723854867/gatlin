package org.gatlin.soa.sinapay.api;

import java.util.List;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.model.AccountMiddleTips;
import org.gatlin.sdk.sinapay.notice.CompanyAuditNotice;
import org.gatlin.sdk.sinapay.response.QueryTradeRelatedResponse;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaCompanyAudit;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.model.BalanceInfo;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyBankCardModifyParam;
import org.gatlin.soa.sinapay.bean.param.QueryBalanceParam;
import org.gatlin.soa.user.bean.entity.Company;
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
	 * 用户实名认证
	 */
	UserSecurity realname(RealnameParam param);
	
	/**
	 * 绑定银行卡
	 */
	String bankCardBind(BankCardBindParam param, String bankId, Geo geo);
	
	void bankCardBindTimeout(String id);
	
	// 确认绑卡
	String bankCardBindConfirm(BankCardConfirmParam param);
	
	// 解绑银行卡
	String bankCardUnbind(SoaSidParam param);
	
	// 确认解绑绑卡
	void bankCardUnbindConfirm(BankCardConfirmParam param);

	Pager<SinaBankCard> bankCards(Query query);
	
	SinaUser member(String id);
	
	SinaUser user(Object tid, MemberType type);
	
	boolean isWithhold(MemberType type, Object tid);
	
	String withhold(SoaParam param);
	
	BalanceInfo queryBalance(QueryBalanceParam param);
	
	List<AccountMiddleTips> queryBalanceMiddle();
	
	void companyApply(CompanyApplyParam param, Company company);
	
	void companyApplyNotice(CompanyAuditNotice notice);
	
	void companyBankCardModify(CompanyBankCardModifyParam param); 
	
	String pwdReset(SoaParam param);
	
	String bankCardMobileModify(BankCardMobileModifyParam param);
	
	void bankCardMobileModifyConfirm(BankCardMobileModifyConfirmParam param);
	
	SinaCompanyAudit companyAudit(int companyId);
	
	String pwdSet(SoaParam param, MemberType type, Object tid);
	
	boolean queryPwdSet(MemberType type, Object tid);
	
	QueryTradeRelatedResponse queryRelative(String id);
}
