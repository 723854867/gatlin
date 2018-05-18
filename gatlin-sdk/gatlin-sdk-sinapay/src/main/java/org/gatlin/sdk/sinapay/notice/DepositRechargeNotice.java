package org.gatlin.sdk.sinapay.notice;

import javax.validation.constraints.NotEmpty;

public class DepositRechargeNotice extends SinaNotice {

	private static final long serialVersionUID = 8070110946535018059L;

	@NotEmpty
	private String outer_trade_no;
	private String inner_trade_no;
	@NotEmpty
	private String deposit_status;
	private String deposit_amount;
	private String pay_method;

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

	public String getDeposit_status() {
		return deposit_status;
	}

	public void setDeposit_status(String deposit_status) {
		this.deposit_status = deposit_status;
	}

	public String getDeposit_amount() {
		return deposit_amount;
	}

	public void setDeposit_amount(String deposit_amount) {
		this.deposit_amount = deposit_amount;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
}
