package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.model.message.WrapResponse;
import org.gatlin.sdk.sinapay.notice.BidNotice;
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
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(SinapayNoticeController.class);
	
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
		if (recharge.getState() == RechargeState.WAIT_RECALL) {				// 发起待收
			try {
				sinapayOrderService.rechargeCollect(recharge.getId(), notice.meta().getIp());
			} catch (Exception e) {
				logger.error("充值 - {} 成功待收失败！充值成功回调 -{}", notice.getOuter_trade_no(), SerializeUtil.GSON.toJson(notice), e);
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
		if (withdraw.getState() == SinaWithdrawState.FAILED) {			// 提现失败：发起待收
			try {
				sinapayOrderService.withdrawFailure(withdraw.getId(), notice);
			} catch (Exception e) {
				logger.error("新浪提现订单 - {} 提现失败撤回失败！提现回调请求 - {}", notice.getOuter_trade_no(), SerializeUtil.GSON.toJson(notice), e);
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
	
	// 标的录入回调
	@ResponseBody
	@RequestMapping("bid")
	public Object bid(@Valid BidNotice notice) {
		sinapayOrderService.bidNotice(notice);
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
	
	// 新浪放款回调
	@ResponseBody
	@RequestMapping("loanout")
	public Object loanout(@Valid WithdrawNotice notice) {
		sinapayOrderService.loanoutNotice(notice);
		return new WrapResponse(SinaNotice.RESPONSE_OK);
	}
}
