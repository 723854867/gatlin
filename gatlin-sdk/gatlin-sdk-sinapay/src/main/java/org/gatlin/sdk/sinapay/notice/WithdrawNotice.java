package org.gatlin.sdk.sinapay.notice;

import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;

public class WithdrawNotice extends SinaNotice {

	private static final long serialVersionUID = 4437089553046489251L;

	private String card_id;
	private String outer_trade_no;
	private String inner_trade_no;
	private String withdraw_amount;
	private WithdrawState withdraw_status;

	public String getOuter_trade_no() {
		return outer_trade_no;
	}

	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}

	public String getInner_trade_no() {
		return inner_trade_no;
	}

	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}

	public String getWithdraw_amount() {
		return withdraw_amount;
	}

	public void setWithdraw_amount(String withdraw_amount) {
		this.withdraw_amount = withdraw_amount;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	public WithdrawState getWithdraw_status() {
		return withdraw_status;
	}
	
	public void setWithdraw_status(WithdrawState withdraw_status) {
		this.withdraw_status = withdraw_status;
	}
}
