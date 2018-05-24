package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.message.WrapResponse;
import org.gatlin.sdk.sinapay.notice.CompanyAuditNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.SinaNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新浪支付通知
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay/notice")
public class SinapayNoticeController {
	
	@Resource
	private SinapayOrderService sinapayOrderService;
	@Resource
	private SinapayMemberService sinapayMemberService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setFieldMarkerPrefix("⊙");
	}

	// 充值成功通知成功执行待收
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object rechargeDeposit(@Valid DepositRechargeNotice notice) {
		SinaRecharge recharge = sinapayOrderService.noticeDepositRecharge(notice);
		RechargeState state = RechargeState.valueOf(recharge.getState());
		if (state == RechargeState.WAIT_RECALL) {				// 发起待收
			try {
				sinapayOrderService.rechargeCollect(recharge.getId(), notice);
			} catch (Exception e) {
				throw new CodeException(e);
			}
		}
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
	
	// 待收回调
	@ResponseBody
	@RequestMapping("collect")
	public Object collect(@Valid TradeNotice notice) {
		sinapayOrderService.collectNotice(notice);
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
	
	// 提现代付回调
	@ResponseBody
	@RequestMapping("withdraw/pay")
	public Object withdrawPay(@Valid TradeNotice notice) {
		sinapayOrderService.withdrawPayNotice(notice);
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
	
	// 提现回调
	@ResponseBody
	@RequestMapping("withdraw/deposit")
	public Object withdrawDeposit(@Valid WithdrawNotice notice) {
		SinaWithdraw withdraw = sinapayOrderService.withdrawNotice(notice);
		SinaWithdrawState state = SinaWithdrawState.valueOf(withdraw.getState());
		if (state == SinaWithdrawState.FAILED) {			// 提现失败：发起待收
			try {
				sinapayOrderService.withdrawCollect(withdraw.getId(), notice);
			} catch (Exception e) {
				throw new CodeException(e);
			}
		}
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
	
	// 企业认证回调
	@ResponseBody
	@RequestMapping("company/audit")
	public Object companyAudit(@Valid CompanyAuditNotice notice) {
		sinapayMemberService.companyApplyNotice(notice);
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
}
